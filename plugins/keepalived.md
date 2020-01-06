# 目录
  * [说明](#说明)
  * [Keepalive简介](#Keepalive简介)
  * [Keepalived安装](#Keepalived安装)
  * [Keepalived配置](#Keepalived配置)
  * [注意事项](#注意事项)
  * [引用](#引用)


## 说明
  * 官网：https://keepalived.org/index.html
  * 目前只支持Linux系统

## Keepalived简介
`Keepalived is a routing software written in C. The main goal of this project is to provide simple and robust facilities for loadbalancing and high-availability to Linux system and Linux based infrastructures. Loadbalancing framework relies on well-known and widely used Linux Virtual Server (IPVS) kernel module providing Layer4 loadbalancing. Keepalived implements a set of checkers to dynamically and adaptively maintain and manage loadbalanced server pool according their health. On the other hand high-availability is achieved by VRRP protocol. VRRP is a fundamental brick for router failover. In addition, Keepalived implements a set of hooks to the VRRP finite state machine providing low-level and high-speed protocol interactions. In order to offer fastest network failure detection, Keepalived implements BFD protocol. VRRP state transition can take into account BFD hint to drive fast state transition. Keepalived frameworks can be used independently or all together to provide resilient infrastructures.`

1. keepalived介绍
Keepalived是Linux下一个轻量级的高可用解决方案，它与HeartBeat、RoseHA实现的功能类似，都可以实现服务或者网络的高可用，但是又有差别：HeartBeat是一个专业的、功能完善的高可用软件，它提供了HA软件所需的基本功能，比如心跳检测和资源接管，监测集群中的系统服务，在群集节点间转移共享IP地址的所有者等，HeartBeat功能强大，但是部署和使用相对比较麻烦；与HeartBeat相比，Keepalived主要是通过虚拟路由冗余来实现高可用功能，虽然它没有HeartBeat功能强大，但Keepalived部署和使用非常简单，所有配置只需一个配置文件即可完成。

keepalived主要有三个模块，分别是core、check和vrrp。core模块为keepalived的核心，负责主进程的启动、维护以及全局配置文件的加载和解析。check负责健康检查，包括常见的各种检查方式。vrrp模块是来实现VRRP协议的。

keepalived实现的功能主要有三个：
* 将IP地址飘移到其他节点上，
* 在另一个主机上生成ipvs规则
* 健康状况检查
keepalived通过软件的方式在其内部模拟实现VRRP协议，然后借助于VRRP协议实现IP地址漂移。

2. keepalived用来做什么
Keepalived是基于VRRP协议的高级应用，作用于网络层、传输层和应用层交换机制的处理高可用的软件。主要用作RealServer的健康状态检查以及LoadBalance主机和BackUP主机之间failover的实现。

Keepalived起初是为LVS设计的，专门用来监控集群系统中各个服务节点的状态。它根据layer3, 4 & 5交换机制检测每个服务节点的状态，如果某个服务节点出现异常，或工作出现故障，Keepalived将检测到，并将出现故障的服务节点从集群系统中剔除，而在故障节点恢复正常后，Keepalived又可以自动将此服务节点重新加入到服务器集群中，这些工作全部自动完成，不需要人工干涉，需要人工完成的只是修复出现故障的服务节点。

Keepalived后来又加入了VRRP的功能，VRRP是Virtual Router Redundancy Protocol（虚拟路由器冗余协议）的缩写，它出现的目的是为了解决静态路由出现的单点故障问题，通过VRRP可以实现网络不间断地、稳定地运行。因此，Keepalived一方面具有服务器状态检测和故障隔离功能，另一方面也具有HA cluster功能。

keepalived可以实现轻量级的高可用，一般用于前端高可用，且不需要共享存储，一般常用于两个节点的高可用（常见的前端高可用组合有LVS+Keepalived、Nginx+Keepalived、HAproxy+Keepalived）。

3. Keepalived工作原理
Keepalived作为一个高性能集群软件，它还能实现对集群中服务器运行状态的监控及故障隔离。接下来介绍下Keepalived对服务器运行状态监控和检测的工作原理。 Keepalived工作在TCP/IP参考模型的第三、第四和第五层，也就是网络层、传输层和应用层。根据TCP/IP参考模型各层所能实现的功能，Keepalived运行机制如下：

   * 在网络层，运行着四个重要的协议：互连网协议IP、互连网控制报文协议ICMP、地址转换协议ARP以及反向地址转换协议RARP。Keepalived在网络层采用的最常见的工作方式是通过ICMP协议向服务器集群中的每个节点发送一个ICMP的数据包（类似于ping实现的功能），如果某个节点没有返回响应数据包，那么就认为此节点发生了故障，Keepalived将报告此节点失效，并从服务器集群中剔除故障节点。
   * 在传输层，提供了两个主要的协议：传输控制协议TCP和用户数据协议UDP。传输控制协议TCP可以提供可靠的数据传输服务，IP地址和端口，代表一个TCP连接的一个连接端。要获得TCP服务,须在发送机的一个端口上和接收机的一个端口上建立连接，而Keepalived在传输层就是利用TCP协议的端口连接和扫描技术来判断集群节点是否正常的。比如，对于常见的Web服务默认的80端口、SSH服务默认的22端口等，Keepalived一旦在传输层探测到这些端口没有响应数据返回，就认为这些端口发生异常，然后强制将此端口对应的节点从服务器集群组中移除。
   * 在应用层，可以运行FTP、TELNET、SMTP、DNS等各种不同类型的高层协议，Keepalived的运行方式也更加全面化和复杂化，用户可以通过自定义Keepalived的工作方式，例如用户可以通过编写程序来运行Keepalived，而Keepalived将根据用户的设定检测各种程序或服务是否允许正常，如果Keepalived的检测结果与用户设定不一致时，Keepalived将把对应的服务从服务器中移除。

`虚拟IP原理`
何为虚拟IP，就是一个未分配给真实主机的IP，也就是说对外提供服务的主机除了有一个真实IP外还有一个虚拟IP，使用这两个IP中的任意一个都可以连接到这台主机，当服务器发生故障无法对外提供服务时，动态将这个虚拟IP切换到备用主机。

虚拟IP实现原理主要是靠TCP/IP的ARP协议。因为IP地址只是一个逻辑地址，在以太网中MAC地址才是真正用来进行数据传输的物理地址，每台主机中都有一个ARP高速缓存，存储同一个网络内的IP地址与MAC地址的对应关系，以太网中的主机发送数据时会先从这个缓存中查询目标IP对应的MAC地址，会向这个MAC地址发送数据。操作系统会自动维护这个缓存。这就是整个实现的关键。

Keepalived软件主要是通过VRRP协议实现高可用功能的。VRRP是Virtual Router RedundancyProtocol(虚拟路由器冗余协议）的缩写，·`VRRP出现的目的就是为了解决静态路由单点故障问题的，它能够保证当个别节点宕机时，整个网络可以不间断地运行`。当LVS进行主备切换的时候，对外提供服务的IP是如何做到切换的呢？这就依赖于keepalived 所应用的vrrp协议，即Virtual Reduntant  Routing Protocol，虚拟冗余路由协议。简单来讲，此协议是将IP设置在虚拟接口之上，根据一定的规则实现IP在物理主机上流动，即哪台主机可以占有该IP。
Keepalived使用虚拟IP实现双机热备解决方案。

## Keepalived安装
* yum直接安装：yum install keepalived
* 编译安装：https://github.com/acassen/keepalived

## Keepalived配置
配置文件keepalived.conf

可配置的选项：
* 工作模式： 抢占式 | 非抢占式
* 通知提醒 notification_email
* 判定规则： 连续几次失败认为服务不可用；连续几次成功认为服务可用
* notify_master | notify_backup | notify_fault时可以执行脚本


Keepalived的所有配置都在一个配置文件里面，主要分为三类：
* 全局配置
* VRRPD配置
* LVS 配置

  * 全局配置是对整个 Keepalived 生效的配置，一个典型的配置如下：
  ```conf
    global_defs {
    notification_email {         #设置 keepalived 在发生事件（比如切换）的时候，需要发送到的email地址，可以设置多个，每行一个。
        acassen@firewall.loc
        failover@firewall.loc
        sysadmin@firewall.loc
    }
    notification_email_from Alexandre.Cassen@firewall.loc    #设置通知邮件发送来自于哪里，如果本地开启了sendmail的话，可以使用上面的默认值。
    smtp_server 192.168.200.1    #指定发送邮件的smtp服务器。
    smtp_connect_timeout 30      #设置smtp连接超时时间，单位为秒。
    router_id LVS_DEVEL          #是运行keepalived的一个表示，多个集群设置不同。
    }
  ```

  * VRRPD配置：VRRPD 的配置是 Keepalived 比较重要的配置，主要分为两个部分 VRRP 同步组和 VRRP实例，也就是想要使用 VRRP 进行高可用选举，那么就一定需要配置一个VRRP实例，在实例中来定义 VIP、服务器角色等。
    * VRRP Sync Groups:
    不使用Sync Group的话，如果机器（或者说router）有两个网段，一个内网一个外网，每个网段开启一个VRRP实例，假设VRRP配置为检查内网，那么当外网出现问题时，VRRPD认为自己仍然健康，那么不会发生Master和Backup的切换，从而导致了问题。Sync group就是为了解决这个问题，可以把两个实例都放进一个Sync Group，这样的话，group里面任何一个实例出现问题都会发生切换。
    ```conf
    vrrp_sync_group VG_1{ #监控多个网段的实例
    group {
        VI_1 #实例名
        VI_2
        ......
    }
    notify_master /path/xx.sh     #指定当切换到master时，执行的脚本
    netify_backup /path/xx.sh     #指定当切换到backup时，执行的脚本
    notify_fault "path/xx.sh VG_1"   #故障时执行的脚本
    notify /path/xx.sh
    smtp_alert 　　#使用global_defs中提供的邮件地址和smtp服务器发送邮件通知
    }
    ```
    * VRRP实例（instance）配置:
    VRRP实例就表示在上面开启了VRRP协议，这个实例说明了VRRP的一些特征，比如主从，VRID等，可以在每个interface上开启一个实例。
    ```conf
    vrrp_instance VI_1 {
        state MASTER         #指定实例初始状态，实际的MASTER和BACKUP是选举决定的。
        interface eth0       #指定实例绑定的网卡
        virtual_router_id 51 #设置VRID标记，多个集群不能重复(0..255)
        priority 100         #设置优先级，优先级高的会被竞选为Master，Master要高于BACKUP至少50
        advert_int 1         #检查的时间间隔，默认1s
        nopreempt            #设置为不抢占，说明：这个配置只能在BACKUP主机上面设置
        preempt_delay        #抢占延迟，默认5分钟
        debug                #debug级别
        authentication {     #设置认证
            auth_type PASS    #认证方式，支持PASS和AH，官方建议使用PASS
            auth_pass 1111    #认证的密码
        }
        virtual_ipaddress {     #设置VIP，可以设置多个，用于切换时的地址绑定。格式：#<IPADDR>/<MASK> brd <IPADDR> dev <STRING> scope <SCOPT> label <LABE
            192.168.200.16/24 dev eth0 label eth0:1
            192.168.200.17/24 dev eth1 label eth1:1
            192.168.200.18
        }
    }
    ``
    * LVS 配置:
    虚拟服务器virtual_server定义块 ，虚拟服务器定义是keepalived框架最重要的项目了，是keepalived.conf必不可少的部分。 该部分是用来管理LVS的，是实现keepalive和LVS相结合的模块。ipvsadm命令可以实现的管理在这里都可以通过参数配置实现，注意：real_server是被包含在viyual_server模块中的，是子模块。

## 注意事项
* keepalived.conf文件的检测参数要相同
* 确保心跳监测服务器之间能访问

# 引用
* [Keepalived配置说明](https://keepalived.org/manpage.html)
* [CentOS安装配置keepalived实现高可用](https://blog.csdn.net/wengengeng/article/details/80837079)
* [Keepalived 配置文件解释](https://www.cnblogs.com/yanjieli/p/10687701.html)
* [Linux高可用之Keepalived](https://www.jianshu.com/p/b050d8861fc1)
* [使用keepalived搭建主备切换环境](https://www.cnblogs.com/freebird92/p/6606379.html)