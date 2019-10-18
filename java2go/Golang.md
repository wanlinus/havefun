# Golang

[TOC]



## 基础

### 数据类型

#### 值类型(变量直接存储值(栈)))

整数(int, int8, int16, int32, int64, uint, uint8, uint16, uint32, uint64), 浮点(float32 float64)

布尔(bool), 字符串(string), 数组, 结构体(struct)

####引用类型(变量存地址(堆)))

指针(Pointer), 管道(Channel), 函数, 切片(Slice), 接口(interface), map

| 类型    | 有无符号 | 占用空间                         | 表示范围                                                    | 备注              |
| ------- | :------: | -------------------------------- | ----------------------------------------------------------- | ----------------- |
| int8    |    有    | 1字节                            | -128 ~ 127                                                  |                   |
| int16   |    有    | 2字节                            | -32768 ~ 32767                                              |                   |
| int32   |    有    | 4字节                            | ${-2^{31}}$ ~ ${2^{31}}-1$                                  |                   |
| int64   |    有    | 8字节                            | ${-2^{63}}$ ~ ${2^{63}-1}$                                  |                   |
| uint8   |    无    | 1字节                            | 0 ~ 255                                                     |                   |
| uint16  |    无    | 2字节                            | 0 ~ 65535                                                   |                   |
| uint32  |    无    | 4字节                            | 0 ~ ${2^{32}-1}$                                            |                   |
| uint64  |    无    | 8字节                            | 0 ~ ${2^{64}-1}$                                            |                   |
| int     |    有    | 32位系统4字节<br />64位系统8字节 | ${-2^{31}}$ ~ ${2^{31}-1}$ <br />${-2^{63}}$ ~ ${2^{63}-1}$ |                   |
| uint    |    无    | 32位系统4字节<br />64位系统8字节 | 0 ~ ${2^{32}-1}$<br />0 ~ ${2^{64}-1}$                      |                   |
| rune    |    有    | 与int32一样                      | ${-2^{31}}$ ~ ${2^{31}}-1$                                  | 表示一个Unicode码 |
| byte    |    无    | 与uint8等价                      | 0 ~ 255                                                     | 存储字符选用byte  |
| float32 |          | 4字节                            | -3.403${*10^{38}}$ ~ 3.403${*10^{38}}$                      |                   |
| float64 |          | 8字节                            | -1.798${*10^{308}}$ ~ 1.798${*10^{308}}$                    |                   |
| bool    |          | 1个字节                          | true false                                                  |                   |

#### 查看变量类型和占用字节

```go
var n1 = 100
fmt.Printf("Type of n1: %T sizeof %d byte \n", n1, unsafe.Sizeof(n1))
```

### 声明变量

```go
// 1. 指定变量类型. 声明后若不赋值, 使用默认值
var i int
var b string = "wanli"

// 2. 根据值自行判定变量类型
var num = 10.01

// 3. 省略var, :=
name := "wanli"
```

### 同时声明多个变量

```go
var a1, a2, a3 int

var b1, b2, b3 = 100, "wanli", 10.01

c1, c2, c3 := 100, "wanli", 10.01

//全局变量
var(
	d1 = 100
  d2 = "wanli"
  d3 = 10.1
)
```

### fmt.Printf常用格式化

1. `%v` 以默认的方式打印变量的值

2. `%T` 打印变量的类型

   #### Integer

3. `%+d` 带符号的整型，`fmt.Printf("%+d", 255)`输出`+255`

4. `%q` 打印单引号

5. `%o` 不带零的八进制

6. `%#o` 带零的八进制

7. `%x` 小写的十六进制

8. `%X` 大写的十六进制

9. `%#x` 带0x的十六进制

10. `%U` 打印Unicode字符

11. `%#U` 打印带字符的Unicode

12. `%b` 打印整型的二进制

13. `%5d` 表示该整型最大长度是5

14. `%-5d`则相反，打印结果会自动左对齐

15. `%05d`会在数字前面补零

    #### Float

16. `%f` (=`%.6f`) 6位小数点

17. `%e` (=`%.6e`) 6位小数点（科学计数法）

18. `%g` 用最少的数字来表示

19. `%.3g` 最多3位**数字**来表示

20. `%.3f` 最多3位**小数**来表示

    ####string

21. `%s` 正常输出字符串

22. `%q` 字符串带双引号，字符串中的引号带转义符

