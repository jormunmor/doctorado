/*
	This class implements an actionlib server to communicate
	with the arcas_exec_layer GUI. By the moment it is
	a fake server. It increments the goal pose.position.z a
	certain amount.
*/

#include <iostream>
#include <ros/ros.h>
#include <actionlib/server/simple_action_server.h>
#include <arcas_exec_layer/ArcasExecLayerAction.h>
#include <string>
#include "constants.h"

// Moveit - move group interface classes
#include <moveit/move_group_interface/move_group.h>
#include <moveit/planning_scene_interface/planning_scene_interface.h>
// Moveit- debug publishers
#include <moveit_msgs/DisplayRobotState.h>
#include <moveit_msgs/DisplayTrajectory.h>
// Moveit - planning scene collision objects classes
#include <moveit_msgs/AttachedCollisionObject.h>
#include <moveit_msgs/CollisionObject.h>
// Mesh class definitions
#include <geometric_shapes/shape_operations.h>
// Quadrotor action clients (motion_action_handler) to land and take-off
#include <motion_action_handler/actions/land_action_client.h>
#include <motion_action_handler/actions/takeoff_action_client.h>
// Arm action client (motion_action_handler) to extend arm
#include <motion_action_handler/actions/extend_arm_action_client.h>
// Topic: Quad position references
#include <arcas_msgs/QuadControlReferencesStamped.h>
// Topic: Quad position estimation
#include <arcas_msgs/QuadStateEstimationWithCovarianceStamped.h>
// Topic: Arm position estimation
#include <arcas_msgs/ArmStateEstimationStamped.h>

class BonebrakerServer;
typedef struct {
	BonebrakerServer* serverObject;
	arcas_exec_layer::ArcasExecLayerGoalConstPtr goal;
	bool actionFinished;
	bool actionSuccess;

} ActionThreadData;

/// TODO: avoid defining the whole class in a single .cpp file. Separate it in .h and .cpp files.

class BonebrakerServer
{
	protected:

		ros::NodeHandle nh;
		// NodeHandle instance must be created before this line. Otherwise strange error may occur.
		actionlib::SimpleActionServer<arcas_exec_layer::ArcasExecLayerAction> as; 
		std::string action_name_;
		ros::AsyncSpinner *spinner;
		ros::Publisher quad_control_ref_pub;
		ros::Subscriber quad_state_estimation_sub; // quad subscriber
		ros::Subscriber sub_pose_arm;	// arm subscriber
		arcas_msgs::QuadControlReferencesStamped quad_control_ref;
		arcas_msgs::QuadStateEstimationWithCovarianceStamped quad_state_estimation;
		arcas_msgs::ArmStateEstimation arm_state;
		bool positionInitialized;

		// Action client wrappers.
		TakeOffActionWrapper *takeoffActionclient;
		AALExtensionActionWrapper aalExtensionActionWrapper;

		// Create messages that are used to published feedback/result.
		arcas_exec_layer::ArcasExecLayerFeedback feedback;
		arcas_exec_layer::ArcasExecLayerResult result;

		// move_group interface objects
		moveit::planning_interface::MoveGroup *uav_group;
		moveit::planning_interface::MoveGroup *arm_group;
		moveit::planning_interface::MoveGroup *gripper_group;
		moveit::planning_interface::PlanningSceneInterface *planning_scene_interface;

	public:

