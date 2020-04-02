## 目录
* [CentOS中安装MySQL](#CentOS中安装MySQL)

### 番外篇
* 数据库的扩展没有web服务器那样容易
* sql查询速度，服务器硬件，网卡流量，磁盘IO，大表，大事务会对数据库性能造成影响，idle空闲时间
* max_connection默认是100，连接数超过100的话后面的请求会等待connection，如果超时就返回5XX错误
* SSK fashionIO磁盘
* 大表对DDL操作的影响
  * MySQL 5.5前，建立索引会锁表
  * MySQL 5.5后，不会缩表但会引起主从延迟
  * 修改表结构需要长时间缩表
  * 采用分库分表来拆分成多个小表
    * 分表主键的选择
    * 分表后跨分区数据的查询和统计
  * 对大表的历史数据归档-可以减少对前后端业务的影响
    * 归档时间点的选择
    * 如何进行归档操作
  * 大事务：运行时间长，操作的数据比较多的事务，例如余额宝计算用户昨天的收益，特别是理财产品买的多的情况下，回滚所需的时间长

#### MySQL体系结构
* MySQL最大的特点-插件式存储引擎，能够将数据的插入，提取相分离

### 常用命令
```sql
show variables like '%isolation%' --查看数据库事务隔离级别的设置
```

### 注意点
* 最好不要在主库上做数据库备份，大型活动前取消这类计划

### CentOS中安装MySQL
* 查看官方文档 https://dev.mysql.com/doc/mysql-yum-repo-quick-guide/en/
* 下载MySQL yum包 http://dev.mysql.com/downloads/repo/yum/（要查看系统的版本 uname -a）
* 下载yum包 wget http://repo.mysql.com/mysql57-community-release-el7-10.noarch.rpm
* 安装软件源 rpm -Uvh platform-and-version-specific-package-name.rpm
* 安装mysql服务端  yum install  -y  mysql-community-server
* 启动myssql service mysqld start

> 注意
* 首次启动后要及时登陆并修改密码
* 使用 `grep 'temporary password' /var/log/mysqld.log` 获取临时密码
* `mysql -uroot -p`输入密码登陆
* 修改全局参数
    * `set global validate_password_policy=0;`
    * `set global validate_password_length=1;`
* 修改密码 `ALTER USER 'root'@'localhost' IDENTIFIED BY 'xxxxxx';
* 开启远程连接
    `Grant all privileges on *.* to 'root'@'%' identified by 'yourpassword' with grant option;`
    `flush privileges;`

### 查看MySql数据库物理文件存放位置
* mysql> show global variables like "%datadir%"
    * Windows中一般存储在C：\ProgramData\MySQL Server 5.6\Data\
    * Linux中在/var/lib/mysql/
* 同理，可以显示全部的全局变量
    * show global variables;

### Mysql执行sql文件
* 进入mysql安装目录，找到mysql.exe所在文件夹
```
mysql -uroot -p123456 -Ddbname<C:\a.sql
```
* 进入mysql command
```
source C:\a.sql
```