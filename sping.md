## Spring

### Factory Pattern
#### Simple Factory
#### Factory Method
#### Abstract Factory

### IOC/DI
#### IOC简介
#### 注入方式
#### IOC实现基本原理

### AOP
#### AOP引入
#### JDK代理
#### CGLib代理
#### 模拟Spring-IOC
#### 模拟Spring-AOP

### Spring 基础
#### Hello Spring
#### Spring Singleton
#### Spring Factory Method
#### Spring Inject Method
#### Spring Liferay Cycle
#### Spring Autowire
#### Abstract Parent Import
#### Spring Annotation
#### Spring Collections

### Spring AOP
#### Spring AOP引入
#### Spring AOP引入2
#### Spring AOP基本概念

### Spring Web
#### Spring MVC流程
#### Hello Spring MVC与自定义url
#### 接受请求参数
#### Spring MVC返回html
#### Spring response body
#### Spring json ignore
#### Spring json serialize
#### Spring json deserialize
#### 静态资源处理
#### 注解调用service
#### 异常处理@exceptionhandler
#### 获得beanFactory对象


http://www.mybatis.org/mybatis-3/zh/configuration.html

## Mybatis
### Environment
### Configuration XML
### Mapper XML
### Create Operation
### Read Operation
### Update Operation
### Delete Operation
### Annotations
### Stored Procedures
### Dynamic Query



### 配置文件的基本结构
* configuration —— 根元素 
    * properties —— 定义配置外在化
    * settings —— 一些全局性的配置
    * typeAliases —— 为一些类定义别名
    * typeHandlers —— 定义类型处理，也就是定义java类型与数据库中的数据类型之间的转换关系
    * objectFactory —— 对象工厂
    * plugins —— Mybatis的插件，插件可以修改Mybatis内部的运行规则
    * environments —— 配置Mybatis的环境 
        * environment 
             * transactionManager —— 事务管理器
             * dataSource —— 数据源
    * databaseIdProvider
    * mappers —— 指定映射文件或映射类


首先回顾一下hibernate中操作数据库的流程：

1）读取配置

2）获取SessionFactory（重量级，只有一个）

3）获取session

4）开启事务

5）进行CRUD操作

6）提交事务

7）关闭session



在我们的mybatis中，也有类似的步骤：

1）获取SqlSessionFactory

2）获取SqlSession

3）进行CURD操作

4）提交事务

5）关闭SqlSession

通过核心配置XML方式

Reader reader = Resources.getResourceAsReader("ibatis-config.xml");
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
通过Configuration方式（其实就是将XML配置转化为对应的对象）

DataSource dataSource = ...
TransactionFactory transactionFactory = new JdbcTransactionFactory();
Environment environment = new Environment("development", transactionFactory, dataSource);
Configuration configuration = new Configuration(environment);
configuration.addMapper(BlogMapper.class);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
获取SqlSession

SqlSession sqlSession = factory.openSession();
