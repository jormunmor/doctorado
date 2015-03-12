TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp \
    AStarUtilities.cpp

unix|win32: LIBS += -lopencv_core

unix|win32: LIBS += -lopencv_imgproc

unix|win32: LIBS += -lopencv_highgui

HEADERS += \
    fsa.h \
    stlastar.h \
    AStarUtilities.h
