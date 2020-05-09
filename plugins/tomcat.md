### 学习计划

### 学习笔记
* [Tomcat服务器架构](plugins/一张图了解Tomcat架构.md)
* [Tomcat如何处理一个请求](plugins/一张图了解Tomcat架构.md)
* [Tomcat安全配置建议](https://github.com/zhonghuasheng/Tutorial/wiki/Tomcat%E5%AE%89%E5%85%A8%E9%85%8D%E7%BD%AE)
* [Tomcat Access日志分析](shell/linux命令在tomcat日志中的应用.md)
* [Tomcat Nio理解](https://www.jianshu.com/p/76ff17bc6dea)

### 优化
1. enableLookup默认为true，修改为false，这个是禁止DNS反向查询IP，tomcat中有个access_log用于记录哪个IP访问了哪个url，项目初期时我们是可以开启这个配置，干什么呢？第一是统计访问最多的请求落在了哪些地方，帮助我们发现问题。第二是统计出来的IP地址可以大致统计出请求来自的区域，这对于云部署时选择服务器的region提供依据。