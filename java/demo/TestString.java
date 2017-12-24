package myapp;

class TestString_1{
	
}
public class TestString {
	public static void main(String[] args){
		String str1 = new String("China");
		String str2 = new String("China");
		System.out.println(str1.equals(str2));
		if(str1 == str2)
			System.out.println("str1 == str2");   //类是在栈区存放，创建的两个String对象，占用了两个不同的地址
		else
			System.out.println("str1 != str2");  //这里的 == 号，比较的是对象本身，因为这两个对象占用的不同的内存区域，所以是不相等的
		
		
		String str3 = "China";  // 变量存放在内存的数据区，这里的str3和str4指向同一个数据"China",所以他们是相等的。
		String str4 = "China";
		System.out.println(str3.equals(str4));
		if(str3 == str4)
			System.out.println("str3 == str4");
		else
			System.out.println("str3 != str4");
		
		
		
		//String类常用方法
		String str5 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		System.out.println(str5.charAt(3));  //输出d，charAt(int index)返回字符串中第index+1个字符（从0开始的）
		
		System.out.println(str5.length()); //输出52，length()，返回字符串的长度。
		
		System.out.println(str5.indexOf("g"));//输出6，indexOf(String str),返回str在字符串中第一次出现的位置，从0开始
		
		System.out.println(str5.toLowerCase());//将字符串全部转为小写
		
		System.out.println(str5.toUpperCase());//将字符串全部转为大写
		
		//indexOf(String str,int fromIndex) 返回字符串中从fromindex开始,str第一次出现的位置
		//equalsIgnoreCase(String another) 比较字符串与another是否一样（忽略大小写）
		//replace(char oldChar,char newChar)在字符串中，用newChar替换oldChar
		
		int a1 = 123;
		String str6 = "456";
		str6 = String.valueOf(a1);
		System.out.printf("str6 = %s\n", str6);
		
		//统计一个String对象中大写字母，小写字母，非字母 各自出现的次数
		//方法一
		String str7 = "abAMc,!23";
		int cntU = 0;  //大写字母个数
		int cntL = 0;  //小写字母个数
		int cntOther = 0; //非字母个数
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
		
		System.out.println("大写字母个数："+cntU);
		System.out.println("小写字母个数："+cntL);
		System.out.println("非字母个数："+cntOther);
		
		//方法二 通过Character.isUpperCase(ch)和Character.isLowerCase(ch)来判断ch是大写字母还是小写字母
		
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
