#-------------------------------------------------
#
# Project created by QtCreator 2015-04-09T15:18:57
#
#-------------------------------------------------

QT       += core gui

TARGET = arcas_exec_layer
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    vehiclescheduler.cpp \
    ArcasExecLayerClient.cpp \
    qcustomplot.cpp \
    ganttdialog.cpp

HEADERS  += mainwindow.h \
    vehiclescheduler.h \
    ArcasExecLayerClient.h \
    constants.h \
    qcustomplot.h \
    ganttdialog.h

FORMS    += mainwindow.ui \
    ganttdialog.ui

# ROS flags
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lroscpp
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lrosconsole
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lroscpp_serialization
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lrostime
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lxmlrpcpp
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lactionlib
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lcpp_common
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lrosconsole_log4cxx
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lrosconsole_backend_interface
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../opt/ros/hydro/lib/ -lconsole_bridge


INCLUDEPATH += $$PWD/../../../../../../opt/ros/hydro/include
DEPENDPATH += $$PWD/../../../../../../opt/ros/hydro/include

# The next is to include the arcas_exec_layer package includes
INCLUDEPATH += $$PWD/../../../../../../home/jorge/catkin_ws/devel/include

# BOOST flags
unix:!macx:!symbian: LIBS += -L$$PWD/../../../../../../usr/lib/ -lboost_thread

INCLUDEPATH += $$PWD/../../../../../../usr/include
DEPENDPATH += $$PWD/../../../../../../usr/include
