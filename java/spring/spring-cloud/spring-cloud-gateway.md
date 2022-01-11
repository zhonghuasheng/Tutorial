## 目录
* [SpringCloud Gateway为什么会出现](#简介)
* [能用来干什么](#功能特性)

## 简介
SpringCloud Gateway是基于spring-webflux，采用netty+reactor（Spring WebFlux 是一个异步非阻塞式的 Web 框架，它能够充分利用多核 CPU 的硬件资源去处理大量的并发请求，WebFlux 内部使用的是响应式编程（Reactive Programming），以 Reactor 库为基础, 基于异步和事件驱动，可以让我们在不扩充硬件资源的前提下，提升系统的吞吐量和伸缩性。Spring MVC 构建于 Servlet API 之上，使用的是同步阻塞式 I/O 模型，什么是同步阻塞式 I/O 模型呢？就是说，每一个请求对应一个线程去处理。）

## 功能特性
1. 路由：能够在任意请求属性上匹配路由
2. 过滤：
3. 集成Hystrix熔断器
4. 集成Spring Cloud DiscoveryClient，能够从注册中心获取服务注册列表，实现自动路由
5. 集成了Ribbon，实现了负载均衡
6. 能够限制请求速率

Route(路由)：路由是构建网关的基本模块，它由ID，目标URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由，目标URI会被访问。
Predicate(断言)：这是一个java 8的Predicate，可以使用它来匹配来自HTTP请求的任何内容，可以理解为当满足这种条件后才会被转发
Filter(过滤器)：
