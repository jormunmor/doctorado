/***************************************************************************
**                                                                        **
**  QCustomPlot, an easy to use, modern plotting widget for Qt            **
**  Copyright (C) 2011, 2012, 2013, 2014 Emanuel Eichhammer               **
**                                                                        **
**  This program is free software: you can redistribute it and/or modify  **
**  it under the terms of the GNU General Public License as published by  **
**  the Free Software Foundation, either version 3 of the License, or     **
**  (at your option) any later version.                                   **
**                                                                        **
**  This program is distributed in the hope that it will be useful,       **
**  but WITHOUT ANY WARRANTY; without even the implied warranty of        **
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         **
**  GNU General Public License for more details.                          **
**                                                                        **
**  You should have received a copy of the GNU General Public License     **
**  along with this program.  If not, see http://www.gnu.org/licenses/.   **
**                                                                        **
****************************************************************************
**           Author: Emanuel Eichhammer                                   **
**  Website/Contact: http://www.qcustomplot.com/                          **
**             Date: 27.12.14                                             **
**          Version: 1.3.0                                                **
****************************************************************************/

/************************************************************************************************************
**                                                                                                         **
**  This is the example code for QCustomPlot.                                                              **
**                                                                                                         **
**  It demonstrates basic and some advanced capabilities of the widget. The interesting code is inside     **
**  the "setup(...)Demo" functions of MainWindow.                                                          **
**                                                                                                         **
**  In order to see a demo in action, call the respective "setup(...)Demo" function inside the             **
**  MainWindow constructor. Alternatively you may call setupDemo(i) where i is the index of the demo       **
**  you want (for those, see MainWindow constructor comments). All other functions here are merely a       **
**  way to easily create screenshots of all demos for the website. I.e. a timer is set to successively     **
**  setup all the demos and make a screenshot of the window area and save it in the ./screenshots          **
**  directory.                                                                                             **
**                                                                                                         **
*************************************************************************************************************/

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QDebug>
#include <QDesktopWidget>
#include <QScreen>
#include <QMessageBox>
#include <QMetaEnum>
#include <iostream>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setGeometry(400, 250, 542, 390);
    //setupDemo(1);
  
    // for making screenshots of the current demo or all demos (for website screenshots):
    //QTimer::singleShot(1500, this, SLOT(allScreenShots()));
    //QTimer::singleShot(4000, this, SLOT(screenShot()));
}

void MainWindow::setupDemo(int demoIndex)
{
    switch(demoIndex)
    {
        case 0: setupBarChartDemo(ui->customPlot); break;
        case 1: setupAutomaticBarChartDemo(ui->customPlot); break;
        case 2: setupAutomaticBarChartDemo2(ui->customPlot); break;
    }
    setWindowTitle("ArcasExecLayer: Gantt Demo");
    statusBar()->clearMessage();
    currentDemoIndex = demoIndex;
    //ui->customPlot->replot();
}

