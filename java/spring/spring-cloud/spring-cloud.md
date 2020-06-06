### 微服务本身的复杂度带来的问题
* 分散在各个服务器上的日志如何处理？
* 如果业务出现了错误和异常，如何定位和处理？
* 如何跟踪业务的处理顺序和结果？
* 微服务时代 运维监控是难点

* [Spring Cloud注册中心 - Eureka](spring-cloud-eureka.md)
* [Spring Cloud熔断器 - Hystrix](spring-cloud-hystrix.md)

### 笔记
* SpringCloud是需要SpringBoot做基础的，因此在选择好SpringCloud的版本之后，要查看该版本依赖的是哪个SpringBoot版本，不能随便选择SpringBoot的版本，否则容易遇到意想不到的错误
* SpringCloud Feign中，如果A服务通过Feign调用B服务，需要传递多个参数时，需要将参数组装成一个DTO来传递，否则会报参数找不到的错误

### 引用
* SpringCloud系列 https://www.cnblogs.com/h--d/tag/SpringCloud/