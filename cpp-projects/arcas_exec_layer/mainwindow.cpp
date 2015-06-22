#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "vehiclescheduler.h"
#include <unistd.h>
#include <iostream>
#include <QMessageBox>
#include <QFile>
#include <QFileDialog>
#include <QTime>
#include <math.h>

/// TODO: implement the closePlan action

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    // The table widget contains all the operations the UAVs have to do.
    // It is invisible until a plan is loaded. It also occupy all the
    // main window area.
    ui->tableWidget->setVisible(false);
    // Create first a map to store the operation list for each of the uavs.
    uavOperations = new QMap<int, QVector<QStringList> >();
    uavThreads = new QMap<int, QThread*>();
    ganttDialog = new GanttDialog(this);
    ganttDialog->setWindowTitle("Gantt Chart");
    //ganttDialog = new QDialog(this);

    //ganttPlot = new QCustomPlot(ganttDialog);
    //ganttDialog->show();

}

MainWindow::~MainWindow()
{
    std::cout << "Closing the application..." << std::endl;
    delete ui;
    delete uavOperations;
    //delete ganttPlot;
    delete ganttDialog;
    cleanThreads(); // This will also delete the uavThreads

}

void MainWindow::on_actionOpenPlan_triggered()
{
    QString fileName = QFileDialog::getOpenFileName(this,
        tr("Open Plan"), QDir::homePath(), tr("Text Files (*.txt)"));
    if(fileName != 0) // cancel button pressed
    {
        readPlanFile(fileName);

    } else
    {
        std::cout << "Open Plan Action canceled by user." << std::endl;

    }

}

void MainWindow::readPlanFile(QString fileName)
{
    // Open the plan file
    QFile planFile(fileName);

    if (!planFile.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        showMessageBox(QString("Unable to open selected file."));
        return;
    }

    // Parse the plan file.
    while(!planFile.atEnd())
    {
        // Get a line as a QString
        QByteArray aLine = planFile.readLine();
        QString line(aLine);
        // Eliminate unnecesary chars. If no '!' char is detected on any line, then exit (the file is not a valid JSHOP2 plan)
        int exclamationIndex = line.indexOf("!");
        if(exclamationIndex < 0)
        {
            showMessageBox(QString("The selected file is not a valid plan file."));
            return;

        }
        QString operation = line.mid(exclamationIndex + 1, line.size() - (exclamationIndex + 1) - 2); // -2 to exclude the ')' and '\n' chars
        // Check if 'assembly_plan_finished' operator reached (end of the plan).
        // To this to avoid inserting it in the table widget.
        if(operation.contains("assembly_plan_finished"))
        {
            break;
        }

        // Split the fields of the operation by whitespace
        QStringList decomposedOperation = operation.split(" ");

        // Allmost all operators have as second field the executing UAV ID.
        // However, some operators such as do_synchro_mission have as second
        // field other thing. Such logic is implemented next.

        // Special case: do_synchro_mission operator. Will remove the 'do_synchro_mission' field from
        // the QStringList, and retain only the 'assemble'. By this way, all the QStringList from all operators will have
        // as first field the operator name, as second field the executing UAV ID, an as third the another executing UAV ID
        // in case of cooperative operations.
        if(operation.contains("do_synchro_mission assemble"))
        {
            decomposedOperation = decomposedOperation.mid(1);

        }

        // All cases: look if the third field is a UAV ID. All UAV IDs contains
        // the keyword 'uav'. If there is a second UAV ID, then the operation must
        // appear in two uav queues doe to be cooperative.

        // We first manage the first UAV. We know that it exists, all operations are done at least by one.
        int firstUAVId = decomposedOperation.at(1).right(decomposedOperation.at(1).size() - Ui::UAV_IDENTIFIER.size()).toInt();
        // If the entry does not exists, QMap creates one automatically instead of returning null.
        // Insert the decomposedOperation into the map by retrieving the vector of the given UAV
        QVector<QStringList> vect1 = uavOperations->value(firstUAVId);
        vect1.push_back(decomposedOperation);
        uavOperations->insert(firstUAVId, vect1);

        // In the JSHOP2 plan the sync operator will appear as:
        // ..
        // (sync uav1 uav2)
        // ..
        // (sync uav2 uav1)
        // The sync operation is the only 'cooperative' operation that appears once
        // for each of the involved UAVs. In fact, the operator is not 'really' cooperative, it means that
        // uav1 requires synchronization with uav2 in the first line. Thus, inserting it to the first UAV that appears
        // in the QStringList is enough. Other way, the same operation will appear multiple
        // times for each of the UAVs.

        if(operation.contains("sync "))
        {

            continue;

        }

        // Now we manage the second UAV ID, if it exists.
        if(decomposedOperation.at(2).contains(Ui::UAV_IDENTIFIER)) // It exists
        {
            int secondUAVId = decomposedOperation.at(2).right(decomposedOperation.at(2).size() - Ui::UAV_IDENTIFIER.size()).toInt();
            // If the entry does not exists, QMap creates one automatically instead of returning null.
            // Insert the decomposedOperation into the map by retrieving the vector of the given UAV
            QVector<QStringList> vect2 = uavOperations->value(secondUAVId);
            vect2.push_back(decomposedOperation);
            uavOperations->insert(secondUAVId, vect2);

        }

    }

    // Close the plan file
    planFile.close();

    // Check the created QMap
    /*
    std::cout << "QMap value: " << uavOperations->size() << std::endl;
    for(int i=0; i<uavOperations->size(); i++)
    {
        QVector<QStringList> aVector = uavOperations->value(i+1);
        std::cout << "Number of operation for uav" << (i+1) << ": " << aVector.size() << std::endl;
    }
    */

    // Fill the table widget
    fillTableWidget();

    // All ok, set visible the table widget, the gantt chart and enable the execute action
    ui->tableWidget->setVisible(true);
    ui->actionExecute->setEnabled(true);
    QPoint point = this->pos();
    point.setX(point.x() + this->width() + 1); // Set the gantt dialog at the right of the main window.
    ganttDialog->move(point);
    setGanttPlot();
    ganttDialog->show();
    //ganttDialog->ui->customPlot;

}



