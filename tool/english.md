* FAQ=Frequently Asked Question 常问问题
* Mutual Exclusion=互斥
* Visibility=可见性
* Monitoring=监控
* Feel free to let me know if you have any questions.
* scalable:可扩展的
* actor:演员，参与者，行动者
* financial:金融的，财务的，财政的，有钱的
* abbreviation:省略，缩写
* contribution:贡献
* prerequisites:前提条件
* partition:分片
* POC: Proof of concept 是对某些想法的一个较短而不完整的实现，以证明其可行性，示范其原理
* IaaS: 基础设施服务， Infrastructure-as-a-Service
    * Compute, Storage, Networking
    * AWS, Google Cloud Platform, Azure, OpenStack, ZStack
* PaaS: 平台服务，Platform-as-a-Service, 也可以叫中间件
    * MySQL, Mangodb, RabbitMQ, Java, Node,js
    * CloudFoundry, OpenShift
* SaaS: 软件服务，Software-as-a-Service
    * Email, IM, Facebook, Twitter
    * Almost every application can be SaaS
* WMS: Warehouse Management System 仓库管理系统
* ROI: Return On Investment 投资回报率
* DevOps:（Development和Operations的组合词）是一组过程、方法与系统的统称，用于促进开发（应用程序/软件工程）、技术运营和质量保障（QA）部门之间的沟通、协作与整合。
* QPS = req/sec = 请求数/秒 （单个进程每秒请求服务器的成功次数）
* TPS = Transaction Per Second 每秒处理的事务量（如用户提交一个表单，数据落库就是一个事务）
* failover机制又称失效转移或故障切换，指系统中的某一项设备或服务失效而无法运行时，另一项设备或服务能自动接手原失效系统所执行的工作。
* failback机制又称自动回复，是指主系统或服务因升级而暂时不可用，现有服务需要转义到备用系统，待主系统升级后切换服务至主系统
* Oops:哎呀
* LOL：开怀大笑
* Simple Storage Service S3 简单存储服务
* Elastic Compute Cloud EC2 弹性计算云
* DOS： 拒绝服务攻击，利用合理的服务请求占用过多的服务资源，从而使合法用户无法得到服务的响应的行为，这就是DOS攻击。信息安全三要素-保密性、完整性和可用性。DOS针对的目标采用的使可用性攻击。
* DDos: Distributd Denial of Service 分布式拒绝服务，是指攻击者利用大量的“肉鸡”，对攻击目标发动大量的正常或非正常请求，耗尽目标主机资源或者网络资源，从而使被攻击的主机不能为正常的用户提供服务。
* Martin Fowler Microservices微服务之父 马丁.福勒 Thoughtworks首席科学家
* Playload: FormData和Payload是浏览器传输给接口的两种格式，这两种方式浏览器是通过Content-Type来进行区分的(了解Content-Type)，如果是 application/x-www-form-urlencoded的话，则为formdata方式，如果是application/json或multipart/form-data的话，则为 Request Payload的方式。
* delivery 交付
* collaboration 协作
* refactoring 重构
* IOC Inversion of Control  控制反转
* DI Dependency Injection 依赖注入
* AOP Aspect Oriented Programming 面向切面编程
* This approach(方式，途径) makes it easier to re-use（重用） the component somewhere else and deliver（交互） the component's intended（期望的） appearance even if the global styles are different.
* OOP Object Oriented Programming 面向对象编程
* The best way to build a habit is to start small.
* Life turns out to be better if you think from other's perspective.
* Here is a list of keywords in the Java programming language. You cannot use any of the following as identifiers in your programs. The keywords const and goto are reserved, even though they are not currently used. true, false, and null might seem like keywords, but they are actually literals; you cannot use them as identifiers in your programs.


|  |  |  |  |  |
|--|--|--|--|--|
| abstract | continue | for | new | switch |
| assert*** | default | goto* | package | synchronized |
| break | double | implements | private | this |
| byte | else | import | public | throws |
| case | enum**** | instanceof | return | transient |
| char | final | interface | static | void |
| class | finally | log | strictfp** | volatile |
| const* | float | native | super | while |

