 

sudo vim /lib/systemd/system/docker.service

ExecStart=/usr/bin/dockerd -H fd:// -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375

systemctl daemon-reload

systemctl restart docker

 

### 使用远端docker server

unset DOCKER_HOST

nc -v -w 2 10.0.0.20 -z 2375 &> /dev/null

if [ $? -eq 0 ]; then

​    export DOCKER_HOST="tcp://10.0.0.20:2375"

fi