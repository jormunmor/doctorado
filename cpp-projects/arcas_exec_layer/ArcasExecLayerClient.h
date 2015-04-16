/*
	This class is a demo implemented to show how to use
	the actionlib classes wrapped in a class. The class
	is implemented to receive as argument function pointers
	that will be used as callbacks for actionlib functions.
    Objects of this class are supposed to be instantiated
    after a call to ros::init has been done somewhere else.
*/

#ifndef ARCASEXECLAYERCLIENT_H
#define ARCASEXECLAYERCLIENT_H

#include <actionlib/client/simple_action_client.h>
#include <actionlib/client/terminal_state.h>
#include <arcas_exec_layer/ArcasExecLayerAction.h>

typedef arcas_exec_layer::ArcasExecLayerGoal Goal;

class ArcasExecLayerClient
{

public:
    ArcasExecLayerClient(std::string server_name,
				actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleActiveCallback active_cb,
                actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleFeedbackCallback feedback_cb,
                actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleDoneCallback done_cb
	);	
	~ArcasExecLayerClient();
	void doneCb(const actionlib::SimpleClientGoalState& state, const arcas_exec_layer::ArcasExecLayerResultConstPtr& result);
	int executeGoal(Goal& goal);

private:
	bool finished;
	int finalState;
    std::string clientName;
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>* action_client;
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleActiveCallback activeCb;
	actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleFeedbackCallback feedbackCb;
    actionlib::SimpleActionClient<arcas_exec_layer::ArcasExecLayerAction>::SimpleDoneCallback doneCb2;

};

#endif // ARCASEXECLAYERCLIENT_H
