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