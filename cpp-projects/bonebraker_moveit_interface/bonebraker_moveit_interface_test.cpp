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
  while(!positionInitialized)
  {
	  //~ ros::spinOnce();
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

  // We will use the :planning_scene_interface:`PlanningSceneInterface class to deal directly with the world.
  moveit::planning_interface::PlanningSceneInterface planning_scene_interface;  


  //! Getting Basic Information
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^
  // Print the name of the reference frame for the robot.
  ROS_INFO("Planning frame (uav): %s", uav_group.getPlanningFrame().c_str());
  ROS_INFO("Reference frame (uav): %s", uav_group.getPoseReferenceFrame().c_str());
  ROS_INFO("Planning frame (arm): %s", arm_group.getPlanningFrame().c_str());
  ROS_INFO("Reference frame (arm): %s", arm_group.getPoseReferenceFrame().c_str());
  ROS_INFO("Planning frame (gripper): %s", gripper_group.getPlanningFrame().c_str());
  ROS_INFO("Reference frame (gripper): %s", gripper_group.getPoseReferenceFrame().c_str());  
  // Print the name of the end-effector link for the group.
  ROS_INFO("End_Effector (uav): %s", uav_group.getEndEffectorLink().c_str());
  ROS_INFO("End_Effector (arm): %s", arm_group.getEndEffectorLink().c_str());
  ROS_INFO("End_Effector (gripper): %s", gripper_group.getEndEffectorLink().c_str());

	// NOTE: The Quadrotor and the Gripper don't have end-effector, so we use MoveGroup::setJointValueTarget() for set the target joint/s value   
	// instead MoveGroup::setPositionTarget() for set the target position and orientation


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
  ROS_INFO("Add a MESH into the world");  
  planning_scene_interface.addCollisionObjects(collision_objects_);

  // Sleep so we have time to see the object in RViz
  sleep(2.0);
  
  
  //! Path constraints for the UAV (not roll and pitch for planning)
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  moveit_msgs::OrientationConstraint ocm;  
  ocm.link_name = "base_link";  
  ocm.header.frame_id = "base_link";
  ocm.orientation.w = 1.0;
  ocm.absolute_x_axis_tolerance = 0.1;
  ocm.absolute_y_axis_tolerance = 0.1;
  ocm.absolute_z_axis_tolerance = 0.1;
  ocm.weight = 1.0;
  
  // Now, set it as the path constraint for the group.
  moveit_msgs::Constraints uav_constraints;
  uav_constraints.orientation_constraints.push_back(ocm);  
  uav_group.setPathConstraints(uav_constraints);
  
  
  //! Planning to a Pose goal to the uav
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // IMPORTANT NOTE: The 'uav_group.setPositionTarget()' and the 'uav_group.setPoseTarget()' fails at the goal construction. Error not identificated... Maybe because it's not a chain 
  // So we use the 'uav_group.setJointValueTarget()' that not fail. For the uav, it's a floating joint.

  
  //! Planning to a joint-space goal to the uav
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  
  // Set planning start pose
  uav_group.setStartStateToCurrentState();
  
  // First get the current set of joint values for the group.
  std::vector<double> group_variable_values;
  uav_group.getCurrentState()->copyJointGroupPositions(uav_group.getCurrentState()->getRobotModel()->getJointModelGroup(uav_group.getName()), group_variable_values);
    
  // Set the goal pose = position + orientation
  tfScalar roll = 0.0;
  tfScalar pitch = 0.0;
  tfScalar yaw = -M_PI/2.0;
  tf::Quaternion q;
  q.setRPY(roll,pitch,yaw);
  group_variable_values[0] = -5.5;		// X [m]
  group_variable_values[1] = -5.5;		// Y [m]
  group_variable_values[2] = 2.0;		// Z [m]
  group_variable_values[3] = q.x();		// qx
  group_variable_values[4] = q.y();		// qy
  group_variable_values[5] = q.z();		// qz
  group_variable_values[6] = q.w();		// qw
  
  // Plan for this group
  moveit::planning_interface::MoveGroup::Plan uav_plan;
  uav_group.setJointValueTarget(group_variable_values);
  bool uav_success = uav_group.plan(uav_plan);

  ROS_INFO("Visualizing plan for the uav group %s",uav_success?"":"FAILED");
  /* Sleep to give Rviz time to visualize the plan. */
  sleep(5.0);

  //! Moving the uav to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^
  if(uav_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  uav_success = uav_group.execute(uav_plan);
	  // Check the result
	  ROS_INFO("Executed plan for the uav group %s",uav_success?"":"FAILED");    
	  // If we want both plan and execute --> uav_group.move(); 
  }


  //! Arm extension using the arm extension action clien
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // Arm state topic
  ros::Subscriber sub_pose_arm_ = node_handle.subscribe("/aal_1/arm_state_estimation", 1, &armStateEstimationCallback);
  // Arm extension ActionClient
  AALExtensionActionWrapper aalExtensionActionWrapper;
  printf("Exetending the arm.."); 
  sleep(2.0);
  aalExtensionActionWrapper.extendArm();
  while(arm_state.arm_state != arcas_msgs::ArmStateEstimation::EXTENDED) 
  {
	  sleep(1);
	  printf("."); 
  }
  printf(" [OK]\n");

  //! Planning to a Pose goal to the arm
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // Set planning start pose
  arm_group.setStartStateToCurrentState();

  // Set planning target pose, /wirst_1 frame respect to the /world frame (note: by default if uav frame is parallel to world frame and at the picked_up arm pose, wirst_1 is turned PI/4 in yaw)
  tfScalar roll_ = 0.0;
  tfScalar pitch_ = 0.0;//M_PI/2.0; // Inicialmente el gripper esta como para coger algo vertical. Dandole un pitch de pi/2 seria para coger algo en horizontal
  tfScalar yaw_ = M_PI/4.0 - M_PI/2.0; // Los pi/4 de por defecto menos pi/2 que esta girado actualmente el quad
  tf::Quaternion q_;
  q_.setRPY(roll_,pitch_,yaw_);
  //~ printf("QUATERNION: X: %f, Y: %f, Z: %f, W: %f\n", q_.x(), q_.y(), q_.z(), q_.w());
  geometry_msgs::Pose target_pose_;
  target_pose_.orientation.x = q_.x();
  target_pose_.orientation.y = q_.y();
  target_pose_.orientation.z = q_.z();
  target_pose_.orientation.w = q_.w();
  target_pose_.position.x = -5.5;  
  target_pose_.position.y = -5.5;
  target_pose_.position.z = 1.60;
  arm_group.setPoseTarget(target_pose_);
  
  // Plan for this group
  moveit::planning_interface::MoveGroup::Plan arm_plan;
  bool arm_success = arm_group.plan(arm_plan);
  // Check result
  ROS_INFO("Visualizing plan 1 (pose goal) %s",arm_success?"":"FAILED");    
  sleep(5.0);


  //! Moving the arm to a pose goal
  //! ^^^^^^^^^^^^^^^^^^^^^
  if(arm_success)
  {
	  // Request to execute (BLOCKING!!) the plan
	  arm_success = arm_group.execute(arm_plan);
	  // Check the result
	  ROS_INFO("Executed plan 1 %s",arm_success?"":"FAILED");    
	  // If we want both plan and execute --> arm_group.move(); 
  }

  //! Planning to a joint-space goal to the gripper
  //! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^    
  // Set planning start pose
  gripper_group.setStartStateToCurrentState();
  
  // First get the current set of joint values for the group.
  std::vector<double> gripper_variable_values;
  gripper_group.getCurrentState()->copyJointGroupPositions(gripper_group.getCurrentState()->getRobotModel()->getJointModelGroup(gripper_group.getName()), gripper_variable_values);
    
  // Set the goal pose (open)
  gripper_variable_values[0] = 0.79;		// X [m]
  gripper_variable_values[1] = -0.79;		// Y [m]
    
  // Plan for this group
  moveit::planning_interface::MoveGroup::Plan gripper_plan;
  gripper_group.setJointValueTarget(gripper_variable_values);
  bool gripper_success = gripper_group.plan(gripper_plan);

  ROS_INFO("Visualizing plan for the gripper group %s",gripper_success?"":"FAILED");
  /* Sleep to give Rviz time to visualize the plan. */
  sleep(5.0);

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

  //! Exit
  //! ^^^^
  ros::shutdown();  
  return 0;
}
