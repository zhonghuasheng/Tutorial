### 学习计划
* B站8小时转go开发
* go-kit实战小例子

#### go basic

#### go web
* 示例地址 https://github.com/zhonghuasheng/JAVA_LOVE_GO/tree/master/gop/com/zhonghuasheng/web

### 目录
* RPC
* Protobuf
* go thinking
* basic
* 空白标识符
* 注意

### RPC
整体过程就是：
1. 客户端发送数据（以字节流的方式）
2. 服务端接收并解析。根据约定知道执行什么，然后把结果返回客户端
RPC就是把
1. 上述流程封装下，使其操作更加优化，让使用者感觉像是调用本地方法一样
2. 使用一些大家都认可的协议 使其规范化
3. 做成一些框架，直接或间接产生利益

### Protobuf
Google Protocol Buffer
轻便高效的序列化数据结构协议

### go thinking
众多语言学术流派，面向过程、面向对象、函数式编程、面向消息编程等。Go语言接受函数式编程、支持匿名函数与闭包，接受以Erlang语言为代表的面向消息编程思想，支持goroutine和通道，并推荐使用消息而不是共享内存来进行并发编程。Go语言最主要的特性
* 自动垃圾回收
* runtime系统调度机制
* 更丰富的内置类型
* 函数多返回值
* 错误处理
* 匿名函数和闭包
* 类型和接口
* 并发编程
* 反射
* 语言交互性

### basic
* Go程序由多个标记组成，可以是关键字、标识符、常量、字符串、符号。
    * 行分隔符： 每一行代表一个语句结束。建议每一行加上分号;标识结尾，不加的话Go编译器自动完成
    * 注释：注释不会被编译
    * 标识符：标识符用来命名变量、类型等程序实体
    * 字符串连接：Go语言的字符串可以通过+实现
    * 关键字：Go语言包含25个关键字。break default func interface select case defer go map struct chan else goto package switch const flatthrough if range type continue for import return var
        * Go语言还有36个预定义标识符
* 空白标识符_是一个只写变量，你不能得到它的值，被用于抛弃值。这样做是因为Go语言中你必须使用所有被声明的变量，但有时你并不需要使用从一个函数得到的所有返回值。
* 环境搭建
    * windows环境搭建
        * 安装go二进制文件 到镜像网站下载 https://goproxy.io/zh/
        * 下载Goland IDEA，设置GOPATH
        * 配置GOPROXY代理，不然很慢
    * go get xxx 下载xxx包
    * go mod tidy 刷新 类似maven update
* go三层经典模型
    * Transport 主要负责Http, gRpc, thrift
    * Services 业务函数
    * Endpoint 定义request, response格式等
* 四种变量声明方式


### 注意
* go语言中{不能单独放在一行，否则运行时会产生错误

### 其他
* facebook grace平滑升级项目
* 腾讯在15年的时候使用go做了docker万台规模，主要用于蓝鲸游戏平台

### 常见错误
* package command-line-arguments is not a main package
要运行的go文件，package不是main。PS，也要有一个main函数

* sql: Scan error on column index 3, name "created_at": unsupported Scan, storing driver.Value type []uint8 into type *time.Time
在conn上加parseTime=true，eg `/guestdb?charset=utf8&parseTime=true`