* B2C：企业与消费者之间的电子商务，类似天猫、京东是属于此类模式，企业在电子商务平台上售卖，消费者进行购买，是最常见的电子商务模式。
* B2B：企业与企业之间的电子商务，也可以说是供应方与采购方之间的电子商务，此类模式是解决了上游到中游的采购问题，降低采购成本，类似阿里巴巴的1688。
* C2C：消费者与消费者之间的电子商务，此类模式对商家的包容性更大，很多是个人，比如：淘宝、微店都是此类。
* O2O：线上到线下再到线上，线上消费，线下服务，线上核销，例如：美团、饿了么。
* How many times have you had to start over from scratch? 你有多少次从头开始？
* By and large, 总的来说 = In general
* reserve 存储
* RollingFile: rolling 周而复始的，有规律的。出自log4j中将日志文件拆分为周而复始的文件碎片。
* SEO: Search Engine Optimization搜索引擎优化
* VisiualVM is a visual tool integrating commandline JDK tools and lightweight profileing capabilities. - VisualVM是一个集成JDK命令的轻量级可视化工具 - light是光的意思，lightweight轻量级(光很轻)，heavyweight重量级
* The supreme happiness of life is the convication(信念) that we are loved. 生活中最大的幸福是坚信有人爱我们。
* Cache Coherence 缓存一致性
* failover 失效转移
* AB测试 比较AB两个哪个好的测试
* polling 轮询
* pojo Plain Ordinary Java Object 简单的Java对象，也就是普通的JavaBeans，是为了避免和EJB混淆而创建的简称。
* po Persisent Object持久化对象
* demultiplexer 信号分离器，多路分配器 reactor模式中的名词
* allocator 分配者
* Cache Hit Ratio 缓存命中率，MyBatis中二级缓存log会输出
* chunk 块，大块
* fair 合理的，公正的
* reentrant 可重入的
* AQS AbstractQueuedSynchronizer类，是Java中独占锁和共享锁的公共父类，锁的许多公共方法都是在这个类中实现
* DMA: Direct Memory Access 直接存储器访问。他的作用就是不需要经过CPU进行数据传输
* sandboxie 沙箱机制：沙箱是一种虚拟系统程序，沙箱提供的环境相对于每个运行的程序都是独立的，而且不会对现有系统产生影响。Docker就是使用了沙箱机制，相互之间不会有任何接口（类似iphone的app），更重要的是容器性能开销极低。
* VO（View Object）：视图对象，用于展示层，它的作用是把某个指定页面（或组件）的所有数据封装起来。
* DTO（Data Transfer Object）：数据传输对象，用于展示层与服务层之间的数据传输对象, Controller中接收的对象就是DTO
* DO（Domain Object）：领域对象，就是从现实世界中抽象出来的有形或无形的业务实体。
* PO（Persistent Object）：持久化对象，它跟持久层（通常是关系型数据库）的数据结构形成一一对应的映射关系，如果持久层是关系型数据库，那么，数据表中的每个字段（或若干个）就对应PO的一个（或若干个）属性。
    * 模型：下面以一个时序图建立简单模型来描述上述对象在三层架构应用中的位置
        * 用户发出请求（可能是填写表单），表单的数据在展示层被匹配为VO。
        * 展示层把VO转换为服务层对应方法所要求的DTO，传送给服务层。
        * 服务层首先根据DTO的数据构造（或重建）一个DO，调用DO的业务方法完成具体业务。
        * 服务层把DO转换为持久层对应的PO（可以使用ORM工具，也可以不用），调用持久层的持久化方法，把PO传递给它，完成持久化操作。
* Swimlane Flowcharts: 泳道图是一种反映商业流程中人与人之间关系的特殊图表，有助于分清在流程过程中各人工作范围
* Guava： 是由谷歌维护的基于Java的开源库，主要用于集合，缓存，支持原语，并发性，常见的验证等方法。
* DTS： 数据传输服务DTS（Data Transmission Service）是阿里云提供的实时数据流服务，支持RDBMS、NoSQL、OLAP等，集数据迁移/订阅/同步于一体，为您提供稳定安全的传输链路。
* 内存泄漏：memory leak，已分配的内存未释放或者无法释放，一直占着，造成系统内存的浪费，最后引发内存被耗尽，导致内存溢出
* 内存溢出：out of memory， 就是内存不够，可能程序启动的时候就不够，比如大型游戏需要的内存超出了主机的内存，或者是内存慢慢被占用完，最后导致溢出