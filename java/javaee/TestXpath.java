package xml;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/*
 * 使用xpath提取xml文档的数据
 * */
public class TestXpath {
	public static void main(String[] args) throws Exception {
		String username = "vvv";
		String password = "123";
		
		
		//检测xml文档中是否有匹配的用户名和密码
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("src/xml/users.xml"));
		Node node = (Node)document.selectSingleNode("//user[@username='"+username+"' and @password='"+password+"']");
		if(node == null) {
			System.out.println("用户名或密码错误！");
		}else {
			System.out.println("验证通过！");
		}
		
		
	}
}
