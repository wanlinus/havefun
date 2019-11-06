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

// 3.s 省略var, :=
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

//不能直接修改string的字符, 可以用一下方式
//1 这种方式不能修改汉字
var str string = "hello"
arr1 := []byte(str) // 转成byte切片
arr1[0] = 'z'
str = string(arr1)

//2 这种没有限制
arr1 := []rune(str)
arr[0] = '哈'
str = string(arr1)
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
    // recover()是内建函数, 用于捕捉异常
		if err := recover(); err != nil {
			fmt.Println("err: ", err)
		}
	}()
	num1, num2 := 10, 0
	fmt.Println("result: ", num1/num2)
}
```

**自定义错误**

1. error.New(): 返回一个error类型的值, 表示一个错误
2. panic(): 内置函数, 接受一个interface{}类型的值,表示输出错误信息, 并退出程序

```go
func main() {
	err := readConf("aaa")
	if err != nil{
		panic(err)
	}
	fmt.Println("读取文件错误")
}

func readConf(name string)(err error){
	if !strings.HasSuffix(name,"ini") {
		return errors.New("文件读取错误")
	}
	return nil
}
```

### 数组

```go
//数组是值类型, 在默认情况下是值传递, 因此会进行拷贝
var 变量名 [num]数据类型
//如 var arr [10]int

//四种初始化数组的方式
var arr [3]int = [3]int{1, 2, 3}
var arr = [3]int{5, 6, 7}
var arr = [...]int{8, 9, 0}
var arr = [...]int{1:10, 0:30, 3:90}
```

### 切片(Slice) 

```go
//切片是数组的引用, 引用类型
var 变量名 []类型
// 如: var sli []int

//定义
//定义一个切片, 让切片去引用一个创建好的数组 
var arr [5]int = [...]int {1, 2, 3, 5, 6}
var sli = arr[1, 3] //下标从1到3
//make type:类型, len:大小 cap:指定容量,可选>=len
var 切片名 []type = make([], len, [cap])
var sli []float64 = make([]float64, 5, 10)
//直接分配
var sli []int = []int {1, 2, 3}

//获取切片长度
len(sli)

//获取切片容量
cap(sli)

//扩容
append(sli, []int{10, 20, 20})
append(sli, sli2...) //后面必须有3个点

//copy
copy(dst, src []Type)

var sli3 = []int{1, 2, 3, 4, 5}
var sli4 = make([]int, len(sli3))
copy(sli4, sli3)
sli3[0] = 9
fmt.Println(sli3)
fmt.Println(sli4)
//[9 2 3 4 5]
//[1 2 3 4 5]
```

**数组和切片**

```go
//定义并实例化切片	
var sli []int = make([]int, 10, 50)
fmt.Printf("sli type %T\n", sli)
//定义并实例化数组
var arr [10]int = *new([10]int)
fmt.Printf("arr type %T\n", arr)

//sli type []int
//arr type [10]int
```

### 多维数组

```go
var arr = *new([4][6]int)
//[[0 0 0 0 0 0] [0 0 0 0 0 0] [0 0 0 0 0 0] [0 0 0 0 0 0]]

//遍历多维数组
var arr = *new([4][6]int)
for i := 0; i < len(arr); i++ {
  for j := 0; j < len(arr[i]); j++ {
    fmt.Printf("arr[%d][%d] %d\t", i, j, arr[i][j])
  }
  fmt.Println()
}

```

### Map

map是一种引用类型

```go
//keyType可以是多种类型, bool,数字,string,指针,channel,interface,struct,array
//alueType和keyType类型一样
var 变量名 map[keyType]valueType

//分配空间
var m map[string]string = make(map[string]string, 10)
m := map[string]string{
  "name":"wanli", "age":"18",
}
//增 改一样
m["add"] = "add str"

//删除map key
delete(map, key)
//delete(m, "hello")

//查 存在ok为true,否则false
val, ok:= m["check"]

//遍历
for k, v := range m{
  fmt.Printf("k=%v v=%v\n", k, v)
}
```

### Map切片

```go
//定义
var mapsli = []map[string]string
mapsli = make([]map[string]string, 2)
mapsli[0] = make(map[string]string)
```

## 面向对象编程

Golang也支持面向对象编程(OOP), 但是和传统的面向对象编程有区别, 并不是纯粹的面向对象编程语言, 所以应该说**Golang支持面向对象编程特性**比较准确. Golang没有**Class**, 他的OOP特性是通过**结构体**实现的. 并且去掉了继承*, *方法重载*, *构造函数*, *析构函数*, *隐藏this指针*, 这些特性是用过其他方式来实现的

### 结构体

```go
//定义结构体
type StructType struct{
	field1 Type
  field2 Type
  ...
}

