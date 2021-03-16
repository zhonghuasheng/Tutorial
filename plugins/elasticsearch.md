### 学习笔记
* 快速上手 http://www.ruanyifeng.com/blog/2017/08/elasticsearch.html
* ES基础部分
* ES的工作过程

## ES基础部分
> 两个端口(9200/9300)
* 节点客户端（Node Client）
    节点客户端作为一个非数据节点加入到本地集群中。换句话说，它本身不保存任何数据，但是它知道数据在集群中的哪个节点中，并且可以把请求转发到正确的节点。
* 传输客户端（Transport Client）
    轻量级的传输客户端可以将请求发送到远程集群。它本身不加入集群，但是它可以将请求转发到集群中的一个节点上。

两个 Java 客户端都是通过 9300 端口并使用 Elasticsearch 的原生 传输 协议和集群交互。集群中的节点通过端口 9300 彼此通信。如果这个端口没有打开，节点将无法形成一个集群。

> Near Realtime (NRT)

ES号称对外提供的是近实时的搜索服务，意思是数据从写入ES到可以被Searchable仅仅需要1秒钟，所以说基于ES执行的搜索和分析可以达到秒级。

> Node 与 Cluster
Elastic 本质上是一个分布式数据库，允许多台服务器协同工作，每台服务器可以运行多个 Elastic 实例。
单个 Elastic 实例称为一个节点（node）。一组节点构成一个集群（cluster）。

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
* 创建索引 curl -X PUT 'localhost:9200/accounts' -H 'content-Type:application/json' -d 'JSON数据'
* 删除索引 curl -X DELETE 'localhost:9200/accounts'
* 列出每个index所包含的type curl 'localhost:9200/_mapping?pretty=true'
* 新增数据（指定id为1） curl -X PUT 'localhost:9200/accounts/person/1' -H 'content-Type:application/json' -d '{"user":"张三","title":"工程师","desc":"数据库管理"}'
* 新增数据（使用POST不指定id） curl -X POST 'localhost:9200/accounts/person' -H 'content-Type:application/json' -d '{"user":"张三","title":"工程师","desc":"数据库管理"}'
* 更新数据 curl -X PUT 'localhost:9200/accounts/person/1' -H 'content-Type:application/json' -d '{"user":"张三","title":"工程师","desc":"数据库管理"}'
* 查看记录(返回不带索引信息) culr 'localhost:9200/accounts/person/1'
* 查看记录(返回带索引信息) culr 'localhost:9200/accounts/person/1?pretty=true'
* 搜索所有记录 curl 'localhost:9200/accounts/person/_search'
* 条件搜索（es的match） curl 'localhost:9200/accounts/person/_search' -d '{"query": {"match": {"desc": "系统"}}}'
* 条件搜索 or curl 'localhost:9200/accounts/person/_search' -d '{"query": {"match": {"desc": "系统 计算"}}}' -H 'content-Type:application/json'
* 条件搜索 and(执行多个关键词的and搜索，必须使用布尔查询) curl 'localhost:9200/accounts/person/_search' -H 'content-Type:application/json' -d '{"query":{"bool":{"must":[{"match": {"desc": "数据库"}}, {"match": {"desc": "管理"}}]}}}'
* 查看集群的健康状态
* curl后跟-i可以显示http头信息
```
[root@VM-0-16-centos ~]# curl -XGET 'http://localhost:9200/_count?pretty' -d '{"query": {"match_all": {}}}' -i
HTTP/1.1 200 OK
Warning: 299 Elasticsearch-5.5.1-19c13d0 "Content type detection for rest requests is deprecated. Specify the content type using the [Content-Type] header." "Mon, 15 Mar 2021 07:31:04 GMT"
content-type: application/json; charset=UTF-8
content-length: 95
{
  "count" : 0,
  "_shards" : {
    "total" : 0,
    "successful" : 0,
    "failed" : 0
  }
}

```

## 常用url
* 查看集群的健康状况 http://localhost:9200/_cat
* 查看 http://118.24.164.117:9200/_cat/health?v
    说明：v是用来要求在结果中返回表头
    状态值说明
    * Green - everything is good (cluster is fully functional)，即最佳状态
    * Yellow - all data is available but some replicas are not yet allocated (cluster is fully functional)，即数据和集群可用，但是集群的备份有的是坏的
    * Red - some data is not available for whatever reason (cluster is partially functional)，即数据和集群都不可用
* 查看集群的节点 http://localhost:9200/_cat/?v 
* 查看所有索引 http://118.24.164.117:9200/_cat/indices?v

## 索引
* 创建索引
* 构建索引映射
映射类别 Mapping type 废除说明
ES最先的设计是用索引类比关系型数据库的数据库，用mapping type 来类比表，一个索引中可以包含多个映射类别。这个类比存在一个严重的问题，就是当多个mapping type中存在同名字段时（特别是同名字段还是不同类型的），在一个索引中不好处理，因为搜索引擎中只有 索引-文档的结构，不同映射类别的数据都是一个一个的文档（只是包含的字段不一样而已）
从6.0.0开始限定仅包含一个映射类别定义（ "index.mapping.single_type": true ），兼容5.x中的多映射类别。从7.0开始将移除映射类别

## ES常见问题汇总
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
# 把ES的安装包拷贝到esuser的home目录下，然后找个地方解压，如果是在root目录下，后面会遇到比较多的问题
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

* 把elasticsearch目录换到不属于root目录的其他目录就行了
```

### vm.max_map_count不足
```shell
ERROR: [1] bootstrap checks failed
[1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[2021-01-27T19:24:38,603][INFO ][o.e.n.Node               ] [Q-gAi65] stopping ...

* 解决方案
sudo sysctl -w vm.max_map_count=262144
```


### 使用DBeaver连接ES时报 current license is non-compliant for [jdbc]
默认安装的ES的类型type=basic，是不支持JDBC客户端连接的，白金版的才支持，查看
```
[root@VM_0_12_centos software]# curl -XGET http://localhost:9200/_license
{
  "license" : {
    "status" : "active",
    "uid" : "8d3fd3a6-0861-4a95-9a7b-0ce499474b3b",
    "type" : "basic",
    "issue_date" : "2021-01-28T08:53:11.889Z",
    "issue_date_in_millis" : 1611823991889,
    "max_nodes" : 1000,
    "issued_to" : "elasticsearch",
    "issuer" : "elasticsearch",
    "start_date_in_millis" : -1
  }
}
```
再使用上述命令查看type=trail，刷新客户端就可以连接了

### "error" : "Content-Type header [application/x-www-form-urlencoded] is not supported"
这个问题，是在报文Content-type的参数：application/x-www-form-urlencoded不支持Json发送。需要改成application/Json
所以需要添加参数 ; -H ‘Content-Type: application/json’

```shell
[esuser@VM_0_12_centos root]$ curl -X PUT 'localhost:9200/accounts' -H 'content-Type:application/json' -d '{"mappings":{"person":{"properties":{"user":{"type":"text","analyzer":"ik_max_word","search_analyzer":"ik_max_word"},"title":{"type":"text","analyzer":"ik_max_word","search_analyzer":"ik_max_word"},"desc":{"type":"text","analyzer":"ik_max_word","search_analyzer":"ik_max_word"}}}}}'
{"acknowledged":true,"shards_acknowledged":true,"index":"accounts"}
```