## 学习笔记
- [RocketMQ相比其他MQ的优势特性](#RocketMQ相比其他MQ的优势特性)

## 实战技巧
## 百问

### RocketMQ相比其他MQ的优势特性
目前主流的MQ主要是Rocketmq、kafka、Rabbitmq，Rocketmq相比于Rabbitmq、kafka具有主要优势特性有：
* 支持事务型消息（消息发送和DB操作保持两方的最终一致性，rabbitmq和kafka不支持）
* 支持结合rocketmq的多个系统之间数据最终一致性（多方事务，二方事务是前提）
* 支持18个级别的延迟消息（rabbitmq和kafka不支持）
* 支持指定次数和时间间隔的失败消息重发（kafka不支持，rabbitmq需要手动确认）
* 支持consumer端tag过滤，减少不必要的网络传输（rabbitmq和kafka不支持）
* 支持重复消费（rabbitmq不支持，kafka支持）
