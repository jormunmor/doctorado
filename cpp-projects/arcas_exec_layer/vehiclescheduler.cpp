#include "vehiclescheduler.h"
#include <iostream>
#include <unistd.h>
#include <QApplication>
#include <QTime>
#include <string>

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
    QString nodeName = QString("action_client")  + QString::number(vehicleID);
    //std::string nodeName = "action_client" + std::to_string(vehicleID);

    ArcasExecLayerClient actionClient(nodeName.toStdString(), "ArcasExecLayerServer", boost::bind(&VehicleScheduler::activeCb, this), boost::bind(&VehicleScheduler::feedbackCb, this, _1));

    int tableColumn = 0;
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
        int executingStateColor = Ui::EXECUTING;
        int finishedStateColor = Ui::FINISHED;
        if(op.size()>=3 && op.at(2).contains(Ui::UAV_IDENTIFIER)) // This is a cooperative operation, the third field is a uav ID
        {
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
            emit(stateChanged(tableRow, tableColumn, Ui::SYNCHRONIZING));

            // Configure the color to use after the synchronizing.
            executingStateColor = Ui::EXECUTING_COOP;
            finishedStateColor = Ui::FINISHED_COOP;

            // Signal our cooperative operation and wait until
            // secondUavId waits for us.
            QString joined = op.join(" ");
            //std::cout << "Operation: " << joined.toStdString() << std::endl;
            //std::cout << "Emiting... " <<  "waiting thread: " << vehicleID << " requestedThread: " << secondUavId << std::endl;
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

        // Execute the operation. By the moment, simulate
        // its execution by waiting a random amount.
        int seconds = (qrand() % 5) + 1;

        // Emit the executing signal to display the status in the table
        emit(stateChanged(tableRow, tableColumn, executingStateColor));

        // Execute (wait for the moment)
        sleep(seconds);

        // Emit the finish signal to display the status in the table
        emit(stateChanged(tableRow, tableColumn, finishedStateColor));

        tableColumn++;

    }

}

void VehicleScheduler::threadSync(int waitingThreadVehicleId, int requestedThreadVehicleId)
{
    //std::cout << "Inside " << vehicleID <<  " || waiting thread: " << waitingThreadVehicleId << " requestedThread: " << requestedThreadVehicleId << std::endl;

    if(waitingThreadVehicleId != vehicleID) // That wasn't me who sent this signal
    {
        if(requestedThreadVehicleId == vehicleID) // Someone is waiting for me.
        {
            //std::cout << "Thread " << QThread::currentThreadId() << ": vehicleID" << waitingThreadVehicleId << " is waiting for vehicleID" << requestedThreadVehicleId << " (mine)" << std::endl;
            syncRequests.push_back(waitingThreadVehicleId);

        }
    }
    else // That was me who sent this signal, discard it
    {
        //std::cout << "Thread " << QThread::currentThreadId() << ": that was me who sent that signal. Ignoring it..." << std::endl;
    }

}

void VehicleScheduler::activeCb(void)
{
    std::cout << "activeCb from vehicleID: " << vehicleID << std::endl;
}

void VehicleScheduler::feedbackCb(const arcas_exec_layer::ArcasExecLayerFeedbackConstPtr& feedback)
{
    std::cout << "feedbackCb from vehicleID: " << vehicleID << std::endl;
}
