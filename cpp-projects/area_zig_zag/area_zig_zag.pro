TEMPLATE = app
CONFIG += console
CONFIG -= qt

SOURCES += main.cpp \
    surveillance_waypoints.cpp \
    ll-utm/ll-utm.cpp

HEADERS += \
    surveillance_waypoints.h \
    ll-utm/ll-utm_constants.h \
    ll-utm/ll-utm.h

unix|win32: LIBS += -lopencv_core

unix|win32: LIBS += -lopencv_imgproc

unix|win32: LIBS += -lopencv_highgui
