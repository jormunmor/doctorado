#include <ros/ros.h>
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

// Pick bar pose parameters
#define PICK_BAR_POS_X 					-7.0			// bar_center
#define PICK_BAR_POS_Y 					0.0
#define PICK_BAR_POS_Z 					1.10
#define PICK_BAR_POS_YAW 				M_PI

// Place bases pose parameters
#define PLACE_BASES_POS_X 				6.0			// center between bases
#define PLACE_BASES_POS_Y 				5.0
#define PLACE_BASES_POS_Z 				1.10
#define PLACE_BASES_POS_YAW 			0.0

// UAV increment pos aproximation with respect the pick bar pos
#define UAV_APROX_POS_dX 				0.0	// base_link WRT bar_center
#define UAV_APROX_POS_dY 				0.0
#define UAV_APROX_POS_dZ 				0.75
#define UAV_APROX_POS_dYAW 				-M_PI/4.0

// Arm increment pos aproximation with respect the pick bar pos
#define ARM_APROX_POS_dX 				0.0	// wirst_1 WRT bar_center
#define ARM_APROX_POS_dY 				0.0
#define ARM_APROX_POS_dZ 				0.25
#define ARM_APROX_POS_dYAW 				0.0//NOT USED
#define ARM_PICK_POS_dZ 				0.060

// Count to set the UAV as stabilizated (1 seg if quad_state topic is at 100 Hz)
#define UAV_STABILIZATION_COUNT 		100

// Max planning attempts
#define UAV_MAX_PLANNING_ATTEMPTS 		10
#define ARM_MAX_PLANNING_ATTEMPTS 		10
#define GRIPPER_MAX_PLANNING_ATTEMPTS 	10

//! Callback to get the current state of the quadrotor
bool positionInitialized;
arcas_msgs::QuadStateEstimationWithCovarianceStamped quad_state_estimation;
void quadStateEstimationCallback(const arcas_msgs::QuadStateEstimationWithCovarianceStampedConstPtr &st)
{
   quad_state_estimation = *st;
   positionInitialized = true;
   //~ ROS_INFO("Reading position!!");
}

//! Callback to get the current state of the arm
arcas_msgs::ArmStateEstimation arm_state;
void armStateEstimationCallback(const arcas_msgs::ArmStateEstimationStamped& arm)
{
	arm_state = arm.arm_state_estimation;
	//~ ROS_INFO("Reading position!!");
}

//! Check if the UAV is stabilizated (This function must be not blocking because the topic must be read)
bool checkUavStabilization()
{
	double roll = quad_state_estimation.quad_state_estimation_with_covariance.attitude.roll;
	double pitch = quad_state_estimation.quad_state_estimation_with_covariance.attitude.roll;
	
	if(fabs(roll) <= 0.002 && fabs(pitch) <= 0.002)
		return true;
	else
		return false;
}

