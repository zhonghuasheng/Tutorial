> 本篇主要介绍Spring Boot中的基础知识

### `Spring Boot文件目录介绍`
* /src
    * main
        * java: 目录下放置所有的Java文件
        * resource: 存放所有的资源文件，包括静态资源文件、配置文件、页面文件等
            * static: 存放各类静态资源
            * application.properties：配置文件，SpringBoot默认支持.properties和.yml这两种格式的配置文件
            * templates: 用于存放各类模板文件，如thymeleaf
    * test
        * java: 放置单元测试类的Java代码
* /target：放置编译后的.class文件、配置文件等

Spring Boot将很多配置文件进行了统一管理，且配置了默认值。Spring Boot会自动在/src/main/resources目录下寻找application.properties或者application.yml配置文件。.properties配置文件的优先级高于.yml。

### `Spring Boot中的起步依赖`

### `Spring Boot中相关注解`

* @SpringBootApplication: 是一个组合注解，包含@EnableAutoConfiguration，@ComponentScan和@SpringBootConfiguration三个注解，是项目启动注解。

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.atoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class, args);
    }
}

```
