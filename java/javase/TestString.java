package myapp;

class TestString_1{
	
}
public class TestString {
	public static void main(String[] args){
		String str1 = new String("China");
		String str2 = new String("China");
		System.out.println(str1.equals(str2));
		if(str1 == str2)
			System.out.println("str1 == str2");   //������ջ����ţ�����������String����ռ����������ͬ�ĵ�ַ
		else
			System.out.println("str1 != str2");  //����� == �ţ��Ƚϵ��Ƕ�������Ϊ����������ռ�õĲ�ͬ���ڴ����������ǲ���ȵ�
		
		
		String str3 = "China";  // ����������ڴ���������������str3��str4ָ��ͬһ������"China",������������ȵġ�
		String str4 = "China";
		System.out.println(str3.equals(str4));
		if(str3 == str4)
			System.out.println("str3 == str4");
		else
			System.out.println("str3 != str4");
		
		
		
		//String�ೣ�÷���
		String str5 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(str5.charAt(3));  //���d��charAt(int index)�����ַ����е�index+1���ַ�����0��ʼ�ģ�
		
		System.out.println(str5.length()); //���52��length()�������ַ����ĳ��ȡ�
		
		System.out.println(str5.indexOf("g"));//���6��indexOf(String str),����str���ַ����е�һ�γ��ֵ�λ�ã���0��ʼ
		
		System.out.println(str5.toLowerCase());//���ַ���ȫ��תΪСд
		
		System.out.println(str5.toUpperCase());//���ַ���ȫ��תΪ��д
		
		//indexOf(String str,int fromIndex) �����ַ����д�fromindex��ʼ,str��һ�γ��ֵ�λ��
		//equalsIgnoreCase(String another) �Ƚ��ַ�����another�Ƿ�һ�������Դ�Сд��
		//replace(char oldChar,char newChar)���ַ����У���newChar�滻oldChar
		
		int a1 = 123;
		String str6 = "456";
		str6 = String.valueOf(a1);
		System.out.printf("str6 = %s\n", str6);
		
		//ͳ��һ��String�����д�д��ĸ��Сд��ĸ������ĸ ���Գ��ֵĴ���
		//����һ
		String str7 = "abAMc,!23";
		int cntU = 0;  //��д��ĸ����
		int cntL = 0;  //Сд��ĸ����
		int cntOther = 0; //����ĸ����
		int i;
		for(i = 0;i < str7.length();i++){
			char ch = str7.charAt(i);
			if(ch >= 'a' && ch <= 'z'){
				cntL++;
			}else if(ch >= 'A' && ch <= 'Z'){
				cntU++;
			}else{
				cntOther++;
			}
		}
		
		System.out.println("��д��ĸ������"+cntU);
		System.out.println("Сд��ĸ������"+cntL);
		System.out.println("����ĸ������"+cntOther);
		
		//������ ͨ��Character.isUpperCase(ch)��Character.isLowerCase(ch)���ж�ch�Ǵ�д��ĸ����Сд��ĸ
		
		StringBuffer sb = new StringBuffer();
		sb.append("abc");
		sb.insert(2, "d");
		sb.append("123");
		sb.delete(3, 6);
		sb.reverse();
		String str8 = sb.toString();
		System.out.println(str8);
		
		
		
	}
}
