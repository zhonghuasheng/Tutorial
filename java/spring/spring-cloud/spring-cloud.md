### 微服务本身的复杂度带来的问题
* 分散在各个服务器上的日志如何处理？
* 如果业务出现了错误和异常，如何定位和处理？
* 如何跟踪业务的处理顺序和结果？

### 微服务时代 运维监控是难点

启动一个服务注册中心，只需要一个注解@EnableEurekaServer，这个注解需要在springboot工程的启动application类上加：
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( EurekaServerApplication.class, args );
    }
}

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( EurekaServerApplication.class, args );
    }
}

当client向server注册时，它会提供一些元数据，例如主机和端口，URL，主页等。Eureka server 从每个client实例接收心跳消息。 如果心跳超时，则通常将该实例从注册server中删除。

在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于http restful的。Spring cloud有两种服务调用方式，一种是ribbon+restTemplate，另一种是feign。在这一篇文章首先讲解下基于ribbon+rest。

Ribbon is a client side load balancer which gives you a lot of control over the behaviour of HTTP and TCP clients. Feign already uses Ribbon, so if you are using @FeignClient then this section also applies.
ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。Feign默认集成了ribbon。

### 笔记
* SpringCloud是需要SpringBoot做基础的，因此在选择好SpringCloud的版本之后，要查看该版本依赖的是哪个SpringBoot版本，不能随便选择SpringBoot的版本，否则容易遇到意想不到的错误
* SpringCloud Feign中，如果A服务通过Feign调用B服务，需要传递多个参数时，需要将参数组装成一个DTO来传递，否则会报参数找不到的错误