void MainWindow::showMessageBox(QString message)
{
    QMessageBox Msgbox;

    Msgbox.setText(message);
    Msgbox.exec();

}

void MainWindow::fillTableWidget()
{
    // Check the length of the biggest queue, as the number
    // of columns for the table will be equal.

    int maxLength = 0;

    QMap<int, QVector<QStringList> >::iterator it;
    for (it = uavOperations->begin(); it != uavOperations->end(); it++)
    {
        int length = it.value().size();
        if(length > maxLength)
        {
            maxLength = length;

        }

    }

    // Set the table dimensions
    ui->tableWidget->setRowCount(uavOperations->size());
    ui->tableWidget->setColumnCount(maxLength);

    // Set the column labels
    QStringList horizontalLabels;
    for(int i=1; i<=maxLength; i++)
    {
        horizontalLabels.push_back(QString("Operation ") + QString::number(i));
    }
    ui->tableWidget->setHorizontalHeaderLabels(horizontalLabels);

    // Set the table content and row labels.
    // uav IDs usually start at 1, but this may not be true, so we
    // new a row counter to move among the lines of the table. Indexing
    // them with the uav ID is not a good idea.
    QStringList verticalLabels;
    int row = 0;
    for (it = uavOperations->begin(); it != uavOperations->end(); it++)
    {
        // Retrieve the queue for the given vehicle
        QVector<QStringList> queue = it.value();
        // Put some text on each of the cells, and set the start color to red
        for(int j=0; j<queue.size(); j++)
        {
            QStringList operation = queue.at(j);
            QTableWidgetItem* item = new QTableWidgetItem(operation.at(0));
            item->setTextAlignment(Qt::AlignCenter);
            // All operations have the same starting dark color, except cooperatives
            // which use other. To see if cooperative, check if the third
            // field is a uav identifier.
            if(operation.size()>=3 && operation.at(2).contains(Ui::UAV_IDENTIFIER))
            {
                item->setBackground(Qt::darkMagenta);
            }
            else
            {
                item->setBackground(Qt::darkCyan);
            }
            item->setTextColor(Qt::white);
            item->setToolTip(operation.join(" "));
            ui->tableWidget->setItem(row, j, item);

        }

        verticalLabels.push_back(Ui::UAV_IDENTIFIER + QString::number(it.key()));
        row++;
    }    
    ui->tableWidget->setVerticalHeaderLabels(verticalLabels);

}

