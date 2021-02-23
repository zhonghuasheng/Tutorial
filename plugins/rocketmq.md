## 学习笔记
- [RocketMQ相比其他MQ的优势特性](#RocketMQ相比其他MQ的优势特性)
- [RocketMQ安装，启动，基础实例](#http://rocketmq.apache.org/docs/quick-start/)
- [RocketMQ与SpringBoot集成基础示例](https://github.com/zhonghuasheng/JAVA/tree/master/springboot/springboot-rocketmq/src/test/java/com/springboot/rocketmq)
- [RocketMQ顺序消息](http://note.youdao.com/noteshare?id=7e23b1b1e3842a06862f6d06184e4466&sub=FB7D72F268AE453BA750B846BC2DAAB0)
- [RocketMQ如何保证消息的可靠性](https://blog.csdn.net/qq_38545713/article/details/104758104)
## 实战技巧
* 常用命令
```cmd
start mqnamesrv.cmd
start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true -c ../conf/broker.conf
```
* 使用官方下载的rocketmq，直接启动，是可以使用ip地址访问，一旦配置了conf/broker.conf，并启动了，就需要配置里面的brokerIP1，不然使用ip访问不了，总之，建议使用配置文件启动

## 百问

### RocketMQ的主要特点

### RocketMQ相比其他MQ的优势特性
目前主流的MQ主要是Rocketmq、kafka、Rabbitmq，Rocketmq相比于Rabbitmq、kafka具有主要优势特性有：
* 支持事务型消息（消息发送和DB操作保持两方的最终一致性，rabbitmq和kafka不支持）
* 支持结合rocketmq的多个系统之间数据最终一致性（多方事务，二方事务是前提）
* 支持18个级别的延迟消息（rabbitmq和kafka不支持）
* 支持指定次数和时间间隔的失败消息重发（kafka不支持，rabbitmq需要手动确认）
* 支持consumer端tag过滤，减少不必要的网络传输（rabbitmq和kafka不支持）
* 支持重复消费（rabbitmq不支持，kafka支持）

### RocketMQ各部分角色介绍
RocketMQ由四部分组成，先来直观地了解一下这些角色以及各自的功能。分布式消息队列是用来高效地传输消息的，它的功能和现实生活中的邮局收发信件很类似，我们类比一下相应的模块 现实生活中的邮政系统要正常运行，离不开下面这四个角色，一是发信者，二是收信者，三是负责暂存、传输的邮局，四是负责协调各个地方邮局的管理机构 对应到 RocketMQ 中，这四个角色就是 Producer、Consumer、Broker、NameServer。
启动 RocketMQ 的顺序是先启动NameServer，再启动Broker，这时候消息队列已经可以提供服务了，想发送消息就使用Producer来发送，想接收消息就使用Consumer来接收。
了解了四种角色以后再介绍Topic、Message Queue 这两个名词。一个分布式消息队列中间件部署好以后，可以给很多个业务提供服务，同一个业务也有不同类型的消息要投递，这些不同类型的消息以不同的Topic名称来区分。所以发送和接收消息前，先创建Topic，针对某个Topic发送和接收消。有了Topic以后，还需要解决性能问题，如果一个Topic发送和接收的数据量非常大，需要能支持增加并行处理的机器来提高处理速度，这时候一个Topic可以根据需求设置一个或多个Message Queue, Message Queue类似分区或Partition。Topic有了多个Message Queue 后，消息可以并行地向各个Message Queue发送，消费者也可以并行地从多个Message Queue 读取消息并消费。

`NameServer`
对于一个消息队列集群来说，系统由很多台机器组成，每个机器的角色、 IP 地址都不相同，而且这些信息是变动的。在这种情况下， 如果一个新的Producer Consumer 加入 ，怎么配置连接信息呢？ NameServer 的存在主要是为了解决这类问题，由 NameServer 维护这些配置信息、状态信息，其他角色都通过 NameServer 来协同执行。
NameServer 是整个消息队列中的状态服务器，集群的各个组件通过它来了解全局的信息 同时午，各个角色的机器都要定期 NameServer 上报自己的状态，超时不上报的话， NameServer 认为某个机器出故障不可用了，其他的组件会把这个机器从可用列表里移除。NamServer 可以部署多个，相互之间独立，其他角色同时向多个 NameServer机器上报状态信息，从而达到热备份的目的 NameServer 本身是无状态的，也就是说 NameServer 中的 Broker Topic 等状态信息不会持久存储，都是由各个角色定时上报并存储到内存中的（NameServer 支持配置参数的持 化，一般用不到）

### 异常
1. RocketMQ在windows下broker启动失败解决方法
```
今天RocketMQ所在的电脑意外关机了，重启后怎么都启动不了broker，研究了好久才发现解决办法
其实很简单
把c:/user/你的用户名/里面的store里面的所有文件全部删除，再启动，成功
```