	BonebrakerServer(std::string name) :
		as(nh, name, boost::bind(&BonebrakerServer::executeCB, this, _1), false), action_name_(name)
	{
		as.start();
		spinner = new ros::AsyncSpinner(1);
		spinner->start();

		// quad control reference publisher
		quad_control_ref_pub = nh.advertise<arcas_msgs::QuadControlReferencesStamped>("/ual_1/quad_control_references", 1);

	  
		// This sleep is ONLY to allow Rviz to come up
		sleep(5.0);

		//! Bonebraker takeoff and land actions 
		positionInitialized = false;

		// Topic subscriber to get the quad position.
		quad_state_estimation_sub = nh.subscribe("/ual_1/quad_state_estimation", 1, &BonebrakerServer::quadStateEstimationCallback, this);

		// Topic subscriber to get the arm position.
		sub_pose_arm = nh.subscribe("/aal_1/arm_state_estimation", 1, &BonebrakerServer::armStateEstimationCallback, this);

		printf("Waiting for initial position...");
		while(!positionInitialized)
		{
			sleep(1);
			printf(".");
		}
		printf(" [OK]\n");

		// Send actual pos estimation as ref
		quad_control_ref.quad_control_references.position_ref.x = quad_state_estimation.quad_state_estimation_with_covariance.position.x;
		quad_control_ref.quad_control_references.position_ref.y = quad_state_estimation.quad_state_estimation_with_covariance.position.y;
		quad_control_ref.quad_control_references.position_ref.z = 1.0;
		quad_control_ref.quad_control_references.heading = quad_state_estimation.quad_state_estimation_with_covariance.attitude.yaw;
		quad_control_ref.quad_control_references.velocity_ref = 1.0; // it should be high because a little velocity sature so much the controller
		quad_control_ref_pub.publish(quad_control_ref);

		// Instantiate action clients
		// TAKEOFF client
		takeoffActionclient = new TakeOffActionWrapper("ual_1");
		while(!takeoffActionclient->waitForServer())
		{
			ROS_WARN("TakeOffAction server is unresponsive. Retrying...");
		}
		
		// MOVE action
		uav_group = new moveit::planning_interface::MoveGroup("quadrotor");
		arm_group = new moveit::planning_interface::MoveGroup("arm");
		gripper_group = new moveit::planning_interface::MoveGroup("gripper");
		planning_scene_interface = new moveit::planning_interface::PlanningSceneInterface();	

				
		// Set the workspace bounding box. The box is specified in the planning frame 
		arm_group->setWorkspace(-8.0, -8.0, 0.0, 8.0, 8.0, 3.0);	
		uav_group->setWorkspace(-8.0, -8.0, 0.0, 8.0, 8.0, 3.0);
		// Set Planning time: maximum amount of time to use when planning
		arm_group->setPlanningTime(10.0);
		uav_group->setPlanningTime(10.0);
		// Set Planning attempts: the number of times the motion plan is to be computed from scratch before the shortest solution is returned
		arm_group->setNumPlanningAttempts(10); ///TODO: check the MAX_PLANNING_ATTEMPTS constants: are necessary?
		uav_group->setNumPlanningAttempts(10);
		// Set the uav tolerance
		uav_group->setGoalOrientationTolerance(0.001);
		uav_group->setGoalPositionTolerance(0.01);

		// Adding a MESH collision object to the planning scene
		// First, we will define the collision object message.
		moveit_msgs::CollisionObject collision_object_;
		collision_object_.header.frame_id = uav_group->getPlanningFrame();
		// The id of the object is used to identify it
		collision_object_.id = "mesh";
		// Define the mesh to add to the world
		shapes::Mesh* m = shapes::createMeshFromResource("package://bonebraker_moveit/pipes.dae");
		shape_msgs::Mesh co_mesh;
		shapes::ShapeMsg co_mesh_msg;
		shapes::constructMsgFromShape(m,co_mesh_msg);
		co_mesh = boost::get<shape_msgs::Mesh>(co_mesh_msg);
		collision_object_.meshes.push_back(co_mesh);
		geometry_msgs::Pose mesh_pose;
		mesh_pose.orientation.w = 1.0;
		mesh_pose.position.x =  0.0;
		mesh_pose.position.y = 0.0;
		mesh_pose.position.z =  0.0;
		collision_object_.mesh_poses.push_back(mesh_pose);
		collision_object_.operation = collision_object_.ADD;
		std::vector<moveit_msgs::CollisionObject> collision_objects_;  
		collision_objects_.push_back(collision_object_);  

		// Now, let's add the collision object into the world
		planning_scene_interface->addCollisionObjects(collision_objects_);

		// Path constraints for the UAV (not roll and pitch for planning)
		moveit_msgs::OrientationConstraint ocm;  
		ocm.link_name = "base_link";  
		ocm.header.frame_id = "world";
		ocm.orientation.w = 1.0;
		ocm.absolute_x_axis_tolerance = 0.1;
		ocm.absolute_y_axis_tolerance = 0.1;
		ocm.absolute_z_axis_tolerance = 2.0 * M_PI;
		ocm.weight = 1.0;

		// Now, set it as the path constraint for the group.
		moveit_msgs::Constraints uav_constraints;
		uav_constraints.orientation_constraints.push_back(ocm);  
		uav_group->setPathConstraints(uav_constraints);


  

	}

