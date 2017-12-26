package myapp;

import java.awt.*;
import java.awt.event.*;

class AL implements ActionListener{
	public void actionPerformed(ActionEvent e){
		System.out.println("事件监听器！");
	}
}

class WA extends WindowAdapter{
	public void windowClosing(WindowEvent e) {
		System.exit(-1);
	}
}
public class TestFrame {
	public static void main(String[] args) {
		Frame f = new Frame("窗口1");  //创建窗口，并命名
		//f.setSize(400, 400);  //设置窗口尺寸
		//f.setLocation(300,50);  //设置窗口位置
		f.setBounds(300, 50, 300, 300);  //设置窗口尺寸和位置
		f.setBackground(Color.CYAN);	//设置窗口背景色
		f.setVisible(true);		//设置窗口的可见性
		Button btn = new Button("按钮");	//创建按钮，并命名
		btn.setLocation(100, 100);	//设置按钮位置
		f.add(btn);			//添加按钮到窗口
		
		
		//布局管理器 BorderLayout
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
		
		
		//布局管理器GridLayout
		Frame f2;
		f2 = new Frame("Grid Layout");
		f2.setBounds(400,300,300,300);
		f2.setVisible(true);
		f2.setLayout(new GridLayout(2,3));  //当元素不够时，会优先满足行数,列数是编译器根据行数自动计算出来的
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
		
		
		//事件监听器
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
		
		
		//布局管理器十个按钮练习
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
		Button btn1 = new Button("按钮一");
		Button btn2 = new Button("按钮二");
		Button btn3 = new Button("按钮三");
		Button btn4 = new Button("按钮四");
		Button btn5 = new Button("按钮五");
		Button btn6 = new Button("按钮六");
		Button btn7 = new Button("按钮七");
		Button btn8 = new Button("按钮八");
		Button btn9 = new Button("按钮九");
		Button btn10 = new Button("按钮十");
	
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