int main(int argc, char **argv)
{
  ros::init(argc, argv, "bonebraker_moveit_interface");
  ros::NodeHandle node_handle;  
  ros::AsyncSpinner spinner(1);
  spinner.start();

  // quad control reference publisher
  ros::Publisher quad_control_ref_pub = node_handle.advertise<arcas_msgs::QuadControlReferencesStamped>("/ual_1/quad_control_references", 1);
  arcas_msgs::QuadControlReferencesStamped quad_control_ref;
  
  // This sleep is ONLY to allow Rviz to come up
  sleep(5.0);

    
  //! Bonebraker takeoff and land actions 
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  positionInitialized = false;
  // Topic subscriber to get the quad position. Only for read the first position to takeoff
  ros::Subscriber quad_state_estimation_sub = node_handle.subscribe("/ual_1/quad_state_estimation", 1, &quadStateEstimationCallback);

  printf("Waiting to intial position...");
  fflush(stdout);
  while(!positionInitialized)
  {
	  //~ ros::spinOnce();
	  sleep(1);
	  printf(".");
	  fflush(stdout);
  }
  printf(" [OK]\n");
  
  // Send actual pos estimation as ref
  quad_control_ref.quad_control_references.position_ref.x = quad_state_estimation.quad_state_estimation_with_covariance.position.x;
  quad_control_ref.quad_control_references.position_ref.y = quad_state_estimation.quad_state_estimation_with_covariance.position.y;
  quad_control_ref.quad_control_references.position_ref.z = 1.0;
  quad_control_ref.quad_control_references.heading = quad_state_estimation.quad_state_estimation_with_covariance.attitude.yaw;
  quad_control_ref.quad_control_references.velocity_ref = 1.0; // it should be high because a little velocity sature so much the controller
  quad_control_ref_pub.publish(quad_control_ref);

  // Taking-off ActionClient
  TakeOffActionWrapper takeoffActionclient("ual_1");
  sleep(3); // Needed between initialization and using it
  ROS_INFO_STREAM("Take-Off Action Sent");
  takeoffActionclient.takeOff();
  sleep(2);
	
  // Land ActionClient (Not used here)
  LandActionWrapper landActionClient("ual_1");

  
  //! MoveGroup and PlanningScene Setup
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // The :move_group_interface:`MoveGroup` class can be easily setup using just the name of the group you would like to control and plan for.
  moveit::planning_interface::MoveGroup uav_group("quadrotor");
  moveit::planning_interface::MoveGroup arm_group("arm");
  moveit::planning_interface::MoveGroup gripper_group("gripper");

  // Moveit planning Plan Objects 
  moveit::planning_interface::MoveGroup::Plan uav_plan;
  std::vector<double> uav_group_target_values;
  moveit::planning_interface::MoveGroup::Plan arm_plan;
  geometry_msgs::Pose arm_target_pose_;
  moveit::planning_interface::MoveGroup::Plan gripper_plan;
  std::vector<double> gripper_group_target_values;

  // We will use the :planning_scene_interface:`PlanningSceneInterface class to deal directly with the world.
  moveit::planning_interface::PlanningSceneInterface planning_scene_interface;  

  //! Configuring the Planning
  //! ^^^^^^^^^^^^^^^^^^^^^^^^
  // Set the workspace bounding box. The box is specified in the planning frame 
  arm_group.setWorkspace(-8.0, -8.0, 0.0, 8.0, 8.0, 3.0);				// The best option woulld be set the origin = uav position and reduced the bounding box size, but there isn't a origen function
  uav_group.setWorkspace(-8.0, -8.0, 0.0, 8.0, 8.0, 3.0);				// ¿QUIZAS VARIANDO EL PLANNING REFERENCE? setPoseTarget() es en el referenceFrame, asi que no le afectaria... PROBAR!!!
  // Set Planning time: maximum amount of time to use when planning
  arm_group.setPlanningTime(10.0);
  uav_group.setPlanningTime(10.0);
  // Set Planning attempts: the number of times the motion plan is to be computed from scratch before the shortest solution is returned
  arm_group.setNumPlanningAttempts(10);
  uav_group.setNumPlanningAttempts(10);
  
  // Set the uav tolerance
  uav_group.setGoalOrientationTolerance(0.001);
  uav_group.setGoalPositionTolerance(0.01);
  
  
  //! Configuring the Planning Scene
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  /* Sleep to let planning scene to be loaded*/
  sleep(2.0);
  
  // Adding a MESH collision object to the planning scene
  // 
  // First, we will define the collision object message.
  moveit_msgs::CollisionObject collision_object_;
  collision_object_.header.frame_id = uav_group.getPlanningFrame();
  
  // The id of the object is used to identify it
  collision_object_.id = "mesh";
  
  // Define the mesh to add to the world
  //~ shapes::Mesh* m = shapes::createMeshFromResource("package://bonebraker_moveit/pipes.dae");
  shapes::Mesh* m = shapes::createMeshFromResource("package://bonebraker_moveit/pipes_table.stl");
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
  ROS_INFO("Add a MESH into the world");  
  planning_scene_interface.addCollisionObjects(collision_objects_);

  // Sleep so we have time to see the object in RViz
  sleep(2.0);
  
  //! Path constraints for the UAV (not roll and pitch for planning)
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
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
  uav_group.setPathConstraints(uav_constraints);


/*******************************************************************************************************************
*************************************            PICK            ***************************************************
********************************************************************************************************************/  
  // TARGET POSE VARS
  tfScalar roll = 0.0; //uav
  tfScalar pitch = 0.0;
  tfScalar yaw = 0.0;
  tf::Quaternion q;
  tfScalar roll_ = 0.0; //arm
  tfScalar pitch_ = 0.0;
  tfScalar yaw_ = 0.0;
  tf::Quaternion q_;
  
  
  
  //! Planning to a Pose goal to the uav
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // IMPORTANT NOTE: The 'uav_group.setPositionTarget()' and the 'uav_group.setPoseTarget()' fails at the goal construction. Error not identificated... Maybe because it's not a chain 
  // So we use the 'uav_group.setJointValueTarget()' that not fail. For the uav, it's a floating joint.

  
  //! Planning to a joint-space goal to the uav	--	UAV TO APROX POSITION
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  bool uav_success = false;
  int uav_planning_attempts = 0;
  while(!uav_success && uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  uav_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), uav_group_target_values);
		
	  // Set the goal pose = position + orientation
	  roll = 0.0;
	  pitch = 0.0;
	  yaw = PICK_BAR_POS_YAW + UAV_APROX_POS_dYAW;
	  q.setRPY(roll,pitch,yaw);
	  uav_group_target_values[0] = PICK_BAR_POS_X + UAV_APROX_POS_dX;		// X [m]
	  uav_group_target_values[1] = PICK_BAR_POS_Y + UAV_APROX_POS_dY;		// Y [m]
	  uav_group_target_values[2] = PICK_BAR_POS_Z + UAV_APROX_POS_dZ;		// Z [m]
	  uav_group_target_values[3] = q.x();		// qx
	  uav_group_target_values[4] = q.y();		// qy
	  uav_group_target_values[5] = q.z();		// qz
	  uav_group_target_values[6] = q.w();		// qw
	  
	  // Plan for this group
	  uav_group.setJointValueTarget(uav_group_target_values);
	  uav_success = uav_group.plan(uav_plan);

	  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");
	  
	  uav_planning_attempts++;
  }

  //! Moving the uav to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }
  else
  {
	  ROS_ERROR("UAV planning failed %d times, Exiting test program...", UAV_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }


  //! Arm extension using the arm extension action clien -- EXTEND ARM
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // Arm state topic
  ros::Subscriber sub_pose_arm_ = node_handle.subscribe("/aal_1/arm_state_estimation", 1, &armStateEstimationCallback);
  // Arm extension ActionClient
  AALExtensionActionWrapper aalExtensionActionWrapper;
  printf("Exetending the arm..");
  fflush(stdout);
  sleep(2.0);
  aalExtensionActionWrapper.extendArm();
  while(arm_state.arm_state != arcas_msgs::ArmStateEstimation::EXTENDED) 
  {
	  sleep(1);
	  printf("."); 
	  fflush(stdout);
  }
  printf(" [OK]\n");

  //! Planning to a Pose goal to the arm -- ARM TO APROX POSITION
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  
  // Wait to UAV stabilization
  int stabilization_index = 0;
  printf("Waiting for UAV stabilization..");
  fflush(stdout);
  while(stabilization_index <= UAV_STABILIZATION_COUNT)
  {
	 usleep(10000); 
	 
	 if(checkUavStabilization())
	 {	
		stabilization_index++;
		printf(".");
		fflush(stdout);
	 }
  }
  printf(" [OK]\n");
  
  
  bool arm_success = false;
  int arm_planning_attempts = 0;
  while(!arm_success && arm_planning_attempts <= ARM_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  arm_group.setStartStateToCurrentState();

	  // Set planning target pose, /wirst_1 frame respect to the /world frame (note: by default if uav frame is parallel to world frame and at the picked_up arm pose, wirst_1 is turned PI/4 in yaw)
	  roll_ = 0.0;
	  pitch_ = M_PI/2.0; // Inicialmente el gripper esta como para coger algo vertical. Dandole un pitch de pi/2 seria para coger algo en horizontal
	  yaw_ = M_PI/4.0 - 3.0*M_PI/4.0; // Los pi/4 de por defecto menos pi/2 que esta girado actualmente el quad
	  q_.setRPY(roll_,pitch_,yaw_);
	  //~ printf("QUATERNION: X: %f, Y: %f, Z: %f, W: %f\n", q_.x(), q_.y(), q_.z(), q_.w());
	  arm_target_pose_.orientation.x = q_.x();
	  arm_target_pose_.orientation.y = q_.y();
	  arm_target_pose_.orientation.z = q_.z();
	  arm_target_pose_.orientation.w = q_.w();
	  arm_target_pose_.position.x = PICK_BAR_POS_X + ARM_APROX_POS_dX;  
	  arm_target_pose_.position.y = PICK_BAR_POS_Y + ARM_APROX_POS_dY;
	  arm_target_pose_.position.z = PICK_BAR_POS_Z + ARM_APROX_POS_dZ;
	  arm_group.setPoseTarget(arm_target_pose_);
	  
	  // Plan for this group
	  arm_success = arm_group.plan(arm_plan);
	  // Check result
	  ROS_INFO("Visualizing plan for the arm group (pose goal) %s",arm_success?"":"FAILED");    
  	
  	  arm_planning_attempts++;
  }

  //! Moving the arm to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(arm_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  arm_success = arm_group.execute(arm_plan);
	  // Check the result
	  ROS_INFO("Executed for the arm group %s",arm_success?"":"FAILED");    
	  // If we want both plan and execute --> arm_group.move(); 
  }
    else
  {
	  ROS_ERROR("Arm planning failed %d times, Exiting test program...", ARM_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }

  //! Planning to a joint-space goal to the gripper -- OPEN THE GRIPPER
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
  bool gripper_success = false;
  int gripper_planning_attempts = 0;
  while(!gripper_success && gripper_planning_attempts <= GRIPPER_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  gripper_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  gripper_group.getCurrentState()->copyJointGroupPositions(gripper_group.getCurrentState()->getRobotModel()->getJointModelGroup(gripper_group.getName()), gripper_group_target_values);
		
	  // Set the goal pose (open)
	  gripper_group_target_values[0] = 0.79;		// X [m]
	  gripper_group_target_values[1] = -0.79;		// Y [m]
		
	  // Plan for this group
	  gripper_group.setJointValueTarget(gripper_group_target_values);
	  gripper_success = gripper_group.plan(gripper_plan);

	  ROS_INFO("Visualizing plan for the gripper group %s",gripper_success?"":"FAILED");
	  
	  gripper_planning_attempts++;
  }

  //! Moving the gripper to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(gripper_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  gripper_success = gripper_group.execute(gripper_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the gripper group %s",gripper_success?"":"FAILED");    
	  // If we want both plan and execute --> gripper_group.move(); 
  }
  else
  {
	  ROS_ERROR("Gripper planning failed %d times, Exiting test program...", GRIPPER_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }

  //! Planning to a joint-space goal to the uav -- UAV TO GRASP POSITION
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  // Wait to UAV stabilization
  stabilization_index = 0;
  printf("Waiting for UAV stabilization..");
  fflush(stdout);
  while(stabilization_index <= UAV_STABILIZATION_COUNT)
  {
	 usleep(10000); 
	 
	 if(checkUavStabilization())
	 {	
		stabilization_index++;
		printf(".");
		fflush(stdout);
	 }
  }
  printf(" [OK]\n");
  
  
  uav_success = false;
  uav_planning_attempts = 0;
  while(!uav_success && uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  uav_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), uav_group_target_values);
		
	  // Set the goal pose = position + orientation
	  roll = 0.0;
	  pitch = 0.0;
	  yaw = PICK_BAR_POS_YAW + UAV_APROX_POS_dYAW;;
	  q.setRPY(roll,pitch,yaw);
	  uav_group_target_values[0] = PICK_BAR_POS_X + UAV_APROX_POS_dX;		// X [m]
	  uav_group_target_values[1] = PICK_BAR_POS_Y + UAV_APROX_POS_dY;		// Y [m]
	  uav_group_target_values[2] = PICK_BAR_POS_Z + UAV_APROX_POS_dZ - ARM_APROX_POS_dZ + ARM_PICK_POS_dZ ;		// Z [m]
	  uav_group_target_values[3] = q.x();		// qx
	  uav_group_target_values[4] = q.y();		// qy
	  uav_group_target_values[5] = q.z();		// qz
	  uav_group_target_values[6] = q.w();		// qw
	  
	  // Plan for this group
	  uav_group.setJointValueTarget(uav_group_target_values);
	  uav_success = uav_group.plan(uav_plan);

	  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");
	  
	  uav_planning_attempts++;
  }


  //! Moving the uav to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }
  else
  {
	  ROS_ERROR("UAV planning failed %d times, Exiting test program...", UAV_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }
  
  //! Planning to a joint-space goal to the gripper -- CLOSE THE GRIPPER
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^   
  gripper_success = false;
  gripper_planning_attempts = 0;
  while(!gripper_success && gripper_planning_attempts <= GRIPPER_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  gripper_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  gripper_group.getCurrentState()->copyJointGroupPositions(gripper_group.getCurrentState()->getRobotModel()->getJointModelGroup(gripper_group.getName()), gripper_group_target_values);
		
	  // Set the goal pose (open)
	  gripper_group_target_values[0] = 0.0;		// X [m]
	  gripper_group_target_values[1] = 0.0;		// Y [m]
		
	  // Plan for this group
	  gripper_group.setJointValueTarget(gripper_group_target_values);
	  gripper_success = gripper_group.plan(gripper_plan);

	  ROS_INFO("Visualizing plan for the gripper group %s",gripper_success?"":"FAILED");

	  gripper_planning_attempts++;
  }
  
  //! Moving the gripper to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(gripper_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  gripper_success = gripper_group.execute(gripper_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the gripper group %s",gripper_success?"":"FAILED");    
	  // If we want both plan and execute --> gripper_group.move(); 
  }
  else
  {
	  ROS_ERROR("Gripper planning failed %d times, Exiting test program...", GRIPPER_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }
  
  //! Planning to a joint-space goal to the uav -- UAV TO APROX POSITION
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  uav_success = false;
  uav_planning_attempts = 0;
  while(!uav_success && uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  uav_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), uav_group_target_values);
		
	  // Set the goal pose = position + orientation
	   roll = 0.0;
	   pitch = 0.0;
	   yaw = PICK_BAR_POS_YAW + UAV_APROX_POS_dYAW;
	  q.setRPY(roll,pitch,yaw);
	  uav_group_target_values[0] = PICK_BAR_POS_X + UAV_APROX_POS_dX;		// X [m]
	  uav_group_target_values[1] = PICK_BAR_POS_Y + UAV_APROX_POS_dY;		// Y [m]
	  uav_group_target_values[2] = PICK_BAR_POS_Z + UAV_APROX_POS_dZ;		// Z [m]
	  uav_group_target_values[3] = q.x();		// qx
	  uav_group_target_values[4] = q.y();		// qy
	  uav_group_target_values[5] = q.z();		// qz
	  uav_group_target_values[6] = q.w();		// qw
	  
	  // Plan for this group
	  uav_group.setJointValueTarget(uav_group_target_values);
	  uav_success = uav_group.plan(uav_plan);

	  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");
	  
	  uav_planning_attempts++;
  }
  
  //! Moving the uav to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }
  else
  {
	  ROS_ERROR("UAV planning failed %d times, Exiting test program...", UAV_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }



/*******************************************************************************************************************
*************************************            PLACE            **************************************************
********************************************************************************************************************/


  //! Planning to a joint-space goal to the uav -- UAV TO APROX POSITION 
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  uav_success = false;
  uav_planning_attempts = 0;
  while(!uav_success && uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  uav_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), uav_group_target_values);
		
	  // Set the goal pose = position + orientation
	  roll = 0.0;
	  pitch = 0.0;
	  yaw = PLACE_BASES_POS_YAW + UAV_APROX_POS_dYAW;
	  q.setRPY(roll,pitch,yaw);
	  uav_group_target_values[0] = PLACE_BASES_POS_X + UAV_APROX_POS_dX;		// X [m]
	  uav_group_target_values[1] = PLACE_BASES_POS_Y + UAV_APROX_POS_dY;		// Y [m]
	  uav_group_target_values[2] = PLACE_BASES_POS_Z + UAV_APROX_POS_dZ;		// Z [m]
	  uav_group_target_values[3] = q.x();										// qx
	  uav_group_target_values[4] = q.y();										// qy
	  uav_group_target_values[5] = q.z();										// qz
	  uav_group_target_values[6] = q.w();										// qw
	  
	  // Plan for this group
	  uav_group.setJointValueTarget(uav_group_target_values);
	  uav_success = uav_group.plan(uav_plan);

	  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");

	  uav_planning_attempts++;
  }
  
  //! Moving the uav to a pose goal 
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }
  else
  {
	  ROS_ERROR("UAV planning failed %d times, Exiting test program...", UAV_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }
    
  //! Planning to a joint-space goal to the uav -- UAV TO PLACE POSITION
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  // Wait to UAV stabilization
  stabilization_index = 0;
  printf("Waiting for UAV stabilization..");
  fflush(stdout);
  while(stabilization_index <= UAV_STABILIZATION_COUNT)
  {
	 usleep(10000); 
	 
	 if(checkUavStabilization())
	 {	
		stabilization_index++;
		printf(".");
		fflush(stdout);
	 }
  }
  printf(" [OK]\n");
  
  uav_success = false;
  uav_planning_attempts = 0;
  while(!uav_success && uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  uav_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), uav_group_target_values);
		
	  // Set the goal pose = position + orientation
	  roll = 0.0;
	  pitch = 0.0;
	  yaw = PLACE_BASES_POS_YAW + UAV_APROX_POS_dYAW;
	  q.setRPY(roll,pitch,yaw);
	  uav_group_target_values[0] = PLACE_BASES_POS_X + UAV_APROX_POS_dX;												// X [m]
	  uav_group_target_values[1] = PLACE_BASES_POS_Y + UAV_APROX_POS_dY;												// Y [m]
	  uav_group_target_values[2] = PLACE_BASES_POS_Z + UAV_APROX_POS_dZ - ARM_APROX_POS_dZ + ARM_PICK_POS_dZ ;		// Z [m]
	  uav_group_target_values[3] = q.x();		// qx
	  uav_group_target_values[4] = q.y();		// qy
	  uav_group_target_values[5] = q.z();		// qz
	  uav_group_target_values[6] = q.w();		// qw
	  
	  // Plan for this group
	  uav_group.setJointValueTarget(uav_group_target_values);
	  uav_success = uav_group.plan(uav_plan);

	  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");
	 
	  uav_planning_attempts++;
  }
  
  //! Moving the uav to a pose goal 
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }
  else
  {
	  ROS_ERROR("UAV planning failed %d times, Exiting test program...", UAV_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }
  
  //! Planning to a joint-space goal to the gripper -- OPEN THE GRIPPER
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^    
  gripper_success = false;
  gripper_planning_attempts = 0;
  while(!gripper_success && gripper_planning_attempts <= GRIPPER_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  gripper_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  gripper_group.getCurrentState()->copyJointGroupPositions(gripper_group.getCurrentState()->getRobotModel()->getJointModelGroup(gripper_group.getName()), gripper_group_target_values);
		
	  // Set the goal pose (open)
	  gripper_group_target_values[0] = 0.79;		// X [m]
	  gripper_group_target_values[1] = -0.79;		// Y [m]
		
	  // Plan for this group
	  gripper_group.setJointValueTarget(gripper_group_target_values);
	  gripper_success = gripper_group.plan(gripper_plan);

	  ROS_INFO("Visualizing plan for the gripper group %s",gripper_success?"":"FAILED");
	  
	  gripper_planning_attempts++;
  }

  //! Moving the gripper to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(gripper_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  gripper_success = gripper_group.execute(gripper_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the gripper group %s",gripper_success?"":"FAILED");    
	  // If we want both plan and execute --> gripper_group.move(); 
  }
  else
  {
	  ROS_ERROR("Gripper planning failed %d times, Exiting test program...", GRIPPER_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }

  //! Planning to a joint-space goal to the uav -- UAV TO APROX POSITION
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  uav_success = false;
  uav_planning_attempts = 0;
  while(!uav_success && uav_planning_attempts <= UAV_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  uav_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), uav_group_target_values);
		
	  // Set the goal pose = position + orientation
	  roll = 0.0;
	  pitch = 0.0;
	  yaw = PLACE_BASES_POS_YAW + UAV_APROX_POS_dYAW;
	  q.setRPY(roll,pitch,yaw);
	  uav_group_target_values[0] = PLACE_BASES_POS_X + UAV_APROX_POS_dX;		// X [m]
	  uav_group_target_values[1] = PLACE_BASES_POS_Y + UAV_APROX_POS_dY;		// Y [m]
	  uav_group_target_values[2] = PLACE_BASES_POS_Z + UAV_APROX_POS_dZ;		// Z [m]
	  uav_group_target_values[3] = q.x();										// qx
	  uav_group_target_values[4] = q.y();										// qy
	  uav_group_target_values[5] = q.z();										// qz
	  uav_group_target_values[6] = q.w();										// qw
	  
	  // Plan for this group
	  uav_group.setJointValueTarget(uav_group_target_values);
	  uav_success = uav_group.plan(uav_plan);

	  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");
	
	  uav_planning_attempts++;
  }
  
  //! Moving the uav to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }
  else
  {
	  ROS_ERROR("UAV planning failed %d times, Exiting test program...", UAV_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }
  
  //! Planning to a joint-space goal to the gripper
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^    
  gripper_success = false;
  gripper_planning_attempts = 0;
  while(!gripper_success && gripper_planning_attempts <= GRIPPER_MAX_PLANNING_ATTEMPTS)
  {
	  // Set planning start pose
	  gripper_group.setStartStateToCurrentState();
	  
	  // First get the current set of joint values for the group.
	  gripper_group.getCurrentState()->copyJointGroupPositions(gripper_group.getCurrentState()->getRobotModel()->getJointModelGroup(gripper_group.getName()), gripper_group_target_values);
		
	  // Set the goal pose (open)
	  gripper_group_target_values[0] = 0.0;		// X [m]
	  gripper_group_target_values[1] = 0.0;		// Y [m]
		
	  // Plan for this group
	  gripper_group.setJointValueTarget(gripper_group_target_values);
	  gripper_success = gripper_group.plan(gripper_plan);

	  ROS_INFO("Visualizing plan for the gripper group %s",gripper_success?"":"FAILED");
	
	  gripper_planning_attempts++;
  }
    
  //! Moving the gripper to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  if(gripper_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  gripper_success = gripper_group.execute(gripper_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the gripper group %s",gripper_success?"":"FAILED");    
	  // If we want both plan and execute --> gripper_group.move(); 
  }
  else
  {
	  ROS_ERROR("Gripper planning failed %d times, Exiting test program...", GRIPPER_MAX_PLANNING_ATTEMPTS);
	  exit(0);
  }
  
  //! Exit
  //! ^^^^
  ros::shutdown();  
  return 0;
}
