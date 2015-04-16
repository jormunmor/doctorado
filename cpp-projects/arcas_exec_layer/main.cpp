#include <QtGui/QApplication>
#include "mainwindow.h"
#include <ros/ros.h>

int main(int argc, char *argv[])
{
    ros::init(argc, argv, "ArcasExecLayerMainGUI");
    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    int result = a.exec();
    ros::shutdown();

    return result;
}
