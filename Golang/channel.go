package main

import (
	"fmt"
)

func main(){

	//创建int类型的管道
	var intChan chan int
	intChan = make(chan int,3)

	//向管道写入数据
	intChan<- 10
	num := 211
	intChan<- num

	intChan<- 50

	//在给管道写入数据时，不能超过其容量

	//查看管道的长度和cap(容量)
	fmt.Printf("channel len = %v cap = %v \n",len(intChan),cap(intChan))

	close(intChan)	//关闭管道，关闭管道后，不能再往管道里面写数据，只能读数据

	//从管道中读取数据
	var num2 int
	num2 = <-intChan

	fmt.Printf("num2 = %d \n",num2)

	//查看管道的长度和cap(容量)
	fmt.Printf("channel len = %v cap = %v \n",len(intChan),cap(intChan))

	//在没有使用协程的情况下，如果管道数据已经全部取出，再取就会报deadlock错误


	//channel支持for-range方式进行遍历
	//在遍历时，如果channel没有关闭，会出现deadlock的错误
	//在遍历时，如果channel已经关闭，则会正常遍历数据，遍历完后，就会退出遍历


	//遍历管道
	intChan2 := make(chan int,10)
	for i := 0;i < 10;i++ {
		intChan2<- i*2	//放入10个数据到管道
	}

	close(intChan2)  //先关闭管道再遍历数据

	for v := range intChan2 {
		fmt.Println("v = ",v)
	}




}