void MainWindow::setupAutomaticBarChartDemo(QCustomPlot *customPlot)
{
    QTime now = QTime::currentTime();
    qsrand(now.msec());

    // Common part of all charts.

    // Prepare Y-axis with vehicle labels:
    QVector<double> ticks;
    QVector<QString> labels;
    ticks << 1;
    labels << "UAV1";
    customPlot->yAxis->setAutoTicks(false);
    customPlot->yAxis->setAutoTickLabels(false);
    customPlot->yAxis->setTickVector(ticks);
    customPlot->yAxis->setTickVectorLabels(labels);
    customPlot->yAxis->setTickLabelRotation(60);
    customPlot->yAxis->setSubTickCount(0);
    customPlot->yAxis->setTickLength(0, 4);
    customPlot->yAxis->grid()->setVisible(true);
    customPlot->yAxis->setRange(0, 2);

    // Prepare X-axis with time units:
    customPlot->xAxis->setRange(0, 12.1);
    customPlot->xAxis->setPadding(5); // a bit more space to the left border
    customPlot->xAxis->setLabel("Time (s)");
    customPlot->xAxis->grid()->setSubGridVisible(true);
    QPen gridPen;
    gridPen.setStyle(Qt::SolidLine);
    gridPen.setColor(QColor(0, 0, 0, 25));
    customPlot->yAxis->grid()->setPen(gridPen);
    gridPen.setStyle(Qt::DotLine);
    customPlot->yAxis->grid()->setSubGridPen(gridPen);

    // Set the chart properties: alignment, drag and zoom.
    customPlot->axisRect()->insetLayout()->setInsetAlignment(0, Qt::AlignTop|Qt::AlignHCenter);
    customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom);

    // Create the colors and pen
    QColor boxColorArray[3];
    QColor penColorArray[3];
    //boxColorArray[0] = QColor(255, 131, 0, 50);
    //boxColorArray[1] = QColor(1, 92, 191, 50);
    //boxColorArray[2] = QColor(150, 222, 0, 70);
    boxColorArray[0] = QColor(255, 131, 0);
    boxColorArray[1] = QColor(1, 92, 191);
    boxColorArray[2] = QColor(150, 222, 0);
    penColorArray[0] = QColor(255, 131, 0);
    penColorArray[1] = QColor(1, 92, 191);
    penColorArray[2] = QColor(150, 222, 0);
    QPen pen;
    pen.setWidthF(1.2);

    // Create a fake first bar
    QCPBars *previousBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
    customPlot->addPlottable(previousBar);

    // Lets print 12 boxes.
    for(int i=0; i<125; i++)
    {
        std::cout << "Looping at 300 msecs..." << std::endl;
        delay(100);
        //int randomIndex =  qrand() % 3;
        int randomIndex =  (i<25)? 0 : (i<100)? 1 : 2;
        pen.setColor(penColorArray[randomIndex]);
        QCPBars *currentBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
        currentBar->setPen(pen);
        currentBar->setBrush(boxColorArray[randomIndex]);
        customPlot->addPlottable(currentBar);
        QVector<double> currentBarData;
        currentBarData << 1;
        QVector<double> ticks;
        ticks << 1;
        currentBar->setData(ticks, currentBarData);
        currentBar->moveAbove(previousBar);
        previousBar = currentBar;
        ui->customPlot->replot();

    }

}

void MainWindow::setupAutomaticBarChartDemo2(QCustomPlot *customPlot)
{
    QTime now = QTime::currentTime();
    qsrand(now.msec());

    // Common part of all charts.

    // Prepare Y-axis with vehicle labels:
    QVector<double> ticks;
    QVector<QString> labels;
    ticks << 1;
    labels << "UAV1";
    customPlot->yAxis->setAutoTicks(false);
    customPlot->yAxis->setAutoTickLabels(false);
    customPlot->yAxis->setTickVector(ticks);
    customPlot->yAxis->setTickVectorLabels(labels);
    customPlot->yAxis->setTickLabelRotation(60);
    customPlot->yAxis->setSubTickCount(0);
    customPlot->yAxis->setTickLength(0, 4);
    customPlot->yAxis->grid()->setVisible(true);
    customPlot->yAxis->setRange(0, 2);

    // Prepare X-axis with time units:
    customPlot->xAxis->setRange(0, 12.1);
    customPlot->xAxis->setPadding(5); // a bit more space to the left border
    customPlot->xAxis->setLabel("Time (s)");
    customPlot->xAxis->grid()->setSubGridVisible(true);
    QPen gridPen;
    gridPen.setStyle(Qt::SolidLine);
    gridPen.setColor(QColor(0, 0, 0, 25));
    customPlot->yAxis->grid()->setPen(gridPen);
    gridPen.setStyle(Qt::DotLine);
    customPlot->yAxis->grid()->setSubGridPen(gridPen);

    // Set the chart properties: alignment, drag and zoom.
    customPlot->axisRect()->insetLayout()->setInsetAlignment(0, Qt::AlignTop|Qt::AlignHCenter);
    customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom);

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

}