	bool executeAction(const arcas_exec_layer::ArcasExecLayerGoalConstPtr &goal)
	{
		bool success = false;
		switch(goal->actionType)
		{
			case TAKEOFF:
				success = executeTAKEOFF(goal);				 
				break;
			case MOVE: 
				success = executeMOVE(goal);
				break;
			case PICK: 
				success = executePICK(goal);
				break;

		}

		return success;
		

	}

	~BonebrakerServer()
	{
		delete spinner;
		delete takeoffActionclient;
		delete uav_group;
		delete arm_group;
		delete gripper_group;
		delete planning_scene_interface;

	}

	private:

	void executeCB(const arcas_exec_layer::ArcasExecLayerGoalConstPtr &goal)
	{
		ActionThreadData threadData;
		threadData.goal = goal;
		threadData.actionFinished = false;
		threadData.actionSuccess = false;
		threadData.serverObject = this;

		pthread_t t;
		int rc = pthread_create(&t, NULL, &BonebrakerServer::threadFunction, &threadData);
		if(rc)
		{
        	ROS_INFO("Unable to create execution thread.");
			as.setAborted(result);
			return;

		}

		while(!threadData.actionFinished)
		{
			setFeedbackMsg();
			as.publishFeedback(feedback);
			ros::Duration(0.01).sleep(); // sleep 10 millisecons

		}

		setFeedbackMsg();
		as.publishFeedback(feedback);

		if(threadData.actionSuccess)
		{
			as.setSucceeded(result);

		}
		else
		{
			as.setAborted(result);

		}

	}

	static void *threadFunction(void *context)
	{
		ActionThreadData* threadData = (ActionThreadData*) context;
		bool success = threadData->serverObject->executeAction(threadData->goal);				
		threadData->actionSuccess = success;
		threadData->actionFinished = true;

	}	

	/*
		This function sets the feedback msg to have the current pose of the vehicle.
	*/
	void setFeedbackMsg()
	{
		// Set the feedback message position to the current position
		feedback.pose.position.x = quad_state_estimation.quad_state_estimation_with_covariance.position.x;
		feedback.pose.position.y = quad_state_estimation.quad_state_estimation_with_covariance.position.y;
		feedback.pose.position.z = quad_state_estimation.quad_state_estimation_with_covariance.position.z;
		//ROS_INFO("Current height: %lf", feedback.pose.position.z);
		// Set the feedback message orientation to the current orientation
		tfScalar roll = quad_state_estimation.quad_state_estimation_with_covariance.attitude.roll;
		tfScalar pitch = quad_state_estimation.quad_state_estimation_with_covariance.attitude.pitch;
		tfScalar yaw = quad_state_estimation.quad_state_estimation_with_covariance.attitude.yaw;
		tf::Quaternion orientation;
		orientation.setRPY(roll, pitch, yaw);
		feedback.pose.orientation.x = orientation.x();
		feedback.pose.orientation.y = orientation.y();
		feedback.pose.orientation.z = orientation.z();
		feedback.pose.orientation.w = orientation.w();		

	}

	/// TODO: take a look at the TakeOffActionWrapper implementation, it has
	/// tOff_Active_CB, tOff_Feedback_CB and tOff_Done_CB unused. Can be useful.	
	bool executeTAKEOFF(const arcas_exec_layer::ArcasExecLayerGoalConstPtr &goal)
	{
		ROS_INFO("TAKEOFF Goal arrived.");
		// The first feedback will have the pose before taking off.
		//setFeedbackMsg();
		//as.publishFeedback(feedback);
		takeoffActionclient->takeOff(); /// TODO: the TAKEOFF height is set as a constant in Control/ual/include/ual/ualhectorgazebo.h
		//setFeedbackMsg();
		//as.publishFeedback(feedback);
		ros::Rate loop_rate(30); // loop at 10 Hz
		while(takeoffActionclient->hasGoalRunning())
		{	
			// Publish the feedback and loop
			//setFeedbackMsg();
			//as.publishFeedback(feedback);		
			loop_rate.sleep();	

		}
		//ros::Duration(5).sleep();
		ROS_INFO("TAKEOFF Action succeeded!");
		//setFeedbackMsg();
		//as.publishFeedback(feedback);
		//result.pose = feedback.pose; // Set the result with the current feedback
		//as.setSucceeded(result);
		return true;

	}