/*
  This function, which executes after the Execute menu item is pressed,
  create the threads and scheduler objects that will run inside. It is
  important to note that the run function of the threads is executed on
  a new thread, but the slots of the threads are executed inside the main
  application thread. The scheduler objects have been moved to the threads,
  and thus its slots will be executed on the threads, and not inside the
  main application thread. When connecting signals, this must me taken into
  account.
*/
void MainWindow::on_actionExecute_triggered()
{
    // First we clean the previous threads.
    cleanThreads();
    // Create a new threads map.
    uavThreads = new QMap<int, QThread*>();

    QMap<int, QVector<QStringList> >::iterator it;
    int row = 0; // The row of the table on which each uav is displayed
    for (it = uavOperations->begin(); it != uavOperations->end(); it++)
    {
        QThread* t = new QThread();
        VehicleScheduler* sch = new VehicleScheduler(it.key(), it.value(), row, generateLocationsMap());
        sch->moveToThread(t);
        connect(t, SIGNAL(finished()), sch, SLOT(deleteLater()));
        connect(this, SIGNAL(startSchedulers()), sch, SLOT(execute()));
        connect(this, SIGNAL(threadSync(int,int)), sch, SLOT(threadSync(int,int)));
        connect(sch, SIGNAL(stateChanged(int,int,int)), this, SLOT(updateTable(int,int,int)));
        connect(sch, SIGNAL(syncThread(int,int)), this, SLOT(syncThread(int,int)));
        connect(sch, SIGNAL(newGanttAction(int,int)), this, SLOT(addGanttBar(int,int)));
        connect(sch, SIGNAL(updateGantt(int, float)), this, SLOT(updateGanttPlot(int, float)));
        t->start();
        uavThreads->insert(it.key(), t);
        row++;

    }

    // Start the schedulers objects (the threads are running).
    emit(startSchedulers());
    //sleep(3);
    //emit(threadSync(1, 4)); // Simulation of vehicleID 1 waiting for vehicleID 4.

}

void MainWindow::syncThread(int waitingThreadVehicleId, int requestedThreadVehicleId)
{
    emit(threadSync(waitingThreadVehicleId, requestedThreadVehicleId));
}

