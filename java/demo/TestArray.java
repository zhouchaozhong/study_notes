package myapp;

import java.util.Arrays;

public class TestArray {
	
	
	public static void showArr(int [] arr){
		for(int i = 0;i < arr.length;i++){
			System.out.println(arr[i]);
		}
	}
	
	public static void main(String[] args){
		
		//数组的创建
		//方式一
		int [] arr = {0,1,2};
		
		//方式二
		int [] arr1;
		arr1 = new int[3];
		arr1[0] = 3;
		arr1[1] = 4;
		arr1[2] = 5;
		
		//方式三
		int [] arr2 = new int[] {1,2,3};
		showArr(arr2);		
		
		//数组的覆盖
		int [] arr3 = {1,2,3,4,5};
		int [] arr4 = {-1,-2,-3,-4,-5};
		System.arraycopy(arr3, 0, arr4, 1, 2);
		System.out.println("a = ");
		showArr(arr3);
		
		System.out.println("b = ");
		showArr(arr4);
		
		
		int [] arr5 = {1,2,3,4,5,6,7,8,9,10};
		for(int e : arr5)  //增强循环，将数组arr5中的元素赋值给e
			System.out.printf("%d ",e);
		
			System.out.println(" ");
		
		
		//数组排序
		int [] arr6 = {1,3,6,4,2,5,10,7,8,9};
		Arrays.sort(arr6);
		System.out.println("排序后的数组为：");
		showArr(arr6);
			
		
		
	}
	
}