	bool executeMOVE(const arcas_exec_layer::ArcasExecLayerGoalConstPtr &goal)
	{
		// Publish some feedback
		//as.publishFeedback(feedback);
		ROS_INFO("MOVE Goal arrived.");

		// Stabilize the vehicle
		stabilize();

		// Planning to a joint-space goal to the uav
		// Set planning start pose
		uav_group->setStartStateToCurrentState();

		// First get the current set of joint values for the group.
		std::vector<double> group_variable_values;
		uav_group->getCurrentState()->copyJointGroupPositions(uav_group->getCurrentState()->getRobotModel()->getJointModelGroup(uav_group->getName()), group_variable_values);

		// Set the goal pose
		group_variable_values[0] = goal->pose.position.x;		// X [m]
		group_variable_values[1] = goal->pose.position.y;		// Y [m]
		group_variable_values[2] = goal->pose.position.z;		// Z [m]
		// The orientation is done by converting roll, pitch and yaw to quaternion,
		// to avoid problems derived from loosing decimals.
		tf::Quaternion q;
		q.setRPY(0.0, 0.0, goal->yaw);

		group_variable_values[3] = q.x();	// qx
		group_variable_values[4] = q.y();	// qy
		group_variable_values[5] = q.z();	// qz
		group_variable_values[6] = q.w();	// qw

		ROS_INFO("Moving to position (%lf, %lf, %lf)", goal->pose.position.x, goal->pose.position.y, goal->pose.position.z);
		ROS_INFO("Moving to orientation (%lf, %lf, %lf, %lf)", q.x(), q.y(), q.z(), q.w());

		// Publish some feedback
		//as.publishFeedback(feedback);

		// Plan for this group
		moveit::planning_interface::MoveGroup::Plan uav_plan;
		uav_group->setJointValueTarget(group_variable_values);
		bool uav_success = uav_group->plan(uav_plan);

		// Publish some feedback
		//as.publishFeedback(feedback);

		// Moving the uav to a pose goal
		if(uav_success)
		{
			ROS_INFO("Planning phase succeeded.");
			// Request to execute (BLOCKING!!) the plan
			uav_success = uav_group->execute(uav_plan);
			// Check the result
			if(!uav_success)
			{
				ROS_INFO("MOVE Action aborted. Execute phase failed!.");
				//as.setAborted(result);
				return false;

			}

		} 
		else
		{
			ROS_INFO("MOVE Action aborted. Planning phase failed!.");
			//as.setAborted(result);
			return false;
		}
	
		ROS_INFO("MOVE Action succeeded!.");
		//as.setSucceeded(result);
		return true;

	}

	bool executePICK(const arcas_exec_layer::ArcasExecLayerGoalConstPtr &goal)
	{
		ROS_INFO("PICK Goal arrived.");		

		// Publish some feedback
		//as.publishFeedback(feedback);
		
		// Stabilize before extending the arm, not really needed, but doesn't hurt
		stabilize();

		// Extend the arm
		if(!armExtend())
		{
			//as.setAborted(result);
			return false;

		}

		// Publish some feedback
		//as.publishFeedback(feedback);

		// Stabilize after extending the arm, REALLY NEEDED
		stabilize();

		// Move the arm to pick pose
		if(!armToPickPose())
		{
			//as.setAborted(result);
			return false;

		}

		// Publish some feedback
		//as.publishFeedback(feedback);

		// Stabilize before opening the gripper
		stabilize();

		// Open the gripper
		if(!armGripperInteraction(true))
		{
			//as.setAborted(result);
			return false;

		}

		// Publish some feedback
		//as.publishFeedback(feedback);

		// Stabilize before moving
		stabilize();

		// Uav to grasp position
		bool down = true;
		if(!vehicleToPosition(down))
		{
			//as.setAborted(result);
			return false;

		}

		// Publish some feedback
		//as.publishFeedback(feedback);

		// Stabilize before closing the gripper
		stabilize();

		// Close the gripper
		if(!armGripperInteraction(false))
		{
			//as.setAborted(result);
			return false;

		}

		// Vehicle rise up
		if(!vehicleToPosition(!down))
		{
			//as.setAborted(result);
			return false;

		}

		// Publish some feedback
		//as.publishFeedback(feedback);

		ROS_INFO("PICK Action succeeded!.");
		return true;		
		//as.setSucceeded(result);


	}

	void quadStateEstimationCallback(const arcas_msgs::QuadStateEstimationWithCovarianceStampedConstPtr &st)
	{
	   quad_state_estimation = *st;
	   positionInitialized = true;

	}

	void armStateEstimationCallback(const arcas_msgs::ArmStateEstimationStamped &arm)
	{
		arm_state = arm.arm_state_estimation;

	}

