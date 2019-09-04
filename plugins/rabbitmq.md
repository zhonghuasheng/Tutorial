
# RabbitMQ消息的持久化
https://www.cnblogs.com/bigberg/p/8195622.html
## 什么情况下会造成消息丢失？
* 突然断电

## 哪些地方需要持久化
```java
// 队列的持久化 durable=true
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
String msg = "Hello World! 你好世界！";
// 消息的持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
```

## 常用命令

* List Queue
``` shell
rabbitmqctl list_queues name messages messages_ready \ messages_unacknowledged
```

* Find Queue
```
rabbitmqctl list_queues| grep hello | awk '{print $1}'
```

* Delete Queue
```
rabbitmqctl  delete_queue queueName
```