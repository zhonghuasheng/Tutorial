### 快速入门
### 快速上手

### Docker是什么
Docker是一个开源的应用容器引擎，Docker其实就是可以打包程序和运行环境，把环境和程序一起发布的容器，当你需要发布程序时，你可以使用Docker将运行环境一起发布，其他人拿到你的程序后可以直接运行，避免出现一次编译，到处调试的尴尬局面~。Docker的出现主要是为了解决“在我的机器上是正常的，为什么到你的机器上就不正常了”的问题，但是随着Docker的进步，以及K8S等编排软件的流行，Docker的使用场景大大扩展，Docker已经成为高可用服务集群持续交付、继续集成以及云原生的关键技术。

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

#### Docker中启动springboot项目