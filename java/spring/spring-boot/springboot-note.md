### springboot各个依赖都是干什么的
* spring-boot-starter-parent: spring-boot-starter-parent是一个特殊的starter，它用来提供相关的Maven默认依赖。使用它之后，常用的包依赖可以省去version标签。通过继承spring-boot-starter-parent，默认具备了如下功能：Java版本（Java8）、源码的文件编码方式（UTF-8）、依赖管理、打包支持、动态识别资源、识别插件配置、识别不同的配置，如：application-dev.properties 和 application-dev.yml
* spring-boot-maven-plugin: 用于执行package命令是生成可执行的jar，如果不添加，执行java -jar时会报错
“springboot-helloworld-2.2.5.RELEASE.jar中没有主清单属性”




### swagger
* 配置依赖swagger2
```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifictId>springfox-swagger2</artifictId>
<dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifictId>springfox-swagger-ui</artifictId>
<dependency>
```
* 配置Swagger2所需的Docket Bean
```java
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot使用Swagger2构建api文档")
                .description("文档的详细描述")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhonghuasheng.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
```
* 使用swagger注解在controller上
```java
    @ApiOperation(value = "获取用户", notes = "根据url中的id获取用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable Long id) {
        return null;
    }
```
* 配置好swagger后访问http://localhost:8080/swagger-ui.html
* Swagger提供的注解
  * @Api:修饰整个类，用于描述Controller类
  * @ApiOperation：描述类的方法或者说是一个接口
  * @ApiParam: 单个参数描述
  * @ApiModel: 用对象来接收参数
  * @ApiResponse：HTTP响应的一个描述
  * @ApiIgnore: swagger会忽略这个方法
  * @ApiError: 发生错误返回的信息
  * 其他注解自查

### 引用
* 理解spring-boot-starter-parent的作用 https://www.jianshu.com/p/628acadbe3d8

### 常见问题
* Mapped Statements collection does not contain value for
    多数时mapper文件的问题，文件名/方法名/在properties中定义的扫描文件等
* SpringBoot读取配置的两种方式
  * 通过ConfigurableApplicationContext对象来读取 - 代码读取
  * 通过配置@Value注解到bean上读取