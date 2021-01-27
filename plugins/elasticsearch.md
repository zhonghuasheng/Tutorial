### 学习笔记
* ES基础部分

## ES基础部分
> 两个端口(9200/9300)
* 节点客户端（Node Client）
    节点客户端作为一个非数据节点加入到本地集群中。换句话说，它本身不保存任何数据，但是它知道数据在集群中的哪个节点中，并且可以把请求转发到正确的节点。
* 传输客户端（Transport Client）
    轻量级的传输客户端可以将请求发送到远程集群。它本身不加入集群，但是它可以将请求转发到集群中的一个节点上。

两个 Java 客户端都是通过 9300 端口并使用 Elasticsearch 的原生 传输 协议和集群交互。集群中的节点通过端口 9300 彼此通信。如果这个端口没有打开，节点将无法形成一个集群。

> Near Realtime (NRT)

ES号称对外提供的是近实时的搜索服务，意思是数据从写入ES到可以被Searchable仅仅需要1秒钟，所以说基于ES执行的搜索和分析可以达到秒级。

> Index

index是一类拥有相似属性的document的集合，index名称必须是小写的字符。比如用户可以创建一个index，产品可以创建一个index，订单也可以创建一个index。

> Type

type作为index中的逻辑类别。比如用户这个index下，可以分用户的type，爱好的type

> Document

document就是ES中存储的一条数据，就像mysql中的一行记录一样。

> ES与Mysql对比着看

| ES | MySQL |
| -- | -- |
| index | 数据库 |
| type | 表 |
| document | 行 |

> Shards

shard可以理解为ES中的最小工作单元，可以理解为一个lucene的实现，拥有完整的创建索引，处理请求的能力。shard分为primary shard和replicas shard，primary shard与其对应的replicas shard不能同时存在于一台server中，当primary shard宕机时，其对应的replicas shard可以继续响应用户的都请求。通过这种分片的机制，可以横向的成倍提升系统的吞吐量，比如一个shard可以处理200/s请求，此时再加一个服务器，就能支持400/s请求，天生分布式，高可用。此外，每个一document肯定存在一个primary shard和对应的replica shard中，绝对不会出现同一个document同时存在于多个primary shard中的情况。
问题的引入：如果让一个index自己存储1TB的数据，响应的速度就会下降，为了解决这个问题，ES提供了一种将用户的index进行subdivide(分割，再分割)的操作，就是将index分片，每一片都叫一个shards，进而实现了将整体庞大的数据分布在不同的服务器上存储。

## ES启动失败常见问题汇总
### 内存不足

```shell
[root@VM_0_12_centos elasticsearch-5.5.1]# ./bin/elasticsearch
Java HotSpot(TM) 64-Bit Server VM warning: INFO: os::commit_memory(0x0000000085330000, 2060255232, 0) failed; error='Cannot allocate memory' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 2060255232 bytes for committing reserved memory.
# An error report file with more information is saved as:
# /root/software/elasticsearch-5.5.1/hs_err_pid26326.log

* 解决方案

编辑安装目录/elasticsearch-5.1.1/config下的jvm.options
#-Xms2g(默认是2g)
#-Xmx2g(默认是2g)
-Xms128m
-Xmx128m
```

### 不能以root用户启动es
```shell
[root@VM_0_12_centos elasticsearch-5.5.1]# ./bin/elasticsearch
[2021-01-27T16:48:47,418][WARN ][o.e.b.ElasticsearchUncaughtExceptionHandler] [] uncaught exception in thread [main]
org.elasticsearch.bootstrap.StartupException: java.lang.RuntimeException: can not run elasticsearch as root
        at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:127) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.bootstrap.Elasticsearch.execute(Elasticsearch.java:114) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.cli.EnvironmentAwareCommand.execute(EnvironmentAwareCommand.java:67) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.cli.Command.mainWithoutErrorHandling(Command.java:122) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.cli.Command.main(Command.java:88) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:91) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.bootstrap.Elasticsearch.main(Elasticsearch.java:84) ~[elasticsearch-5.5.1.jar:5.5.1]
Caused by: java.lang.RuntimeException: can not run elasticsearch as root
        at org.elasticsearch.bootstrap.Bootstrap.initializeNatives(Bootstrap.java:106) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.bootstrap.Bootstrap.setup(Bootstrap.java:194) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.bootstrap.Bootstrap.init(Bootstrap.java:351) ~[elasticsearch-5.5.1.jar:5.5.1]
        at org.elasticsearch.bootstrap.Elasticsearch.init(Elasticsearch.java:123) ~[elasticsearch-5.5.1.jar:5.5.1]
        ... 6 more

* 解决方案 新建ES用户
#  添加一个用户组
[root@localhost root]# groupadd esgroup
# 添加一个用户，-g是在用户组下 -p是密码
[root@localhost local]# useradd esuser -g esgroup -p Abcde12345_
# 进入es的安装目录
[root@localhost local]# cd /usr/local/elasticsearch 
# 给用户esuser授权 chown [-cfhvR] [--help] [--version] user[:group] file...
[root@localhost local]# chown -R esuser:esgroup elasticsearch-5.5.1/
# 切换到esuser用户
[root@localhost elasticsearch]# su esuser
#  再次启动
[XXX@localhost elasticsearch]$ ./bin/elasticsearch
```

### 找不到JAVA环境
```
[esuser@VM_0_12_centos elasticsearch-5.5.1]$ ./bin/elasticsearch
Error: Could not find or load main class org.elasticsearch.tools.JavaVersionChecker
Elasticsearch requires at least Java 8 but your Java version from /apps/jdk1.8.0_25/bin/java does not meet this requirement

* 很莫名其妙的重新登陆又好了
```

### vm.max_map_count不足
```shell
ERROR: [1] bootstrap checks failed
[1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[2021-01-27T19:24:38,603][INFO ][o.e.n.Node               ] [Q-gAi65] stopping ...

* 解决方案
sudo sysctl -w vm.max_map_count=262144
```

### 启动
* `nohup ./bin/elaseticsearch >xxx.log 2>&1 &`
* 请求`curl localhost:9200`得到说明信息
```shell
[esuser@VM_0_12_centos elasticsearch-5.5.1]$ curl localhost:9200
{
  "name" : "Q-gAi65",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "ZzneLnfdSIOPMp1bjA9shQ",
  "version" : {
    "number" : "5.5.1",
    "build_hash" : "19c13d0",
    "build_date" : "2017-07-18T20:44:24.823Z",
    "build_snapshot" : false,
    "lucene_version" : "6.6.0"
  },
  "tagline" : "You Know, for Search"
}
```
* 关闭 `ps -ef | grep elasticsearch` `kill -9 PID`
* 默认情况下，Elastic 只允许本机访问，如果需要远程访问，可以修改 Elastic 安装目录的config/elasticsearch.yml文件，去掉network.host的注释，将它的值改成0.0.0.0，然后重新启动 Elastic，打开网页访问。