void MainWindow::setGanttPlot()
{
    // Prepare Y-axis with vehicle labels:
    QVector<double> ticks;
    QVector<QString> labels;
    for(int i=0; i<ui->tableWidget->rowCount(); i++)
    {
        QTableWidgetItem* verticalHeaderCell = ui->tableWidget->verticalHeaderItem(i);
        labels << verticalHeaderCell->text();
        ticks << (i + 1);

        // Set the QCPBar vector for each vehicle, with an initial capacity for 10 bars.
        QVector<QCPBars*> newVector;
        plotBars.push_back(newVector);

    }

    // Configure the y-Axis.
    ganttDialog->customPlot->yAxis->setAutoTicks(false);
    ganttDialog->customPlot->yAxis->setAutoTickLabels(false);
    ganttDialog->customPlot->yAxis->setTickVector(ticks);
    ganttDialog->customPlot->yAxis->setTickVectorLabels(labels);
    ganttDialog->customPlot->yAxis->setTickLabelRotation(60);
    ganttDialog->customPlot->yAxis->setSubTickCount(0);
    ganttDialog->customPlot->yAxis->setTickLength(0, 4);
    ganttDialog->customPlot->yAxis->grid()->setVisible(true);
    ganttDialog->customPlot->yAxis->setRange(0, ticks.last() + 1);

    // Prepare X-axis with time units:
    ganttDialog->customPlot->xAxis->setRange(0, 12);
    ganttDialog->customPlot->xAxis->setAutoTickStep(false);
    ganttDialog->customPlot->xAxis->setTickStep(1);
    //ganttDialog->customPlot->xAxis->setAutoSubTicks(false);
    //ganttDialog->customPlot->xAxis->setSubTickCount(1);
    //ganttDialog->customPlot->xAxis->setSubTickLength(0.5);
    //ganttDialog->customPlot->xAxis->setAutoTickCount(1);
    ganttDialog->customPlot->xAxis->setPadding(5); // a bit more space to the left border
    ganttDialog->customPlot->xAxis->setLabel("Time (s)");
    //ganttDialog->customPlot->xAxis->grid()->setSubGridVisible(true);

    QPen gridPen;
    gridPen.setStyle(Qt::SolidLine);
    gridPen.setColor(QColor(0, 0, 0, 25));
    ganttDialog->customPlot->yAxis->grid()->setPen(gridPen);
    gridPen.setStyle(Qt::DotLine);
    ganttDialog->customPlot->yAxis->grid()->setSubGridPen(gridPen);

    // Set the chart properties: alignment, drag and zoom.
    ganttDialog->customPlot->axisRect()->insetLayout()->setInsetAlignment(0, Qt::AlignTop|Qt::AlignHCenter);
    ganttDialog->customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom);

    // Now, for each of the vehicles, we create a fake bar that will be the initial on which
    // the rest will be stacked.
    for(int i=0; i<ui->tableWidget->rowCount(); i++)
    {
        QCPBars *initialBar = new QCPBars(ganttDialog->customPlot->yAxis, ganttDialog->customPlot->xAxis);
        QVector<double> initialBarData;
        QVector<double> initialTicks;
        initialBarData << 0;
        ticks << i+1;
        initialBar->setData(initialTicks, initialBarData);
        ganttDialog->customPlot->addPlottable(initialBar);
        plotBars[i].push_back(initialBar);

    }

}

void MainWindow::addGanttBar(int row, int action)
{

    //ROS_INFO("addGanttBar for row: %d", row);
    // Recover the previous bar, because the new will be stacked on top of it
    //ROS_INFO("row bar vector size: %d", plotBars.at(row).size());
    QCPBars *previousBar = plotBars.at(row).last();
    if(previousBar == NULL)
        ROS_INFO("previousBar is NULL");

    // Create the new bar
    QCPBars *newBar = new QCPBars(ganttDialog->customPlot->yAxis, ganttDialog->customPlot->xAxis);
    QVector<double> newBarData;
    QVector<double> newBarTicks;
    newBarData << 0;
    newBarTicks << row + 1;
    newBar->setData(newBarTicks, newBarData);
    ganttDialog->customPlot->addPlottable(newBar);
    newBar->moveAbove(previousBar);
    plotBars[row].push_back(newBar);

    // Set the pen for the new bar
    QPen pen;
    pen.setWidthF(1.2);
    switch(action)
    {
        case TAKEOFF: pen.setColor(QColor(255, 131, 0));
                      newBar->setBrush(QColor(255, 131, 0, 50));
                      break;

        case MOVE:    pen.setColor(QColor(1, 92, 191));
                      newBar->setBrush(QColor(1, 92, 191, 50));
                      break;
        case PICK:    pen.setColor(QColor(190, 0, 190));
                      newBar->setBrush(QColor(190, 0, 190, 50));
                      break;
        case PLACE:   pen.setColor(QColor(0, 146, 30));
                      newBar->setBrush(QColor(0, 146, 30, 50));
                      break;

        default: break;

    }

    newBar->setPen(pen);

    //ROS_INFO("Exiting addGanttBar");

}

void MainWindow::updateGanttPlot(int row, float secs)
{
    //ROS_INFO("Update Gantt for row %d with %lf secs", row, secs);

    // Get the bar to update, it is the last of the vector
    QCPBars* bar = plotBars[row].last();
    QCPBarData oldBarData = bar->data()->value(row + 1);
    //ROS_INFO("Old bar data: %lf", oldBarData.value);
    QVector<double> newBarData;
    newBarData << oldBarData.value + secs;
    //ROS_INFO("New bar data: %lf", newBarData[0]);
    QVector<double> ticks;
    ticks.push_back(row + 1);
    bar->setData(ticks, newBarData);
    ganttDialog->customPlot->replot();

    //ROS_INFO("Exiting updateGanttPlot");


}

