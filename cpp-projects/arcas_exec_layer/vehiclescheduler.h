#ifndef SCHEDULER_H
#define SCHEDULER_H

#include <QObject>
#include <QVector>
#include <QStringList>
#include <QThread>
#include <QMetaType>
#include <QTimer>
// The followings are needed to use the actionlib client
#include "ArcasExecLayerClient.h"
#include "constants.h"
#include <map>

namespace Ui {

    class VehicleScheduler;

    enum OperationState{
        QUEUED,
        QUEUED_COOP,
        EXECUTING,
        EXECUTING_COOP,
        SYNCHRONIZING,
        ABORTED,
        ABORTED_COOP,
        FAILED,
        FAILED_COOP,
        FINISHED,
        FINISHED_COOP
    };

    const QString UAV_IDENTIFIER = "uav";

}

class VehicleScheduler : public QObject
{
    Q_OBJECT
public:
    explicit VehicleScheduler(const int &id, const QVector<QStringList> &ops, const int &row, std::map<int, Goal> *locMap, QObject *parent = 0);
    ~VehicleScheduler();
    void activeCb(void);
    void feedbackCb(const arcas_exec_layer::ArcasExecLayerFeedbackConstPtr& feedback);
    void doneCb(const actionlib::SimpleClientGoalState& state, const arcas_exec_layer::ArcasExecLayerResultConstPtr& result);

signals:
    void stateChanged(int row, int column, int state);
    void syncThread(int waitingThreadVehicleId, int requestedThreadVehicleId);
    void newGanttAction(int row, int action);
    void updateGantt(int row);

private slots:
    void execute();
    void threadSync(int waitingThreadVehicleId, int requestedThreadVehicleId);
    void updateGanttAction();

private:
    int vehicleID; // Id of the vehicle for the scheduler.
    int tableRow; // The table row on which the uav is displayed.
    int waitingFor; // Id of the vehicle the scheduler is waiting for.
    int currentOp; // The number of the operation the vehicle is currently executing.
    int currentOpType; // The type of the current operation (STANDALONE or COOPERATIVE).
    ActionType currentActionType; // The ype of the current action (TAKEOFF, MOVE, LAND)
    std::map<int, Goal> *locationsMap; // each location identifier is associated with a position and orientation.
    QVector<QStringList> operations; // The operations the vehicle must execute.
    QVector<int> syncRequests;
    QTimer *timer;


};

#endif // SCHEDULER_H
