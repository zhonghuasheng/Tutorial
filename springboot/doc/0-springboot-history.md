`特殊说明` 本笔记是我在阅读技术文章时收藏的，原文链接在这里https://www.cnblogs.com/ityouknow/p/9175980.html，如有侵权敬请告知移除

### 思考
* Spring Boot诞生的背景时什么？
* Spring Boot带来了什么样的开发体验

### Spring History
说起Spring Boot 我们不得不先了解一下 Spring 这个企业，不仅因为 Spring Boot 来源于 Spirng 大家族，而且 Spring Boot 的诞生和 Sping 框架的发展息息相关。

时间回到2002年，当时正是 Java EE 和 EJB 大行其道的时候，很多知名公司都是采用此技术方案进行项目开发。这时候有一个美国的小伙子认为 EJB 太过臃肿，并不是所有的项目都需要使用 EJB 这种大型框架，应该会有一种更好的方案来解决这个问题。

为了证明他的想法是正确的，于2002年10月甚至写了一本书《 Expert One-on-One J2EE 》，介绍了当时 Java 企业应用程序开发的情况，并指出了 Java EE 和 EJB 组件框架中存在的一些主要缺陷。在这本书中，他提出了一个基于普通 Java 类和依赖注入的更简单的解决方案。

在书中，他展示了如何在不使用 EJB 的情况下构建高质量，可扩展的在线座位预留系统。为了构建应用程序，他编写了超过 30,000 行的基础结构代码，项目中的根包命名为 com.interface21(21代表21世纪)，所以人们最初称这套开源框架为 interface21，也就是 Spring 的前身。

在这本书发布后，一对一的 J2EE 设计和开发一炮而红。这本书免费提供的大部分基础架构代码都是高度可重用的。 2003 年 Rod Johnson 和同伴在此框架的基础上开发了一个全新的框架命名为 Spring ,据 Rod Johnson 介绍 Spring 是传统 J2EE 新的开始。随后 Spring 发展进入快车道。

* 2004 年 03 月，1.0 版发布。
* 2006 年 10 月，2.0 版发布。
* 2007 年 11 月更名为 SpringSource，同时发布了 Spring 2.5。
* 2009 年 12 月，Spring 3.0 发布。
* 2013 年 12 月，Pivotal 宣布发布 Spring 框架 4.0。
* 2017 年 09 月，Spring 5.0 发布。

随着使用 Spring 进行开发的个人和企业越来越多，Spring 也慢慢从一个单一简洁的小框架变成一个大而全的开源软件，Spring 的边界不断的进行扩充，到了后来 Spring 几乎可以做任何事情了，市面上主流的开源软件、中间件都有 Spring 对应组件支持，人们在享用 Spring 的这种便利之后，也遇到了一些问题。

Spring 每集成一个开源软件，就需要增加一些基础配置，慢慢的随着人们开发的项目越来越庞大，往往需要集成很多开源软件，因此后期使用 Spirng 开发大型项目需要引入很多配置文件，太多的配置非常难以理解，并容易配置出错，到了后来人们甚至称 Spring 为配置地狱。

Spring 似乎也意识到了这些问题，急需有这么一套软件可以解决这些问题，这个时候微服务的概念也慢慢兴起，快速开发微小独立的应用变得更为急迫，Spring 刚好处在这么一个交叉点上，于 2013 年初开始的 Spring Boot 项目的研发，2014年4月，Spring Boot 1.0.0 发布。

Spring Boot 诞生之初，就受到开源社区的持续关注，陆续有一些个人和企业尝试着使用了 Spring Boot，并迅速喜欢上了这款开源软件。直到2016年，在国内 Spring Boot 才被正真使用了起来，期间很多研究 Spring Boot 的开发者在网上写了大量关于 Spring Boot 的文章，同时有一些公司在企业内部进行了小规模的使用，并将使用经验分享了出来。从2016年到2018年，使用 Spring Boot 的企业和个人开发者越来越多，我们从 Spring Boot 关键字的百度指数就可以看出。

当然 Spring Boot 不是为了取代 Spring ,Spring Boot 基于 Spring 开发，是为了让人们更容易的使用 Spring。看到 Spring Boot 的市场反应，Spring 官方也非常重视 Spring Boot 的后续发展，已经将 Spring Boot 作为公司最顶级的项目来推广，放到了官网上第一的位置，因此后续 Spring Boot 的持续发展也被看好。

