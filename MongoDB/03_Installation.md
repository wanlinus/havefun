# Installation

MongoDB有两个版本：*Community*和*Enterprise*。

> 窍门:
>
>  手册的此部分包含有关安装MongoDB的信息。
>
> - 有关将当前部署升级到MongoDB 4.2的说明，请参阅[升级过程](https://docs.mongodb.com/manual/release-notes/4.2/#upgrade)。
> - 有关升级到当前版本的最新修补程序版本的说明，请参阅[升级到MongoDB的最新版本](https://docs.mongodb.com/manual/tutorial/upgrade-revision/)。

## MongoDB社区版安装教程

MongoDB社区版安装教程包括：

|         |                                                              |
| :------ | :----------------------------------------------------------- |
| Linux   | [Install MongoDB Community Edition on Red Hat or CentOS](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-red-hat/)<br />[Install MongoDB Community Edition on Ubuntu](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/)<br />[Install MongoDB Community Edition on Debian](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-debian/)<br />[Install MongoDB Community Edition on SUSE](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-suse/)<br />[Install MongoDB Community on Amazon Linux](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-amazon/) |
| Mac     | [ Install MongoDB Community Edition on macOS](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/) |
| Windwos | [Install MongoDB Community Edition on Windows](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/) |

## MongoDB企业版安装教程

MongoDB企业版安装教程包括：

|         |                                                              |
| :------ | :----------------------------------------------------------- |
| Linux   | [ Install MongoDB Enterprise Edition on Red Hat or CentOS](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-red-hat/)<br />[Install MongoDB Enterprise Edition on Ubuntu](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-ubuntu/)<br />[Install MongoDB Enterprise Edition on Debian](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-debian/)<br />[Install MongoDB Enterprise Edition on SUSE](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-suse/)<br />[Install MongoDB Enterprise on Amazon Linux](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-amazon/) |
| Mac     | [ Install MongoDB Enterprise on macOS](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-os-x/) |
| Windows | [ Install MongoDB Enterprise Edition on Windows](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-on-windows/) |
| Docker  | [ Install MongoDB Enterprise with Docker](https://docs.mongodb.com/manual/tutorial/install-mongodb-enterprise-with-docker/) |

## 将Community Edition升级到Enterprise Edition教程

> 重要
>
> 不要使用这些说明升级到另一个发行版本。要升级发行版，请参阅相应的发行升级说明，例如[Upgrade to MongoDB 4.2](https://docs.mongodb.com/manual/release-notes/4.2/#upgrade)。

- [升级到MongoDB Enterprise（独立）](https://docs.mongodb.com/manual/tutorial/upgrade-to-enterprise-standalone/)
- [升级到MongoDB Enterprise（副本集）](https://docs.mongodb.com/manual/tutorial/upgrade-to-enterprise-replica-set/)
- [升级到MongoDB Enterprise（共享群集）](https://docs.mongodb.com/manual/tutorial/upgrade-to-enterprise-sharded-cluster/)

## 支持的平台

*在版本3.4中进行了更改：* MongoDB不再支持32位x86平台。

### x86_64

平台支持停产通知

| Ubuntu 14.04 | 支持已在MongoDB 4.2+中删除。                              |
| ------------ | --------------------------------------------------------- |
| Debian 8     | 支持已在MongoDB 4.2+中删除。                              |
| Debian 7     | 在MongoDB 4.0 +，3.6.6 +，3.4.16 +和3.2.21+中删除了支持。 |
| SLES 11      | 在MongoDB 3.6.4 +，3.4.15 +和3.2.20+中删除了支持。        |
| Ubuntu 12.04 | 在MongoDB 3.6.4 +，3.4.15 +和3.2.20+中删除了支持。        |

*即将停产的通知*：

| Windows 7 / 2008R2   | MongoDB将在将来的版本中终止支持。 |
| -------------------- | --------------------------------- |
| Windows 8/2012       | MongoDB将在将来的版本中终止支持。 |
| Windows 8.1 / 2012R2 | MongoDB将在将来的版本中终止支持。 |

| 平台                                                         | 4.2社区与企业 | 4.0社区与企业 | 3.6社区与企业 | 3.4社区与企业 |
| :----------------------------------------------------------- | :-----------: | :-----------: | :-----------: | :-----------: |
| Amazon Linux 2013.03及更高版本                               |       ✓       |       ✓       |       ✓       |       ✓       |
| Amazon Linux 2                                               |       ✓       |       ✓       |               |               |
| Debian 8                                                     |               |       ✓       |       ✓       |       ✓       |
| Debian 9                                                     |       ✓       |       ✓       |    3.6.5+     |               |
| Debian 10                                                    |    4.2.1+     |               |               |               |
| RHEL / CentOS / Oracle Linux [[1\]](https://docs.mongodb.com/manual/installation/#oracle-linux) 6.2及更高版本 |       ✓       |       ✓       |       ✓       |       ✓       |
| RHEL / CentOS / Oracle Linux [[1\]](https://docs.mongodb.com/manual/installation/#oracle-linux) 7.0及更高版本 |       ✓       |       ✓       |       ✓       |       ✓       |
| RHEL / CentOS / Oracle Linux [[1\]](https://docs.mongodb.com/manual/installation/#oracle-linux) 8.0及更高版本 |    4.2.1+     |               |               |               |
| SLES 12                                                      |       ✓       |       ✓       |       ✓       |       ✓       |
| Solaris 11 64位                                              |               |               |               |    仅社区     |
| Ubuntu 14.04                                                 |               |       ✓       |       ✓       |       ✓       |
| Ubuntu 16.04                                                 |       ✓       |       ✓       |       ✓       |       ✓       |
| Ubuntu 18.04                                                 |       ✓       |       ✓       |               |               |
| Windows Vista                                                |               |               |               |       ✓       |
| Windows 7 / Server 2008 R2                                   |       ✓       |       ✓       |       ✓       |       ✓       |
| Windows 8/2012 R2及更高版本                                  |       ✓       |       ✓       |       ✓       |       ✓       |
| macOS 10.12及更高版本                                        |       ✓       |               |               |               |
| macOS 10.11                                                  |               |       ✓       |       ✓       |       ✓       |

| [1]  | *（[1](https://docs.mongodb.com/manual/installation/#id1)，[2](https://docs.mongodb.com/manual/installation/#id2)，[3](https://docs.mongodb.com/manual/installation/#id3)）*的MongoDB仅支持运行红帽兼容内核（RHCK）的Oracle的Linux。MongoDB的确实**不**支持坚不可摧的企业内核（UEK）。 |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

### ARM64 

平台支持停产通知

| Ubuntu 16.04 ARM64 | 支持已在MongoDB Community 4.2+中删除。 |
| ------------------ | -------------------------------------- |
|                    |                                        |

| 平台         | 4.2社区与企业 | 4.0社区与企业 | 3.6社区与企业 | 3.4社区与企业 |
| :----------- | :-----------: | :-----------: | :-----------: | :-----------: |
| Ubuntu 18.04 |       ✓       |               |               |               |
| Ubuntu 16.04 |               |       ✓       |     企业      |     企业      |

### PPC64LE（MongoDB企业版）

| 平台            | 4.2企业 | 4.0企业 |     3.6企业      |     3.4企业      |
| :-------------- | :-----: | :-----: | :--------------: | :--------------: |
| RHEL / CentOS 7 |    ✓    |    ✓    |        ✓         |        ✓         |
| Ubuntu 16.04    |         |    ✓    | 从3.6.13开始删除 | 从3.4.21开始删除 |

### s390x 

| 平台            | 4.2社区与企业 | 4.0社区与企业 | 3.6企业 | 3.4企业 |
| :-------------- | :-----------: | :-----------: | :-----: | :-----: |
| RHEL / CentOS 7 |       ✓       |    4.0.6+     |         |         |
| RHEL / CentOS 6 |       ✓       |       ✓       |         |         |
| SLES12          |       ✓       |    4.0.6+     |         |         |
| Ubuntu 18.04    |       ✓       |    4.0.6+     |         |         |