	/// TODO: look the case of the robotic arm failing at extending
	bool armExtend()
	{
		ROS_INFO("Extending the arm.");
		aalExtensionActionWrapper.extendArm();
		while(aalExtensionActionWrapper.hasGoalRunning()) 
		{
		  sleep(10);

		}
		ROS_INFO("Arm extended.");

		return true;

	}

	bool armToPickPose()
	{
		geometry_msgs::Pose arm_target_pose;
		moveit::planning_interface::MoveGroup::Plan arm_plan;
		double barX = quad_state_estimation.quad_state_estimation_with_covariance.position.x;
		double barY = quad_state_estimation.quad_state_estimation_with_covariance.position.y;
		double barZ = quad_state_estimation.quad_state_estimation_with_covariance.position.z - PICK_PART_Z_OFFSET;
		bool arm_success = false;
		int arm_planning_attempts = 0;
		while(!arm_success && (arm_planning_attempts <= ARM_MAX_PLANNING_ATTEMPTS))
		{
			// Set planning start pose
			arm_group->setStartStateToCurrentState();

			tfScalar roll = 0.0;
			tfScalar pitch = M_PI/2.0; // Inicialmente el gripper esta como para coger algo vertical. Dandole un pitch de pi/2 seria para coger algo en horizontal
			tfScalar yaw = M_PI/4.0 - 3.0*M_PI/4.0; // Los pi/4 de por defecto menos pi/2 que esta girado actualmente el quad
			tf::Quaternion q;
			q.setRPY(roll, pitch, yaw);

			arm_target_pose.orientation.x = q.x();
			arm_target_pose.orientation.y = q.y();
			arm_target_pose.orientation.z = q.z();
			arm_target_pose.orientation.w = q.w();
			arm_target_pose.position.x = barX + ARM_APROX_POS_dX;  
			arm_target_pose.position.y = barY + ARM_APROX_POS_dY;
			arm_target_pose.position.z = barZ + ARM_APROX_POS_dZ;
			arm_group->setPoseTarget(arm_target_pose);

			// Plan for this group
			arm_success = arm_group->plan(arm_plan);
			arm_planning_attempts++;

		}

		//! Moving the arm to a pose goal
		//! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		if(arm_success)
		{
			ROS_INFO("Arm planning phase to pick pose SUCCEEDED.");
			arm_success = arm_group->execute(arm_plan);
			if(!arm_success)
			{
				ROS_INFO("PICK Action aborted. Execute phase FAILED.");
				return false;

			}

		}
		else
		{
		  ROS_INFO("Arm planning phase to pick pose FAILED.");
		  return false;

		}
	
		return true;

	}

	bool armGripperInteraction(bool open)
	{
		bool gripper_success = false;
		int gripper_planning_attempts = 0;
		std::vector<double> gripper_group_target_values;
		moveit::planning_interface::MoveGroup::Plan gripper_plan;
		while(!gripper_success && gripper_planning_attempts <= GRIPPER_MAX_PLANNING_ATTEMPTS)
		{
		  // Set planning start pose
		  gripper_group->setStartStateToCurrentState();
		  
		  // First get the current set of joint values for the group.
		  gripper_group->getCurrentState()->copyJointGroupPositions(gripper_group->getCurrentState()->getRobotModel()->getJointModelGroup(gripper_group->getName()), gripper_group_target_values);

		  // Set the goal pose (open)
		  gripper_group_target_values[0] = (open)? 0.79 : 0;
		  gripper_group_target_values[1] = (open)? -0.79 : 0;

		  // Plan for this group
		  gripper_group->setJointValueTarget(gripper_group_target_values);
		  gripper_success = gripper_group->plan(gripper_plan);
		  gripper_planning_attempts++;

		}

		if(gripper_success)
		{
			ROS_INFO("Gripper planning phase SUCCEEDED.");
			gripper_success = gripper_group->execute(gripper_plan);
			if(!gripper_success)
			{
				ROS_INFO("PICK Action aborted. Execute phase FAILED.");    
				return false;

			}

		}
		else
		{
			ROS_INFO("Gripper planning phase FAILED.");
			return false;

		}		

		return true;

	}

