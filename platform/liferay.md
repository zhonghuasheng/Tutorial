### 你是如何介绍Liferay的
Liferay给自己打的广告是“The world's leading open source portal platform” - 世界上领先的开源门户网站，和PostgreSQl给自己打的广告类似“The World's Most Advanced Open Source Relational Database” - 世界上最先进的开源关系型数据库一样，足以显示出其强大。Liferay号称不用写一行代码就可以轻松搭建一个高可用的CMS系统，Content Management System。通过以下几点来介绍Liferay：

* 背景：Liferay开始于2000年，距今已经19年了，在全球有22个办事处和18W的社区人员。其版本发生了数次迭代，目前最新的开源社区版本是7.1，使用JAVA8技术。其开发人员中一部分曾今参与过JDK源码编写的，在2008年与Sun公司签署技术共享协议，为Liferay框架的发展引进了大量的技术人才。国外有众多的企业门户网站使用的是Liferay，比如著名的ReadHeat，国内对Liferay的熟知程度较少（国内的CMS侧重于电商），另外一个著名的CMS是Wordpress，其早期是做博客开源系统的，后期慢慢向CMS转变。
* 开源，社区完善：Liferay的CE版本是免费开源的，支持二开并进行商业使用，常见的Liferay问题可以通过社区博客找到并解决。
* 集成诸多的通用功能。用户管理，权限管理，角色管理，内容管理等；还有一些开箱即用的插件，比如博客Blog，聊天Chat等；一些最新的技术也集成到Liferay,如集成了Tomcat，Spring, Hibernate，支持主流的关系型数据库，支持可配的S3, EFS等文件系统，6.x版本中集成了Lucene作为搜索引擎，7.x中集成了Solr，继承了Kaleo作为工作流。对外采用JSON Web Service/SOAP来开发WEB API；前端使用AUI和Bootstrap。另外, Liferay是一个支持多站点的框架-即一套代码支持多个不同的站点，那么Liferay是如何做到的？其中一个关键的技术是Database Sharding, 在6.x版本中，每个站点的数据库是独立的。支持动态配置表单。
* 支持修改源代码。Liferay通过Plugins的方式，支持热插拔的部署自己的代码。Liferay分为Portlet/Layout/Theme/Ext/Hook五个模块。