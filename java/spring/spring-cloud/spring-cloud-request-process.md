## 目录

## SpringCloud调用接口过程
Feign -> Hystrix -> Ribbon -> Http Client(apache http components OR Okhttp)
1. `接口请求调用`：当调用被@FeignClient注解修饰的接口时，在框架内部，将请求转化成Feign的请求实例`feign.Request`，交由Feign框架处理
2. `Feign`：转化请求，Feign是一个http请求调用的轻量级框架，可以以Java接口注解的方式调用Http请求，封装了Http调用流程
3. `Hystrix`：熔断处理机制，Feign的调用关系，会被Hystrix代理拦截，对每一个Feign调用请求，Hystrix都会将其包装成HystrixCommand，参与Hystrix的流控和熔断规则。如果请求判断需要熔断，则Hystrix直接熔断，抛出异常或者使用FallbackFactory返回熔断Fallback结果；如果通过，则将调用请求传递给Ribbon组件
4. `Ribbon`：服务地址选择，当请求传递到Ribbon之后，Ribbon会根据自身维护的服务列表，根据服务的服务质量，如平均响应时间、Load等，结合特定的规则，从列表中挑选合适的服务实例，选择好机器之后，然后将机器实例的信息请求传递给HttpClient客户端，HttpClient客户端来执行真正的Http接口调用
5. `Http Client`：Http客户端，真正执行Http调用根据上层Ribbon传递过来的请求，已经指定了服务地址，则HttpClient开始执行真正的Http请求