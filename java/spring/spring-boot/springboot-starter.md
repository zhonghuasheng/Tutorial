### 常见问题
1. SpringBoot Starter解决了什么问题？
2. 你讲一下SpringBoot Starter的启动原理
3. 如何自定义一个SpringBoot Starter
4. 真实项目中SpringBoot Starter的使用案例

#### SpringBoot Starter解决了什么问题？
1. SpringBoot的Starter解决了Spring MVC时代被xml配置文件搞的晕头转向的问题。举个例子，日常开发web项目，不适用SpringBoot时需要引入spring-web, spring-webmvc, spring-aop等等，而SpringBoot只需要引入spring-boot-starter-web。
2. 约定大于配置。在应用配置文件中加入相应的配置，配置都是组件约定好的。
3. SpringBoot Starter中可以声明很多通用的方法和bean。

#### SpringBoot Starter的启动原理
1. SpringBoot默认扫描启动类所在的包下的主类和子类的所有组件，但并没有包括依赖包中的类。那么starter中的bean是如何被发现和加载的呢？
2. @SpringBootApplication这个复合组件中有个@EnableAutoConfiguration的注解，借助@Import的支持，能够收集和注册依赖包中相关bean的定义。通过一层一层翻源码发现，可以发现SpringFactoriesLoader.loadFactoryNames方法调用了loadSpringFactories，从所有jar包中读取META-INF/spring.factories文件信息。spring.factories中配置的是被@Configuration注解修饰的配置类。（SPI技术）这样启动之后starter中的bean也被注入到了容器中。

#### 自定义SpringBoot Starter的步骤
1. 新建一个项目，命名格式采用xxx-spring-boot-starter。spring规定非官方的starter以xxx-spring-boot-starter，官方的以spring-boot-starter-xxx命名。
2. 添加starter中需要的依赖
3. 添加必要的配置类，可以写一些通用的模板类，然后在resource/下建META-INF/spring.factories文件，把配置类配到里面
4. 执行mvn clean install把jar包打到本地maven仓库，然后就可以使用了

#### SpringBoot Starter使用案例


### 参考引用
* https://www.cnblogs.com/hello-shf/p/10864977.html