void MainWindow::updateTable(int tableRow, int tableColumn, int state)
{

    QTableWidgetItem* cell = ui->tableWidget->item(tableRow, tableColumn);

    switch(state)
    {
        case Ui::QUEUED: cell->setBackground(Qt::darkCyan); break;
        case Ui::QUEUED_COOP: cell->setBackground(Qt::darkMagenta); break;
        case Ui::EXECUTING: cell->setBackground(Qt::darkYellow); break;
        case Ui::EXECUTING_COOP: cell->setBackground(Qt::yellow); break;
        case Ui::SYNCHRONIZING: cell->setBackground(Qt::blue); break;
        case Ui::ABORTED: cell->setBackground(Qt::darkRed); break;
        case Ui::ABORTED_COOP: cell->setBackground(Qt::red); break;
        case Ui::FAILED: cell->setBackground(Qt::darkRed); break;
        case Ui::FAILED_COOP: cell->setBackground(Qt::red); break;
        case Ui::FINISHED: cell->setBackground(Qt::darkGreen); break;
        case Ui::FINISHED_COOP: cell->setBackground(Qt::green); break;
        default: break;

    }

}

/*
  This function is used to safely clean the threads. It should suffice
  with deleting the uavThreads map, but doing this does not hurt.
  If the uavThreads was not previously created, then the job is done.
*/
void MainWindow::cleanThreads()
{
    if(uavThreads == NULL)
    {
        return;

    }

    QMap<int, QThread* >::iterator it;
    for (it = uavThreads->begin(); it != uavThreads->end(); it++)
    {
        QThread* thread = it.value();
        if(thread != NULL)
        {
            thread->quit();

        }

    }

    delete uavThreads;

}

/*
  To generate a map from locations to poses. Hard-coded by the moment.

  */
