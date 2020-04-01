* 引用 https://blog.csdn.net/w1014074794/article/details/88827946


首先，在spring boot2之后，对redis连接的支持，默认就采用了lettuce。这就一定程度说明了lettuce 和Jedis的优劣。

### 概念：
* Jedis：是老牌的Redis的Java实现客户端，提供了比较全面的Redis命令的支持
* Redisson：实现了分布式和可扩展的Java数据结构
* Lettuce：高级Redis客户端，用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器

### 优点：
* Jedis：比较全面的提供了Redis的操作特性
* Redisson：促使使用者对Redis的关注分离，提供很多分布式相关操作服务，例如，分布式锁，分布式集合，可通过Redis支持延迟队列
* Lettuce：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Lettuce的API是线程安全的，所以可以操作单个Lettuce连接来完成各种操作

> 可伸缩：
* Jedis：使用阻塞的I/O，且其方法调用都是同步的，程序流需要等到sockets处理完I/O才能执行，不支持异步。Jedis客户端实例不是线程安全的，所以需要通过连接池来使用Jedis
* Redisson：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Redisson的API是线程安全的，所以可以操作单个Redisson连接来完成各种操作
* Lettuce：基于Netty框架的事件驱动的通信层，其方法调用是异步的。Lettuce的API是线程安全的，所以可以操作单个Lettuce连接来完成各种操作

* lettuce能够支持redis4，需要java8及以上。
* lettuce是基于netty实现的与redis进行同步和异步的通信。

### lettuce和jedis比较：
* jedis使直接连接redis server,如果在多线程环境下是非线程安全的，这个时候只有使用连接池，为每个jedis实例增加物理连接
* lettuce的连接是基于Netty的，连接实例（StatefulRedisConnection）可以在多个线程间并发访问，StatefulRedisConnection是线程安全的，所以一个连接实例可以满足多线程环境下的并发访问，当然这也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
* Redisson实现了分布式和可扩展的Java数据结构，和Jedis相比，功能较为简单，不支持字符串操作，不支持排序、事务、管道、分区等Redis特性。Redisson的宗旨是促进使用者对Redis的关注分离，从而让使用者能够将精力更集中地放在处理业务逻辑上。

总结：
优先使用Lettuce，如果需要分布式锁，分布式集合等分布式的高级特性，添加Redisson结合使用，因为Redisson本身对字符串的操作支持很差。
