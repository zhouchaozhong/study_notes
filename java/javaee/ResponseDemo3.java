package cn.myapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/res3")
public class ResponseDemo3 extends HttpServlet {
	public static final int WIDTH = 120;
	public static final int HEIGHT = 35;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//1.设置背景色
		setBackground(g);
		
		//2.设置边框
		setBorder(g);
		
		//3.画干扰线
		drawRandomLine(g);
		
		//4.写随机数
		drawRandomNum((Graphics2D)g);
		
		//5.图形写给浏览器
		resp.setContentType("image/jpeg");
		ImageIO.write(image, "jpg", resp.getOutputStream());
	}

	private void drawRandomNum(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(new Font("微软雅黑",Font.BOLD,20));
		
		//常用中文简体字的unicode码
		String base = "\u7684\u4e00\u662f\u4e86\u6211\u4e0d\u4eba\u5728\u4ed6\u6709\u8fd9\u4e2a\u4e0a\u4eec\u6765\u5230\u65f6\u5927\u5730\u4e3a\u5b50\u4e2d\u4f60\u8bf4\u751f\u56fd\u5e74\u7740\u5c31\u90a3\u548c\u8981\u5979\u51fa\u4e5f\u5f97\u91cc\u540e\u81ea\u4ee5\u4f1a\u5bb6\u53ef\u4e0b\u800c";
		int x = 5;
		for(int i = 0;i < 4;i++) {
			int degree = new Random().nextInt()%30;  //-30 ~ 30
			
			String ch = base.charAt(new Random().nextInt(base.length()))+"";
			g.rotate(degree*Math.PI/180,x,20); //设置字体旋转的角度
			g.drawString(ch, x, 20);
			g.rotate(-degree*Math.PI/180,x,20);
			x += 30;
		}
		
	}

	private void drawRandomLine(Graphics g) {
		g.setColor(Color.GREEN);
		for(int i = 0;i < 5;i++) {
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
		
	}

	private void setBorder(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(1, 1, WIDTH-2, HEIGHT-2);
		
	}

	private void setBackground(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
