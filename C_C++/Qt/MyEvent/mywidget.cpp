#include "mywidget.h"
#include "ui_mywidget.h"
#include <QDebug>
#include <QKeyEvent>

MyWidget::MyWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::MyWidget)
{
    ui->setupUi(this);
    timerId = this->startTimer(1000);     //毫秒为单位  每隔1s触发一次定时器
    connect(ui->pushButton,&MyButton::clicked,[=](){
        qDebug() << "按钮被按下";
    });

}

MyWidget::~MyWidget()
{
    delete ui;
}

void MyWidget::keyPressEvent(QKeyEvent *e){
    qDebug() << (char)e->key();
}

void MyWidget::timerEvent(QTimerEvent *e){
    static int sec = 0;
    ui->label->setText(QString("<center><h1>timer out: %1</h1></center>").arg(sec++));
    if(sec == 30){
        this->killTimer(this->timerId);
    }
}
