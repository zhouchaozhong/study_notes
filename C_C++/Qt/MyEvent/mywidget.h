#ifndef MYWIDGET_H
#define MYWIDGET_H

#include <QWidget>

namespace Ui {
class MyWidget;
}

class MyWidget : public QWidget
{
    Q_OBJECT

public:
    explicit MyWidget(QWidget *parent = nullptr);
    ~MyWidget();

protected:
    //键盘按下事件
    void keyPressEvent(QKeyEvent *);
    //计时器事件
    void timerEvent(QTimerEvent *);

private:
    Ui::MyWidget *ui;
    int timerId;
};

#endif // MYWIDGET_H