23. `%#q` 字符串带反引号，如果字符串内有反引号，就用双引号代替

24. `%x` 将字符串转换为小写的16进制格式

25. `%X` 将字符串转换为大写的16进制格式

26. `% x` 带空格的16进制格式

27. `%5s` 最小宽度为5

28. `%-5s` 最小宽度为5（左对齐）

29. `%.5s` 最大宽度为5

30. `%5.7s` 最小宽度为5，最大宽度为7

31. `%-5.7s` 最小宽度为5，最大宽度为7（左对齐）

32. `%5.3s` 如果宽度大于3，则截断

33. `%05s` 如果宽度小于5，就会在字符串前面补零

    ####struct

34. `%v` 正常打印。比如：`{sam {12345 67890}}`

35. `%+v` 带字段名称。比如：`{name:sam phone:{mobile:12345 office:67890}`

36. `%#v` 用Go的语法打印。

37. `%t` 打印true或false

38. `%p` 带0x的指针

39. `%#p` 不带0x的指针

 ### 基本数据类型互转string

```go
//base ==> string
// 使用fmt.Sprintf()
var num1 int = 99
var num2 float64 = 32.3
var b bool = true
var ch byte = 'b'
var str string

str = fmt.Sprintf("%d", num1)
fmt.Printf("str type %T str=%q\n", str, str)
str = strconv.Itoa(num1)
fmt.Printf("str type %T str=%q\n", str, str)
str = fmt.Sprintf("%f", num2)
fmt.Printf("str type %T str=%v\n", str, str)
str = fmt.Sprintf("%t", b)
fmt.Printf("str type %T str=%v\n", str, str)
str = fmt.Sprintf("%c", ch)
fmt.Printf("str type %T str=%v", str str)

//strconv
str = strconv.FormatInt(int64(num1), 10)
fmt.Printf("str type %T str=%v", str, str)

// string == base
var b, _ = strconv.ParseBool("true")
var i, _ = strconv.PaeseInt("33", 0, 64)
```

### 指针

一个指针变量指向了一个值的内存地址。

```go
var i = 10
fmt.Printf(&i) //0xc000092010
var ptr = &i
fmt.Printf("ptr type is %T, value is %v\n", ptr, ptr) // ptr type is *int
fmt.Printf("ptr 的地址 %v", &ptr)
fmt.Printf("ptr 指向的值 %v", *ptr)
```

从终端读取数据

```go
var name string
fmt.Scanln(&name)

var str string
fmt.Scanf(&str)
```

switch

```go
switch key{
  case 'a': fmt.Println("")
  case 'b': fmt.Println("不需要break")
  default: fmt.Println("")
}
```

for

```go
var str = "hello world你好"
for i := 0; i < len(str); i++ {
	fmt.Printf("index: %v, value: %c\n", i, str[i])
}	
//不能正确显示中文, 解决办法如下
//1. for-range
var str = "hello world你好"
for index, s := range str {
	fmt.Printf("index: %v, value: %v\n", index, s)
}
//2. []rune
var str2 = []rune(str)
for i := 0; i < len(str2); i++ {
	fmt.Printf("index: %v, value %c\n", i, str2[i])
}
```

九九乘法表

```go
for i := 1; i < 10; i++ {
	for j := 1; j <= i; j++ {
		fmt.Printf("%v*%v=%v\t", j, i, i*j)
	}
	fmt.Println()
}

1*1=1
1*2=2   2*2=4
1*3=3   2*3=6   3*3=9
1*4=4   2*4=8   3*4=12  4*4=16
1*5=5   2*5=10  3*5=15  4*5=20  5*5=25
1*6=6   2*6=12  3*6=18  4*6=24  5*6=30  6*6=36
1*7=7   2*7=14  3*7=21  4*7=28  5*7=35  6*7=42  7*7=49
1*8=8   2*8=16  3*8=24  4*8=32  5*8=40  6*8=48  7*8=56  8*8=64
1*9=9   2*9=18  3*9=27  4*9=36  5*9=45  6*9=54  7*9=63  8*9=72  9*9=81
```

### Type自定义类型

```go
type MyInt int
type MyFunc func(int, string) float64
```

### Func

支持对函数返回值命名

```go
func getNumAndSub(n1 int, n2 int)(sum int, sub int){
  sum = n1 + n2
  sub = n1 - n2
  return
}
```

支持自定义数据类型

