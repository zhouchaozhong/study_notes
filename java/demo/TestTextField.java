package myapp;

import java.awt.*;
import java.awt.event.*;

class MyMonitor implements ActionListener{
	private TF tf;
	public MyMonitor(TF tf) {
		this.tf = tf;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String str1 = tf.tf1.getText();
		String str2 = tf.tf2.getText();
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		int num3 = num1 + num2;
		Integer it = new Integer(num3);
		String str3 = it.toString();
		tf.tf3.setText(str3);
	}
}

class TF{
	public TextField tf1,tf2,tf3;
	public void launch() {
		tf1 = new TextField(30);
		tf2 = new TextField(30);
		tf3 = new TextField(30);
		Button btn = new Button("=");
		Label lb = new Label("+");
		Frame f = new Frame("文本框相加示例");
		f.setLayout(new FlowLayout());
		f.add(tf1);
		f.add(lb);
		f.add(tf2);
		f.add(btn);
		f.add(tf3);
		f.pack();
		f.setVisible(true);
		
		btn.addActionListener(new MyMonitor(this));
	}
}
public class TestTextField {
	public static void main(String[] args) {
		TF tf = new TF();
		tf.launch();

		
	}
}
