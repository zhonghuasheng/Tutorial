# 目录
* [导读](#导读)
* [Log的发展历程](#Log的发展历程)
* [Log4j2介绍](#Log4j2介绍)
* [Log4j2配置详解](#Log4j2配置详解)
* [Log4j2应用](#Log4j2应用)

## 导读
在日常的面试中，问到日志这块的很少，但是也会问到关于日志的选型和对比，主要考察你平时的总结。我这篇笔记呢主要是从Java中日志的发展历史，到流行的log4j2的常规使用和规范进行一个系统的总结。

## Log的发展历程
> 历史
1. JUL (Java Util Logging) 早期的JDK内置的Log。包含Logger，Handler，Formatter三个模块。
2. Log4j 由于JUL的开发不友好，Log4j推出之后迅速扩展开来。后来Log4j被Apache Foundation收入门下之后，由于理念不合，Log4j的作者Ceki离开了，后来开发了Slf4j和Logback。
3. JCL (Jakarta Commons-Logging) 早期Apache开源项目，用于管理各个Java子项目，诞生的目的是提供了一套API来实现不同Logger之间的切换。
4. SLF4J (Simple Logging Facade For Java) 简单日志门面，SLF4J仅仅提供的是接口定义，这样的抽象层可以将应用程序从日志框架中解耦。和JCL功能类似，但JCL有一个致命的缺点就是算法复杂，出现问题难以排除。值得一提的是SLF4J的作者就是Log4j的作者。
5. Logback 是Log4j的作者的另一个开源日志组件，与Log4j相比，Logback吸取了Log4j的经验，重构了其狠心功能，使它的性能提升了很多，大约是Log4j的10倍，内存占用更小了，并且完整实现了Slf4j API，便于切换日志框架。SpringBoot默认集成Logback。Logback后期的跟新没有Log4j2频繁。
6. Log4j2 由于Slf4j和Logback的推出，市场的使用率很高，并且Logback还集成进了SpringBoot，你想啊，这不打Apache Log4j部门的脸嘛。Apache Logging一直关门憋大招，Log4j2于2014年发布GA版本，不仅吸收了Logback的先进功能，更引入了优秀的锁机制、LMAX Disruptor等先进特性，在性能上全面超越了Log4j和Logback，特别是在高并发的处理上。

> 原生Java日志组件长什么样
Java日志API由以下三个核心组件组成：
* Loggers: Logger负责捕获事件并将其发送给合适的Appender
* Appenders: 也称为Handler，负责将日志事件记录到目标位置。在将日志事件输出之前，Appenders使用Layouts来对事件进行格式化处理
* Layouts: 也称为Formatters，它负责对日志事件中的数据进行转换和格式化。Layouts决定了数据在一条日志记录中的最终形式。
当Logger记录一个事件时，它将事件转发给适当的Appender。然后Appender使用Layout来对日志记录进行格式化，并将其发送给控制台、文件或者其它目标位置。另外，Filters可以让你进一步指定一个Appender是否可以应用在一条特定的日志记录上。在日志配置中，Filters并不是必须的，但可以让你更灵活地控制日志消息的流动。

> logback与log4j2比较
logback和log4j2都宣称自己是log4j的后代，一个是出于同一个作者，另一个则是在名字上根正苗红。

撇开血统不谈，比较一下log4j2和logback：
* log4j2比logback更新：log4j2的GA版在2014年底才推出，比logback晚了好几年，这期间log4j2确实吸收了slf4j和logback的一些优点（比如日志模板），同时应用了不少的新技术
* 由于采用了更先进的锁机制和LMAX Disruptor库，log4j2的性能优于logback，特别是在多线程环境下和使用异步日志的环境下
* 二者都支持Filter（应该说是log4j2借鉴了logback的Filter），能够实现灵活的日志记录规则（例如仅对一部分用户记录debug级别的日志）
* 二者都支持对配置文件的动态更新
* 二者都能够适配slf4j，logback与slf4j的适配应该会更好一些，毕竟省掉了一层适配库
* logback能够自动压缩/删除旧日志
* logback提供了对日志的HTTP访问功能
* log4j2实现了“无垃圾”和“低垃圾”模式。简单地说，log4j2在记录日志时，能够重用对象（如String等），尽可能避免实例化新的临时对象，减少因日志记录产生的垃圾对象，减少垃圾回收带来的性能下降

log4j2和logback各有长处，总体来说，如果对性能要求比较高的话，log4j2相对还是较优的选择。

> SLF4J门面日志的好处
诸如 SLF4J 这样的抽象层，会将你的应用程序从日志框架中解耦。
应用程序可以在运行时选择绑定到一个特定的日志框架（例如 java.util.logging、Log4j 或者 Logback），这通过在应用程序的类路径中添加对应的日志框架来实现。
如果在类路径中配置的日志框架不可用，抽象层就会立刻取消调用日志的相应逻辑。
抽象层可以让我们更加容易地改变项目现有的日志框架，或者集成那些使用了不同日志框架的项目。

> PS：
1. SpringBoot官方建议使用“-spring”的命名规则，进行日志的配置，如：logback-spring.xml，log4j2-spring.xml
2. Tinylog是一个小巧的日志框架 https://tinylog.org/v2/
3. 大佬Ceki的github https://github.com/ceki

## Log4j2介绍

## Log4j2介绍

## Log4j2应用

# 参考
* 为什么Log4j2优于Log4j、Logback https://www.jianshu.com/p/1ec9f5763c5c
* log4j2.xml详解 https://www.jianshu.com/p/8ded6531ef76
* log4j2系列 https://www.jianshu.com/nb/36706646
* 论Java日志组件的选择 https://www.jianshu.com/p/85d141365d39
* 2020 年了，Java 日志框架到底哪个性能好？——技术选型篇 https://www.cnblogs.com/xuningfans/p/12196175.html
* Java Log 日志 https://www.jianshu.com/p/ca3d96e64607