package main

import (
	"fmt"
	"reflect"
)



type Student struct {
	Name string
	Age int
	Gender string
}

func reflectTest(b interface{}){

	// 先获取reflect.Type 
	rType := reflect.TypeOf(b)
	fmt.Println("rType = ",rType)
	//获取reflect.Value
	rVal := reflect.ValueOf(b)
	fmt.Printf("rVal = %v  rVal type = %T \n",rVal,rVal)

	n2 := 2 + rVal.Int()
	fmt.Printf("n2 = %v \n",n2)

	// 下面我们将 rVal 转成 interface{}
	iv := rVal.Interface()
	//将interface{} 通过断言转成需要的类型
	num2 := iv.(int)
	fmt.Println("num2 = ",num2)

}

func main(){

	var num int = 100
	reflectTest(num)



}