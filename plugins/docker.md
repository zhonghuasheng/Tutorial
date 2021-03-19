### 快速上手

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