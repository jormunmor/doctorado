/*
	This class is a demo implemented to show how to use
	the actionlib classes wrapped in a class. The class
	is implemented to receive as argument function pointers
	that will be used as callbacks for actionlib functions.

	May be deleted after the package is	debugged.
*/

#include "ArcasExecLayerClient.h"

ArcasExecLayerClient::ArcasExecLayerClient(
    std::string node_name,
    std::string server_name,
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleActiveCallback active_cb,
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleFeedbackCallback feedback_cb
)
{
	// Init the ROS node
	int argc = 1;
    char* argv[] = {(char*) node_name.c_str()};
	ros::init(argc, argv, node_name);

	// Create the action client
    action_client = new actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>(server_name, true);

	// Setup the callback functions
	activeCb = active_cb;
	feedbackCb = feedback_cb;

}

ArcasExecLayerClient::~ArcasExecLayerClient()
{
	delete action_client;
	ros::shutdown();

}

void ArcasExecLayerClient::doneCb(const actionlib::SimpleClientGoalState& state,
            const arcas_exec_layer::ArcasExecLayerResultConstPtr& result)
{
	finished = true;
	finalState = state.state_;
	ROS_INFO("Finishing execution of the client.");

}

int ArcasExecLayerClient::executeGoal(Goal& goal)
{
	// Set the state of the client prior to the execution
	finished = false;

	ROS_INFO("Waiting for action server to start.");
	action_client->waitForServer();
	ROS_INFO("Action server started, sending goal.");

	// Send Goal
	action_client->sendGoal(goal, boost::bind(&ArcasExecLayerClient::doneCb, this, _1, _2), activeCb, feedbackCb);

	// loop until the action is finished at 10 Hz frecuency
	ros::Rate r(10);
	while (!finished)
	{
		ros::spinOnce(); // We are not interested in the blocking version of spin()  
		r.sleep();
	}

	return finalState;

}





