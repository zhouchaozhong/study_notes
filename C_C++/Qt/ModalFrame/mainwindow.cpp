#include "mainwindow.h"
#include <QMenu>
#include <QMenuBar>
#include <QAction>
#include <QDialog>
#include <QDebug>
#include <QMessageBox>
#include <QFileDialog>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
{
    QMenuBar *mBar = menuBar();
    setMenuBar(mBar);
    QMenu *menu = mBar->addMenu("对话框");
    QAction *p1 = menu->addAction("模态");
    connect(p1,&QAction::triggered,[=](){
        QDialog *p = new QDialog;
        p->setAttribute(Qt::WA_DeleteOnClose);
        p->show();
    });

    QAction *p3 = menu->addAction("关于对话框");
    connect(p3,&QAction::triggered,[=](){
        QMessageBox::about(this,"about","关于qt");
    });

    QAction *p4 = menu->addAction("问题对话框");
    connect(p4,&QAction::triggered,[=](){
        int ret = QMessageBox::question(this,"question","Are you ok?",QMessageBox::Ok|QMessageBox::Cancel);
        switch(ret){
            case QMessageBox::Ok:
                qDebug() << "I am Ok!";
                break;
            case QMessageBox::Cancel:
                qDebug() << "I am not Ok!";
            default:
                break;
        }
    });

    QAction *p5 = menu->addAction("文件对话框");
    connect(p5,&QAction::triggered,[=](){
        QString path = QFileDialog::getOpenFileName(this,"open","../","source(*.cpp *.h);;Text(*.txt);;all(*.*)");
        qDebug() << path;
    });
}


MainWindow::~MainWindow()
{

}