type Dog struct{
  Name string
  Age int
  Color string
}

//使用结构体
//1
var dog Dog
dog.Name="旺财"
dog.Age=2
fmt.Println(dog)
//2
dog := Dog{Name:"旺财", Age:2}
//3
var d *Dog = new(Dog)
//下面这样写是标准的写法
(*d).Name = "旺财"
//下面是golang做了简化
d.Name = "旺财"
```

Golang中结构体是值类型, 可以不用先初始化,默认为零值, 指针, slice,和map的零值都是nil, 即还没有分配空间, `has a`

```go
type Dog struct{
  Name string
  Age int
}

func main(){
  var dog Dog = *new(Dog)
}
```

struct互相转换字段必须完全一样

struct可以在每个字段上架一个tag, 该tag可以使用反射机制获取,常用语序列化和反序列化

```go
type Master string{
  Name string `json:name`
  Skill string `json:skill`
}
```

### 方法

Golang中的方法是作用在指定数据类型上的, 即和指定数据类型绑定.

```go
//首先要有自定义类型
type 自定义类型 Type{}

func (类型引用 自定义类型) 方法名(传入参数)(返回值){
	方法体
  return 返回值
}

//e.g
type Person struct{
  Name string `json:"name"`
  Age int			`json:"age"` 
}
//将eat方法和Person绑定, 只能通过Person调用, 在这里p只是调用之的拷贝
func (p Person) eat(){
  fmt.Println("我的名字是: ", p.Name)
}
```

用指针还是值传递

```go
//定义一个结构体
type Point struct{
  X int `json:"x"`
  Y int `json:"y"`
}
//定义两个方法
func (p Point) AlterValue(){
  p.X = 10
  p.Y = 10
}

func (p *Point) AlterPointer(){
  (*p).X = 20
  p.Y = 20
}

func main(){
	point := Point{1,1}
	point.AlterValue()
	fmt.Printf("alter value point: x:%v, y:%v\n", point.X, point.Y)
  //(&point).AlterPointer() //标准使用地址调用
	point.AlterPointer()
	fmt.Printf("alter value point: x:%v, y:%v\n", point.X, point.Y)
}

//结果
alter value point: x:1, y:1
alter value point: x:20, y:20
```

如果方法实现了String()这个方法, 那么调用fmt.Println()的时候就会自动去调用String()方法

**方法和函数的区别**

1. 调用方式不同
2. 对于普通函数, 接受者是什么类型传入值必须一样, 方法不同
3. 方法的接受者为值类型时, 可以用指针类型调用

#### 工厂模式

Golang没有构造函数, 通常使用工厂模式来解决这个问题

#### 三大特性

**封装 继承 多态**

使用struct将字段首字母小写, 提供首字母大写的方法实现对外暴露, golang在开发中没有特别强调封装.对面向对象做了简化

#### 继承

解决代码复用的问题, 让我们的编程更加靠近人类思维, 使用匿名结构体实现. 如果一个struct嵌套了另一个

匿名结构体, 那么这个结构体就可以直接访问匿名结构体的字段和方法

```go
package main

import "fmt"

func main() {
	stu := Student{People: People{"asd", 14}, Score: 323.2}
	stu.Score=600
	stu.GetAge()
}

type People struct {
	Name string
	age  int
}

type Student struct {
	People
	Score float64
}

func (s *Student) GetAge(){
	fmt.Printf("我的年龄是 %d 岁", s.age) //可以访问私有字段
}
```

对于自有字段和继承字段同名采用就近原则, 对于多继承重名必须指定调用哪个匿名结构体

### 接口(Interface)

一组方法定义

```go
type Read interface{
  method1()
}
```

不需要显示实现, 一个自定义类型需要将某个接口的所有方法都实现了,才能说这个自定义类型实现了这个接口. 只要是自定义类型都可以实现接口

interface是引用类型

所有类型都实现了空接口(`interface{}`)



**继承的价值**: 解决代码的复用性和可维护性

**接口的价值**: 设计, 规范



类型断言

```go
package main

import "fmt"

type Point struct{
  x int
  y int
}

