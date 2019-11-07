# MongoDB

[TOC]

## 简介

MongoDB是一个基于分布式的文件存储数据库. C++编写. 旨在为WEB应用提供可扩展的高性能数据库存储方案.

## 特点

- 高性能 易部署, 易使用
- 面向集合存储
- 模式自由
- 支持完全索引, 包含内部对象
- 支持查询
- 支持复制和故障恢复

## 适用场景

- 网站数据：Mongo 非常适合实时的插入，更新与查询，并具备网站实时数据存储所需的复制及高度伸缩性。
- 缓存：由于性能很高，Mongo 也适合作为信息基础设施的缓存层。在系统重启之后，由Mongo 搭建的持久化缓存层可以避免下层的数据源过载。
- 大尺寸、低价值的数据：使用传统的关系型数据库存储一些数据时可能会比较昂贵，在此之前，很多时候程序员往往会选择传统的文件进行存储。
- 高伸缩性的场景：Mongo 非常适合由数十或数百台服务器组成的数据库，Mongo 的路线图中已经包含对MapReduce 引擎的内置支持。
- 用于对象及JSON 数据的存储：Mongo 的BSON 数据格式非常适合文档化格式的存储及查询。

## 安装

推荐使用Docker的方式, 简单

```bash
docker run -d -p 27017:27017 -v mongo_configdb:/data/configdb -v mongo_db:/data/db --name mongo mongo
```

其他方式参看[官方网站](https://www.mongodb.com/download-center/community).



## 常用命令

使用mongo客户端链接服务器

```bash
docker exec -it mongo bash

root@6e2e570881c0:/# mongo
MongoDB shell version v4.2.1
connecting to: mongodb://127.0.0.1:27017/?compressors=disabled&gssapiServiceName=mongodb
Implicit session: session { "id" : UUID("6e2ae5ca-e947-45e9-98be-5c3d7e48533c") }
MongoDB server version: 4.2.1
Server has startup warnings: 
2019-11-05T15:59:57.481+0000 I  STORAGE  [initandlisten] 
2019-11-05T15:59:57.481+0000 I  STORAGE  [initandlisten] ** WARNING: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine
2019-11-05T15:59:57.481+0000 I  STORAGE  [initandlisten] **          See http://dochub.mongodb.org/core/prodnotes-filesystem
2019-11-05T16:00:00.613+0000 I  CONTROL  [initandlisten] 
2019-11-05T16:00:00.613+0000 I  CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2019-11-05T16:00:00.613+0000 I  CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2019-11-05T16:00:00.613+0000 I  CONTROL  [initandlisten] 
---
Enable MongoDB's free cloud-based monitoring service, which will then receive and display
metrics about your deployment (disk utilization, CPU, operation statistics, etc).

The monitoring data will be available on a MongoDB website with a unique URL accessible to you
and anyone you share the URL with. MongoDB may use this information to make product
improvements and to suggest MongoDB products and deployment options to you.

To enable free monitoring, run the following command: db.enableFreeMonitoring()
To permanently disable this reminder, run the following command: db.disableFreeMonitoring()
---

> 
```

显示当前使用的database, 默认是使用test

```bash
> db
```

切换数据库的命令和MySQL类似, 不需要在切换数据库之前创建. 他会在你第一次存储数据的时候自动创建

```bash
> use examples
```



## Databases and Collections(数据库和集合)

MongoDB使用BSON文档将数据存在Collections中, Collections存在Databases中

![](https://docs.mongodb.com/manual/_images/crud-annotated-collection.bakedsvg.svg)

### Databases(数据库)

在MongoDB中, databases保存文档型的集合

在shell中, **db**指向你当前的数据库, 输入**db**显示当前的数据库

```bash
> db 
```

这个操作应该返回

创建一个数据库

```
> use myDB
> db.myNewCollection1.insertOne({x: 1})
```

