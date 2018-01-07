package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

/*
 * dom4j解析xml文档
 * */

public class XMLParser {
	
	
	//读取xml文档第二个title标签内的值
	@Test
	public void read() throws Exception {
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/xml/book.xml"));
		Element root = document.getRootElement();
		Element book = root.elements("book").get(1);
		String value = book.element("title").getText();
		String attr = book.element("title").attributeValue("name");
		System.out.println(value);
		System.out.println(attr);
		
	}
	
	//往xml文档里面添加元素
	@Test
	public void add() throws Exception{
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/xml/book.xml"));
		Element book = document.getRootElement().element("book");
		book.addElement("ISBN").setText("11123343483");
		
		//格式化xml文档
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//OutputFormat format = OutputFormat.createCompactFormat();
		
		format.setEncoding("UTF-8");
		
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/xml/book.xml"),"UTF-8"),format);
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/book.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	
	//往xml文档指定位置添加元素
	@Test
	public void add2() throws Exception{
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/xml/book.xml"));
		Element book = document.getRootElement().element("book");
		List list = book.elements();
		Element price = DocumentHelper.createElement("price");
		price.setText("120元");
		list.add(2, price);
		
		
		//格式化xml文档
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//OutputFormat format = OutputFormat.createCompactFormat();
		
		format.setEncoding("UTF-8");
		
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/xml/book.xml"),"UTF-8"),format);
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/book.xml"),format);
		writer.write(document);
		writer.close();
	}
	
	
	//输出xml文档中的节点
	@Test
	public void delete() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/xml/book.xml"));
		Element price = document.getRootElement().element("book").element("price");
		price.getParent().remove(price);
		
		
		//格式化xml文档
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//OutputFormat format = OutputFormat.createCompactFormat();
		
		format.setEncoding("UTF-8");
		
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/xml/book.xml"),"UTF-8"),format);
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/book.xml"),format);
		writer.write(document);
		writer.close();		
		
	}
	
	
	
	//更新xml文档中的节点内容
	@Test
	public void update() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/xml/book.xml"));
		Element book = document.getRootElement().elements("book").get(1);
		book.element("author").setText("陆雪琪");
		
		
		//格式化xml文档
		OutputFormat format = OutputFormat.createPrettyPrint();
		
		//OutputFormat format = OutputFormat.createCompactFormat();
		
		format.setEncoding("UTF-8");
		
		//XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/xml/book.xml"),"UTF-8"),format);
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/book.xml"),format);
		writer.write(document);
		writer.close();		
		
	}
	
	
	
	
	
}
