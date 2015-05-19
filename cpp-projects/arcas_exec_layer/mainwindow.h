#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QMap>
#include <QVector>
#include <QStringList>
#include <QThread>
#include <QDialog>
#include "vehiclescheduler.h"
#include <map>
#include "ganttdialog.h"
//#include "qcustomplot.h"

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
    void addGanttBar(int row, int action);
    void updateGanttPlot(int row);

private:
    void showMessageBox(QString message);
    void readPlanFile(QString fileName);
    void fillTableWidget();
    void cleanThreads();
    std::map<int, Goal> *generateLocationsMap();
    void setGanttPlot();
    Ui::MainWindow *ui;
    GanttDialog *ganttDialog;
    //QDialog *ganttDialog;
    //QCustomPlot *ganttPlot;
    QMap<int, QVector<QStringList> > *uavOperations;
    QMap<int, QThread* > *uavThreads;
    static const QString UAV_IDENTIFIER;
    QVector< QVector<QCPBars *> > plotBars; // This will hold the QCPBars for each of the rows (vehicles) of the table

};

#endif // MAINWINDOW_H
