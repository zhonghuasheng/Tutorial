### 学习路线
* Docker基础知识
    * [CentOS下安装Docker](#CentOS下安装Docker)
    * [基础概念](https://www.cnblogs.com/Can-daydayup/p/15559341.html)
    * [常用命令](#常用命令)
* Docker基础使用
    * Docker发布SpringBoot项目 https://www.jianshu.com/p/397929dbc27d
    * Docker+Jenkins+Git+SpringBoot构建自动化部署

### Docker是什么
Docker是一个开源的应用容器引擎，Docker其实就是可以打包程序和运行环境，把环境和程序一起发布的容器，当你需要发布程序时，你可以使用Docker将运行环境一起发布，其他人拿到你的程序后可以直接运行，避免出现一次编译，到处调试的尴尬局面~。Docker的出现主要是为了解决“在我的机器上是正常的，为什么到你的机器上就不正常了”的问题，但是随着Docker的进步，以及K8S等编排软件的流行，Docker的使用场景大大扩展，Docker已经成为高可用服务集群持续交付、继续集成以及云原生的关键技术。

### Docker解决了什么问题
Docker主要解决了在你的环境上运行没问题，但是在我的环境上运行有问题

#### CentOS下安装Docker
``` shell
# linux 3.10 内核，docker官方说至少3.8以上，建议3.10以上
[root@localhost ~]# uname -a
# 把yum包更新到最新(温馨提示：新环境或测试环境可随意操作，生产环境酌情慎重更新)
[root@localhost ~]# yum update
# 设置yum源
[root@localhost ~]# yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo（阿里仓库）
# 可以查看所有仓库中所有docker版本
[root@localhost ~]# yum list docker-ce --showduplicates | sort -r
# 安装Docker，命令：yum install docker-ce-版本号
[root@localhost ~]# yum install docker-ce-18.03.1.ce
[root@localhost ~]# systemctl start docker
[root@localhost ~]# systemctl enable  docker
[root@localhost ~]# docker version
```

#### 常用命令
```shell
启动 systemctl start docker
停止 systemctl stop docker
重启 systemctl restart docker
查看状态 systemctl status docker
开机启动 systemctl enable docker
查docker信息 docker info
列举在跑的容器 docker ps
列举出所有容器 docker ps -a 包含历史
查看镜像 docker image ls 或 docker images
启动容器 docker start containerId
停止容器 docker stop containerId
重启容器 docker restart containerId 或者 docker restart 容器名字
删除容器 docker rm containerId
查看日志 docker logs [-f 跟踪日志输出 -t 显示时间戳 --since="2021-11-19"显示某个时间的所有日志 --tail=10列出最新的N条日志] containerId
修改容器名字 docker rename 容器原来名 要改为的名字
```

#### Docker中启动springboot项目

FROM java:8
MAINTAINER luke.chen <xiaoyong690@126.com>
VOLUME /tmp
ADD demo-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]