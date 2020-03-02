### springboot各个依赖都是干什么的
* spring-boot-starter-parent: spring-boot-starter-parent是一个特殊的starter，它用来提供相关的Maven默认依赖。使用它之后，常用的包依赖可以省去version标签。通过继承spring-boot-starter-parent，默认具备了如下功能：Java版本（Java8）、源码的文件编码方式（UTF-8）、依赖管理、打包支持、动态识别资源、识别插件配置、识别不同的配置，如：application-dev.properties 和 application-dev.yml
* spring-boot-maven-plugin: 用于执行package命令是生成可执行的jar，如果不添加，执行java -jar时会报错
“springboot-helloworld-2.2.5.RELEASE.jar中没有主清单属性”


* Mapped Statements collection does not contain value for
多数时mapper文件的问题，文件名/方法名/在properties中定义的扫描文件等

### 引用
* 理解spring-boot-starter-parent的作用 https://www.jianshu.com/p/628acadbe3d8