func main(){
  var a interface{}
  var point Point Point{1, 2}
  a = point //ok
  var b Point
  b ok = a.(Point) //类型断言
  if !ok {
  	//转化失败
  }
  fmt.Println(b)
}
```

## 文件操作

使用流来处理, 主要在`os.File`

```go
f, e := os.Open("/Users/wanli/a.txt")
defer f.Close()
if e != nil {
  fmt.Println("errorororo", e)
}
fmt.Println("file is ", f)
```

带缓冲的方式(bufio)

```go
func main() {
	f, e := os.Open("/Users/wanli/a.txt")
  if e != nil {
    fmt.Println("errorororo", e)
  }
  defer f.Close()

  reader := bufio.NewReader(f)

  for {
    str, err := reader.ReadString('\n')
    if err == io.EOF {
      break
    }
    fmt.Print("读取的值: ", str)
  }
  fmt.Println("文件读取结束")
}
```

一次性读取到内存(ioutil)

```go
func main() {
	f := "/Users/wanli/a.txt"
	bytes, e := ioutil.ReadFile(f)
	if e != nil {
		fmt.Println("输出错误", e)
	}
	fmt.Printf("%s", string(bytes))
}
```

写文件

```go
func main() {
	f := "/Users/wanli/a.txt"
	file, e := os.OpenFile(f, os.O_RDWR | os.O_APPEND, 666)
	defer file.Close()
	if e != nil{
		fmt.Println("error")
	}
	defer file.Close()
	writer := bufio.NewWriter(file)
	for i := 0; i < 5;i++{
		writer.WriteString("hahahah\n")
	}
	writer.Flush()
}
```

## JSON

序列化, 使用`json.Marshal()`

```go
func Marshal(v interface{}) ([]byte, error) {...}
```

```go
package main

import (
	"encoding/json"
	"fmt"
)

func main() {
	demo := JSONDemo{12, "jsondemo"}
	bytes, e := json.Marshal(&demo)
	if e != nil {
		return
	}
	fmt.Println(string(bytes))  //{"id":12,"name":"jsondemo"}
}

type JSONDemo struct {
	Id   int    `json:"id"`
	Name string `json:"name"`
}
```

反序列化`json.Unmarshal()`

```go
func Unmarshal(data []byte, v interface{}) error {...}
```

```go
package main

import (
	"encoding/json"
  "fmt"
)

func main() {
	var js = `{"a": [1,10,3], "b": [10, 22, 11,2,3,4], "c": [1,2,3,4,5]}`
	//反序列化不需要make, 因为被封装到Unmarshal()
	var m map[string][]int
	err := json.Unmarshal([]byte(js), &m)
	if err != nil {
		return
	}
	fmt.Println(m)
}
```

## Test

golang 自带测试框架 testing 自带go test. 

1. 测试文件必须以`_test.to`结尾, 如: `cal_test.go`
2. 测试用例函数必须以`Test`开头, 如: `TestSum()`
3. 形参必须是`t *testing.T`
4. 允许有多个测试用例
5. `go test` 运行正确无日志, 错误有日志, `go test -v` 运行正确或者错误都有日志

```go
//main.go
package main

func Sum(a, b int) int {
  return a +b
}

//cas_test.go
package main

import "testing"

func TestSum(t *testing.T) {
	s := Sum(4, 7)
	if  s != 11 {
 		t.Fatalf("执行错误 Sum(4, 7), 期望值: %v, 实际值: %v", 11,s )
	}
	t.Log("执行正确 Sum(4, 7)")

	s2 := Sum(1, 1)

	if s2 != 3{
		t.Fatalf("执行错误 Sum(1, 1), 期望值: %v, 实际值: %v", 3, s2)
	}
	t.Log("执行正确 Sum(1, 1)")
}

// go test -v

//=== RUN   TestSum
//--- FAIL: TestSum (0.00s)
//    cas_test.go:10: 执行正确 Sum(4, 7)
//    cas_test.go:15: 执行错误 Sum(1, 1), 期望值: 3, 实际值: 2
//FAIL
```

测试单个文件一定要带上被测试的原文件

```bash
go test -v cal_test.go cal.go
```

测试单个方法

```bash
go test -v -test.run Sum
```

## goroutine(协程)和channel(管道)

- 进程和线程

  进程是操作系统进行资源分配和调度的基本单位

  线程是程序执行的最小单元

- 并发和并行

  **并发**: 多线程程序在单核上运行

  **并行**: 多线程程序在多核上运行