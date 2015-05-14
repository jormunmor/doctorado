#ifndef GANTTDIALOG_H
#define GANTTDIALOG_H

#include <QDialog>
#include "qcustomplot.h"

namespace Ui {
class GanttDialog;
}

class GanttDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit GanttDialog(QWidget *parent = 0);
    ~GanttDialog();
    QCustomPlot *customPlot;

private:
    Ui::GanttDialog *ui;
};

#endif // GANTTDIALOG_H
