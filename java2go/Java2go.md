# Java2Go

[TOC]

这篇文章名为Java2Go,顾名思义就是从一个java开发者的角度学习Golang.

我从事的公司主要业务是做PaSS平台, 虽然架构使用SpringCloud搭建起来的.不过里面会用到docker, kubernetes, istio, knative等很多云原生的组件,而这些组件都是用golang写的所以和这些组件打交道的模块是使用golang编写. 云原生是以后的趋势. 所以学习golang扩充我的技能很有必要.我平时在公司做的工作主要是对解决系统业务的bug和对jenkins的二次开发, 主要使用的语言是java.市场上的java程序员非常的多最近比较空闲有机会学习由于才毕业一年多如果文章中存在缺陷请见谅

##Hello World

好,下面开始正式的golang学习, 先来一个helllworld

```java
//java HelloWorld.java
public class HelloWorld{
  public static void main(String[] args){
    System.out.println("Hello World");
  }
}
```

```go
//golang helloworld.go
package main

import "fmt"

func main(){
  fmt.Println("Hello World")
}
```

对比Java和golang的HelloWorld.两个程序都很简单. 有相似点也有不同的地方

相似:

1. **程序入口main**, golang叫函数, java叫方法. 

​	`方法指的是一段被它关联的对象通过它的名字调用的代码块` 

​	`函数（function）是指一段可以直接被其名称调用的代码块`

2. **package**关键字一样
3. **import** 关键字一样

不同:

1. **文件名**: Java文件名必须和公开类名相同, golang没有限制
2. **程序入口方法(函数)包名**: java 包名可以任意, 只要是psvm就可以. golang main必须在main包中
3. **main传参**: java为String[] args, golang没有
4. **行结束符**: java必须使用`;`结尾, golang不强制使用`;`, 因为golang编译器会在后面默认加(这就有点恶心了)

## 基本数据类型

绝大部分(我怕读者开发一个没有基本数据类型的语言)编程语言都是有基本数据类型的, 下面列出他们

|  类型  | java                | golang                                                       |
| :----: | ------------------- | :----------------------------------------------------------- |
|  整形  | byte short int lang | int8(uint8) int16(uint16) int32(uint32) int64(uint64) int(uint)<br/>byte rune uintptr |
|  浮点  | float double        | float32 float64 complex64 complex128                         |
|  字符  | char                | /                                                            |
|  布尔  | boolean             | bool                                                         |
| 字符串 | /                   | string                                                       |
|  数组  |                     |                                                              |
| 结构体 |                     |                                                              |

对比两种语言`数值型基本数据类型`(为什么这么说呢?是因为golang还有其他数据类型), 发现golang的整数型有非常的多, 下面来和java做一个一一说明:

1. **整型**: `int8`(golang)等价`byte`(java), 都是占用8位(一个字节).没错, `int16`(golang)等价`short`(java), `int32`(golang)等价 `int`(java), `int64`(golang)等价`long`(java), 分别占用16, 32, 64位. 不过`int`(golang)和`int`(java)太一样, golang中`int`是根据操作系统位数确定的, 32位系统int占32位, 64位系统占64位, java中的`int`固定占位32位.golang在前面数据类型的基础上添加了一种叫**无符号(unsigned)**类型数据类型(很多语言都有, java没有), 就是`uint8`, `uint16`, `uint32`, `uint64`,`uint`存储的值必须为非负数. `uintptr` 这个类型比较特殊, 他代表能存储指针的整型
2. **浮点型**: 按照golang尿性, `float32`,`float64`肯定占位32, 64位, 分别对应`float`, `double`, golang还增加了64位和128位的复数类型
3. **字符型**: golang中是没有`char`类型的, 因为每一个字符在计算机中存储的当时都是和字符编码表的一个数字一一对应, 所以golang中使用整型数据存储字符, 一般推荐使用`byte`, `rune`类型, 前者存ASCII码, 后者存Unicode.
4. **布尔型**: 就名字不一样, 一个`boolean`(java), 一个`bool`(golang)
5. **字符串**: 这里要注意了, java中的java.lang.String表示字符串类型,是一个类,不是基础类型. golang中的字符串就有专门的数据类型`string` 初值为`""`不是`nil`(golang中的空不是`null`而是`nil`, 有点反人类),他是一个UTF-8字符串
6. **array, struct**: `array` java中是引用类型, golang是值类型.` struct` java中没有这个概念, 可以把它理解为只有成员变量没有方法的class
7. **引用类型**: slice, map, channel这三种golang类型, java中类似的是`ArrayList`, `Map`, `MQ`.

## 基本语法

###变量定义

变量定义这块我觉得golang是真的反人类, 在大学里面我们是从C, C++, Java学过来, 都是按照

```java
变量类型 变量名称 [= 值];
var 变量名 = 值; //自动类型推断 java10+支持 不再有杠精了吧
```

在Golang中定义变量有多重方式, 和C系语言刚好相反

```go
var 变量名 [变量类型] [=] [值]
var 变量1, 变量2, 变量3 类型
变量 := 值 //自动类型推断
变量1, 变量2, 变量3 s:= 值1, 值2, 值3

// 比如 
var n1 int = 4
var arg1, arg2 arg3 int
b2 := 23
n1, n2, n3 := 12, "hello", 14.9
```

### 方法定义

java定义方法是必须要有返回类型, 方法名, 传入参数类型形如:

```java
[修饰符] 返回值类型 方法名([传入参数]){
	[return;]
}

// e.g
public static void main(String[] args){
}
```

golang函数定义使用关键字`func`, 和java不同的地方是将返回类型放后面,这点和定义变量的时候类似. golang函数还有其他的特性比如返回多个参数. 

```go
func 方法名([传入参数]) [(返回类型)] {
}

// e.g
func helloworld(n1, n2 int) (sum, sub int) {
  sum = n1 + n2
  sub = n1 - n2
  return
}
```

上面举了一个例子, 使用`hellworld`函数计算两个数的和和差

看起来很奇怪下面来解释一下:

1. 函数定义使用`func`关键字声明
2. 同时定义多个相同类型变量
3. 支持对函数返回值命名(我大java不支持)

Golang支持**可变参数**定义, 和Java类似

```go
func sum(n1 int, args ...int) (all int){
	// do some thing
}
```

**init函数**, golang中有一个非常特殊的函数`init`, 他会在调用main之前调用.他在java中没有一个对等的方法. 既然说到init函数, 那就说下golang中的初始化操作流程 `全局变量定义` -> `init` -> main. 既然全局变量的优先级高于init, 那么其实是可让自定义函数的执行在init之前, 如:

```go
package main

import "fmt"

var num = func(n1, n2 int) int {
	fmt.Println("执行初始化操作 num")
	return n1 + n2
}(10, 2)

func init() {
	fmt.Println("执行init初始化操作")
}

func main() {
	fmt.Println("执行main num:", num)
}
```

终端输出:

```
执行初始化操作 num
执行init初始化操作
执行main num: 12
```

**匿名函数** 前面的代码示例在定义num全局变量的时候使用了没有函数名的函数, 对于这种函数叫匿名函数. 对于这波骚操作java中我立马就想到了一个类似的用法 -- lambda表达式.