std::map<int, Goal>* MainWindow::generateLocationsMap()
{
    std::map<int,Goal>* locMap = new std::map<int, Goal>;

    /*
    Goal goal23;
    geometry_msgs::Pose pose23;
    pose23.position.x = -4 - 0.3 + 0.075; // placed base x_pos - placed_base_x_dim/2 + picked_part_x_dim/2
    pose23.position.y = -6;
    //pose23.position.z = 0.25;
    //pose23.position.z = 1.24 + 0.75 + 0.25;
    //pose23.position.z = 1.24 + 0.75;
    pose23.position.z = 0.21 + 0.21 + 0.75 - 0.05; // placed base height + picked part height + flying offset - placed base handle height
    pose23.orientation.x = 0;
    pose23.orientation.y = 0;
    pose23.orientation.z = 0;
    pose23.orientation.w = 1;
    goal23.pose = pose23;
    goal23.yaw = M_PI - M_PI/4.0;
    */

    /*
    Goal goal1;
    geometry_msgs::Pose pose1;
    pose1.position.x = -4;
    pose1.position.y = -4;
    pose1.position.z = 2;
    pose1.orientation.x = 0;
    pose1.orientation.y = 0;
    pose1.orientation.z = 0.383; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose1.orientation.w = 0.924;
    goal1.pose = pose1;
    goal1.yaw = M_PI/4.0;
    */

    /*
    Goal goal80;
    geometry_msgs::Pose pose80;
    pose80.position.x = -6;
    pose80.position.y = -4;
    pose80.position.z = 2;
    pose80.orientation.x = 0;
    pose80.orientation.y = 0;
    pose80.orientation.z = 0.383; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose80.orientation.w = 0.924;
    goal80.pose = pose80;
    goal80.yaw = M_PI/4.0;
    */

    Goal goal1; // First part initial pos
    geometry_msgs::Pose pose1;
    pose1.position.x = -6.25;
    pose1.position.y = -6.5;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose1.position.z = 1.215 + 0.75; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose1.orientation.x = 0;
    pose1.orientation.y = 0;
    pose1.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose1.orientation.w = 1;
    goal1.pose = pose1;
    goal1.yaw = M_PI - M_PI/4.0; // Add an offset

    Goal goal2; // First part final pos
    geometry_msgs::Pose pose2;
    pose2.position.x = 4.841;
    pose2.position.y = -6.156;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose2.position.z = 0.21 + 0.21 + 0.75 - 0.05; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose2.orientation.x = 0;
    pose2.orientation.y = 0;
    pose2.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose2.orientation.w = 1;
    goal2.pose = pose2;
    goal2.yaw = M_PI; // Add an offset

    Goal goal3; // First part initial pos
    geometry_msgs::Pose pose3;
    pose3.position.x = -6.75;
    pose3.position.y = -6.5;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose3.position.z = 1.215 + 0.75; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose3.orientation.x = 0;
    pose3.orientation.y = 0;
    pose3.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose3.orientation.w = 1;
    goal3.pose = pose3;
    goal3.yaw = M_PI - M_PI/4.0; // Add an offset

    Goal goal4; // First part final pos
    geometry_msgs::Pose pose4;
    pose4.position.x = 5.840;
    pose4.position.y = -5.150;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose4.position.z = 0.21 + 0.21 + 0.75 - 0.05; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose4.orientation.x = 0;
    pose4.orientation.y = 0;
    pose4.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose4.orientation.w = 1;
    goal4.pose = pose4;
    goal4.yaw = M_PI; // Add an offset

    Goal goal5; // First part initial pos
    geometry_msgs::Pose pose5;
    pose5.position.x = -7.25;
    pose5.position.y = -6.5;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose5.position.z = 1.215 + 0.75; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose5.orientation.x = 0;
    pose5.orientation.y = 0;
    pose5.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose5.orientation.w = 1;
    goal5.pose = pose5;
    goal5.yaw = M_PI - M_PI/4.0; // Add an offset

    Goal goal6; // First part final pos
    geometry_msgs::Pose pose6;
    pose6.position.x = 5.158;
    pose6.position.y = -5.846;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose6.position.z = 0.21 + 0.21 + 0.75 - 0.05; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose6.orientation.x = 0;
    pose6.orientation.y = 0;
    pose6.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose6.orientation.w = 1;
    goal6.pose = pose6;
    goal6.yaw = M_PI; // Add an offset

    /*
    Goal goal11; // Part center position
    geometry_msgs::Pose pose10;
    pose10.position.x = -7;
    pose10.position.y = 0;
    //pose10.position.z = 1.10 + 0.75; // Add an offset, this is the center of the bar PARA LA BARRA
    pose10.position.z = 1.215 + 0.75; // Add an offset, this is the center of the bar PARA MI PIEZA
    //pose10.position.z = 1.24 + 0.75 + 0.25; // Add an offset, this is the center of the box
    //pose10.position.z = 1.24 + 0.75; // Add an offset, this is the center of the box
    pose10.orientation.x = 0;
    pose10.orientation.y = 0;
    pose10.orientation.z = 0; // The lack of decimals make this quaternion incorrect, use yaw instead.
    pose10.orientation.w = 1;
    goal10.pose = pose10;
    goal10.yaw = M_PI - M_PI/4.0; // Add an offset
    */

    locMap->insert(std::pair<int, Goal>(1, goal1));
    locMap->insert(std::pair<int, Goal>(2, goal2));
    locMap->insert(std::pair<int, Goal>(3, goal3));
    locMap->insert(std::pair<int, Goal>(4, goal4));
    locMap->insert(std::pair<int, Goal>(5, goal5));
    locMap->insert(std::pair<int, Goal>(6, goal6));
    //locMap->insert(std::pair<int, Goal>(23, goal23));
    //locMap->insert(std::pair<int, Goal>(80, goal80));
    //locMap->insert(std::pair<int, Goal>(10, goal10));

    // Position 23 is the hard-coded initial position present in the launch_simulator.launch.
    // -x -6.0 -y -6.0 -z 0.25  -R 0.0 -P 0.0 -Y 0.0

    return locMap;
}
