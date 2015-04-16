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

VehicleScheduler::VehicleScheduler(const int &id, const QVector<QStringList> &ops, const int &row, QObject *parent) :
    QObject(parent), vehicleID(id), tableRow(row), waitingFor(-1), operations(ops), syncRequests(QVector<int>())
{

}

VehicleScheduler::~VehicleScheduler()
{

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

        // Execute The action. First we create the goal.
        Goal fakeGoal;
        fakeGoal.vehicleID = vehicleID;
        fakeGoal.group = VEHICLE_FRAME;
        fakeGoal.actionType = TAKEOFF;
        fakeGoal.pose.position.z = 10;
        actionClient.executeGoal(fakeGoal);
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
    // Emit the signal to display the status in the table. activeCb
    // executes when the operation is about to start to execute.)
    int operationState = 0;

    switch(currentOpType)
    {
        case STANDALONE: operationState = Ui::EXECUTING; break;
        case COOPERATIVE: operationState = Ui::EXECUTING_COOP;break;
        default: break;
    }

    emit(stateChanged(tableRow, currentOp, operationState));
}

/**
  This function executes periodically after the current action starts its
  execution and stops executing after the action finishes. Its purpose is
  to update the gantt chart.
  */
void VehicleScheduler::feedbackCb(const arcas_exec_layer::ArcasExecLayerFeedbackConstPtr& feedback)
{

}

/**
  This function executes after the current action has finished its
  execution. Its purpose is to update the action state in the table widget.
  By the moment, we only consider success and fail states.
  */
void VehicleScheduler::doneCb(const actionlib::SimpleClientGoalState& state, const arcas_exec_layer::ArcasExecLayerResultConstPtr& result)
{
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
}
