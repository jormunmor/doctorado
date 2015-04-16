/*
	This class is a demo implemented to show how to use
	the actionlib classes wrapped in a class. The class
	is implemented to receive as argument function pointers
	that will be used as callbacks for actionlib functions.

	May be deleted after the package is	debugged.
*/

#ifndef ARCASEXECLAYERCLIENT_H
#define ARCASEXECLAYERCLIENT_H

#include <iostream>
#include <string>
#include <ros/ros.h>
#include <actionlib/client/simple_action_client.h>
#include <actionlib/client/terminal_state.h>
#include <arcas_exec_layer/ArcasExecLayerAction.h>

typedef arcas_exec_layer::ArcasExecLayerGoal Goal;

class ArcasExecLayerClient
{

public:
    ArcasExecLayerClient(std::string node_name,
                std::string server_name,
				actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleActiveCallback active_cb,
				actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleFeedbackCallback feedback_cb
	);	
	~ArcasExecLayerClient();
	void doneCb(const actionlib::SimpleClientGoalState& state, const arcas_exec_layer::ArcasExecLayerResultConstPtr& result);
	int executeGoal(Goal& goal);

private:
	bool finished;
	int finalState;
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>* action_client;
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleActiveCallback activeCb;
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleFeedbackCallback feedbackCb;

};

#endif // ARCASEXECLAYERCLIENT_H