void MainWindow::setupBarChartDemo(QCustomPlot *customPlot)
{
  demoName = "Bar Chart Demo";
  // Create empty bar chart objects.
  /*
  QCPBars *regen = new QCPBars(customPlot->xAxis, customPlot->yAxis);
  QCPBars *nuclear = new QCPBars(customPlot->xAxis, customPlot->yAxis);
  QCPBars *fossil = new QCPBars(customPlot->xAxis, customPlot->yAxis);
  */
  QCPBars *landBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  QCPBars *moveBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  QCPBars *takeOffBar = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  customPlot->addPlottable(landBar);
  customPlot->addPlottable(moveBar);
  customPlot->addPlottable(takeOffBar);

  // FOR UAV8
  QCPBars *takeOffBar8 = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  QCPBars *takeOffBar8bis = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  QCPBars *moveBar8 = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  QCPBars *landBar8 = new QCPBars(customPlot->yAxis, customPlot->xAxis);
  customPlot->addPlottable(takeOffBar8);
  QPen pen;
  pen.setWidthF(1.2);
  pen.setColor(QColor(255, 131, 0));
  takeOffBar8->setPen(pen);
  takeOffBar8->setBrush(QColor(255, 131, 0, 50));
  customPlot->addPlottable(moveBar8);
  pen.setColor(QColor(1, 92, 191));
  moveBar8->setPen(pen);
  moveBar8->setBrush(QColor(1, 92, 191, 50));
  customPlot->addPlottable(landBar8);
  pen.setColor(QColor(150, 222, 0));
  landBar8->setPen(pen);
  landBar8->setBrush(QColor(150, 222, 0, 70));
  customPlot->addPlottable(takeOffBar8bis);
  pen.setColor(QColor(255, 131, 0));
  takeOffBar8bis->setPen(pen);
  takeOffBar8bis->setBrush(QColor(255, 131, 0, 50));
  moveBar8->moveAbove(takeOffBar8);
  landBar8->moveAbove(moveBar8);
  takeOffBar8bis->moveAbove(landBar8);
  QVector<double> takeOffBar8Data, moveBar8Data, landBar8Data, takeOffBar8bisData;
  takeOffBar8Data << 1;
  moveBar8Data << 2;
  landBar8Data << 3;
  takeOffBar8bisData << 1.5;
  QVector<double> ticks8;
  ticks8 << 4;
  takeOffBar8->setData(ticks8, takeOffBar8Data);
  moveBar8->setData(ticks8, moveBar8Data);
  landBar8->setData(ticks8, landBar8Data);
  takeOffBar8bis->setData(ticks8, takeOffBar8bisData);

  // Set names and colors for bars.
  takeOffBar->setName("TAKEOFF");
  pen.setColor(QColor(255, 131, 0));
  takeOffBar->setPen(pen);
  takeOffBar->setBrush(QColor(255, 131, 0, 50));

  moveBar->setName("MOVE");
  pen.setColor(QColor(1, 92, 191));
  moveBar->setPen(pen);
  moveBar->setBrush(QColor(1, 92, 191, 50));

  landBar->setName("LAND");
  pen.setColor(QColor(150, 222, 0));
  landBar->setPen(pen);
  landBar->setBrush(QColor(150, 222, 0, 70));
  // stack bars ontop of each other:
  moveBar->moveAbove(takeOffBar);
  landBar->moveAbove(moveBar);
  
  // prepare y axis with vehicle labels:
  QVector<double> ticks;
  QVector<QString> labels;
  ticks << 1 << 2 << 3 << 4;
  labels << "UAV1" << "UAV2" << "UAV3" << "UAV4";
  customPlot->yAxis->setAutoTicks(false);
  customPlot->yAxis->setAutoTickLabels(false);
  customPlot->yAxis->setTickVector(ticks);
  customPlot->yAxis->setTickVectorLabels(labels);
  customPlot->yAxis->setTickLabelRotation(60);
  customPlot->yAxis->setSubTickCount(0);
  customPlot->yAxis->setTickLength(0, 4);
  customPlot->yAxis->grid()->setVisible(true);
  customPlot->yAxis->setRange(0, 5);
  
  // prepare X axis:
  customPlot->xAxis->setRange(0, 12.1);
  customPlot->xAxis->setPadding(5); // a bit more space to the left border
  customPlot->xAxis->setLabel("Time (s)");
  customPlot->xAxis->grid()->setSubGridVisible(true);
  QPen gridPen;
  gridPen.setStyle(Qt::SolidLine);
  gridPen.setColor(QColor(0, 0, 0, 25));
  customPlot->yAxis->grid()->setPen(gridPen);
  gridPen.setStyle(Qt::DotLine);
  customPlot->yAxis->grid()->setSubGridPen(gridPen);
  
  // Add data:
  QVector<double> fossilData, nuclearData, regenData;
  fossilData  << 1 << 2 << 3 ;
  nuclearData << 0.08*10.5 << 0.12*5.5 << 0.12*5.5;
  regenData   << 0.06*10.5 << 0.05*5.5 << 0.04*5.5;
  takeOffBar->setData(ticks, fossilData);
  moveBar->setData(ticks, nuclearData);
  landBar->setData(ticks, regenData);
  
  customPlot->axisRect()->insetLayout()->setInsetAlignment(0, Qt::AlignTop|Qt::AlignHCenter);
  customPlot->setInteractions(QCP::iRangeDrag | QCP::iRangeZoom);
}

