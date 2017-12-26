package myapp;

import java.awt.*;
import java.awt.event.*;

class AL implements ActionListener{
	public void actionPerformed(ActionEvent e){
		System.out.println("�¼���������");
	}
}

class WA extends WindowAdapter{
	public void windowClosing(WindowEvent e) {
		System.exit(-1);
	}
}
public class TestFrame {
	public static void main(String[] args) {
		Frame f = new Frame("����1");  //�������ڣ�������
		//f.setSize(400, 400);  //���ô��ڳߴ�
		//f.setLocation(300,50);  //���ô���λ��
		f.setBounds(300, 50, 300, 300);  //���ô��ڳߴ��λ��
		f.setBackground(Color.CYAN);	//���ô��ڱ���ɫ
		f.setVisible(true);		//���ô��ڵĿɼ���
		Button btn = new Button("��ť");	//������ť��������
		btn.setLocation(100, 100);	//���ð�ťλ��
		f.add(btn);			//��Ӱ�ť������
		
		
		//���ֹ����� BorderLayout
		Frame f1;
		f1 = new Frame("Border Layout");
		f1.setBounds(300,300,300,300);
		f1.setVisible(true);
		Button bn = new Button("BN");
		Button bs = new Button("BS");
		Button bw = new Button("BW");
		Button be = new Button("BE");
		Button bc = new Button("BC");

		f1.add(bn,BorderLayout.NORTH);
		f1.add(bs,BorderLayout.SOUTH);
		f1.add(bw,BorderLayout.WEST);
		f1.add(be,BorderLayout.EAST);
		f1.add(bc,BorderLayout.CENTER);
		
		
		//���ֹ�����GridLayout
		Frame f2;
		f2 = new Frame("Grid Layout");
		f2.setBounds(400,300,300,300);
		f2.setVisible(true);
		f2.setLayout(new GridLayout(2,3));  //��Ԫ�ز���ʱ����������������,�����Ǳ��������������Զ����������
		Button b1 = new Button("b1");
		Button b2 = new Button("b2");
		Button b3 = new Button("b3");
		Button b4 = new Button("b4");
		Button b5 = new Button("b5");
		f2.add(b1);
		f2.add(b2);
		f2.add(b3);
		f2.add(b4);
		f2.add(b5);
		//f2.pack();
		
		
		//�¼�������
		Frame f3;
		f3 = new Frame();
		f3.setBounds(600, 200, 200, 200);
		f3.setVisible(true);
		AL al = new AL();
		Button bne = new Button("OK");
		f3.add(bne);
		bne.addActionListener(al);
		f3.addWindowListener(new WA());
		//f3.pack();
		
		
		//���ֹ�����ʮ����ť��ϰ
		Frame f4;
		f4 = new Frame();
		f4.setBounds(600,100,300,300);
		f4.setVisible(true);
		f4.setLayout(new GridLayout(2,1));
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setLayout(new BorderLayout());
		p2.setLayout(new BorderLayout());
		Panel p1_1 = new Panel();
		Panel p2_1 = new Panel();
		p1_1.setLayout(new GridLayout(2,1));
		p2_1.setLayout(new GridLayout(2,2));
		Button btn1 = new Button("��ťһ");
		Button btn2 = new Button("��ť��");
		Button btn3 = new Button("��ť��");
		Button btn4 = new Button("��ť��");
		Button btn5 = new Button("��ť��");
		Button btn6 = new Button("��ť��");
		Button btn7 = new Button("��ť��");
		Button btn8 = new Button("��ť��");
		Button btn9 = new Button("��ť��");
		Button btn10 = new Button("��ťʮ");
	
		p1.add(btn1,BorderLayout.WEST);
		p1_1.add(btn3);
		p1_1.add(btn4);
		p1.add(p1_1,BorderLayout.CENTER);
		p1.add(btn2,BorderLayout.EAST);
		p2.add(btn5,BorderLayout.WEST);
		p2.add(p2_1,BorderLayout.CENTER);
		p2_1.add(btn7);
		p2_1.add(btn8);
		p2_1.add(btn9);
		p2_1.add(btn10);
		p2.add(btn6,BorderLayout.EAST);
		f4.add(p1);
		f4.add(p2);
		//f4.pack();
		
		
		
		
	}
}
