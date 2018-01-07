package xml;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;



/*
 * DOM解析器解析XML文档
 * 
 * */
public class TestXmlDom {
	
	//读取xml节点<title>数据结构</title> 节点中的值
	@Test
	public void read() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		NodeList list = document.getElementsByTagName("title");
		Node node = list.item(1);
		String content = node.getTextContent();
		
		System.out.println(content);
	}
	
	
	//读取xml文件中的所有标签名
	@Test
	public void read2() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		//得到根节点
		Node root = document.getElementsByTagName("bookshelf").item(0);
		list(root);
	}
	
	private void list(Node node) {
		if(node instanceof Element) {
			System.out.println(node.getNodeName());
		}
		
		NodeList list = node.getChildNodes();
		for(int i = 0;i < list.getLength();i++) {
			Node child = list.item(i);
			list(child);
		}
	}
	
	
	//得到xml文档中标签属性的值 <title copyright="机械工业出版社">编译原理</title>
	@Test
	public void read3() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		Element bookname = (Element) document.getElementsByTagName("title").item(0);
		String value = bookname.getAttribute("copyright");
		System.out.println(value);		
	}
	
	
	//向xml文档中添加节点<ISBN>123456</ISBN>
	@Test
	public void add() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		//创建节点
		Element isbn = document.createElement("ISBN");
		isbn.setTextContent("123456");
		
		//得到参考节点
		Element refNode = (Element)document.getElementsByTagName("author").item(0);
		
		
		//得到要插入的节点
		Element book = (Element)document.getElementsByTagName("book").item(0);
		book.appendChild(isbn);
		
		
		//往book节点的指定位置插入节点
		book.insertBefore(isbn, refNode);
		
		//把更新后内存写入xml文档
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer tf = tfactory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/xml/book.xml")));
		
	}
	
	
	
	//向xml文档中的节点添加属性:<title>编译原理</title>添加name="xxx"属性
	@Test
	public void addAttr() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		Element bookname = (Element)document.getElementsByTagName("title").item(0);
		bookname.setAttribute("name", "xxx");
		//把更新后内存写入xml文档
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer tf = tfactory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/xml/book.xml")));
		
	}
	
	//删除xml文档中的节点
	@Test
	public void delete() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		//得到要删除的节点
		Element e = (Element)document.getElementsByTagName("ISBN").item(0);
		//得到要删除的节点的父节点
		//Element book = (Element)document.getElementsByTagName("book").item(0);
		e.getParentNode().removeChild(e);
		
		//删除节点
		//book.removeChild(e);
		//把更新后内存写入xml文档
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer tf = tfactory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/xml/book.xml")));
		
	}
	
	//更新xml文档中的节点内容
	@Test
	public void update() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("src/xml/book.xml");
		//得到要更新的节点
		Element e = (Element)document.getElementsByTagName("price").item(0);
		e.setTextContent("60.00元");

		//把更新后内存写入xml文档
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer tf = tfactory.newTransformer();
		tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream("src/xml/book.xml")));
		
	}
	
	
	
	
	
	/*
	 * 
	 * SAX解析器解析XML文档
	 * 
	 * */
	
	
	@Test
	public void saxRead() throws Exception, SAXException {
		//1.创建解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2.得到解析器
		SAXParser sp = factory.newSAXParser();
		//3.得到读取器
		XMLReader reader = sp.getXMLReader();
		//4.设置内容处理器
		//reader.setContentHandler(new ListHandler());
		reader.setContentHandler(new TagValueHandler());
		//5.读取xml文档内容
		reader.parse("src/xml/book.xml");
		
	}
	
	
	
	
	
	
	
	
	
	
	
}




//获取指定标签的值
class TagValueHandler extends DefaultHandler{
	private String currentTag; //记住当前解析到的是什么标签
	private int index = 2; //记住想获取第几个作者标签的值
	private int currentIndex;  //当前解析到的是第几个标签

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	
		if("author".equals(currentTag) && currentIndex == index) {
			System.out.println(new String(ch,start,length));
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		currentTag = null;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atttibutes) throws SAXException {
		currentTag = name;
		if(currentTag.equals("author")) {
			currentIndex++;
		}
	}
	
}




class ListHandler implements ContentHandler{

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println(new String(ch,start,length));
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("</" + qName + ">");
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		// TODO Auto-generated method stub
		
		System.out.println("<" + qName + ">");
		
		for(int i = 0;atts != null && i < atts.getLength();i++) {
			String attName = atts.getQName(i);
			String attValue = atts.getValue(i);
			System.out.println(attName + "=" + attValue);
		}

	
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
}
