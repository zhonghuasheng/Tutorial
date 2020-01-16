## API测试工具（https://www.cnblogs.com/powertoolsteam/p/9772211.html）

### SoapUI
* SoapUI是一个开源测试工具，通过soap/http来检查、调用、实现Web Service的功能/负载/符合性测试。该工具既可作为一个单独的测试软件使用，也可利用插件集成到Eclipse，maven2.X，Netbeans 和intellij中使用。SoapUI Pro是SoapUI的商业非开源版本，实现的功能较开源的SoapUI更多。

* SoapUI支持：
    * API测试：Soap协议，Http协议
    * 压力测试
    * 安全测试

* 团队协作
    * SoapUI：本身一个project是一个xml文件，但是可以通过配置变成一系列文件夹，每个Case、每个Suite均是独立的文件，这样可通过svn/git进行团队协作。支持性较好

### Postman
* Postman是由Postdot Technologies公司打造的一款功能强大的调试HTTP接口的工具，它最早是Chrome中最受欢迎的插件之一，现已扩展到Mac，Windows和Linux客户端。

    * Postman支持：
        * http协议
        * 压力测试

* 团队协作
    * Postman：有团队协作的功能，需要付费。也可以通过Imort/Export 成文件后通过svn/git进行团队协作,一个Collection 可以到处为一个文件。

#### 综合评价
SoapUI 相对Postman 多了一个Soap 协议测试。根据公司内API测试的要求，如果有需求就会很有用，但我们公司是不需要这个选项的。
SoapUI 的功能复杂，界面使用多窗口方式实现，交互复杂，学习成本高，对于使用人员有较高要求。
SoapUI进行API 测试时，是通过Java 直接发送API 请求，和Fiddler等抓包工具的配合需要额外配置。
SoapUI 测返回内容对用中、日文支持不好，会出现乱码现象。
SoapUI的API测试，自动测试需要更强的编程技能。
Postman 脱胎于Chorme 的插件，只支持Http 协议的测试。
Postman 的界面采用Tab形式，类似chrome 的操作方式，界面简单，功能设计简洁，工程的组织只有Collection 和folder ,层级，概念简洁，易学，易用，对于项目组的学习成本低。
Postman 的API 测试，自动测试对于编程的要求相对低一些，可以从测试人员中挑选人员进行培训，培训后可以胜任API测试。
16.结论
综合考虑，如果只是进行Http,https 接口测试建议使用Postman 作为API测试工具，最主要的理由是，简洁易用，学习成本低。