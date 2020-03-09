# 目录
* [学习计划](#学习计划)
* [学习笔记](#学习笔记)
* [百问](#百问)

### 学习计划
* Redis的介绍、优缺点、使用场景
* Redis入门使用 https://www.runoob.com/redis
* Redis各个数据类型及其使用场景 https://www.runoob.com/redis/redis-data-types.html
    * Redis字符串（String）https://www.runoob.com/redis/redis-strings.html
    * Redis哈希（Hash）https://www.runoob.com/redis/redis-hashes.html
    * Redis列表（List）https://www.runoob.com/redis/redis-lists.html
    * Redis集合（Set）https://www.runoob.com/redis/redis-sets.html
    * Redis有序集合（sorted set）https://www.runoob.com/redis/redis-sorted-sets.html
* Redis HyperLogLog
* Redis的发布与订阅，具体实践
* Redis事务
* Redis脚本，为什么需要脚本，能干什么
* Redis日志配置
* Redis持久化，数据备份与恢复
* Redis常用命令
* Redis安全如何保证
* Redis性能测试
* 整理自己的RedisUtil https://www.runoob.com/redis/redis-java.html
* Redis面试题汇总
    * https://www.w3cschool.cn/redis/redis-ydwp2ozz.html

### 学习笔记
> 安装
```
wget http://download.redis.io/releases/redis-5.0.7.tar.gz
tar -zxvf redis-5.0.7.tar.gz
mv redis-5.0.7 /usr/local/redis 不需要先创建/usr/local.redis文件夹
cd /usr/local/redis
make
make install
vi redis.conf
  * bind 0.0.0.0 开发访问
  * daemonize yes 设置后台运行
redis-server ./redis.conf 启动
redis-cli 进入命令行，进行简单的命令操作
vi redis.conf
  > requirepass password 修改密码
redis-cli 再次进入cmd
  > shutdown save 关闭redis，同时持久化当前数据
redis-server ./redis.conf 再次启动redis
redis-cli 进入命令行
  > auth password
将redis配置成系统服务，redis/utils中自带命令，我们只需修改参数
  /usr/local/redis/utils/./install_server.sh
  [root~ utils]# ./install_server.sh
  Welcome to the redis service installer
  Please select the redis port for this instance: [6379] 默认端口不管
  Selecting default: 6379
  Please select the redis config file name [/etc/redis/6379.conf] /usr/local/redis/redis.conf 修改配置文件路径
  Please select the redis log file name [/var/log/redis_6379.log] /usr/local/redis/redis.log 修改日志文件路径
  Please select the data directory for this instance [/var/lib/redis/6379] /usr/local/redis/data 修改数据存储路径
  Please select the redis executable path [/usr/local/bin/redis-server]
  Selected config:
  Port           : 6379
  Config file    : /usr/local/redis/redis.conf
  Log file       : /usr/local/redis/redis.log
  Data dir       : /usr/local/redis/data
  Executable     : /usr/local/bin/redis-server
  Cli Executable : /usr/local/bin/redis-cli
chkconfig --list | grep redis 查看redis服务配置项
  redis_6379      0:off   1:off   2:on    3:on    4:on    5:on    6:off
  服务名是redis_6379
```
> 基础
* Reids支持5中存储的数据格式： String, Hash, List, Set, Sorted Set
    *  redis 的 string 可以包含任何数据。比如jpg图片或者序列化的对象，最大能存储 512MB。
        ```
        set key "value"
        get key
        ```
    *  Redis hash 是一个键值(key=>value)对集合。Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。
        ```
        HMSET key field1 value1 field2 value2
        HMGET key field1
        > return value1
        ```
    * Redis 列表是简单的字符串列表，按照插入顺序排序。列表最多可存储 232 - 1 元素 (4294967295, 每个列表可存储40多亿)。
        ```
        lpush test1 value1
        lpush test1 value2
        lpush test1 value3
        // 遍历
        lrange test1 0 10
        ```
    * Redis 的 Set 是 string 类型的无序集合。集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
        ```
        sadd key value1
        sadd key value2
        sadd key value3
        smembers key
        ```
    * Redis zset 和 set 一样也是string类型元素的集合,且不允许重复的成员。不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。zset的成员是唯一的,但分数(score)却可以重复。
* Redis 与其他 key - value 缓存产品有以下三个特点：
    * Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
    * Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
    * Redis支持数据的备份，即master-slave模式的数据备份。
    * 性能极高 – Redis能读的速度是110000次/s,写的速度是81000次/s 。
    * 原子 – Redis的所有操作都是原子性的，意思就是要么成功执行要么失败完全不执行。单个操作是原子性的。多个操作也支持事务，即原子性，通过MULTI和EXEC指令包起来。
    * 丰富的特性 – Redis还支持 publish/subscribe, 通知, key 过期等等特性。
* Redis Cli中输入ping，服务端返回PONG，说明能够ping通
* Redis 总共支持四个级别：debug、verbose、notice、warning，默认为 notice
* 指定 Redis 最大内存限制maxmemory，Redis 在启动时会把数据加载到内存中，达到最大内存后，Redis 会先尝试清除已到期或即将到期的 Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis 新的 vm 机制，会把 Key 存放内存，Value 会存放在硬盘swap区
* redis-cli --raw可以避免中文乱码
* `DEL runoobkey`在以上实例中 DEL 是一个命令， runoobkey 是一个键。 如果键被删除成功，命令执行后输出 (integer) 1，否则将输出 (integer) 0
* Redis 事务可以一次执行多个命令， 并且带有以下三个重要的保证：
    * 批量操作在发送 EXEC 命令前被放入队列缓存。
    * 收到 EXEC 命令后进入事务执行，事务中任意命令执行失败，其余的命令依然被执行。
    * 在事务执行过程，其他客户端提交的命令请求不会插入到事务执行命令序列中。
* Redis事务从开始到执行会经历以下三个阶段：开始事务 -> 命令入队 -> 执行事务。单个 Redis 命令的执行是原子性的，但 Redis 没有在事务上增加任何维持原子性的机制，所以 Redis 事务的执行并不是原子性的。事务可以理解为一个打包的批量执行脚本，但批量指令并非原子化的操作，中间某条指令的失败不会导致前面已做指令的回滚，也不会造成后续的指令不做。这是官网上的说明 From redis docs on transactions: It's important to note that even when a command fails, all the other commands in the queue are processed – Redis will not stop the processing of commands.
* Redis 脚本使用 Lua 解释器来执行脚本。 Redis 2.6 版本通过内嵌支持 Lua 环境。执行脚本的常用命令为 EVAL。
* 常用命令
    ```
    auth "password" 验证密码
    select index 切换到指定的数据库
    keys * 显示所有key
    incr key 将key的值加一，是原子操作
    decr key 将key的值加一，会出现复数，是原子操作
    del key 删除key
    ```
* Redis SAVE 命令用于创建当前数据库的备份，该命令将在 redis 安装目录中创建dump.rdb文件，也可以指定目录` CONFIG GET dir 输出的 redis 安装目录为 /usr/local/redis/bin`。创建 redis 备份文件也可以使用命令 BGSAVE，该命令在后台执行。
* Redis 通过监听一个 TCP 端口或者 Unix socket 的方式来接收来自客户端的连接，当一个连接建立后，Redis 内部会进行以下一些操作：
    * 首先，客户端 socket 会被设置为非阻塞模式，因为 Redis 在网络事件处理上采用的是非阻塞多路复用模型。
    * 然后为这个 socket 设置 TCP_NODELAY 属性，禁用 Nagle 算法
    * 然后创建一个可读的文件事件用于监听这个客户端 socket 的数据发送
* Redis 管道技术可以在服务端未响应时，客户端可以继续向服务端发送请求，并最终一次性读取所有服务端的响应。管道技术最显著的优势是提高了 redis 服务的性能。
* Redis 分区
    * 分区是分割数据到多个Redis实例的处理过程，因此每个实例只保存key的一个子集。
    * 分区的优势：
        * 通过利用多台计算机内存的和值，允许我们构造更大的数据库。
        * 通过多核和多台计算机，允许我们扩展计算能力；通过多台计算机和网络适配器，允许我们扩展网络带宽。
    * 分区的不足：
        * 涉及多个key的操作通常是不被支持的。举例来说，当两个set映射到不同的redis实例上时，你就不能对这两个set执行交集操作。
        * 涉及多个key的redis事务不能使用。
        * 当使用分区时，数据处理较为复杂，比如你需要处理多个rdb/aof文件，并且从多个实例和主机备份持久化文件。
        * 增加或删除容量也比较复杂。redis集群大多数支持在运行时增加、删除节点的透明数据平衡的能力，但是类似于客户端分区、代理等其他系统则不支持这项特性。然而，一种叫做presharding的技术对此是有帮助的。
    * 分区类型：Redis 有两种类型分区。 假设有4个Redis实例 R0，R1，R2，R3，和类似user:1，user:2这样的表示用户的多个key，对既定的key有多种不同方式来选择这个key存放在哪个实例中。也就是说，有不同的系统来映射某个key到某个Redis服务。
        * 范围分区
            * 最简单的分区方式是按范围分区，就是映射一定范围的对象到特定的Redis实例。比如，ID从0到10000的用户会保存到实例R0，ID从10001到 20000的用户会保存到R1，以此类推。这种方式是可行的，并且在实际中使用，不足就是要有一个区间范围到实例的映射表。这个表要被管理，同时还需要各 种对象的映射表，通常对Redis来说并非是好的方法。
        * 哈希分区
            * 另外一种分区方法是hash分区。这对任何key都适用，也无需是object_name:这种形式，像下面描述的一样简单：用一个hash函数将key转换为一个数字，比如使用crc32 hash函数。对key foobar执行crc32(foobar)会输出类似93024922的整数。对这个整数取模，将其转化为0-3之间的数字，就可以将这个整数映射到4个Redis实例中的一个了。93024922 % 4 = 2，就是说key foobar应该被存到R2实例中。注意：取模操作是取除的余数，通常在多种编程语言中用%操作符实现。【当分区较多或发生变化的时候需要处理一些额外的情况】

### 其他
* Redis设置port为6379的原因

### Redis中关于密码

设置密码
redis> CONFIG SET requirepass secret_password   # 将密码设置为 secret_password

OK

redis> QUIT                                     # 退出再连接，让新密码对客户端生效

[huangz@mypad]$ redis

redis> PING                                     # 未验证密码，操作被拒绝

(error) ERR operation not permitted

redis> AUTH wrong_password_testing              # 尝试输入错误的密码

(error) ERR invalid password

redis> AUTH secret_password                     # 输入正确的密码

OK

redis> PING                                     # 密码验证成功，可以正常操作命令了

PONG

清空密码

redis> CONFIG SET requirepass ""   # 通过将密码设为空字符来清空密码

OK

redis> QUIT

$ redis                            # 重新进入客户端

redis> PING                        # 执行命令不再需要密码，清空密码操作成功

PONG

### Redis中查看所有数据库的命令
* config get databases

### I/O多路复用技术(multiplexing)
关于I/O多路复用(又被称为“事件驱动”)，首先要理解的是，操作系统为你提供了一个功能，当你的某个socket可读或者可写的时候，它可以给你一个通知。这样当配合非阻塞的socket使用时，只有当系统通知我哪个描述符可读了，我才去执行read操作，可以保证每次read都能读到有效数据而不做纯返回-1和EAGAIN的无用功。写操作类似。操作系统的这个功能通过select/poll/epoll/kqueue之类的系统调用函数来使用，这些函数都可以同时监视多个描述符的读写就绪状况，这样，多个描述符的I/O操作都能在一个线程内并发交替地顺序完成，这就叫I/O多路复用，这里的“复用”指的是复用同一个线程。

下面举一个例子，模拟一个tcp服务器处理30个客户socket。假设你是一个老师，让30个学生解答一道题目，然后检查学生做的是否正确，你有下面几个选择：1. 第一种选择：按顺序逐个检查，先检查A，然后是B，之后是C、D。。。这中间如果有一个学生卡主，全班都会被耽误。这种模式就好比，你用循环挨个处理socket，根本不具有并发能力。2. 第二种选择：你创建30个分身，每个分身检查一个学生的答案是否正确。 这种类似于为每一个用户创建一个进程或者线程处理连接。3. 第三种选择，你站在讲台上等，谁解答完谁举手。这时C、D举手，表示他们解答问题完毕，你下去依次检查C、D的答案，然后继续回到讲台上等。此时E、A又举手，然后去处理E和A。。。 这种就是IO复用模型，Linux下的select、poll和epoll就是干这个的。将用户socket对应的fd注册进epoll，然后epoll帮你监听哪些socket上有消息到达，这样就避免了大量的无用操作。此时的socket应该采用非阻塞模式。这样，整个过程只在调用select、poll、epoll这些调用的时候才会阻塞，收发客户消息是不会阻塞的，整个进程或者线程就被充分利用起来，这就是事件驱动，所谓的reactor模式。

### 什么时候适合用缓存
* 数据访问频率
    * 访问频率高，适合用缓存，效果好
    * 访问频率低，不建议使用，效果不佳
* 数据读写比例
    * 读多写少，适合缓存，效果好
    * 读少写多，不建议使用，效果不佳
* 数据一致性
    * 一致性要求低，适合缓存，效果好
    * 一致性要求高，不建议缓存，效果不佳

### Redis中的缓存穿透、缓存雪崩、缓存击穿
* 缓存穿透
```
缓存穿透，是指查询一个数据库一定不存在的数据。正常的使用缓存流程大致是，数据查询先进行缓存查询，如果key不存在或者key已经过期，再对数据库进行查询，并把查询到的对象，放进缓存。如果数据库查询对象为空，则不放进缓存。
```
1. 参数传入对象主键ID
2. 根据key从缓存中获取对象
3. 如果对象不为空，直接返回
4. 如果对象为空，进行数据库查询
5. 如果从数据库查询出的对象不为空，则放入缓存（设定过期时间）

想象一下这个情况，如果传入的参数为-1，会是怎么样？这个-1，就是一定不存在的对象。就会每次都去查询数据库，而每次查询都是空，每次又都不会进行缓存。假如有恶意攻击，就可以利用这个漏洞，对数据库造成压力，甚至压垮数据库。即便是采用UUID，也是很容易找到一个不存在的KEY，进行攻击。

小编在工作中，会采用缓存空值的方式，也就是【代码流程】中第5步，如果从数据库查询的对象为空，也放入缓存，只是设定的缓存过期时间较短，比如设置为60秒。
redisTemplate.opsForValue().set(String.valueOf(goodsId), null, 60, TimeUnit.SECONDS); // 设置过期时间

解决方案
* 接口层增加校验，如用户鉴权校验，id做基础校验，id<=0的直接拦截；
* 从缓存取不到的数据，在数据库中也没有取到，这时也可以将key-value对写为key-null，缓存有效时间可以设置短点，如30秒（设置太长会导致正常情况也没法使用）。这样可以防止攻击用户反复用同一个id暴力攻击

* 缓存雪崩
```
缓存雪崩，是指在某一个时间段，缓存集中过期失效。
```
产生雪崩的原因之一，比如在写本文的时候，马上就要到双十二零点，很快就会迎来一波抢购，这波商品时间比较集中的放入了缓存，假设缓存一个小时。那么到了凌晨一点钟的时候，这批商品的缓存就都过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言，就会产生周期性的压力波峰。

小编在做电商项目的时候，一般是采取不同分类商品，缓存不同周期。在同一分类中的商品，加上一个随机因子。这样能尽可能分散缓存过期时间，而且，热门类目的商品缓存时间长一些，冷门类目的商品缓存时间短一些，也能节省缓存服务的资源。

其实集中过期，倒不是非常致命，比较致命的缓存雪崩，是缓存服务器某个节点宕机或断网。因为自然形成的缓存雪崩，一定是在某个时间段集中创建缓存，那么那个时候数据库能顶住压力，这个时候，数据库也是可以顶住压力的。无非就是对数据库产生周期性的压力而已。而缓存服务节点的宕机，对数据库服务器造成的压力是不可预知的，很有可能瞬间就把数据库压垮。

解决方案：
* 缓存数据的过期时间设置随机，防止同一时间大量数据过期现象发生。
* 如果缓存数据库是分布式部署，将热点数据均匀分布在不同搞得缓存数据库中。
* 设置热点数据永远不过期。

* 缓存击穿
```
缓存击穿，是指一个key非常热点，在不停的扛着大并发，大并发集中对这一个点进行访问，当这个key在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库，就像在一个屏障上凿开了一个洞。
```
小编在做电商项目的时候，把这货就成为“爆款”。
其实，大多数情况下这种爆款很难对数据库服务器造成压垮性的压力。达到这个级别的公司没有几家的。所以，务实主义的小编，对主打商品都是早早的做好了准备，让缓存永不过期。即便某些商品自己发酵成了爆款，也是直接设为永不过期就好了。

解决方案
* 设置热点数据永远不过期。
* 加互斥锁，互斥锁参考代码如下：
说明：
1）缓存中有数据，直接走上述代码13行后就返回结果了
2）缓存中没有数据，第1个进入的线程，获取锁并从数据库去取数据，没释放锁之前，其他并行进入的线程会等待100ms，再重新去缓存取数据。这样就防止都去数据库重复取数据，重复往缓存中更新数据情况出现。
3）当然这是简化处理，理论上如果能根据key值加锁就更好了，就是线程A从数据库取key1的数据并不妨碍线程B取key2的数据，上面代码明显做不到这点。

* 如何解决DB和缓存一致性问题？
答：当修改了数据库后，有没有及时修改缓存。这种问题，以前有过实践，修改数据库成功，而修改缓存失败的情况，最主要就是缓存服务器挂了。而因为网络问题引起的没有及时更新，可以通过重试机制来解决。而缓存服务器挂了，请求首先自然也就无法到达，从而直接访问到数据库。那么我们在修改数据库后，无法修改缓存，这时候可以将这条数据放到数据库中，同时启动一个异步任务定时去检测缓存服务器是否连接成功，一旦连接成功则从数据库中按顺序取出修改数据，依次进行缓存最新值的修改。

[Redis使用单线程的原因](https://github.com/zhonghuasheng/Tutorial/issues/105)

# 引用
* [Redis教程](http://runoob.com/redis)