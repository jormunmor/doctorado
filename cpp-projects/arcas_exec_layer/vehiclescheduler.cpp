#include "vehiclescheduler.h"
#include <iostream>
#include <unistd.h>
#include <QApplication>
#include <QTime>

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

    int tableColumn = 0;
    while(operations.size() > 0)
    {

        // Get the next operation
        QStringList op = operations.at(0);
        operations.remove(0);

        // Check the operation type. Some operations require synchronization
        // among two vehicles. If the curren operation is of that type, we
        // must wait until the other vehicle has reached the operation on its
        // queue.
        int executingStateColor = Ui::EXECUTING;
        int finishedStateColor = Ui::FINISHED;
        if(op.size()>=3 && op.at(2).contains(Ui::UAV_IDENTIFIER))
        {
            executingStateColor = Ui::EXECUTING_COOP;
            finishedStateColor = Ui::FINISHED_COOP;
            // We have reached a cooperative operation, so signal it and wait until
            // someone waits for us.
            emit(syncThread(vehicleID, op.at(2).toInt()));
            QApplication::processEvents();

        }

        // Execute the operation.By the moment, simulate
        // its execution by waiting a random amount.
        int seconds = (qrand() % 5) + 1;
        //std::cout << "Hilo: " << vehicleID << " Aleatorio: " << seconds << std::endl;

        // Emit the executing signal
        emit(stateChanged(tableRow, tableColumn, executingStateColor));

        // Execute (wait for the moment)
        sleep(seconds);

        // Emit the finish signal
        emit(stateChanged(tableRow, tableColumn, finishedStateColor));

        tableColumn++;

    }

}

void VehicleScheduler::threadSync(int waitingThreadVehicleId, int requestedThreadVehicleId)
{
    if(waitingThreadVehicleId != vehicleID) // That wasn't me who sent this signal
    {
        if(requestedThreadVehicleId == vehicleID) // Someone is waiting for me.
        {
            std::cout << "Thread " << QThread::currentThreadId() << ": vehicleID" << waitingThreadVehicleId << " is waiting for vehicleID" << requestedThreadVehicleId << " (mine)" << std::endl;
            syncRequests.push_back(waitingThreadVehicleId);

        }
    }
    else // That was me who sent this signal, discard it
    {
        std::cout << "Thread " << QThread::currentThreadId() << ": that was me who sent that signal. Ignoring it..." << std::endl;
    }

}
