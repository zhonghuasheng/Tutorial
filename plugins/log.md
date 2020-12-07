## 目录
* [导读](#导读)
* [Log的发展历程](#Log的发展历程)
    * [历史](#历史)
    * [原生Java日志组件长什么样](#原生Java日志组件长什么样)
    * [logback与log4j2比较](#logback与log4j2比较)
    * [SLF4J门面日志的好处](#SLF4J门面日志的好处)
* [Log4j2使用](#Log4j2使用)
    * [Log4j2的两种配置方式](#Log4j2的两种配置方式)
    * [Log4j2的三大组件](#Log4j2的三大组件)
    * [Log4j2常用配置文件详解](#Log4j2常用配置文件详解)
    * [异步日志](#异步日志)
* [动态调整日志级别](#动态调整日志级别)
* [代码示例](#代码示例)
* [引用](#参考)

### 导读
在日常的面试中，问到日志这块的很少，但是也会问到关于日志的选型和对比，主要考察你平时的总结。我这篇笔记呢主要是从Java中日志的发展历史，到流行的log4j2的常规使用和规范进行一个系统的总结。

### Log的发展历程
#### 历史
1. JUL (Java Util Logging) 早期的JDK内置的Log。包含Logger，Handler，Formatter三个模块。
2. Log4j 由于JUL的开发不友好，Log4j推出之后迅速扩展开来。后来Log4j被Apache Foundation收入门下之后，由于理念不合，Log4j的作者Ceki离开了，后来开发了Slf4j和Logback。
3. JCL (Jakarta Commons-Logging) 早期Apache开源项目，用于管理各个Java子项目，诞生的目的是提供了一套API来实现不同Logger之间的切换。
4. SLF4J (Simple Logging Facade For Java) 简单日志门面，SLF4J仅仅提供的是接口定义，这样的抽象层可以将应用程序从日志框架中解耦。和JCL功能类似，但JCL有一个致命的缺点就是算法复杂，出现问题难以排除。值得一提的是SLF4J的作者就是Log4j的作者。
5. Logback 是Log4j的作者的另一个开源日志组件，与Log4j相比，Logback吸取了Log4j的经验，重构了其狠心功能，使它的性能提升了很多，大约是Log4j的10倍，内存占用更小了，并且完整实现了Slf4j API，便于切换日志框架。SpringBoot默认集成Logback。Logback后期的跟新没有Log4j2频繁。
6. Log4j2 由于Slf4j和Logback的推出，市场的使用率很高，并且Logback还集成进了SpringBoot，你想啊，这不打Apache Log4j部门的脸嘛。Apache Logging一直关门憋大招，Log4j2于2014年发布GA版本，不仅吸收了Logback的先进功能，更引入了优秀的锁机制、LMAX Disruptor等先进特性，在性能上全面超越了Log4j和Logback，特别是在高并发的处理上。

#### 原生Java日志组件长什么样
Java日志API由以下三个核心组件组成：
* Loggers: 记录器 Logger负责捕获事件并将其发送给合适的Appender
* Appenders: 输出目的地 也称为Handler，负责将日志事件记录到目标位置。在将日志事件输出之前，Appenders使用Layouts来对事件进行格式化处理
* Layouts: 日志布局 也称为Formatters，它负责对日志事件中的数据进行转换和格式化。Layouts决定了数据在一条日志记录中的最终形式。
当Logger记录一个事件时，它将事件转发给适当的Appender。然后Appender使用Layout来对日志记录进行格式化，并将其发送给控制台、文件或者其它目标位置。另外，Filters可以让你进一步指定一个Appender是否可以应用在一条特定的日志记录上。在日志配置中，Filters并不是必须的，但可以让你更灵活地控制日志消息的流动。

#### logback与log4j2比较
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

#### SLF4J门面日志的好处
诸如 SLF4J 这样的抽象层，会将你的应用程序从日志框架中解耦。
应用程序可以在运行时选择绑定到一个特定的日志框架（例如 java.util.logging、Log4j 或者 Logback），这通过在应用程序的类路径中添加对应的日志框架来实现。
如果在类路径中配置的日志框架不可用，抽象层就会立刻取消调用日志的相应逻辑。
抽象层可以让我们更加容易地改变项目现有的日志框架，或者集成那些使用了不同日志框架的项目。

> PS：
1. SpringBoot官方建议使用“-spring”的命名规则，进行日志的配置，如：logback-spring.xml，log4j2-spring.xml
2. Tinylog是一个小巧的日志框架 https://tinylog.org/v2/
3. 大佬Ceki的github https://github.com/ceki

### Log4j2使用
#### Log4j2的两种配置方式
* 通过`ConfigurationFactory`使用编程的方式进行配置
* 通过配置文件配置，支持`xml`、`JSON`、`YAML`和`properties`等文件类型

#### Log4j2的三大组件
* Logger 记录器
```
Loggers节点，常见的有两种：Root和Logger
Root节点用来指定项目的根日志，如果没有单独指定Logger，那么就会默认使用该Root日志输出
Logger节点用来单独指定日志的形式，比如要为指定包下的class指定不同的日志级别等
```
* Appender 输出目的地
```
Appenders节点，常见的有三种子节点：Console, File, RollingFile
console节点用来定义输出到控制台的Appender
File节点用来定义输出到指定位置的文件的Appender
RollingFile节点用来定义超过指定大小自动删除旧的创建新的的Appender
```
* Layout 日志布局
```
Layout日志布局参数很多，这里我只罗列常用的
<PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level [%l] - %msg%n" />
对齐方式，例如 %20c 右对齐， 最小宽度20，无最大宽度，不足20个字符则在数值前面用空格补足，超过20个字符则保留原信息。
数据类型    转换字符             输出日志
日期        %d{HH:mm:ss.SSS}    11:33:08.440
线程名      %t 	                 main
日志级别    %-5level             FATAL
日志名称    %logger{36}          org.apache.logging.log4j.Log4j2Test
日志信息    %msg                 fatal level log
换行        %n                   日志结束换行
```

#### Log4j2常用配置文件详解
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--monitorInterval：Log4j2 自动检测修改配置文件和重新配置本身，设置间隔秒数-->
<configuration monitorInterval="5">
    <!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
    <!--变量配置-->
    <Properties>
        <!-- 格式化输出：
            %date表示日期，%thread表示线程名，
            %-5level：级别从左显示5个字符宽度
            %msg：日志消息，%n是换行符-->
        <!-- %logger{36} 表示 Logger 名字最长36个字符 -->
        <property name="LOG_PATTERN" value="[${sys:SERVICE_NAME}] [%date{yyyy-MM-dd HH:mm:ss.SSS}] [%thread] %-5level %logger{36} - %msg%n" />
        <!-- 定义日志存储的路径，不要配置相对路径 -->
        <property name="FILE_PATH" value="${sys:FILE_PATH}/${sys:SERVICE_NAME}" />
        <property name="FILE_NAME" value="${sys:SERVICE_NAME}" />
    </Properties>
    <appenders>
        <console name="Console" target="SYSTEM_OUT">
            <!--输出日志的格式-->
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <!--控制台只输出level及其以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
        </console>
        <!--文件会打印出所有信息-->
        <File name="Filelog" fileName="${FILE_PATH}/log4j2-all.log" append="true">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </File>
        <!-- 这个会打印出所有的info及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileInfo" fileName="${FILE_PATH}/info.log" filePattern="${FILE_PATH}/${FILE_NAME}-INFO-%d{yyyy-MM-dd}_%i.log.gz">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <!--interval属性用来指定多久滚动一次，单位与RollingFile的filePattern一致，如果filePattern中配置的文件重命名规则是%d{yyyy-MM-dd HH-mm-ss}-%i，最小的时间粒度是ss，即秒钟。-->
                <TimeBasedTriggeringPolicy interval="1"/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <!-- DefaultRolloverStrategy同一文件夹下15个文件开始覆盖-->
            <DefaultRolloverStrategy max="15"/>
        </RollingFile>
        <!-- 这个会打印出所有的warn及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileWarn" fileName="${FILE_PATH}/warn.log" filePattern="${FILE_PATH}/${FILE_NAME}-WARN-%d{yyyy-MM-dd}_%i.log.gz">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <!--interval属性用来指定多久滚动一次-->
                <TimeBasedTriggeringPolicy interval="1"/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <!-- DefaultRolloverStrategy同一文件夹下15个文件开始覆盖-->
            <DefaultRolloverStrategy max="15"/>
        </RollingFile>
        <!-- 这个会打印出所有的error及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileError" fileName="${FILE_PATH}/error.log" filePattern="${FILE_PATH}/${FILE_NAME}-ERROR-%d{yyyy-MM-dd}_%i.log.gz">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <!--interval属性用来指定多久滚动一次-->
                <TimeBasedTriggeringPolicy interval="1"/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <!-- DefaultRolloverStrategy同一文件夹下15个文件开始覆盖-->
            <DefaultRolloverStrategy max="15"/>
        </RollingFile>
    </appenders>
    <!--Logger节点用来单独指定日志的形式，比如要为指定包下的class指定不同的日志级别等。-->
    <!--然后定义loggers，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <!--过滤掉spring和mybatis的一些无用的DEBUG信息-->
        <logger name="org.mybatis" level="info" additivity="false">
            <AppenderRef ref="Console"/>
        </logger>
        <!--监控系统信息-->
        <!--若是additivity设为false，则 子Logger 只会在自己的appender里输出，而不会在 父Logger 的appender里输出。-->
        <Logger name="org.springframework" level="info" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <root level="info">
            <appender-ref ref="Console"/>
            <appender-ref ref="Filelog"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
        </root>
    </loggers>
</configuration>
```

#### Log4j2日志回滚策略
`RollingFileAppender`是Log4j2中的一种能够实现日志文件滚动更新(rollover)的Appender。rollover的意思是当满足一定条件（如文件达到了指定的大小，达到了指定的时间）后，就重命名原日志文件进行归档，并生成新的日志文件用于log写入。如果还设置了一定时间内允许归档的日志文件的最大数量，并对过旧的日志文件进行删除操作。RollingFile实现日志文件滚动更新，依赖于TriggerPolicy和RolloverStrategy。`Policy`是用来控制日志文件何时(When)进行滚动，`Strategy`是用来控制日志文件如何(How)进行滚动的。如果配置的是`RollingFile`或`RollingRandomAccessFile`，则必须配置一个`Policy`。
> Policy触发策略
1. SizeBasedTriggeringPolicy 基于日志文件大小的触发策略。单位有：KB, MB, GB
```xml
<SizeBasedTriggeringPolicy size="100 MB"/>
```
2. CronTriggeringPolicy 基于`Cron`表达式的触发策略，灵活
```xml
<CronTriggeringPolicy schedule="0/5 * * * * ?"/>
```
3. TimeBasedTriggeringPolicy 基于时间的触发策略。该策略主要是完成周期性的log文件封存工作，有两个参数：
* `interval` integer类型，指定两次封存动作之间的时间间隔。这个配置需要和`filePattern`结合使用，`filePattern`日期格式精确到哪一位，interval也精确到哪一个单位。如`filePattern`中配置的文件重命名规则是`%d{yyyy-MM-dd HH-mm-ss}-%i`，单位精确到秒，那么interval的单位也就是秒啦。
* `modulate` boolean类型，说明是否对封存时间进行调制。若modulate=true，则封存时间将以0点为边界进行偏移计算。比如，modulate=true, interval=4hours，那么假设上次封存日志的时间为03:00，则下次封存日志的时间为04:00，之后的封存时间依次为08:00, 12:00 16:00
```xml
<TimeBasedTriggeringPolicy interval="1" modulate="true"/>
```
> Strategy滚动策略
`DefaultRolloverStrategy`默认滚动策略，常用参数`max`，保存日志文件的最大个数，默认是7，大于此值会删除旧的日志文件
```xml
<DefaultRolloverStrategy max="10" />
```

> DeleteAction删除策略
log4j2也提供了删除的策略，方便使用者删除特定的日志文件
```xml
<Appenders>
  <RollingFile name="RollingFile" fileName="${baseDir}/app.log"
        filePattern="${baseDir}/app-%d{yyyy-MM-dd}.log.gz">
    <PatternLayout pattern="%d %p %c{1.} [%t] %m%n" />
    <CronTriggeringPolicy schedule="0 0 0 * * ?"/>
    <DefaultRolloverStrategy>
    <Delete basePath="${baseDir}" maxDepth="2">
        <IfFileName glob="*/app-*.log.gz" />
        <IfLastModified age="60d" />
    </Delete>
    </DefaultRolloverStrategy>
  </RollingFile>
</Appenders>
```

#### lookup - 具体都有哪些可用，翻阅官网即可
“ Lookups provide a way to add values to the Log4j configuration at arbitrary places. They are a particular type of Plugin that implements the StrLookup interface. ”
以上内容复制于log4j2的官方文档lookup - Office Site。其清晰地说明了lookup的主要功能就是提供另外一种方式以添加某些特殊的值到日志中，以最大化松散耦合地提供可配置属性供使用者以约定的格式进行调用。

#### 异步日志
参考Apache Logging的官方介绍，Log4j2的异步日志是通过LMAX Disruptor technology实现的，通过开启一个单独的线程执行IO操作。
Log4j2可以使用AsyncAppender和AsyncLogger两种配置方式，这两者的主要却别是对生成的LogEvent的处理不一样。其中AsyncAppender采用ArrayBlockingQueue来保存需要异步输出的日志事件；AsyncLogger则使用Disruptor框架来实现高吞吐。

|    | 日志输出方式 |
| -- | -- |
| sync | 同步打印日志，日志输出与业务逻辑在同一线程内，当日志输出完毕，才能进行后续业务逻辑操作  |
| Async Appender | 异步打印日志，内部采用ArrayBlockingQueue，对每个AsyncAppender创建一个线程用于处理日志输出。 |
| Async Logger | 异步打印日志，采用了高性能并发框架Disruptor，创建一个线程EventProcessor用于处理日志输出。 |

LMAX：
```
... using queues to pass data between stages of the system was introducing latency, so we focused on optimising this area. The Disruptor is the result of our research and testing. We found that cache misses at the CPU-level, and locks requiring kernel arbitration are both extremely costly, so we created a framework which has "mechanical sympathy" for the hardware it's running on, and that's lock-free. 
```
异步日志配置：
1. 默认情况下，如上的配置走的不是异步模式，如果想在上面的配置中走异步日志的方式，需要配置`-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- Don't forget to set system property
-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
        to make all loggers asynchronous. -->
    
<Configuration status="WARN">
    <Appenders>
    <!-- Async Loggers will auto-flush in batches, so switch off immediateFlush. -->
    <RandomAccessFile name="RandomAccessFile" fileName="async.log" immediateFlush="false" append="false">
        <PatternLayout>
        <Pattern>%d %p %c{1.} [%t] %m %ex%n</Pattern>
        </PatternLayout>
    </RandomAccessFile>
    </Appenders>
    <Loggers>
    <Root level="info" includeLocation="false">
        <AppenderRef ref="RandomAccessFile"/>
    </Root>
    </Loggers>
</Configuration>
```
2. 混合模式和直接使用模式下不用指定contextSelector
```xml
<?xml version="1.0" encoding="UTF-8"?>
    
<!-- No need to set system property "log4j2.contextSelector" to any value
        when using <asyncLogger> or <asyncRoot>. -->
    
<Configuration status="WARN">
    <Appenders>
    <!-- Async Loggers will auto-flush in batches, so switch off immediateFlush. -->
    <RandomAccessFile name="RandomAccessFile" fileName="asyncWithLocation.log"
                immediateFlush="false" append="false">
        <PatternLayout>
        <Pattern>%d %p %class{1.} [%t] %location %m %ex%n</Pattern>
        </PatternLayout>
    </RandomAccessFile>
    </Appenders>
    <Loggers>
    <!-- pattern layout actually uses location, so we need to include it -->
    <AsyncLogger name="com.foo.Bar" level="trace" includeLocation="true">
        <AppenderRef ref="RandomAccessFile"/>
    </AsyncLogger>
    <Root level="info" includeLocation="true">
        <AppenderRef ref="RandomAccessFile"/>
    </Root>
    </Loggers>
</Configuration>
```

### 动态调整日志级别

### 代码示例
https://github.com/zhonghuasheng/JAVA/tree/master/springboot/springboot-log4j2

## 参考
* Log4j2官网 http://logging.apache.org/log4j/2.x/index.html
* 为什么Log4j2优于Log4j、Logback https://www.jianshu.com/p/1ec9f5763c5c
* log4j2.xml详解 https://www.jianshu.com/p/8ded6531ef76
* log4j2系列 https://www.jianshu.com/nb/36706646
* 论Java日志组件的选择 https://www.jianshu.com/p/85d141365d39
* 2020 年了，Java 日志框架到底哪个性能好？——技术选型篇 https://www.cnblogs.com/xuningfans/p/12196175.html
* Java Log 日志 https://www.jianshu.com/p/ca3d96e64607
* Log4j2进阶使用(Pattern Layout详细设置) https://www.jianshu.com/p/37ef7bc6d6eb
* Log4j2中的同步日志与异步日志 https://www.cnblogs.com/yeyang/p/7944906.html