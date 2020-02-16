# JSP知识总结

## 目录
* [JSP简介](#JSP简介)
* [JSP-Velocity-FreeMarker对比](#JSP-Velocity-FreeMarker对比)
* [JSP结构](#JSP结构)
* [JSP生命周期](#JSP生命周期)
* [JSP语法](#JSP语法)
* [JSP-HTTP状态码](#JSP-HTTP状态码)
* [JSP表单处理](#JSP表单处理)
* [JSP编码设置](#JSP编码设置)
* [JSP是不是被淘汰](https://github.com/zhonghuasheng/JAVA/wiki/%E5%AF%B9%E5%A4%A7%E5%9E%8BJAVA-Web%E9%A1%B9%E7%9B%AE%E4%B8%8B%E4%BD%BF%E7%94%A8JSP%E7%9A%84%E6%80%9D%E8%80%83)
* [JSP Velocity FreeMarker对比](https://github.com/zhonghuasheng/JAVA/wiki/JSP---Velocity---FreeMarker%E5%AF%B9%E6%AF%94)
* [JSTL库安装](https://www.runoob.com/jsp/jsp-jstl.html)

## JSP简介

1. 什么是Java Server Pages?

JSP全称Java Server Pages，是一种动态网页开发技术。它使用JSP标签在HTML网页中插入Java代码。标签通常以<%开头以%>结束。
JSP是一种Java servlet，主要用于实现Java web应用程序的用户界面部分。网页开发者们通过结合HTML代码、XHTML代码、XML元素以及嵌入JSP操作和命令来编写JSP。

JSP通过网页表单获取用户输入数据、访问数据库及其他数据源，然后动态地创建网页。

JSP标签有多种功能，比如访问数据库、记录用户选择信息、访问JavaBeans组件等，还可以在不同的网页中传递控制信息和共享信息。

2. 为什么使用JSP？

JSP程序与CGI程序有着相似的功能，但和CGI程序相比，JSP程序有如下优势：
* 性能更加优越，因为JSP可以直接在HTML网页中动态嵌入元素而不需要单独引用CGI文件。
* 服务器调用的是已经编译好的JSP文件，而不像CGI/Perl那样必须先载入解释器和目标脚本。
* JSP基于Java Servlets API，因此，JSP拥有各种强大的企业级Java API，包括JDBC，JNDI，EJB，JAXP等等。
* JSP页面可以与处理业务逻辑的servlets一起使用，这种模式被Java servlet 模板引擎所支持。
最后，JSP是Java EE不可或缺的一部分，是一个完整的企业级应用平台。这意味着JSP可以用最简单的方式来实现最复杂的应用。

3. JSP的优势

以下列出了使用JSP带来的其他好处：

* 与ASP相比：JSP有两大优势。首先，动态部分用Java编写，而不是VB或其他MS专用语言，所以更加强大与易用。第二点就是JSP易于移植到非MS平台上。
* 与纯 Servlets相比：JSP可以很方便的编写或者修改HTML网页而不用去面对大量的println语句。
* 与SSI相比：SSI无法使用表单数据、无法进行数据库链接。
* 与JavaScript相比：虽然JavaScript可以在客户端动态生成HTML，但是很难与服务器交互，因此不能提供复杂的服务，比如访问数据库和图像处理等等。
* 与静态HTML相比：静态HTML不包含动态信息。

## JSP-Velocity-FreeMarker对比
* JSP -> Velocity -> FreeMarker
    * JSP
        * 优点：
            1. 支持好。官方背书，标签库众多，支持JSP标签, 支持EL表达式语言, 功能强大，可以写JAVA代码
            2. 方便开发调试。
        * 缺点：
            1. 破坏了mvc结构
            2. jsp需要编译成class文件执行
    * Velocity: Apache出品，最早用于替代jsp的模板语言
        * 优点：
            1. 可以实现严格的mvc分离
            2. 据说性能比jsp要好一些
            3. 简单易学
        * 缺点：
            1. 第三方标签库少
            2. 难调试
            3. 对jsp标签支持不好
    * FreeMarker： Apache出品
        * 优点：
            1. 可以实现严格的mvc分离
            2. 内置常用功能强大，使用方便
            3. 对jsp标签支持良好
        * 缺点：
            1. 第三方标签库没有jsp多
            2. 难调试

4. 使用JSP的痛点
* 无法做到真正的动静分离。动态资源（JSP中内嵌的JAVA代码）和静态资源（HTML/CSS）耦合在一起，服务器压力大，一旦服务器不稳定，前后端会一起宕机，用户体验差。
* JSP必须要在支持JAVA的Web服务器里运行。对于一些纯静态的资源如图片/CSS/JS等，无法使用Nginx，性能提不上来。
* 第一个请求会比较慢。第一次请求JSP时，需要在web服务器中编译成servlet。
* 效率没有直接使用html高。每次请求jsp都是访问servlert再用输出流输出html页面。
* JSP本身时同步加载的，页面结构复杂的话，响应会慢。

## JSP结构

1. JSP处理流程

JSP处理
以下步骤表明了Web服务器是如何使用JSP来创建网页的：

* 就像其他普通的网页一样，您的浏览器发送一个HTTP请求给服务器。
* Web服务器识别出这是一个对JSP网页的请求，并且将该请求传递给JSP引擎。通过使用URL或者.jsp文件来完成。
* JSP引擎从磁盘中载入JSP文件，然后将它们转化为servlet。这种转化只是简单地将所有模板文本改用println()语句，并且将所有的JSP元素转化成Java代码。
* JSP引擎将servlet编译成可执行类，并且将原始请求传递给servlet引擎。
* Web服务器的某组件将会调用servlet引擎，然后载入并执行servlet类。在执行过程中，servlet产生HTML格式的输出并将其内嵌于HTTP response中上交给Web服务器。
* Web服务器以静态HTML网页的形式将HTTP response返回到您的浏览器中。
* 最终，Web浏览器处理HTTP response中动态产生的HTML网页，就好像在处理静态网页一样。

一般情况下，JSP引擎会检查JSP文件对应的servlet是否已经存在，并且检查JSP文件的修改日期是否早于servlet。如果JSP文件的修改日期早于对应的servlet，那么容器就可以确定JSP文件没有被修改过并且servlet有效。这使得整个流程与其他脚本语言（比如PHP）相比要高效快捷一些。

总的来说，JSP网页就是用另一种方式来编写servlet而不用成为Java编程高手。除了解释阶段外，JSP网页几乎可以被当成一个普通的servlet来对待。

## JSP生命周期
* JSP生命周期：编译阶段 -> 初始化阶段 -> 执行阶段 -> 销毁阶段
    * 编译阶段：Servelt容器将JSP编译为Servlet源文件，生成Servlet类。当浏览器请求JSP页面时，JSP引擎会首先去检查是否需要编译这个文件。如果这个文件爱呢没有被编译过，或者在上次编译后被更改过，则编译这个JSP文件。
    * 初始化阶段：加载与JSP对应的Servlet类，创建其实例，并调用其初始化方法。容器载入JSP文件后，它会首先做一些初始化工作，调用jspInit()方法，初始化只需要做一次。
    * 执行阶段：调用与JSP对应的Servlet实例的服务方法。
    * 销毁阶段：调用与JSP对应的Servlet实例的销毁方法，然后销毁实例。
* 示例代码
```jsp
<%!
public void jspInit() {
    System.out.println("JSP Init");
}

public void jspDestroy() {
    System.out.println("JSP Destory");
}
%>
```

## JSP语法
* JSP声明变量方法等
```jsp
<%! declaration; [ declaration; ]+ ... %>
<%! int i = 0; %>
<%! int a, b, c; %>
```
* JSP表达式
```jsp
<%= 表达式 %>
<p>
   今天的日期是: <%= (new java.util.Date()).toLocaleString()%>
</p>
```

* JSP注释
```jsp
<%-- 该部分注释在网页中不会被显示--%>
```

* JSP指令: JSP指令用来设置与整个JSP页面相关的属性。
```jsp
<%@ directive attribute="value" %>
<%@ page ... %>	定义页面的依赖属性，比如脚本语言、error页面、缓存需求等等
<%@ include ... %>	包含其他文件
<%@ taglib ... %>	引入标签库的定义，可以是自定义标签
```

* JSP行为
```jsp
<jsp:action_name attribute="value" />
jsp:include	用于在当前页面中包含静态或动态资源
jsp:useBean	寻找和初始化一个JavaBean组件
jsp:forward	从一个JSP文件向另一个文件传递一个包含用户请求的request对象
jsp:text	用于封装模板数据
...
```

* JSP9大内置对象
```jsp
application: ServletContext类实例，与应用上下文有关
page: 类似Java类中的this关键字
pageContext： PageContext类的实例，提供对JSP页面所有对象以及命名空间的访问
config: ServletConfig类实例
out: PrintWriter类的实例，用于把结果输出到页面上
request: HttpServletRequest类的实例
respone: HttpServletResponse类的实例
session: HttpSession类的实例
exception: Exception类的对象，代表发生错误的JSP页面中对应的异常对象
```

## JSP-HTTP状态码
|  状态码   |  消息  |  描述  |
|  ----  |  ----  |  ----  |
|  200  |  OK  |  请求被确认  |
|  201  |  Created  |  请求时完整的，新的资源被创建  |

## JSP表单处理
* 表单提交： `get` VS `post`
* 参数读取： `request.getParameter("xxx")`， 其他的有`request.getParameterValues()`、`request.getParameterNames()`、`request.getInputStream()`

## JSP编码设置
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  </head>
  <body>
    <h1>Hello World!</h1>
  </body>
</html>
```

* JSP页面编码:jsp文件本身的编码
```jsp
<%@ page pageEncoding="UTF-8" %>
```

* Web页面显示编码
```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
```

* HTML页面编码
```jsp
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
```

* Web服务器输入/输出流设置编码
```java
request.setCharacterEncoding("UTF-8")
response.setCharacterEncoding("UTF-8")
```

* Web服务器响应输出流
```java
response.setContentType("UTF-8")
```

* 他们之间的相互影响和作用域,以及先后作用顺序
1. pageEncoding: 只是指明了 JSP 页面本身的编码格式，跟页面显示的编码没有关系;
容器在读取(文件)或者(数据库)或者(字符串常量)时将起转化为内部使用的 Unicode,而页面显示的时候将
内部的Unicode转换为contentType指定的编码后显示页面内容;
如果pageEncoding属性存在，那么JSP页面的字符编码方式就由pageEncoding决定，
否则就由contentType属性中的charset决定，如果charset也不存在，JSP页面的字符编码方式就采用
默认的ISO-8859-1。

2. contentType: 指定了MIME类型和JSP页面回应时的字符编码方式。MIME类型的默认值是“text/html”;
字符编码方式的默认值是“ISO-8859-1”. MIME类型和字符编码方式由分号隔开;

3. pageEncoding和contentType的关系:
    1. pageEncoding的内容只是用于jsp输出时的编码，不会作为header发出去的; 是告诉web Server
        jsp页面按照什么编码输出,即web服务器输出的响应流的编码;
    2. 第一阶段是jsp编译成.java，它会根据pageEncoding的设定读取jsp，结果是由指定的编码方案翻译
        成统一的UTF-8 JAVA源码（即.java）.
    3. 第二阶段是由JAVAC的JAVA源码至java byteCode的编译，不论JSP编写时候用的是什么编码方案，
        经过这个阶段的结果全部是UTF-8的encoding的java源码.JAVAC用UTF-8的encoding读取
        java源码，编译成UTF-8 encoding的二进制码（即.class），这是JVM对常数字串在二进制码
        （java encoding）内表达的规范.
    4. 第三阶段是Tomcat（或其的application container）载入和执行阶段二的来的JAVA二进制码，
        输出的结果，也就是在客户端见到的，这时隐藏在阶段一和阶段二的参数contentType就发挥了功效

4. 和contentType效果一样的设置方式还有 html页面charset, response.setCharacterEncoding(),
    response.setContentType(),response.setHeader(); response.setContentType(),
    response.setHeader();优先级最好,其次是response.setCharacterEncoding();再者是
    <%@page contentType="text/html; chareset=gbk"%>,最后是<meta http-equiv="content-type"
    content="text/html; charset=gb2312" />.


5. web页面输入编码: 在设置页面编码<%@page contentType="text/html; chareset=gbk"%>的同时，也就指定了页面的输入编码;如果页面的显示被设置为UTF-8，那么用户所有的页面输入都会按照 UTF-8 编码; 服务器端程序在读 取表单输入之前要设定输入编码; 表单被提交后，浏览器会将表单字段值转换为指定字符集对应的字节值，然后根据 HTTP 标准 URL编码方案对结果字节进行编码.但是页面需要告诉服务器当前页面的编码方式;request.setCharacterEncoding(),能修改Serverlet获取请求的编码,response.setCharacterEncoding(),能修改Serverlet返回结果的编码.