 # Docker常用命令

[TOC]

## docker run

- -a stdin: 指定标准输出类型, 可选`stdin/stdout/stderr`

- -d: 后台运行程序, 返回容器ID

- -i: 交互式运行容器, 通常与-t同时使用

- -t: 为容器分配一个伪终端

- -P: 随机端口映射, 容器端口随机映射到主机的高端口

- -p list: 指定端口映射 格式为: `-p 主机端口:容器端口`

- --name string: 为容器指定一个名字

- --dns list: 设置自定义DNS 

- -h string: 指定容器hostname

- -m bytes: 指定容器最大内存

- -v: 绑定一个卷 `-v 宿主机目录:容器目录`

- --network somework

## Nginx

   ```bash
docker run --name some-nginx -v /some/content:/usr/share/nginx/html:ro -d nginx
   ```

## MySQL

   - 启动一个简单的mysql
   
     ```bash
     docker run -d -p 3306:3306 --name mysql-server -e MYSQL_ROOT_PASSWORD=mysqlpassword mysql:5.7
     //创建一个utf8mb4的schemas
     CREATE SCHEMA `aaa` DEFAULT CHARACTER SET utf8mb4;
     ```
   
   - 将配置文件挂载出来
   
     ```bash
     -v /my/custom:/etc/mysql/conf.d
     ```
     
   - 将数据挂载出来
   
     ```bash
     -v /my/own/datadir:/var/lib/mysql
     ```


## Gitea

   - 使用内置SQLite

   - ```bash
     docker run -d --name=gitea -p 10022:22 -p 80:3000 -v ~/gitea:/data gitea/gitea:latest
     ```

   - 使用外置数据库

## MongoDB

- ```bash
  docker run -d -p 27017:27017 -v mongo_configdb:/data/configdb -v mongo_db:/data/db --name mongo mongo
  ```


## 使用远端docker server

```bash
sudo vim /lib/systemd/system/docker.service

ExecStart=/usr/bin/dockerd -H fd:// -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375

systemctl daemon-reload

systemctl restart docker
```

本地客户端设置

```bash
unset DOCKER_HOST

nc -v -w 2 10.0.0.20 -z 2375 &> /dev/null

if [ $? -eq 0 ]; then

​    export DOCKER_HOST="tcp://10.0.0.20:2375"

fi
```

