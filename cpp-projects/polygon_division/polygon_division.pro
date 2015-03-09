TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp

unix|win32: LIBS += -lopencv_core

unix|win32: LIBS += -lopencv_imgproc

unix|win32: LIBS += -lopencv_highgui
