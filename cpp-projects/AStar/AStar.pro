TEMPLATE = app
CONFIG += console
CONFIG -= qt

SOURCES += \
    MapSearchNode.cpp \
    MapProblem.cpp \
    main.cpp

HEADERS += \
    stlastar.h \
    fsa.h \
    MapSearchNode.h \
    MapProblem.h

unix|win32: LIBS += -lopencv_core

unix|win32: LIBS += -lopencv_imgproc

unix|win32: LIBS += -lopencv_highgui
