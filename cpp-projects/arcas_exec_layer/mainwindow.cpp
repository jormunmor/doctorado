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
        //std::cout << "Open Plan Action canceled by user." << std::endl;
        //std::cout << "Header text: " << verticalHeaderCell->text().toStdString() << std::endl;
    }

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

    /*

    // Create the colors and pen
    QColor boxColorArray[3];
    QColor penColorArray[3];
    //boxColorArray[0] = QColor(255, 131, 0, 50);
    //boxColorArray[1] = QColor(1, 92, 191, 50);
    //boxColorArray[2] = QColor(150, 222, 0, 70);
    boxColorArray[0] = QColor(255, 131, 0, 50);
    boxColorArray[1] = QColor(1, 92, 191, 50);
    boxColorArray[2] = QColor(150, 222, 0, 50);
    penColorArray[0] = QColor(255, 131, 0);
    penColorArray[1] = QColor(1, 92, 191);
    penColorArray[2] = QColor(150, 222, 0);
    QPen pen;
    pen.setWidthF(1.2);

    // Create a fake first bar
    QCPBars *previousBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
    int randomIndex = 0;
    pen.setColor(penColorArray[randomIndex]);
    previousBar->setPen(pen);
    previousBar->setBrush(boxColorArray[randomIndex]);
    customPlot->addPlottable(previousBar);

    QVector<double> previousBarData;
    previousBarData << 0;
    ticks << 1;
    previousBar->setData(ticks, previousBarData);

    // Lets print some boxes.
    for(int i=0; i<125; i++)
    {
        std::cout << "Looping at 300 msecs..." << std::endl;
        delay(100);
        QCPBarData oldBarData = previousBar->data()->value(1);
        std::cout << "Key: " << oldBarData.key << " Value: " << oldBarData.value << std::endl;
        QVector<double> newBarData;
        newBarData << oldBarData.value + 1;
        previousBar->setData(ticks, newBarData);
        ui->customPlot->replot();
        if(i==70)
        {
            QCPBars *anotherBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
            int randomIndex = 1;
            pen.setColor(penColorArray[randomIndex]);
            anotherBar->setPen(pen);
            anotherBar->setBrush(boxColorArray[randomIndex]);
            customPlot->addPlottable(anotherBar);

            QVector<double> anotherBarData;
            anotherBarData << 0;
            ticks << 1;
            anotherBar->setData(ticks, anotherBarData);
            anotherBar->moveAbove(previousBar);
            previousBar = anotherBar;

        }

    }

      */

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

    Goal goal23;
    geometry_msgs::Pose pose23;
    pose23.position.x = -6;
    pose23.position.y = -6;
    pose23.position.z = 0.25;
    pose23.orientation.w = 1;
    pose23.orientation.x = 0;
    pose23.orientation.y = 0;
    pose23.orientation.z = 0;
    goal23.pose = pose23;
    goal23.yaw = 0;

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

    locMap->insert(std::pair<int, Goal>(23, goal23));
    locMap->insert(std::pair<int, Goal>(1, goal1));

    // Position 23 is the hard-coded initial position present in the launch_simulator.launch.
    // -x -6.0 -y -6.0 -z 0.25  -R 0.0 -P 0.0 -Y 0.0

    return locMap;
}
