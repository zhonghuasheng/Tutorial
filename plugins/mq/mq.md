### 学习笔记
- [什么时候使用MQ]()
- [常见问题]()
  - [为什么要用消息队列？（消息队列的应用场景？）](#为什么要用消息队列-消息队列的应用场景)
  - [各种消息队列产品的比较？](#各种消息队列产品的比较)
  - [消息队列的优点和缺点？](#消息队列的优点和缺点)

* MQ总结
  * 消息总线（Message Queue），是一种跨进程的通信机制，用于上下游传递消息。
  * 消息队列的本质是一种“新进先出”的数据结构
  * 常用场景：解耦、异步、削峰
* 什么时候不使用MQ？
  * 上游实时关注执行结果
* 什么时候使用MQ？
  * 数据驱动的任务依赖。D依赖C，C依赖B，B依赖A，
  * 上游不关心多下游执行结果
  * 异步返回执行时间长
* 常见产品
  * ActivyMQ
  * RabbitMQ
  * QMQ是去哪儿网内部广泛使用的消息中间件，自2012年诞生以来在去哪儿网所有业务场景中广泛的应用，包括跟交易息息相关的订单场景； 也包括报价搜索等高吞吐量场景。目前在公司内部日常消息qps在60W左右，生产上承载将近4W+消息topic，消息的端到端延迟可以控制在10ms以内。
  * Apache RocketMQ is a distributed messaging and streaming platform with low latency, high performance and reliability, trillion-level capacity and flexible scalability.
  * Kafka
  * MQTT

### 消息队列的技术选型
消息队列及常见消息队列介绍 - 云+社区...
http://note.youdao.com/noteshare?id=7c550bb62a6597091e4533fbb6b920c1&sub=wcp155548841293021

### 消息队列优势
消息队列作为高并发系统的核心组件之一，能够帮助业务系统解构提升开发效率和系统稳定性。主要具有以下优势：
* 



除了上面列出的应用解棉、流量消峰、消息分发等功能外，消息队列还有保证最终一致性、方便动态扩容等功能。


### 常见问题
1. 为什么要用消息队列？（消息队列的应用场景？）
2. 各种消息队列产品的比较？
3. 消息队列的优点和缺点？
4. 如何保证消息队列的高可用？
5. 如何保证消息不丢失？
6. 如何保证消息不被重复消费？
7. 如何保证消息的顺序性？
8. 基于MQ的分布式事务实现

为什么要用消息队列-消息队列的应用场景
----------------------------------

消息队列的本质是一个先进先出的数据结构。它能在解耦、异步、削峰上提供很好的能力。
1. 解耦（解决不同重要程度、不同能力级别系统之间依赖导致一死全死）
复杂的应用里会存在多个子系统， 比如在电商应用中有订单系统、库存系统、物流系统 支付系统等，这个时候如果各个子系统之间的耦合性太高，整体系统的可用性就会大幅降低，多个低错误率的子系统糅合在一起，得到的是一个高错误率的整体系统。以电商应用为例，用户创建订单后，如果耦合调用库存系统、物流系统、支付系统，任何一个子系统出了故障或者因为升级等原因暂时不可用，都会造成下单操作异常，影响用户使用体验。当转变成基于消息队列的方式后，系统可用性就高多了，比如物流系统因为发生故障，需要几分钟的时间来修 ，在这几分钟的时间里，物流系统要处理的内容被缓存在消息队列里，用户的下单操作可以正常完成。当物流系统恢复后，补充处理存储在消息队列里的订单信息即可，终端用户感知不到物流系统发生过几分钟的故障。 
2. 异步（当存在一对多调用时，可以发一条消息给消息系统，让消息系统通知相关系统，应用间并发处理消息，相比串行处理，减少处理时间）
比如用户注册的消息，需要被数据部门处理，也要被业务部门处理，如果串行调用，会导致用户注册的流程耗时，这时候采用消息异步处理，用户完成基本的注册后只需要写入消息，各个子系统订阅消费此消息。
3. 削峰（主要解决瞬时写压力大于应用服务能力导致消息丢失、系统奔溃等问题）
在秒杀或团队抢购活动中，由于用户请求量较大，导致流量暴增，秒杀的应用在处理如此大量的访问流量后，下游的通知系统无法承载海量的调用量，甚至会导致下游系统崩溃等问题而发生漏通知的情况。为解决这些问题，可在应用和下游通知系统之间加入消息队列来做缓冲。 
4. 蓄流压测（线上有些链路不好压测，可以通过堆积一定量消息再放开来压测）

各种消息队列产品的比较
---------------------
* ActiveMQ, 早期使用的比较多，没有经过大规模吞吐量场景的验证，社区也不是很活跃，现在确实用的不多，不推荐
* RabbitMQ, 采用Erlang语言开发，导致很少有工程师去深入研究和掌控它，对公司而言，如果想进行二次开发，基本处于不可控的状态，但是RabbitMQ是开源的，且社区很活跃，相对来说有比较稳定的支持。如果对性能要求不是特别高，且追求稳定，推荐使用。另外RabbitMQ严格遵循了AMQP协议，功能十分丰富，且提供rabbitmqAdmin可视化管理后端
* RocketMQ，采用Java开发，诞生于电商业务，天生支持分布式，且经过阿里双十一业务场景考验，稳定性和性能均不错，且可以考虑后期二次开发，推荐使用，也提供后端可是化界面。社区活跃度中等
* Kafka, 采用Scala开发，诞生于Hadoop大数据业务，是大数据领域、日志采集的标配，社区活跃度高，推荐使用。

https://blog.csdn.net/zollty/article/details/53958656


消息队列的优点和缺点
-------------------
* 优点：解耦、异步、削峰
* 缺点：
  * 系统可用性降低： 系统引入的外部越多，系统的稳定性就越差。一旦MQ宕机，就会对业务产生影响。 -> `如何保证MQ的高可用？`。之前我们发完文章，直接RPC调用用户模块，获取好友列表，然后挨个增加他们的未读文章列表，为了解耦，我们引入MQ，如果MQ挂了，那他们的未读文章就没有我发的这篇文章。
  * 系统复杂度提高： MQ的引入大大增加了系统的复杂度，以前系统是同步的远程调用，现在是通过MQ进行异步调用。也就是在内容服务和用户服务之间加了一层MQ，那就必须考虑`消息丢失了怎么办？`、`重复消息怎么处理？`、`如何保证消息顺序性消费？`这些情况怎么办？
  * 一致性问题：A系统处理完业务，通过MQ给B、C、D三个系统发消息数据，如果B、C系统处理成功，D系统处理失败，那这个数据就不一致了。比如商城下的订单，要异步同步到门店工单，和OMS订单，那如果门店成功了，OMS失败了，数据就不一致。


![MQ详细对比](img/mq-compare.png)