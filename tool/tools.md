### 持久化相关
* Flyway 是一款开源的数据库版本管理工具，它更倾向于规约优于配置的方式。Flyway 可以独立于应用实现管理并跟踪数据库变更，支持数据库版本自动升级，并且有一套默认的规约，不需要复杂的配置，Migrations 可以写成 SQL 脚本，也可以写在 Java 代码中，不仅支持 Command Line 和 Java API，还支持 Build 构建工具和 Spring Boot 等，同时在分布式环境下能够安全可靠地升级数据库，同时也支持失败恢复等。

* DB连接工具
    * DBeaver Community https://dbeaver.io/
    * Yearning MySQL DBA SQL审计 http://yearning.io/

* S3
  * Minio是GlusterFS创始人之一Anand Babu Periasamy发布新的开源项目。Minio兼容Amason的S3分布式对象存储项目，采用Golang实现，客户端支持Java,Python,Javacript, Golang语言。Minio可以做为云存储的解决方案用来保存海量的图片，视频，文档。由于采用Golang实现，服务端可以工作在Windows,Linux, OS X和FreeBSD上。
* Aliyun
  * Tablestore 表格存储（集存储和搜索于一体，无需单独构建ES）
      * Wide Column： 列存储
      * Timeline模型：主要用于消息数据，使用于IM, Feed流和物联网设备
      * Timestream模型：适用于时序数据、时空数据
      * Grid模型：适用于科学大数据的存储和查询场景
  * Tair 阿里云Tair是阿里云数据库Redis企业版，是基于阿里集团内部使用的Tair产品研发的云上托管键值对缓存服务。Tair作为一个高可用、高性能的分布式NoSQL数据库，专注于多数据结构的缓存与高速存储场景，完全兼容Redis协议。相比云Redis社区版，Tair支持更强的性能、更多的数据结构和更灵活的存储方式。专属集群内Redis实例和Redis实例一样。

* Log
  * Flume最早是Cloudera提供的日志收集系统，是Apache下的一个孵化项目，Flume支持在日志系统中定制各类数据发送方，用于收集数据。Flume提供对数据进行简单处理，并写到各种数据接受方（可定制）的能力 。Flume提供了从console（控制台）、RPC（Thrift-RPC）、text（文件）、tail（UNIX tail）、syslog（syslog日志系统），支持TCP和UDP等2种模式，exec（命令执行）等数据源上收集数据的能力。
  * Rsyslog 是一个 syslogd 的多线程增强版。它提供高性能、极好的安全功能和模块化设计。
  * ELK
  * 阿里云SLS日志产品

### Linux系统相关
* Terminal
    * Linux系统下使用Terminators
    * MobaXterm https://mobaxterm.mobatek.net/

### 搜索相关
* 搜索
    * magi.com AI智能搜索

### OutofBox框架
* 框架
    * Ant Design Pro前端中后台开箱即用的框架 https://pro.ant.design/
    * Liferay 功能丰富的CMS系统

### 开发相关
* 代码行统计工具
  * cloc.exe windows中cmd使用cloc.exe folderPath/
  * IDEA plugin statistic
* Java诊断工具
  *  阿里JAVA诊断工具Arthas

### 帮助类相关
* Hutool是一个Java工具包类库，对文件、流、加密解密、转码、正则、线程、XML等JDK方法进行封装，组成各种Util工具类