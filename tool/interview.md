# 目录
* [面试题](#面试题)
* [面试技巧](#面试技巧)

### 面试题
#### Java 基础
* [说说你对Fail-Fast的理解](http://note.youdao.com/noteshare?id=654c2063da080a2c45968caf25554b7b&sub=wcp1585706133233470)
* [什么是面向对象](https://github.com/zhonghuasheng/Tutorial/issues/188)
* [JDK 和 JRE 有什么区别](https://github.com/zhonghuasheng/Tutorial/issues/207)
* [== 和 equals 的区别是什么](https://github.com/zhonghuasheng/Tutorial/issues/208)
* [两个对象的 hashCode()相同，则 equals()也一定为 true，对吗？](https://github.com/zhonghuasheng/Tutorial/issues/209)
* [final 在 Java 中有什么作用？](https://github.com/zhonghuasheng/Tutorial/issues/231)
* [String 属于基础的数据类型吗？](https://github.com/zhonghuasheng/Tutorial/issues/232)
* [Java中操作字符串都有哪些类？它们之间有什么区别？](https://github.com/zhonghuasheng/Tutorial/issues/233)
* [String str="i"与 String str=new String(“i”)一样吗？](https://github.com/zhonghuasheng/Tutorial/issues/33)
* [如何将字符串反转？](https://github.com/zhonghuasheng/Tutorial/issues/234)
* [普通类，抽象类，接口有哪些区别？](https://github.com/zhonghuasheng/Tutorial/issues/235)
* [抽象类能使用 final 修饰吗？](https://github.com/zhonghuasheng/Tutorial/issues/236)
* [重载和重写的区别](https://github.com/zhonghuasheng/Tutorial/issues/237)
15. Java 中 IO 流分为几种？
16. BIO、NIO、AIO 有什么区别？
17. Files的常用方法都有哪些？
#### 多线程
18. 并行和并发有什么区别？
19. 线程和进程的区别？
20. 守护线程是什么？
21. 创建线程有哪几种方式？
22. 说一下 runnable 和 callable 有什么区别？
23. 线程有哪些状态？
24. sleep() 和 wait() 有什么区别？
25. notify()和 notifyAll()有什么区别？
27. 创建线程池有哪几种方式？
26. 线程的 run()和 start()有什么区别？
28. 线程池都有哪些状态？
29. 线程池中 submit()和 execute()方法有什么区别？
30. 在 Java 程序中怎么保证多线程的运行安全？
31. 多线程锁的升级原理是什么？
32. 什么是死锁？
33. 怎么防止死锁？
34. ThreadLocal 是什么？有哪些使用场景？
35. 说一下 synchronized 底层实现原理？
36. synchronized 和 volatile 的区别是什么？
37. synchronized 和 Lock 有什么区别？
38. synchronized 和 ReentrantLock 区别是什么？
39. 说一下 atomic 的原理？
### spring/spring MVC
* [SpringMVC 说说过滤器、监听器、拦截器有啥区别](https://github.com/zhonghuasheng/Tutorial/issues/197)
* [SpringBoot系列](https://github. com/zhonghuasheng/Tutorial/issues?q=label%3ASpringBoot+)
    * [SpringBoot 的启动原理](https://github. com/zhonghuasheng/Tutorial/issues/185)

40. 为什么要使用 spring？
41. 解释一下什么是 aop？
42. 解释一下什么是 ioc？
43. spring 有哪些主要模块？
44. spring 常用的注入方式有哪些？
45. spring 中的 bean 是线程安全的吗？
46. spring 支持几种 bean 的作用域？
47. spring 自动装配 bean 有哪些方式？
48. spring 事务实现方式有哪些？
49. 说一下 spring 的事务隔离？
50. 说一下 spring mvc 运行流程？
51. spring mvc 有哪些组件？
52. @RequestMapping 的作用是什么？
53. @Autowired 的作用是什么？
四、spring/spring Cloud
54. 什么是 spring boot？
55. 为什么要用 spring boot？
56. spring boot 核心配置文件是什么？
57. spring boot 配置文件有哪几种类型？它们有什么区别？
58. spring boot 有哪些方式可以实现热部署？
59. jpa 和 hibernate 有什么区别？
60. 什么是 spring cloud？
61. spring cloud 断路器的作用是什么？
62. spring cloud 的核心组件有哪些？
五、Mybatis
63. mybatis 中 #{}和 ${}的区别是什么？
64. mybatis 有几种分页方式？
65. RowBounds 是一次性查询全部结果吗？为什么？
66. mybatis 逻辑分页和物理分页的区别是什么？
67. mybatis 是否支持延迟加载？延迟加载的原理是什么？
68. 说一下 mybatis 的一级缓存和二级缓存？
69. mybatis 和 hibernate 的区别有哪些？
70. mybatis 有哪些执行器（Executor）？
71. mybatis 分页插件的实现原理是什么？
72. mybatis 如何编写一个自定义插件？
六、kafk、Zookeeper
73. kafka 可以脱离 zookeeper 单独使用吗？为什么？
74. kafka 有几种数据保留的策略？
75. kafka 同时设置了 7 天和 10G 清除数据，到第五天的时候消息达到了 10G，这个时候 kafka 将如何处理？
76. 什么情况会导致 kafka 运行变慢？
77. 使用 kafka 集群需要注意什么？
78. zookeeper 是什么？
79. zookeeper 都有哪些功能？
80. zookeeper 有几种部署模式？
81. zookeeper 怎么保证主从节点的状态同步？
82. 集群中为什么要有主节点？
83. 集群中有 3 台服务器，其中一个节点宕机，这个时候 zookeeper 还可以使用吗？
84. 说一下 zookeeper 的通知机制？
七、Redis
85. Redis是什么？都有哪些使用场景？
86. redis 有哪些功能？
87. redis 和 memecache 有什么区别？
88. redis 为什么是单线程的？
88. [redis在什么情况下会变慢？](../plugins/redis.md#目录)
89. 什么是缓存穿透？怎么解决？
90. redis 支持的数据类型有哪些？
91. redis 支持的 Java 客户端都有哪些？
92. Jedis 和 redisson 有哪些区别？
93. 怎么保证缓存和数据库数据的一致性？
94. redis 持久化有几种方式？
95. redis 怎么实现分布式锁？
96. redis 分布式锁有什么缺陷？
97. redis 如何做内存优化？
98. redis 淘汰策略有哪些？
99. redis 常见的性能问题有哪些？该如何解决？
八、 JVM
100. 说一下 Jvm 的主要组成部分？及其作用？
101. 说一下 Jvm 运行时数据区？
102. 说一下堆栈的区别？
103. 队列和栈是什么？有什么区别？
104. 什么是双亲委派模型？
105. 说一下类加载的执行过程？
106. 怎么判断对象是否可以被回收？
107. java 中都有哪些引用类型？
108. 说一下 jvm 有哪些垃圾回收算法？
109. 说一下 jvm 有哪些垃圾回收器？
110. 详细介绍一下 CMS 垃圾回收器？
111. 新生代垃圾回收器和老生代垃圾回收器都有哪些？有什么区别？
112. 简述分代垃圾回收器是怎么工作的？
113. 说一下 jvm 调优的工具？
114. 常用的 jvm 调优的参数都有哪些？
> Java线程执行native方法时程序计数器为空，如何确保native执行完后的程序执行的位置
native是非java代码编写的，比如C,C++, 它们无法在java编译时生成字节码，即JVM获取不到native实现，只能通过系统指令去调用native方法,所以执行native时程序计数器值为空undefined。native方法由原生平台直接执行，native方法执行后会退出(栈帧pop)，方法退出返回到被调用的地方继续执行程序。
* [介绍下cap理论](../system/architecture/CAP.md)
* [Java, Spring, SpringBoot, SpringCloud常用注解](../java/spring/spring-annotation.md)
* [这次彻底理解BIO，NIO，多路复用是什么](../system/architecture/io-multiplexing.md)

### 计算机
* 为什么CPU的缓存还是那么小？

### 数据库

* [千万级别的数据，如何更改表字段长度？](../database/database.md#百问)
* 网络IO是什么？
```
可以理解为socket和fd(file descriptor文件描述符)

10中网络ID
单线程同步 -> NTP
多线程同步 -> Natty
单线程异步 -> Redis
半同步半异步 -> Natty
多进程同步 -> fastcgi
多线程异步 -> memcached
多进程异步 -> nginx
每请求每进程（现场）-> Apache/CGI
微进程框架 -> erlang/go/lua
协议框架 -> libco/ntyco/libgo

单线程 epoll -> redis
多线程 epoll -> memcached
多进程 epoll -> nginx

epoll底层数据结构是红黑树，还有一个就绪队列。
while(1) {
    epoll_wait(epfd, events, 1024, -1)
}
epoll中的三个函数
epoll_create() 创建一个网络IO
epoll_ctl() 控制connection的
epoll_wait() 多长时间去轮询一次，看有没有数据
服务器端有个事件循环一直在监听有没有数据（缺点是一直占一个cpu）
```

* 解释下同步/异步，阻塞/非阻塞
```
妈妈让我去厨房烧一锅水，准备下饺子
阻塞：水只要没烧开，我就干瞪眼看着这个锅，沧海桑田，日新月异，我自岿然不动，厨房就是我的家，烧水是我的宿命。
非阻塞：我先去我屋子里打把王者，但是每过一分钟，我都要去厨房瞅一眼，生怕时间长了，水烧干了就坏了，这样导致我游戏也心思打，果不然，又掉段了。
同步：不管是每分钟过来看一眼锅，还是寸步不离的一直看着锅，只要我不去看，我就不知道水烧好没有，浪费时间啊，一寸光阴一寸金，这锅必须发我13薪
异步：我在淘宝买了一个电水壶，只要水开了，它就发出响声，嗨呀，可以安心打王者喽，打完可以吃饺子喽~
总结：
阻塞/非阻塞：我在等你干活的时候我在干啥？
阻塞：啥也不干，死等
非阻塞：可以干别的，但也要时不时问问你的进度
同步/异步：你干完了，怎么让我知道呢？
同步：我只要不问，你就不告诉我
异步：你干完了，直接喊我过来就行

在讨论之前先说明一下IO发生时涉及到的对象和步骤，对于一个network IO，它会涉及到两个系统对象：
* application 调用这个IO的进程
* kernel 系统内核

那他们经历的两个交互过程是：
 阶段1 wait for data 等待数据准备
 阶段2 copy data from kernel to user 将数据从内核拷贝到用户进程中
```

### 秒杀系统
1. 如何控制秒杀商品页面购买按钮的定时点亮？[抢买问题]
2. 如何防止超卖？
3. 如何防止重复下单？
4. 排队下单，类似12306入Queue的操作
参考答案：https://github.com/zhonghuasheng/JAVA/tree/master/seckill#


### 面试技巧
1. 面试前出于礼貌和面试官确认自己讲话是否能被听懂，方言的问题
2. 自我介绍尽量控制在35秒左右（基本信息介绍，工作经历介绍，求职的原因介绍）
3. 在回答问题是使用我的理解是什么什么，不要去绝对回答这个问题，技术没有完全的对错，每个人对技术的理解都不同
4. 面试过程中如果遇到自己没有接触到的知识点，可以这样说：这个知识点我们有接触过，我尝试回答一下我的理解

### 盘涅
2. 集合你平时用的多的有哪些？
```
答：集合的话像Collection下的ArrayList, LinkedList; Set下的HashSet; Map下的HashMap，如果涉及到多线程，线程安全的话，会使用ConcurrentHashMap。
问：我们先聊一下ArrayList和LinkedList吧，它们两个在什么场景下会使用它？
答：ArrayList的底层数据结构是数组，不指定ArrayList大小的时候初始化的大小是0，第一次add的时候size会变成10，扩容的话会是之前的1.5倍。ArrayList由于底层是数组，因此随机查找的速度很快，插入和删除效率比较低。因为它底层是数组，因此分配内存空间的时候要求是连续的内存单元，所以如果需要存储的数据量很大的情况下，不建议使用ArrayList。LinkedList的底层是一个带有头节点和尾节点的双向链表，提供了头插（LinkedFirst）和尾插（LinkedLast），插入和删除比较快，不支持随机查询，LinkedList数据的存储不要求内存空间是连续的。
问：如果在多线程的情况下，我既想用List，又想保证线程安全，那怎么办？
答：我知道的有三种方式：1. 使用Vector，它是一个线程安全的List集合，所有的方法都加了synchronized关键字来保证同步，但它性能很差。[读写都加锁，底层也是数组，扩容时是之前的2倍] 2. 使用Collections.SynchronizedList，它是Collections下的一个静态内部类，它把List下的所有方法都变成了线程安全的，于Vector不同的是，它把synchronized加到了方法内部的代码块上，提高了扩展性[锁的粒度变小] 3. 使用CopyOnWriteArrayList，add的时候加锁，读的时候不加锁，提高了读取性能 [锁粒度变小，同时锁范围变小]
```

3. 常规的来聊一下HashMap
```
答：HashMap在1.7的时候底层使用数组+单链表的数据结构，使用的是头插；1.8使用数组+单链表/红黑树，使用的是尾插。单链表和红黑树之间的转换，当单链表的长度大于等于8，并且它的hash桶大于等于64的时候，它会将单链表转换成红黑树形式存储；它在红黑树的节点的数量小于等于6的时候，它会重新再转换成一个单链表，这是它底层结构的一个变化；当我们往hashmap中put元素的时候，先根据key的hash值得到这个元素在数组中的位置（即下标），然后就可以把这个元素放到对应的位置中了。如果这个元素所在的位子上已经存放有其他元素了，那么在同一个位子上的元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。从hashmap中get元素时，首先计算key的hashcode，找到数组中对应位置的某一元素，然后通过key的equals方法在对应位置的链表中找到需要的元素。从这里我们可以想象得到，如果每个位置上的链表只有一个元素，那么hashmap的get效率将是最高的；另外一点是关于它Hash桶的数量，默认是16个，默认的阀值是0.75，这关系到它的扩容。
问：它扩容是怎么扩容的？
答：扩容的时候首先检测数组中的元素个数，负载因子默认是loadFactor=0.75，因此当它哈希桶占用的容量大于12的时候，就会触发扩容，哈希桶会扩容成之前的两倍，把之前的元素再进行一次哈希运算，然后添加到新的哈希桶，然后按照链表或者红黑树重新排列起来。
问：你给我说下它是不是线程安全的？
答：它不是线程安全的，因为在插入操作的时候多线程会有数据覆盖的可能；另外它在1.7的时候，它在put的时候可能会有一个resize的过程，这个过程可能会照成它的头插会形成一个环形链表形成一个死循环，1.8之后改成尾插了。
问：你平时开发的时候怎么去保证它的线程安全？
答：我平时开发的时候会使用ConcurrentHashMap来保证它的线程安全。另外有HashTable也能保证线程安全，但是HashTable锁的是方法。`待续`
问：聊一聊ConcurrentHashMap
答：1.7的时候底层是一个分片数组，使用了segment锁（继承自ReentrantLock），通过每次只给一个段加锁来保证它的线程安全和并发度；另外，在1.8的时候，它改成了和HashMap一样的数据结构，使用数据加单链表或者红黑树的数据结构，在1.8的时候渐渐放弃了这种分片锁机制，而使用的是synchronized加CAS来做的，我们知道在1.6版本的时候JVM对synchronized的优化是非常大的，现在也是用这个方法来保证线程安全。
问：CAS是什么？
答：Compare And Sweep，比较并替换，是乐观锁的一种实现，在修改之前要将之前读到的值与当前内存中的值进行比较，如果一致就写入。CAS可以认为是一种轻量级的锁，在低并发的时候时候效率很快，在高并发的情况下，CAS需要涉及到CPU的计算，容易引起CPU性能的消耗，高并发的情况下还是建议使用状态机或者锁之类的。另外一点就是会产生ABA的问题，因为在读取和写入之间，可能有其他线程率先完成修改，然后又将值改回到之前，这样就误以为当前的值和读取的值是一致的，这个问题可以通过加一个戳或标志位来标识，也就是相当于加个版本号。
```

4. 刚你提到了synchronized，再跟我聊下它
```
答：关于synchronized，可以用在同步代码块（可指定任意锁）/方法（指定this）/静态方法（指定class对象）。在1.6升级还是蛮大的，首先提供了无锁状态，然后是偏向锁，然后是轻量级锁，然后是重量级锁。偏向锁的话，见名知意，它偏向于获得第一个锁的线程，它会将id写到锁对象的对象头中，等其他线程来的时候，立即会升级到轻量级锁的状态（如果是同一个线程再次进入，先判断是否获取过锁，如果获取过标记位+1，否则修改为1），轻量级锁主要是在低并发的情况下来消除锁的方式，它主要是在你的虚拟机栈中开辟一个空间，叫做Lock Record，将锁对象的Mark Work写到Lock Record，再尝试Lock Record的指针使用CAS去修改锁对象头的那个区域来完成一个加锁的过程，如果再有线程进入的话，并尝试修改这个指针，轻量级锁会升级为一个自旋锁，如果10次未成功就会膨胀成一个重量级锁，也就是一个互斥锁的过程。重量级锁，使用synchronized的时候会在你的代码块前后加上两个指令，monitorenter和monitorexist，通过一个monitor监视器通过计数器的方式来监视这个锁的状态。如果是同步方法的时候，使用的是一个ACC_SYNCHRONIZED标志位，相当于flag，它自动走的是一个同步方法调用的策略，这个原理是比较简单的。
问：什么时候用它？什么时候用ReentrantLock，这个你有考虑吗？
答：有，它两对比的话区别还是蛮大的。从JVM层面来说，synchronized是JVM的一个关键字，ReetrantLock其实是一个类，你需要手动去编码，synchronized使用其实是比较简单的，不需要关心锁的释放；但是使用ReetrantLock的时候你需要手动去lock，然后配合tray finally然后去确保锁的释放，然后ReentrantLock相比synchronized有几个高级特性，当一个线程长期等待得不到锁的时候，你可以手动的去调用一个lockInterruptibly方法尝试中断掉不去等待；另外，它提供了一个公平锁的方式；此外，它提供了一个condition，你可以指定去唤醒绑定到condition身上的线程，来实现一个选择性通知的方式。如果不需要ReentrantLock需要的高级特性的话，建议还是使用synchronized的关键字。
例：因为锁不可逆，如果在早高峰的时候，滴滴打车上的所有锁都升级为重量级锁，那么等过了这个高峰，锁依然是重量级锁，会影响系统的QPS的，所以在使用的时候还是要更具具体的场景来使用
```

5. volatile看过吗？
```
答：volatile修饰的变量保证了多线程下的可见性，当CPU写数据时，发现此变量被volatile修饰时，发现其他CPU中也存在该变量的副本，会发出信号通知其他CPU该变量的缓存行置为无效状态，因此当其他CPU需要读取这个变量时，发现自己缓存中的变量行是无效的，就重新去内存读取。它是通过计算机的总线嗅探机制（MESI）来实现的，当然它也会照成一个问题，就是volitale会一直嗅探，导致一些无效的交互，引发总线风暴。
```