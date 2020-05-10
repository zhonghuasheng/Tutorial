## 学习计划
* Eureka的自我保护机制
* eureka源码--服务的注册、服务续约、服务发现、服务下线、服务剔除、定时任务以及自定义注册中心的思路 https://www.cnblogs.com/yangxiaohui227/p/12604172.html
微服务注册后，在注册中心的注册表结构是一个map: ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry，假如一个order服务部署了三台机器，那么Map的第一个key为服务名称，第二个map的key是实例编号（instance-id）, InstanceInfo该对象封装了服务的主要信息，例如ip 端口 服务名称 服务的编号等

## Eureka的自我保护机制
总结：在网络正常的情况下，如果EurekaServer在一定时间（默认90s）内没有收到某个服务实例的心跳，EurekaServer会注销该实例。OK，这个前提条件是网络正常，那要是网络不正常呢？出现网络不正常的情况有两种，EurekaServer的网络不正常或者某些EurekaClient的网络不正常，先说说EurekaServer网络不正常的情况，如果EurekaServer网络不正常，那么它应该收不到很多Client的心跳；如果说是某些Client的网络不正常，那么它应该可以收到其他的Client的心跳，因此就需要一个阀值来评估要不要进入自我保护机制。Eureka规定在15min内没有收到心跳的服务的实例超过了85%就认为需要进入自我保护。我们先说说进入自我保护之后会发生什么。EurekaServer进入自我保护后，可以接收新的服务注册和查询，也不把那些没有收到心跳的服务移除，在网络稳定之前EurekaServer的这份服务信息是不会同步到其他服务的，直到网络稳定后才同步。在EurekaServer进入自我保护期内，如果原本正常的服务下线，会导致服务的调用失败，这个在API的设计上需要注意，要引入熔断和快速失败的机制。我们再说说是Client这边的网络问题，超过85%的服务在15min内没有心跳，我们基本认为这个系统挂掉了，需要立马排查原因重新上线。最后我们再来说说自我保护啥时候能被接触

自我保护模式被解除的条件是：在 1 分钟后，Renews (last min) < Renews threshold
这两个参数的意思：
    * Renews threshold：Eureka Server 期望每分钟收到客户端实例续约的总数。
    * Renews (last min)：Eureka Server 最后 1 分钟收到客户端实例续约的总数。

进入自我保护的标志：
如果说 Eureka Server 的首页看到一下这段提示，则说明Eureka 进入了保护模式。
EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.

### 自我保护背景
首先对Eureka注册中心需要了解的是Eureka各个节点都是平等的，没有ZK中角色的概念， 即使N-1个节点挂掉也不会影响其他节点的正常运行。

默认情况下，如果Eureka Server在一定时间内（默认90秒）没有接收到某个微服务实例的心跳，Eureka Server将会移除该实例。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，而微服务本身是正常运行的，此时不应该移除这个微服务，所以引入了自我保护机制。

### 自我保护机制
自我保护模式正是一种`针对网络异常波动的安全保护措施`，使用自我保护模式能使Eureka集群更加的健壮、稳定的运行。

  > Eureka Server怎么知道是网络异常呢？

自我保护机制的工作机制是如果在15分钟内超过85%的客户端节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，

Eureka Server自动进入自我保护机制，此时会出现以下几种情况：
1. Eureka Server不再从注册列表中移除因为长时间没收到心跳而应该过期的服务。
2. Eureka Server仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上，保证当前节点依然可用。
3. 当网络稳定时，当前Eureka Server新的注册信息会被同步到其它节点中。

因此Eureka Server可以很好的应对因网络故障导致部分节点失联的情况，而不会像ZK那样如果有一半不可用的情况会导致整个集群不可用而变成瘫痪。

自我保护开关

Eureka自我保护机制，通过配置eureka.server.enable-self-preservation来true打开/false禁用自我保护机制，默认打开状态，建议生产环境打开此配置。
开发环境配置

开发环境中如果要实现服务失效能自动移除，只需要修改以下配置。
