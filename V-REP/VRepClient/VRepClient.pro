TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

LIBS += -lpthread

DEFINES += NON_MATLAB_PARSING
DEFINES += MAX_EXT_API_CONNECTIONS=255

INCLUDEPATH += "../include"
INCLUDEPATH += "../remoteApi"

SOURCES +=  main.cpp \
            ../remoteApi/extApi.c \
            ../remoteApi/extApiPlatform.c

HEADERS +=  ../remoteApi/extApi.h \
            ../remoteApi/extApiPlatform.h
