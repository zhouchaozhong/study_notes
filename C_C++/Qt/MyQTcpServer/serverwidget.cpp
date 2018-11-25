#include "serverwidget.h"
#include "ui_serverwidget.h"

#include <QByteArray>

ServerWidget::ServerWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::ServerWidget)
{
    ui->setupUi(this);

    //监听套接字，指定父对象，让其自动回收空间
    tcpServer = new QTcpServer(this);
    tcpServer->listen(QHostAddress::Any,8888);

    setWindowTitle("服务器:8888");

    connect(tcpServer,&QTcpServer::newConnection,
    [=](){

        //取出建立好连接的套接字

        tcpSocket = tcpServer->nextPendingConnection();

        //获取对方的ip和端口
        QString ip = tcpSocket->peerAddress().toString();
        qint16 port = tcpSocket->peerPort();
        QString temp = QString("[%1:%2]:成功连接！").arg(ip).arg(port);
        ui->textEditRead->setText(temp);

        connect(tcpSocket,&QTcpSocket::readyRead,
        [=](){
          //从通信套接字中取出内容
          QByteArray array = tcpSocket->readAll();
          ui->textEditRead->append(array);
        }
        );

    });


}

ServerWidget::~ServerWidget()
{
    delete ui;
}

void ServerWidget::on_ButtonSend_clicked()
{
    //获取编辑区内容
    QString str = ui->textEditWrite->toPlainText();

    //给对方发送数据
    tcpSocket->write(str.toUtf8().data());
}


void ServerWidget::on_pushButton_2_clicked()
{
    //主动和客户端断开连接
    tcpSocket->disconnectFromHost();
    tcpSocket->close();
}