void MainWindow::realtimeDataSlot()
{
  // calculate two new data points:
#if QT_VERSION < QT_VERSION_CHECK(4, 7, 0)
  double key = 0;
#else
  double key = QDateTime::currentDateTime().toMSecsSinceEpoch()/1000.0;
#endif
  static double lastPointKey = 0;
  if (key-lastPointKey > 0.01) // at most add point every 10 ms
  {
    double value0 = qSin(key); //qSin(key*1.6+qCos(key*1.7)*2)*10 + qSin(key*1.2+0.56)*20 + 26;
    double value1 = qCos(key); //qSin(key*1.3+qCos(key*1.2)*1.2)*7 + qSin(key*0.9+0.26)*24 + 26;
    // add data to lines:
    ui->customPlot->graph(0)->addData(key, value0);
    ui->customPlot->graph(1)->addData(key, value1);
    // set data of dots:
    ui->customPlot->graph(2)->clearData();
    ui->customPlot->graph(2)->addData(key, value0);
    ui->customPlot->graph(3)->clearData();
    ui->customPlot->graph(3)->addData(key, value1);
    // remove data of lines that's outside visible range:
    ui->customPlot->graph(0)->removeDataBefore(key-8);
    ui->customPlot->graph(1)->removeDataBefore(key-8);
    // rescale value (vertical) axis to fit the current data:
    ui->customPlot->graph(0)->rescaleValueAxis();
    ui->customPlot->graph(1)->rescaleValueAxis(true);
    lastPointKey = key;
  }
  // make key axis range scroll with the data (at a constant range size of 8):
  ui->customPlot->xAxis->setRange(key+0.25, 8, Qt::AlignRight);
  ui->customPlot->replot();
  
  // calculate frames per second:
  static double lastFpsKey;
  static int frameCount;
  ++frameCount;
  if (key-lastFpsKey > 2) // average fps over 2 seconds
  {
    ui->statusBar->showMessage(
          QString("%1 FPS, Total Data points: %2")
          .arg(frameCount/(key-lastFpsKey), 0, 'f', 0)
          .arg(ui->customPlot->graph(0)->data()->count()+ui->customPlot->graph(1)->data()->count())
          , 0);
    lastFpsKey = key;
    frameCount = 0;
  }
}

