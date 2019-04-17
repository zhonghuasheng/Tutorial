# Log

### 为什么要使用Log

 *用途主要体现在以下方面:*

    * Install: 安装配置时记录具体的安装步骤
    * Online debug： 可以通过Log来查看一些Environment Issue
    * Collect Information: 通过日志我们可以找到系统中存在的一些漏洞，这些漏洞时平时开发中难以模拟和发现的
    * Assessment: 通过日志我们可以分析用户的行为，进而评估用户对系统保持热衷的程度，通过慢日志分析/评估用户在模块停留的时间以及系统的反应时间
    * Security: 对于一些很严格的操作我们需要记录详细的说明，比如对一些非授权的操作，银行的业务

### Log的等级

|Log4j slf4j |J2SE | Useage|
|:------------|:------|:------|
|trace|FINEST|输出更详细的调试信息|
|debug|FINER|比调试信息详细点|
|debug|FINE|调试信息|
|info|CONFIG|系统配置，系统运行环境信息|
|info|INFO|系统运行时信息，比如Scheduled时间|
|warn|WARNING|警告，此警告不影响系统的执行，不影响下次业务的执行|
|error|SERVER|错误，问题影响到系统的执行，并且系统不能自行恢复到正常状态|
|fatal||宕机|

### 什么时候打Log

* 系统安装配置时，对于系统安装的参数，如jdk版本，JVM内存大小，等主要相关信息的输出，这样可以看到安装到了哪一个步骤，模块部署是否正常
* 异常捕获时输出日志，出错时的参数，特别对于prod上不能debug的环境，有时只能通过日志去猜测可能出错的原因
* scheduled job，如定时发送邮件等，这些需要在job开始前后都要记录日志，甚至中间的过程都要详细记录
* 一些敏感操作，如delete，这个时候需要记录who when delete what, pre value, current value

### 打什么样的Log

* error 和 warn: 出错信息或者警告的描述，相关参数的值，异常的StackTrace
* info: 输出相关信息
* debug： 相关描述，参数信息，异常的StackTrace

### 打Log对系统性能的影响

* 获取logger实例的开销

* 写文件IO开销


### 一些日志框架

### 使用Log时注意事项

http://blog.csdn.net/xiangnideshen/article/details/45894631
http://blog.chinaunix.net/uid-20196318-id-3611197.html

* foreach语句是java5的新特征之一，在遍历数组、集合方面，foreach为开发人员提供了极大的方便。
* foreach语句是for语句的特殊简化版本，但是foreach语句并不能完全取代for语句，然而，任何的foreach语句都可以改写为for语句版本。
* foreach提高了代码的可读性和安全性（不用怕数组越界）
* foreach的缺点是丢失了索引信息

### ELK
ELK是三款软件的组合。是一整套完整的解决方案。分别是由Logstash（收集+分析）、ElasticSearch（搜索+存储）、Kibana（可视化展示）三款软件。ELK主要是为了在海量的日志系统里面实现分布式日志数据集中式管理和查询，便于监控以及排查故障。

ElasticSearch 是一个基于 Lucene 的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于 RESTful API 的 web 接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。

Logstash是一个数据采集、加工处理以及传输的工具，能够将所有的数据集中处理，使不同模式和不同数据的正常化，而且数据源能够随意轻松的添加。

Kibana是一个开源的分析与可视化平台，可以使用各种不同的图表、表格、地图等kibana能够很轻易地展示高级数据分析与可视化。

Apache kafka是消息中间件的一种，是一种分布式的，基于发布/订阅的消息系统。能实现一个为处理实时数据提供一个统一、高吞吐、低延迟的平台，且拥有分布式的，可划分的，冗余备份的持久性的日志服务等特点。

`Filebeta README file`
    # Welcome to Filebeat 6.6.1
Filebeat sends log files to Logstash or directly to Elasticsearch.
## Getting Started
To get started with Filebeat, you need to set up Elasticsearch on
your localhost first. After that, start Filebeat with:
     ./filebeat -c filebeat.yml -e
This will start Filebeat and send the data to your Elasticsearch
instance. To load the dashboards for Filebeat into Kibana, run:
    ./filebeat setup -e
For further steps visit the
[Getting started](https://www.elastic.co/guide/en/beats/filebeat/6.6/filebeat-getting-started.html) guide.
## Documentation
Visit [Elastic.co Docs](https://www.elastic.co/guide/en/beats/filebeat/6.6/index.html)
for the full Filebeat documentation.
## Release notes

`common cmd`
./filebeat -e -c filebeat.yml -d "publish"

`logstash`
node {
    name => 'betas'
}

input {
    betas {
        host => "xxx"
        port => "5044"
    }
}

filter {
}

output {
    elasticsearch {
        hosts => ["xxx:9200"]
    }
}