### Spring Boot
#### `Spring Boot介绍`
Spring Boot 是由 Pivotal 团队提供的全新框架，其设计目的是用来简化新 Spring 应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是 Spring Boot 其实不是什么新的框架，它默认配置了很多框架的使用方式，就像 maven 整合了所有的 jar 包，Spring Boot 整合了所有的框架（不知道这样比喻是否合适）。

Spring Boot 简化了基于 Spring 的应用开发，通过少量的代码就能创建一个独立的、产品级别的 Spring 应用。 Spring Boot 为 Spring 平台及第三方库提供开箱即用的设置，这样你就可以有条不紊地开始。Spring Boot 的核心思想就是约定大于配置，多数 Spring Boot 应用只需要很少的 Spring 配置。采用 Spring Boot 可以大大的简化你的开发模式，所有你想集成的常用框架，它都有对应的组件支持。

#### `Spring Boot特性`
* 使用 Spring 项目引导页面可以在几秒构建一个项目
* 方便对外输出各种形式的服务，如 REST API、WebSocket、Web、Streaming、Tasks
* 非常简洁的安全策略集成
* 支持关系数据库和非关系数据库
* 支持运行期内嵌容器，如 Tomcat、Jetty
* 强大的开发包，支持热启动
* 自动管理依赖
* 自带应用监控
* 支持各种 IED，如 IntelliJ IDEA 、NetBeans

#### `Spring Boot的优势`
* `让配置变得更简单`

Spring Boot 让配置变简单，说到这里我们就需要了解一下 Spring Boot 的核心思想：约定优于配置。那么什么是约定优于配置呢？

约定优于配置（convention over configuration），也称作按约定编程，是一种软件设计范式，旨在减少软件开发人员需做决定的数量，获得简单的好处，而又不失灵活性。

本质是说，开发人员仅需规定应用中不符约定的部分。例如，如果模型中有个名为 User 的类，那么数据库中对应的表就会默认命名为 user。只有在偏离这一约定时，例如将该表命名为”user_info”，才需写有关这个名字的配置。

* `让开发变得更简单`

Spring Boot 对开发效率的提升是全方位的，我们可以简单做一下对比：

在没有使用 Spring Boot 之前我们开发一个 web 项目需要做哪些工作：

    * 1）配置 web.xml，加载 Spring 和 Spring mvc
    * 2）配置数据库连接、配置 Spring 事务
    * 3）配置加载配置文件的读取，开启注解
    * 4）配置日志文件
    *    …
    * n) 配置完成之后部署 tomcat 调试
可能你还需要考虑各个版本的兼容性，jar 包冲突的各种可行性。

那么使用 Spring Boot 之后我们需要开发一个 web 项目需要哪些操作呢？

    * 1）登录网址 http://start.spring.io/ 选择对应的组件直接下载
    * 2）导入项目，直接开发
上面的 N 步和下面的2步形成巨大的反差，这仅仅只是在开发环境搭建的这个方面。

* `让测试变得更简单`

Spring Boot 对测试的支持不可谓不强大，Spring Boot 内置了7种强大的测试框架：

    * JUnit： 一个 Java 语言的单元测试框架
    * Spring Test & Spring Boot Test：为 Spring Boot 应用提供集成测试和工具支持
    * AssertJ：支持流式断言的 Java 测试框架
    * Hamcrest：一个匹配器库
    * Mockito：一个 java mock 框架
    * JSONassert：一个针对 JSON 的断言库
    * JsonPath：JSON XPath 库
我们只需要在项目中引入spring-boot-start-test依赖包，就可以对数据库、Mock、 Web 等各种情况进行测试。

Spring Boot Test 中包含了我们需要使用的各种测试场景，满足我们日常项目的测试需求。

* `让部署变得更简单`

说起 Spring Boot 让部署变简单，就不得不说 Spring Boot 内嵌容器。内嵌容器不只让部署变得简单，其实在开发调试阶段也会带来非常大的便利性，对比以往开发 Web 项目时配置 Tomcat 的繁琐，会让大家使用 Spring Boot 内嵌容器开发时有更深的感触。使用 Spring Boot 开发 Web 项目，让我们不需要关心容器的环境问题，专心写业务代码即可。

Jenkins 是目前持续构建领域使用最广泛的工具之一，Jenkins 是一个独立的开源自动化服务器，可用于自动化各种任务，如构建，测试和部署软件。Jenkins 可以通过本机系统包 Docker 安装，甚至可以通过安装 Java Runtime Environment 的任何机器独立运行。

