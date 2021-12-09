## Redis整体架构分析

如下是我学习redis及阅读源码，总结的redis整体架构图

![](http://r3rutcmq2.hd-bkt.clouddn.com//github01-redis-architecture.PNG)

我认为Redis架构包含四个模块 `网络模型`、`线程模型`、`持久化模型`、`数据结构`。其中`网络模型`使用的是IO多路复用；`线程模型`是对读写采用单线程，其他地方有使用多线程，Redis6.0提出的多线程是对网络的读写采用多线程的方式，核心的cmd的操作还是单线程；`持久化模型`指的是RDB和AOF；`数据结构`需要强调一下，Redis的数据结构是经过精心设计的。

## Redis源码目录文件分析

Redis的作者在readme中进行了较为详细的介绍，全文2W多字，系统的描述了Redis的设计理念和关键的源码文件。
* `deps` 依赖的第三方库
* `src` 源码文件
    * `db.c` 读取插入值逻辑
    * `server.c` redis服务器启动，初始化配置
    * `server.h` 结构体 redisServer，redisDb，client，redisObject 记录了redis服务器，数据库，客户端的信息
    * `dict.h` 字典结构体dict，还有其中的dictht, dictEntry
    * `t_*.h` t_开头的文件是redis底层的数据结构
    * `networking.c` 是处理网络I/O的
    * `object.c` 封装了对redisObject的操作

## Redis数据类型及底层数据结构

![](http://r3rutcmq2.hd-bkt.clouddn.com//github02-redis-data-type.PNG)

![](http://r3rutcmq2.hd-bkt.clouddn.com/01-redis-data-structure.PNG)