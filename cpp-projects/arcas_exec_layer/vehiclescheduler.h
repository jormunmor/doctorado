#ifndef SCHEDULER_H
#define SCHEDULER_H

#include <QObject>
#include <QVector>
#include <QStringList>
#include <QThread>
#include <QMetaType>

namespace Ui {

    class VehicleScheduler;

    enum OperationState {
        QUEUED,
        QUEUED_COOP,
        EXECUTING,
        EXECUTING_COOP,
        ABORTED,
        ABORTED_COOP,
        FINISHED,
        FINISHED_COOP
    };

    const QString UAV_IDENTIFIER = "uav";

}

class VehicleScheduler : public QObject
{
    Q_OBJECT
public:
    explicit VehicleScheduler(const int &id, const QVector<QStringList> &ops, const int &row, QObject *parent = 0);
    ~VehicleScheduler();
    
signals:
    void stateChanged(int row, int column, int state);
    void syncThread(int waitingThreadVehicleId, int requestedThreadVehicleId);

private slots:
    void execute();
    void threadSync(int waitingThreadVehicleId, int requestedThreadVehicleId);
    
private:
    int vehicleID; // Id of the vehicle for the scheduler.
    int tableRow; // The table row on which the uav is displayed.
    int waitingFor; // Id of the vehicle the scheduler is waiting for.
    QVector<QStringList> operations; // The operations the vehicle must execute.
    QVector<int> syncRequests;

};

#endif // SCHEDULER_H
