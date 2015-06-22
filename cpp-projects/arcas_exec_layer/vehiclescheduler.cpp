#include "vehiclescheduler.h"
#include <iostream>
#include <unistd.h>
#include <cstdlib>
#include <QApplication>
#include <QTime>
#include <string>

/// TODO: avoid using QString to create std::strings to use with ROS nodes. Sometimes
/// they introduce unexpected chars. May be due to the encoding?.

using namespace std;

VehicleScheduler::VehicleScheduler(const int &id, const QVector<QStringList> &ops, const int &row, std::map<int, Goal> *locMap, QObject *parent) :
    QObject(parent), vehicleID(id), tableRow(row), waitingFor(-1), locationsMap(locMap), operations(ops), syncRequests(QVector<int>())
{
    lastFeedbackTime = NULL;

}

VehicleScheduler::~VehicleScheduler()
{
    if(lastFeedbackTime != NULL)
    {
        delete lastFeedbackTime;

    }

}

/*
  In this function will be placed all the code related to the execution
  of the operations present in the operations vector. The code needs to be
  always running, so we will not be able to call the blocking exec function
  to pay attention for events for the thread (arriving signals). As a solution,
  we call QApplication::processEvents sometimes. This keeps the thread responsive.
*/
void VehicleScheduler::execute()
{
    std::cout << "Started thread with id: " << QThread::currentThreadId() << " corresponding to vehicle with id: " << vehicleID << std::endl;
    //qsrand(QTime::currentTime().msec());
    qsrand(vehicleID);

    // Create the client object. We do not include it as a class member
    // to avoid collateral effects of not calling ros::init in the vehicle
    // scheduler constructor.

    std::ostringstream ss;
    ss << vehicleID;
    std::string serverName = std::string("VehicleServer") + ss.str();
    ArcasExecLayerClient actionClient(serverName, boost::bind(&VehicleScheduler::activeCb, this), boost::bind(&VehicleScheduler::feedbackCb, this, _1), boost::bind(&VehicleScheduler::doneCb, this, _1, _2));
    currentOp = 0;

    while(operations.size() > 0)
    {

        // Get the next operation
        QStringList op = operations.at(0);
        operations.remove(0);

        // Declare the cooperative uav ID (may not be needed if the operation is not cooperative)
        int secondUavId = -1;

        // Check the operation type. Some operations require synchronization
        // among two vehicles. If the curren operation is of that type, we
        // must wait until the other vehicle has reached the operation on its
        // queue.
        currentOpType = STANDALONE;
        if(op.size()>=3 && op.at(2).contains(Ui::UAV_IDENTIFIER)) // This is a cooperative operation, the third field is a uav ID
        {
            currentOpType = COOPERATIVE;

            // Get the cooperative uav ID
            secondUavId = op.at(2).right(op.at(2).size() - Ui::UAV_IDENTIFIER.size()).toInt();

            // If the second uav ID is equal to the actual vehicleID, we have to flip de second and third positions of the QStringList (the uav IDs).
            // This is because the scheduler always suppose that the first uav in the operations to execute
            // is the owner of the queue. Take as example the cooperative operation pickObject uav1 uav2. This will be appear
            // in that order in both queues, for uav1 and uav2. If we don't flip them, when executing the queue of uav2 then the signal
            // will emmit vehicleId = 2 secondUavId = 2.
            if(secondUavId == vehicleID)
            {
                QStringList flippedList(op);
                flippedList.replace(1, op.at(2));
                flippedList.replace(2, op.at(1));
                secondUavId = op.at(1).right(op.at(1).size() - Ui::UAV_IDENTIFIER.size()).toInt();
                op = flippedList;

            }

            // Set the table cell to synchro color,
            // we must hold this color until the other uav
            // tell us it is synchronized.
            emit(stateChanged(tableRow, currentOp, Ui::SYNCHRONIZING));

            // Signal our cooperative operation and wait until
            // secondUavId waits for us.
            emit(syncThread(vehicleID, secondUavId));

            int uavRequestIndex = -1;
            do
            {
                // Process events. This let us know about the arriving signals.
                QApplication::processEvents();

                // Check if secondUavId is waiting for us
                uavRequestIndex = syncRequests.indexOf(secondUavId);

                if(uavRequestIndex >= 0) // If it is, then we can execute the operation
                {
                    // Delete the request from the vector and exit the loop.
                    syncRequests.remove(uavRequestIndex);
                    break;

                }
                else // If it isn't, then sleep 100 milliseconds and repeat.
                {
                    usleep(100*1000); /// TODO: this function is deprecated on POSIX. Find a better one.

                }


            } while(uavRequestIndex < 0);


        }

        // We are going to execute the goal, so retrieve the pose of the end location (if any).
        // If not, we use a fake pose that will not be used by the action server.
        /*
        geometry_msgs::Pose pose;
        pose.position.x = 0;
        pose.position.y = 0;
        pose.position.z = 0;
        pose.orientation.w = 1;
        pose.orientation.x = 0;
        pose.orientation.y = 0;
        pose.orientation.z = 0;
        */

        // Create the goal variable and configure an initial value for its fields
        Goal goal;
        goal.pose.position.x = 0;
        goal.pose.position.y = 0;
        goal.pose.position.z = 0;
        goal.pose.orientation.w = 1;
        goal.pose.orientation.x = 0;
        goal.pose.orientation.y = 0;
        goal.pose.orientation.z = 0;
        goal.yaw = 0;
        //goal.vehicleID = vehicleID;

        // Check the type of operation
        if(op.at(0).contains("takeoff")) // TAKEOFF
        {
            goal.group = VEHICLE_FRAME;
            goal.actionType = TAKEOFF;
            currentActionType = TAKEOFF;

            // There is no pose in the QStringList, so we use the previous as a fake unused pose for the server.


        } else if(op.at(0).contains("do_move")) // MOVE
        {
            //std::cout << "MOVE action detected." << std::endl;
            //goal.group = VEHICLE_FRAME;
            //goal.actionType = MOVE;
            int locNumber = 0;

            if(currentOpType == STANDALONE)
            {
                locNumber = (int) op.at(3).toDouble(); // The TO location is on the third position of the QStringList.


            } else
            {
                locNumber = (int) op.at(4).toDouble(); // The TO location is on the fourth position of the QStringList.

            }

            // There is a pose in the QStringList, so we get it from the map for the server.
            //pose = locationsMap->at(locNumber);
            goal = locationsMap->at(locNumber);
            goal.group = VEHICLE_FRAME;
            goal.actionType = MOVE;
            currentActionType = MOVE;

        } else if(op.at(0).contains("pick"))
        {
            goal.group = VEHICLE_FRAME;
            goal.actionType = PICK;
            currentActionType = PICK;

        } else if(op.at(0).contains("assemble"))
        {
            goal.group = VEHICLE_FRAME;
            goal.actionType = PLACE;
            currentActionType = PLACE;

        }

        goal.vehicleID = vehicleID;
        //goal.pose = pose;

        // Execute The action. First we create the goal. Blocking function.
        actionClient.executeGoal(goal);
        currentOp++;

    }

}

