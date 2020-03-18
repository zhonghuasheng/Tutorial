# 目录
* [面试题](#面试题)
* [面试技巧](#面试技巧)

### 面试题
* [什么是面向对象](https://github. com/zhonghuasheng/Tutorial/issues/188)
* [SpringMVC 说说过滤器、监听器、拦截器有啥区别]()
* [SpringBoot系列](https://github. com/zhonghuasheng/Tutorial/issues?q=label%3ASpringBoot+)
    * [SpringBoot 的启动原理](https://github. com/zhonghuasheng/Tutorial/issues/185)
一、Java 基础
1. JDK 和 JRE 有什么区别？
2. == 和 equals 的区别是什么？
3. 两个对象的 hashCode()相同，则 equals()也一定为 true，对吗？
4. final 在 Java 中有什么作用？
5. Java 中的 Math. round(-1. 5) 等于多少？
6. String 属于基础的数据类型吗？
7. Java 中操作字符串都有哪些类？它们之间有什么区别？
8. String str="i"与 String str=new String(“i”)一样吗？
9. 如何将字符串反转？
10. String 类的常用方法都有哪些？
11. 抽象类必须要有抽象方法吗？
12. 普通类和抽象类有哪些区别？
13. 抽象类能使用 final 修饰吗？
14. 接口和抽象类有什么区别？
15. Java 中 IO 流分为几种？
16. BIO、NIO、AIO 有什么区别？
17. Files的常用方法都有哪些？
二、多线程
18. 并行和并发有什么区别？
19. 线程和进程的区别？
20. 守护线程是什么？
21. 创建线程有哪几种方式？
22. 说一下 runnable 和 callable 有什么区别？
23. 线程有哪些状态？
24. sleep() 和 wait() 有什么区别？
25. notify()和 notifyAll()有什么区别？
26. 线程的 run()和 start()有什么区别？
27. 创建线程池有哪几种方式？
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
三、spring/spring MVC
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
七、Redis、JVM
85. Redis是什么？都有哪些使用场景？
86. redis 有哪些功能？
87. redis 和 memecache 有什么区别？
88. redis 为什么是单线程的？
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