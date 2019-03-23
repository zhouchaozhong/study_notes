package main

import (
	"fmt"
	"github.com/garyburd/redigo/redis"	// 引入redis包
)

func main(){

	// 通过go向redis写入数据和读取数据
	conn,err := redis.Dial("tcp","192.168.0.199:6379")

	if err != nil {
		fmt.Println("redis.Dial err = ",err)
		return
	}

	defer conn.Close()

	// 2. 通过go向redis写入数据 string [key-val]
	_,err = conn.Do("Set","name","tom lucy")

	if err != nil {
		fmt.Println("set err = ",err)
		return
	}

	fmt.Println("操作ok")


	//3. 通过go 向redis读取数据string [key-val]
	r,err := redis.String(conn.Do("Get","name"))
	if err != nil {
		fmt.Println("Get err = ",err)
		return
	}

	// 因为返回r是interface{}
	// 因为name对应的值是string,因此我们需要转换

	fmt.Println("name = ",r)



}