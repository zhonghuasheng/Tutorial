**`声明`**

该文档为本人学习的总结，有些解决方案不一定适合所有情况，取者自思；学习的资料大多来源网上、书籍、视频，如果引用，我会注明来处，若原著不喜，麻烦告知，我定删除。另外，笔记都是闲暇之余总结，很多没有成体系，所以暂时没有发布，今年会陆陆续续发出来。我写了一个自动commit的脚本，国内国外都会定时同步合并我最新的笔记并发布到Tutorial这个repository。Wiki中也有一些详细的文章，同时Issue中记录的为一些面试题，喜欢的话可以Star下，也可以Fork走作为自己笔记的一部分，总之，生活不易，希望对大家学习有帮助。祝好！！！

# 目录

| 英语 | Java | Spring生态 | 中间件 | 数据库 | 服务器 | 架构设计 | 内功 | 网络 | 程序人生 |
|:----|:-----|:------|:------|:------|:------|:------|:------|:------|:-----|
|<a href="#英语">英语</a>|<a href="#Java基础">Basic</a><br><a href="#Java虚拟机">JVM</a><br><a href="#Java-Web">Web</a><br>|<a href="#Spring生态">Spring</a><br><a href="#Spring生态">Spring Boot</a><br><a href="#Spring生态">Spring Cloud</a>|<a href="#Keepalived">Keepalived</a><br><a href="#ActiveMQ">ActiveMQ</a><br><a href="#RabbitMQ">RabbitMQ</a><br><a href="#Netty">Netty</a><br><a href="#MyBatis">Mybatis</a>|<a href="#MySQL">MySQL</a><br><a href="#Postgresql">Postgresql</a><br><a href="#Mongodb">Mongodb</a><br><a href="#Redis">Redis</a>|<a href="#Tomcat">Tomcat</a><br><a href="#Nginx">Nginx</a>|<a href="#原则">原则</a><br><a href="#安全">安全</a><br><a href="#高可用">高可用</a><br><a href="#扩展性">扩展性</a><br><a href="#伸缩性">伸缩性</a><br><a href="#性能">性能</a><br>|<a href="#数据结构">数据结构</a><br><a href="#算法">算法</a><br><a href="#设计模式">设计模式</a>|<a href="#网络">网络</a>|<a href="#软文">软文</a><br><a href="#规范">规范</a><br><a href="#工具">工具</a><br><a href="#面试集锦">面试集锦</a><br><a href="#实战训练">实战训练</a>|

## 英语
* [计算机行业常用英语积累](tool/english.md)

## Java
* `JavaCore`: &emsp;[Java基础](java/basic/java-basic.md)
&emsp;&emsp;[JDBC基础](https://github.com/zhonghuasheng/JAVA/blob/master/jdbc/src/main/java/com/zhonghuasheng/jdbc/learn01/BasicSteps.java)&emsp;&emsp;[Java集合](java/basic/java-collection.md)&emsp;&emsp;[Java多线程系列](java/basic/java-thread.md)&emsp;&emsp;[JUC系列](java/basic/java-thread-juc.md)&emsp;&emsp;[Java IO基础](java/basic/java-io-nio.md)
* `Java -VM`: &emsp;[Java虚拟机系列](java/jvm/深入理解Java虚拟机.md)&emsp;[JVM虚拟机监控及性能调优系列](java/jvm/JVM虚拟机监控及性能调优.md)
* `Java-Web`: &emsp;[Servlet基础](java/javaweb/servlet.md)&emsp;[JSP基础](java/javaweb/jsp.md)
* `Spring X`: &emsp;[Spring4基础知识系列](java/spring/spring.md#Spring)&emsp;[SpringMVC基础知识系列](java/spring/spring.md#SpringMVC)&emsp;[SpringBoot基础知识系列](java/spring/spring.md#SpringBoot)

## 中间件
* `负载均衡`: &emsp;[Keepalived系列](plugins/keepalived.md)
* `消息通信`: &emsp;[消息通信基础](http://note.youdao.com/noteshare?id=30a11e46aaef3f00d2ecfb84692ca294&sub=wcp157828038663078)&emsp;[ActiveMQ系列](plugins/activemq.md)&emsp;[RabbitMQ系列](plugins/rabbitmq.md) &emsp;[Netty系列](plugins/netty.md)
* `数据访问`: &emsp;[MyBatis系列](plugins/mybatis.md)
* `搜索引擎`: &emsp;[ELK](elasticsearch.md)

## 数据库
* `关系型数据库`: &emsp;[数据库理论基础](database/database.md)&emsp;[MySQL](database/mysql.md)&emsp;[Postgresql](database/postgresql.md)
* `非关系型数据库`: &emsp;[Mongodb学习笔记](database/mongodb.md)
* `内存数据库`: &emsp;[Redis基础知识](database/redis.md)

## 服务器
* [Tomcat服务器](plugins/tomcat.md)&emsp;[Nginx反向代理服务器搭建](plugins/nginx.md)

## 架构设计
* `设计原则`: &emsp;[系统设计注意事项](architecture/系统设计注意事项.md)
* `系统安全`: &emsp;[系统架构安全设计](architecture/系统架构安全设计.md)
* `高可用性`: &emsp;[系统架构高可用设计](architecture/系统架构高可用设计.md)
* `高扩展性`: &emsp;[系统架构扩展性设计](architecture/系统架构扩展性设计.md)
* `高伸缩性`: &emsp;[系统架构伸缩性设计](architecture/系统架构伸缩性设计.md)
* `系统性能`: &emsp;[系统架构性能设计](architecture/系统架构性能设计.md)
* `其他事项`: &emsp;[系统架构设计其他注意事项](architecture/系统架构设计其他注意事项.md)

## 内功
* `设计模式`: [23种设计模式](algorithm/设计模式.md)
* `数据结构`: [数据结构系列](algorithm/数据结构.md)
* `算法`: [算法系列](algorithm/algorithm.md)

## 网络
* `常见网络问题`: [常见网络问题系列](network/network.md)

### 程序人生
> `软文`
* [最好的建议](tool/coding-life.md/#最好的建议)
* [正视自己的价值](tool/coding-life.md/#正视自己的价值)
* [新工程师要干的五件事情](tool/coding-life.md/#新工程师要干的五件事情)

> `规范`
* [雅虎前端34条军规](http://note.youdao.com/noteshare?id=b59d0da4f7bb2b7ba5f73129d85b1ba1)
* [Google Java Coding Style](https://google.github.io/styleguide/javaguide.html)
* [阿里巴巴代码规范](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf)
* [给函数取一个好的名字](http://note.youdao.com/noteshare?id=74f3c5fae9fc26473e7046a700cdad12&sub=wcp1581864078132689)
* [Java命名规范参考](http://note.youdao.com/noteshare?id=c0ca7331624eb2f19b06f623a1b832ae&sub=2F7223EB9D9E4072B60A1FB578BF0AFA)

> `工具`
* [API测试工具](tool/api-testing-tool.md)
* [流量统计，网站分析](tool/common-tools.md)
* [日志管理工具](tool/cronolog.md)
* [Git](tool/git.md)
* [Intellij](tool/intellij.md)
* [Maven](tool/maven.md)
* [VSCode](tool/vscode-settings.md)
* [CloudFlare免费的CDS服务]()
* [LDAP搭建和使用]()
* [常见部署方式](tool/deployment.md)

> `面试集锦`
* [疯狂面试题](tool/interview.md)

> `实战训练`
* [我也写份秒杀商城](system/秒杀.md)

<center>
<h2 align="center">技术栈</h2>
<center>

![](tutorial-2020-03-15.png)

</center>