	/*
		bool down: - true to aprox the vehicle to the part to pick it
					- false to rise up the vehicle after picking the part
	*/
	/// TODO: instead of receiving a boolean, an enum could be better, more descriptive and let
	/// define other types of movements when picking/placing
	bool vehicleToPosition(bool down)
	{
		bool uav_success = false;
		int uav_planning_attempts = 0;
		moveit::planning_interface::MoveGroup::Plan uav_plan;
		std::vector<double> uav_group_target_values;
		double vehicleX = quad_state_estimation.quad_state_estimation_with_covariance.position.x;
		double vehicleY = quad_state_estimation.quad_state_estimation_with_covariance.position.y;
		double vehicleZ = quad_state_estimation.quad_state_estimation_with_covariance.position.z;
		while(!uav_success && (uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS))
		{
			// Set planning start pose
			uav_group->setStartStateToCurrentState();

			// First get the current set of joint values for the group.
			uav_group->getCurrentState()->copyJointGroupPositions(uav_group->getCurrentState()->getRobotModel()->getJointModelGroup(uav_group->getName()), uav_group_target_values);

			// Set the goal pose = position + orientation
			tfScalar roll = 0.0;
			tfScalar pitch = 0.0;
			//tfScalar yaw = PICK_BAR_POS_YAW + UAV_APROX_POS_dYAW;
			tfScalar yaw = quad_state_estimation.quad_state_estimation_with_covariance.attitude.yaw; // The quad orientation is done in the move -> mantain the current yaw
			tf::Quaternion q;
			q.setRPY(roll, pitch, yaw);
			uav_group_target_values[0] = vehicleX;		// X [m]
			uav_group_target_values[1] = vehicleY;		// Y [m]
			if(down)
			{
				uav_group_target_values[2] = vehicleZ - ARM_APROX_POS_dZ + ARM_PICK_POS_dZ;		// aprox to pick

			}
			else
			{
				uav_group_target_values[2] = vehicleZ + ARM_APROX_POS_dZ - ARM_PICK_POS_dZ;		// rise up after pick

			}
			uav_group_target_values[3] = q.x();		// qx
			uav_group_target_values[4] = q.y();		// qy
			uav_group_target_values[5] = q.z();		// qz
			uav_group_target_values[6] = q.w();		// qw

			// Plan for this group
			uav_group->setJointValueTarget(uav_group_target_values);
			uav_success = uav_group->plan(uav_plan);
			uav_planning_attempts++;

		}

		if(uav_success)
		{
			string succeededMsg = (down)? "Vehicle to grasp position planning phase SUCCEEDED." : "Vehicle rise up planning phase SUCCEEDED.";
			ROS_INFO(succeededMsg.c_str());
			uav_success = uav_group->execute(uav_plan);
			if(!uav_success)
			{
				ROS_INFO("PICK Action aborted. Execute phase FAILED.");    
				return false;

			}

		}
		else
		{
			string failedMsg = (down)? "Vehicle to grasp position planning phase FAILED." : "Vehicle rise up planning phase FAILED.";
			ROS_INFO(failedMsg.c_str());
			return false;

		}

		return true;		

	}

	void stabilize()
	{
		// Wait to UAV stabilization
		int stabilization_index = 0;
		ROS_INFO("Waiting for UAV stabilization...");
		while(stabilization_index <= UAV_STABILIZATION_COUNT)
		{
			if(checkUavStabilization()) // If stabilized, increment the counter
			{
				stabilization_index++;

			}
			else
			{
				stabilization_index = 0; // reset the counter, we want 100 times one after each other 
			}

			ros::Duration(0.01).sleep(); // sleep 10 millisecons
	
		}
		ROS_INFO("...[OK]");	

	}

	bool checkUavStabilization()
	{
		double roll = quad_state_estimation.quad_state_estimation_with_covariance.attitude.roll;
		double pitch = quad_state_estimation.quad_state_estimation_with_covariance.attitude.pitch;
	
		if(fabs(roll) <= 0.002 && fabs(pitch) <= 0.002)
		{
			return true; // UAV is stabilized

		}

		return false; // UAV is not stabilized

	}

};


int main(int argc, char** argv)
{
	if(argc <= 1)
	{
		ROS_INFO("Invalid number of arguments. Vehicle ID is required. Exiting...");
	}	

	std::string nodeName = std::string("VehicleServer") + std::string(argv[1]);
	ros::init(argc, argv, nodeName);

	BonebrakerServer server(ros::this_node::getName());
	std::cout << nodeName << " started." << std::endl;	
	while(ros::ok())
	{
		ros::Duration(1).sleep();
	}
	//ros::spin();

	// Get here only after pressing CTRL+C.
	std::cout << "Exiting " << nodeName << "..." << std::endl;

	return 0;
}
