#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QMap>
#include <QVector>
#include <QStringList>
#include <QThread>
#include "vehiclescheduler.h"
#include <map>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT
    
public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

signals:
    void startSchedulers();
    void threadSync(int waitingThreadVehicleId, int requestedThreadVehicleId);
    
public slots:
    void on_actionOpenPlan_triggered();
    void on_actionExecute_triggered();
    void updateTable(int tableRow, int tableColumn, int state);
    void syncThread(int waitingThreadVehicleId, int requestedThreadVehicleId);

private:
    void showMessageBox(QString message);
    void readPlanFile(QString fileName);
    void fillTableWidget();
    void cleanThreads();
    std::map<int, geometry_msgs::Pose> *generateLocationsMap();
    Ui::MainWindow *ui;
    QMap<int, QVector<QStringList> > *uavOperations;
    QMap<int, QThread* > *uavThreads;
    static const QString UAV_IDENTIFIER;

};

#endif // MAINWINDOW_H
