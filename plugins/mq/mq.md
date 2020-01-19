* MQ总结
  * 消息总线（Message Queue），是一种跨进程的通信机制，用于上下游传递消息。
  * 在互联网架构中，MQ是一种非常常见的上下游“逻辑解耦+物理解耦”的消息通信服务。
* 什么时候不使用MQ？
  * 上游实时关注执行结果
* 什么时候使用MQ？
  * 数据驱动的任务依赖
  * 上游不关心多下游执行结果
  * 异步返回执行时间长
* QMQ是去哪儿网内部广泛使用的消息中间件，自2012年诞生以来在去哪儿网所有业务场景中广泛的应用，包括跟交易息息相关的订单场景； 也包括报价搜索等高吞吐量场景。目前在公司内部日常消息qps在60W左右，生产上承载将近4W+消息topic，消息的端到端延迟可以控制在10ms以内。
* Apache RocketMQ is a distributed messaging and streaming platform with low latency, high performance and reliability, trillion-level capacity and flexible scalability.