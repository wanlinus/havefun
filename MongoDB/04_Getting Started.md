# 入门

下一页提供了在MongoDB Shell中进行查询的各种示例。有关使用MongoDB驱动程序的示例，请参阅[其他示例部分](https://docs.mongodb.com/manual/tutorial/getting-started/#gs-additional-examples)中的链接。

在Shell内单击以进行连接。连接后，您可以在上面的外壳中运行示例。

**0. 切换数据库**

在shell中, **db**指向你当前的数据库, 输入**db**显示默认的数据库

````shell
> db
````

当前操作应该返回**test**, 这是默认的数据库

切换数据库,输入 **use <db>**, 例如, 切换到**example**数据库:

```shell
> use examples
```

在切换数据库之前你不需要去创建他. MongoDB会在你第一次存储数据的时候创建(比如在数据库中创建第一个集合)

要验证您的数据库现在是示例，请在上面的外壳中键入**db**

```shell
db
```

**1. 插入数据**

MongoDB将文档存储在集合中. 集合类似于关系数据库中的表. 如果不存在集合. 则在您第一次为该集合存储数据时, MongoDB会创建该集合.

以下示例使用db.collection.insertMany()方法将新文档插入清单集合. 您可以将示例复制并粘贴到上面的Shell中.

```bash
db.inventory.insertMany([
   { item: "journal", qty: 25, status: "A", size: { h: 14, w: 21, uom: "cm" }, tags: [ "blank", "red" ] },
   { item: "notebook", qty: 50, status: "A", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank" ] },
   { item: "paper", qty: 10, status: "D", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank", "plain" ] },
   { item: "planner", qty: 0, status: "D", size: { h: 22.85, w: 30, uom: "cm" }, tags: [ "blank", "red" ] },
   { item: "postcard", qty: 45, status: "A", size: { h: 10, w: 15.25, uom: "cm" }, tags: [ "blue" ] }
]);

// MongoDB adds an _id field with an ObjectId value if the field is not present in the document
```

该操作返回一个包含确认指示符的文档和一个包含每个成功插入的文档的**_id**的数组.

要验证插入, 您可以查询集合(请参见下一个选项卡).

**2. 查询所有的文档**

要从集合中查询文档，可以使用db.collection.find()方法。要查询集合中的所有文档，请将空文档作为查询过滤器文档传递给该方法。

在外壳程序中，复制并粘贴以下内容以返回**inventory**的所有集合文档。

```shell
> db.inventory.find({})
```

格式化结果可以在**find**后使用**.pretty()**

```shell
db.inventory.find({}).pretty()
```

**3. 匹配查询**

对于相等匹配(即<field> equals <value>), 请在查询过滤器中指定<field>:<value>, 并传给db.collection.find()方法

- 在Shell程序中，复制并粘贴以下内容以返回**status**字段等于“ D”的文档:

```shell
db.inventory.find( { status: "D" } );
```

- 在Shell程序中，复制并粘贴以下内容以返回**qty**字段等于0的文档:

```shell
db.inventory.find( { qty: 0 } );
```

- 在Shell程序中，复制并粘贴以下内容以返回其中**qty**字段等于0且**status**字段等于"D"的文档

```shell
db.inventory.find( { qty: 0, status: "D" } );
```

- 在Shell程序中，复制并粘贴以下内容以返回文档，其中嵌套在size文档内的**uom**字段等于"**in**"

```shell
db.inventory.find( { "size.uom": "in" } )
```

- 在shell程序中，复制并粘贴以下内容以返回文档，其中size字段等于文档{h: 14, w: 21, uom: "cm"}

```shell
db.inventory.find( { size: { h: 14, w: 21, uom: "cm" } } )
```

​		嵌入式文档上的相等匹配要求完全匹配，包括字段顺序。

- 在Shell程序中，复制并粘贴以下内容以返回文档，其中**tag**数组包含“red”作为其元素之一

```shell
db.inventory.find( { tags: "red" } )
```

如果**tags**字段是一个字符串而不是一个数组，那么查询只是一个相等匹配。

- 在Shell程序中，复制并粘贴以下内容以返回标签字段与指定数组完全匹配（包括顺序）的文档

```shell
db.inventory.find( { tags: [ "red", "blank" ] } )
```

**4.  指定要返回的字段(映射)**

要指定要返回的字段，请将投影文档传递给**db.collection.find（<查询文档>，<投影文档>）**方法。在投影文档中，指定：

- <field>: 1, 以在返回的文档中包含一个字段
- <field>: 0, 以排除返回的文档中的字段

在Shell程序中，复制并粘贴以下内容，以返回清单集合中所有文档的**_id**，**item**和**status**字段

```shell
db.inventory.find( { }, { item: 1, status: 1 } );
```

您不必指定**_id**字段即可返回该字段。默认情况下返回。要排除该字段，请在投影文档中将其设置为0。例如，复制粘贴以下内容以仅返回项目以及匹配文档中的状态字段: 

```shell
db.inventory.find( {}, { _id: 0, item: 1, status: 1 } );
```