void MainWindow::bracketDataSlot()
{
#if QT_VERSION < QT_VERSION_CHECK(4, 7, 0)
  double secs = 0;
#else
  double secs = QDateTime::currentDateTime().toMSecsSinceEpoch()/1000.0;
#endif
  
  // update data to make phase move:
  int n = 500;
  double phase = secs*5;
  double k = 3;
  QVector<double> x(n), y(n);
  for (int i=0; i<n; ++i)
  {
    x[i] = i/(double)(n-1)*34 - 17;
    y[i] = qExp(-x[i]*x[i]/20.0)*qSin(k*x[i]+phase);
  }
  ui->customPlot->graph()->setData(x, y);
  
  itemDemoPhaseTracer->setGraphKey((8*M_PI+fmod(M_PI*1.5-phase, 6*M_PI))/k);
  
  ui->customPlot->replot();
  
  // calculate frames per second:
  double key = secs;
  static double lastFpsKey;
  static int frameCount;
  ++frameCount;
  if (key-lastFpsKey > 2) // average fps over 2 seconds
  {
    ui->statusBar->showMessage(
          QString("%1 FPS, Total Data points: %2")
          .arg(frameCount/(key-lastFpsKey), 0, 'f', 0)
          .arg(ui->customPlot->graph(0)->data()->count())
          , 0);
    lastFpsKey = key;
    frameCount = 0;
  }
}

void MainWindow::setupPlayground(QCustomPlot *customPlot)
{
  Q_UNUSED(customPlot)
}

MainWindow::~MainWindow()
{
  delete ui;
}

void MainWindow::screenShot()
{
#if QT_VERSION < QT_VERSION_CHECK(5, 0, 0)
  QPixmap pm = QPixmap::grabWindow(qApp->desktop()->winId(), this->x()+2, this->y()+2, this->frameGeometry().width()-4, this->frameGeometry().height()-4);
#else
  QPixmap pm = qApp->primaryScreen()->grabWindow(qApp->desktop()->winId(), this->x()+2, this->y()+2, this->frameGeometry().width()-4, this->frameGeometry().height()-4);
#endif
  QString fileName = demoName.toLower()+".png";
  fileName.replace(" ", "");
  pm.save("./screenshots/"+fileName);
  qApp->quit();
}

void MainWindow::allScreenShots()
{
#if QT_VERSION < QT_VERSION_CHECK(5, 0, 0)
  QPixmap pm = QPixmap::grabWindow(qApp->desktop()->winId(), this->x()+2, this->y()+2, this->frameGeometry().width()-4, this->frameGeometry().height()-4);
#else
  QPixmap pm = qApp->primaryScreen()->grabWindow(qApp->desktop()->winId(), this->x()+2, this->y()+2, this->frameGeometry().width()-4, this->frameGeometry().height()-4);
#endif
  QString fileName = demoName.toLower()+".png";
  fileName.replace(" ", "");
  pm.save("./screenshots/"+fileName);
  
  if (currentDemoIndex < 19)
  {
    if (dataTimer.isActive())
      dataTimer.stop();
    dataTimer.disconnect();
    delete ui->customPlot;
    ui->customPlot = new QCustomPlot(ui->centralWidget);
    ui->verticalLayout->addWidget(ui->customPlot);
    setupDemo(currentDemoIndex+1);
    // setup delay for demos that need time to develop proper look:
    int delay = 250;
    if (currentDemoIndex == 10) // Next is Realtime data demo
      delay = 12000;
    else if (currentDemoIndex == 15) // Next is Item demo
      delay = 5000;
    QTimer::singleShot(delay, this, SLOT(allScreenShots()));
  } else
  {
    qApp->quit();
  }
}

void MainWindow::delay(int msecs)
{
    QTime dieTime = QTime::currentTime().addMSecs(msecs);
    while(QTime::currentTime() < dieTime)
    {
        QCoreApplication::processEvents(QEventLoop::AllEvents, 100);

    }

}






