说直白一点 Jenkins 就是专门来负责如何将代码变成可执行的程序包，将它部署到目标服务器中，并对其运营状态（日志）进行监控的软件。自动化、性能、打包、部署、发布、发布结果自动化验证、接口测试、单元测试等等关于我们打包测试部署的方方面面 Jenkins 都可以很友好的支持。

使用 Jenkins 部署 Spring Boot 项目非常简单，大家想继续了解可以参考我的文章：使用Jenkins部署Spring Boot，只需要前期做一些简单的配置，当我们需要发布项目时只需要点击项目对应的发布按钮，就可以将项目从版本库中拉取、打包、发布到目标服务器中，大大简化了运维后期的部署工作。

虚拟化技术的发展给我们带来了更多的可能性，我们可以利用容器化技术，将 Spring Boot 项目做成镜像，根据容器集群的策略来实现弹性扩容、动态部署等。所以 Spring Boot + Docker + Jenkins 会将 Spring Boot 项目的部署做得更简单化、智能化。

* `让监控变得更简单`

可以说 Spring Boot 就是一款自带监控的开源软件，在设计之初就考虑到应用的监控问题，专门提供了一款监控组件来完成这个工作，这个组件就是
Spring Boot Actuator 。

Spring Boot Actuator 是 Spring Boot 提供的对应用系统监控的集成功能，可以查看应用配置的详细信息，例如自动化配置信息、创建的 Spring beans 以及一些环境属性等。

当然 Spring Boot Actuator 虽然可以监控一个 Spring Boot 应用的健康情况，实际上现在的系统都是需要很多的服务相互配合来完成工作，如何通过一个监控软件来监控所以的 Spring Boot 项目将变得比较紧迫。

在开源界也有人意识到了这个问题，并且基于 Spring boot actuator 做出了一款强大的监控软件，这个软件就是 Spring Boot admin 。

Spring Boot Admin 是一个管理和监控 Spring Boot 应用程序的开源软件。每个应用都认为是一个客户端，通过 HTTP 或者使用 Eureka 注册到 admin server 中进行展示，Spring Boot Admin UI 部分使用 AngularJs 将数据展示在前端。

Spring Boot Admin 是一个针对 spring-boot 的 actuator 接口进行UI美化封装的监控工具。他可以：在列表中浏览所有被监控 spring-boot 项目的基本信息，详细的 Health 信息、内存信息、JVM 信息、垃圾回收信息、各种配置信息（比如数据源、缓存列表和命中率）等，还可以直接修改logger的level。

使用 Spring Boot Admin 不仅可以监控 Spring Boot 项目，还可以监控 Spring Cloud 项目。

简单、直观、易用是它的特点，针对一些特殊情况还可以提供报警服务。所以说使用 Spring Boot Actuator 解决了单个 Spring Boot 的监控问题，使用 Spring Boot Admin 就是解决了整个集群监控的问题。

### Spring, Spring Boot， Spring Cloud的关系
Spring 最初最核心的两大核心功能 Spring Ioc 和 Spring Aop 成就了 Spring，Spring 在这两大核心的功能上不断的发展，才有了 Spring 事务、Spirng Mvc 等一系列伟大的产品，最终成就了 Spring 帝国，到了后期 Spring 几乎可以解决企业开发中的所有问题。

Spring Boot 是在强大的 Spring 帝国生态基础上面发展而来，发明 Spring Boot 不是为了取代 Spring ,是为了让人们更容易的使用 Spring 。所以说没有 Spring 强大的功能和生态，就不会有后期的 Spring Boot 火热, Spring Boot 使用约定优于配置的理念，重新重构了 Spring 的使用，让 Spring 后续的发展更有生命力。

Spring Cloud 是一系列框架的有序集合。它利用 Spring Boot 的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用 Spring Boot 的开发风格做到一键启动和部署。

Spring 并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过 Spring Boot 风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

根据上面的说明我们可以看出来，Spring Cloud 是为了解决微服务架构中服务治理而提供的一系列功能的开发框架，并且 Spring Cloud 是完全基于 Spring Boot 而开发，Spring Cloud 利用 Spring Boot 特性整合了开源行业中优秀的组件，整体对外提供了一套在微服务架构中服务治理的解决方案。

综上我们可以这样来理解，正是由于 Spring Ioc 和 Spring Aop 两个强大的功能才有了 Spring ，Spring 生态不断的发展才有了 Spring Boot ，使用 Spring Boot 让 Spring 更易用更有生命力，Spring Cloud 是基于 Spring Boot 开发的一套微服务架构下的服务治理方案。

用一组不太合理的包含关系来表达它们之间的关系。

    * Spring ioc/aop > Spring > Spring Boot > Spring Cloud