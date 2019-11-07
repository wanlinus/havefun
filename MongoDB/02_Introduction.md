# MongoDB 介绍(Introduction to MongoDB)

[toc]

欢迎使用MongoDB 4.2手册! MongoDB是一个被设计来简化开发和扩展的文档数据库.本手册介绍了MongoDB的关键概念.介绍了查询语言, 并提供操作和管理注意事项与过程以及全面的参考部分

MongoDB数据库提供了社区版和企业版

- MongoDB 社区版是MongoDB的[开源自由](https://github.com/mongodb/mongo/)版本
- MongoDB企业版是MongoDB企业高级版订阅的一部分, 包括为你的MongoDB部署提供全面支持.MongoDB Enterprise还添加了以企业为中心的功能，例如LDAP和Kerberos支持，磁盘加密和审计。

## 文档数据库(Document Database)

MongoDB的一条记录是一个由字段和值组成的数据结构的文档, MongoDB文档类似于JSON对象.字段的值可以包括其他文档, 数组或者文档数组.

![A MongoDB document.](https://docs.mongodb.com/manual/_images/crud-annotated-document.bakedsvg.svg)

使用文档的优点:

- 文档(对象)对应许多编程语言的原生数据类型
- 嵌入式文档或数组减少了数据库的联表查询
- 动态模式支持流畅的多态性。

**集合/视图/按需实例化视图 (Collections/Views/On-Demand Materialized Views) **

MongoDB将文档(documents)存储在[集合(collections)](https://docs.mongodb.com/manual/core/databases-and-collections/#collections)里, 集合类似于关系型数据库的表

除了集合外, MongoDB还支持:

- 只读视图(从3.4开始)
- 按需实例化视图(从4.2开始)

### 关键特性

**高性能 High Performance**

MongoDB提供高性能的数据存储, 尤其是

- 支持嵌入式数据模式以减少数据系统的I/O活动
- 索引支持更快的查询, 并且包括从嵌入文档和数组

**丰富的查询语言 Rich Query Language**

MongoDB 支持丰富的查询语言来支持[读写操作(CRUD)](https://docs.mongodb.com/manual/crud/)以及:

- 数据汇总
- 文本搜索和地理空间查询

**高可用 High Availability**

MongoDB复制工具, 称作[副本集(replica set)](https://docs.mongodb.com/manual/replication/), 提供了:

-  自动故障转移
-  数据冗余

副本集是一组维护相同的数据集的MongoDB服务器, 提供数据冗余和数据高可用

**可水平伸缩**

MongoDB提供了水平可伸缩性作为其核心功能的一部分:

- 分布式机器的数据[分片](https://docs.mongodb.com/manual/sharding/#sharding-introduction)
-  从3.4开始，MongoDB支持基于分片键创建数据区域。在平衡的集群中，MongoDB仅将区域覆盖的读写定向到区域内的那些分片。有关更多信息，请参见区域手册页。 

**支持多存储引擎**

 MongoDB支持[多种存储引擎](https://docs.mongodb.com/manual/core/storage-engines/)：

-  [WiredTiger存储引擎](https://docs.mongodb.com/manual/core/wiredtiger/)(包括对[静态加密](https://docs.mongodb.com/manual/core/security-encryption-at-rest/)的支持)
- [内存存储](https://docs.mongodb.com/manual/core/inmemory/)

 此外，MongoDB提供可插拔的存储引擎API，允许第三方为MongoDB开发存储引擎。

[<-手册内容](./01_MongoDB Manual Contents.md)

[安装->](./03_Installation.md)

