/********************************************************************************
** Form generated from reading UI file 'ganttdialog.ui'
**
** Created: Wed May 13 19:09:00 2015
**      by: Qt User Interface Compiler version 4.8.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_GANTTDIALOG_H
#define UI_GANTTDIALOG_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtGui/QApplication>
#include <QtGui/QButtonGroup>
#include <QtGui/QDialog>
#include <QtGui/QHeaderView>
#include <QtGui/QVBoxLayout>
#include "qcustomplot.h"

QT_BEGIN_NAMESPACE

class Ui_GanttDialog
{
public:
    QVBoxLayout *verticalLayout;
    QCustomPlot *customPlot;

    void setupUi(QDialog *GanttDialog)
    {
        if (GanttDialog->objectName().isEmpty())
            GanttDialog->setObjectName(QString::fromUtf8("GanttDialog"));
        GanttDialog->resize(440, 361);
        verticalLayout = new QVBoxLayout(GanttDialog);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        customPlot = new QCustomPlot(GanttDialog);
        customPlot->setObjectName(QString::fromUtf8("customPlot"));

        verticalLayout->addWidget(customPlot);


        retranslateUi(GanttDialog);

        QMetaObject::connectSlotsByName(GanttDialog);
    } // setupUi

    void retranslateUi(QDialog *GanttDialog)
    {
        GanttDialog->setWindowTitle(QApplication::translate("GanttDialog", "Dialog", 0, QApplication::UnicodeUTF8));
    } // retranslateUi

};

namespace Ui {
    class GanttDialog: public Ui_GanttDialog {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_GANTTDIALOG_H