```go
type MyFunc func(int, int) int
func bb(cc MyFunc) (bb int){
  // do something
  return 
}

```

使用`_`标识符, 忽略返回值

```go
func cal(n1 int, n2 int)(sum int, sub int){
	sum = n1 + n2
  sub = n1 - n2
  return
}

func main(){
  res, _ := cal(10, 20)
  fmt.Printf("res1=%v", res)
}
```

支持可变参数 

```go
// args是slice通过args[indec]可以访问各个值
func sum(n1 int, args ...int) (all int) {
	all = n1
	for _, arg := range args {
		all += arg
	}
	return
}
func main() {
	fmt.Println(sum(10, 20, 20, 30, 30, 44))
}
```

`init`函数

```go
//每一个源文件都可以包含一个init函数, 该函数会在main函数执行前被go运行框架调用
func init(){
	// init something
}
```

初始化操作流程 全局变量定义->init->main

匿名函数

```go
res1 := func(n1, n2 int) int{
  return n1 + n2
}(10, 15)
```

**闭包** 我的理解就是封装了一些数据和对这些数据的操作方法

```go
//函数与其他相关的引用环境组合的一个整体
func bibao() func(int) int{
	var n = 10
	return func(x int) int{
		n += x
		return n
	}
}

// e.g
package main

import "fmt"

func main() {
	f := fibonacci()

	for i := 0; i <= 10; i++ {
		fmt.Println(f())
	}
}
func fibonacci() func() int {
	var pre, current = 0, 1
	return func() int {
		pre, current = current, (pre + current)
		return current
	}
}
```

###defer

```go
func sum(n1, n2 int) int {
	defer fmt.Println("ok n1=", n1)
	defer fmt.Println("ok n2=", n2)
	n1++
	n2++
	res := n1 + n2
	fmt.Println("ok res", res)
	return res
}
```

### 常用内建函数

```go
// 计算字符串长度 len() 
fmt.Println("aaa中国的长度: ", len("aaa中国")) 
//aaa中国的长度:  9; 一个中文占3个字节

//数字类型转string strconv.Atoi()
n, err := strconv.Atoi("123")

// 整数转字符串 strconv.Itoa()
str := strconv.Itoa(1234)

// 字符串转[]byte
var bytes = []byte("hello world")

// []byte转字符串
var str string = string([]byte{97, 98, 99})

// 10进制转2, 8, 16
var str string = strconv.FormatInt(123, 8)

//查找指定字符串
var b boole = strings.Contains("abcdefg", "cde")

//比较字符串
fmt.Println("abc" == "ABC") //false 区分大小写
fmt.Println(strings.EqualFold("abc", "ABC")) //true 不区分大小写

//时间 包time
var now = time.Now()

//格式化时间
fmt.Printf("%d-%d-%d %d:%d:%d",
		now.Year(), now.Month(), now.Day(), now.Hour(), now.Minute(), now.Second())
fmt.Println(now.Format("2006/01/02 15:04:05")) //这是什么骚操作啊
time.Sleep(time.Second * 5) //暂停5秒

// new 分配内存, 主要用来分配值类型, int float struct, 返回指针
i:= new(int)
fmt.Printf("类型:%T, i的值%v, i地址的值%v, i指向的值%v", i, i, &i, *i)
//类型:*int, i的值0xc0000a8000, i地址的值0xc0000a6000, i指向的值0
//相当于: 首先i需要在栈上有一个空间地址为0xc0000a6000,
//然后使用new再在内存中分配一段空间地址为0xc0000a8000, 值为零值
//这时把new出来的地址存到i里

-----------      -------------         -------------
|   变量名 |     |      i     |        |      /     |
-----------      -------------         -------------
|   地址   |     |0xc0000a6000|       /|0xc0000a8000|
-----------      -------------     /   -------------
|    值    |     |0xc0000a8000| /      |     0      |
------------     --------------        -------------- 


//make 用来分配内存, 主要分配引用类型, chan, map, slice


```

### 错误处理

Golang中没有try...catch...finally, 而是使用**defer**, **panic**, **recover**. 抛出一个panic异常, 然后在defer中recover捕获这个异常

```go
func main() {
	test()
	fmt.Println("继续执行main")
}

func test() {
	defer func() {
		err := recover()
		if err != nil {
			fmt.Println("err: ", err)
		}
	}()
	num1, num2 := 10, 0
	fmt.Println("result: ", num1/num2)
}
```


