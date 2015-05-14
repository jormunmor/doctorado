#include "ganttdialog.h"
#include "ui_ganttdialog.h"

GanttDialog::GanttDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::GanttDialog)
{
    ui->setupUi(this);
    customPlot = ui->customPlot;
}

GanttDialog::~GanttDialog()
{
    delete ui;
}