void VehicleScheduler::threadSync(int waitingThreadVehicleId, int requestedThreadVehicleId)
{

    if(waitingThreadVehicleId != vehicleID) // That wasn't me who sent this signal
    {
        if(requestedThreadVehicleId == vehicleID) // Someone is waiting for me.
        {
            syncRequests.push_back(waitingThreadVehicleId);

        }
    }
    else // That was me who sent this signal, discard it
    {

    }

}

/**
  This function executes when the current action starts its execution in the
  remote actionlib server. Its purpose is to update the action state in
  the table widget.
  */
void VehicleScheduler::activeCb(void)
{
    // mark the current time for the action
    QTime current = QTime::currentTime();
    lastFeedbackTime = new QTime(current.hour(), current.minute(), current.second(), current.msec());

    // Emit the signal to display the status in the table. activeCb
    // executes when the operation is about to start to execute.)
    //std::cout << "activeCb" << std::endl;
    ROS_INFO("Action is now ACTIVE");
    int operationState = 0;

    switch(currentOpType)
    {
        case STANDALONE: operationState = Ui::EXECUTING; break;
        case COOPERATIVE: operationState = Ui::EXECUTING_COOP;break;
        default: break;
    }


    emit(stateChanged(tableRow, currentOp, operationState));
    emit newGanttAction(tableRow, currentActionType);
    //ROS_INFO("Exiting ACTIVE...");


}

/**
  This function executes periodically after the current action starts its
  execution and stops executing after the action finishes. Its purpose is
  to update the gantt chart with the time elapsed from the last feedback
  call, or the active call.
  */
void VehicleScheduler::feedbackCb(const arcas_exec_layer::ArcasExecLayerFeedbackConstPtr& feedback)
{
    if(lastFeedbackTime != NULL)
    {
        QTime current = QTime::currentTime();
        QTime *currentFeedbackTime = new QTime(current.hour(), current.minute(), current.second(), current.msec());
        int msecs = lastFeedbackTime->msecsTo(*currentFeedbackTime);
        //ROS_INFO("msecs since last feedback: %d", msecs);
        delete lastFeedbackTime;
        lastFeedbackTime = currentFeedbackTime;
        emit updateGantt(tableRow, msecs/1000.0);

    }

}

/**
  This function executes after the current action has finished its
  execution. Its purpose is to update the action state in the table widget.
  By the moment, we only consider success and fail states. It also stops the
  timer associated with the scheduler.
  */
void VehicleScheduler::doneCb(const actionlib::SimpleClientGoalState& state, const arcas_exec_layer::ArcasExecLayerResultConstPtr& result)
{
    delete lastFeedbackTime;
    lastFeedbackTime = NULL;
    ROS_INFO("Action is now DONE");
    //std::cout << "doneCb" << std::endl;

    int operationState = 0;

    if(state.state_ == actionlib::SimpleClientGoalState::SUCCEEDED)
    {
        if(currentOpType == STANDALONE)
        {
            operationState = Ui::FINISHED;

        }
        else
        {
            operationState = Ui::FINISHED_COOP;

        }
    }
    else // Failed
    {
        if(currentOpType == STANDALONE)
        {
            operationState = Ui::FAILED;

        }
        else
        {
            operationState = Ui::FAILED_COOP;

        }
    }

    // Emit the finish signal to display the status in the table
    emit(stateChanged(tableRow, currentOp, operationState));
    ROS_INFO("Exiting DONE...");
}
