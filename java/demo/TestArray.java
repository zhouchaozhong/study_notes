package myapp;

import java.util.Arrays;

public class TestArray {
	
	
	public static void showArr(int [] arr){
		for(int i = 0;i < arr.length;i++){
			System.out.println(arr[i]);
		}
	}
	
	public static void main(String[] args){
		
		//����Ĵ���
		//��ʽһ
		int [] arr = {0,1,2};
		
		//��ʽ��
		int [] arr1;
		arr1 = new int[3];
		arr1[0] = 3;
		arr1[1] = 4;
		arr1[2] = 5;
		
		//��ʽ��
		int [] arr2 = new int[] {1,2,3};
		showArr(arr2);		
		
		//����ĸ���
		int [] arr3 = {1,2,3,4,5};
		int [] arr4 = {-1,-2,-3,-4,-5};
		System.arraycopy(arr3, 0, arr4, 1, 2);
		System.out.println("a = ");
		showArr(arr3);
		
		System.out.println("b = ");
		showArr(arr4);
		
		
		int [] arr5 = {1,2,3,4,5,6,7,8,9,10};
		for(int e : arr5)  //��ǿѭ����������arr5�е�Ԫ�ظ�ֵ��e
			System.out.printf("%d ",e);
		
			System.out.println(" ");
		
		
		//��������
		int [] arr6 = {1,3,6,4,2,5,10,7,8,9};
		Arrays.sort(arr6);
		System.out.println("����������Ϊ��");
		showArr(arr6);
			
		
		
	}
	
}
