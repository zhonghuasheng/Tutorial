### CentOS中安装MySQL
* 查看官方文档 https://dev.mysql.com/doc/mysql-yum-repo-quick-guide/en/
* 下载MySQL yum包 http://dev.mysql.com/downloads/repo/yum/（要查看系统的版本 uname -a）
* 下载yum包 wget http://repo.mysql.com/mysql57-community-release-el7-10.noarch.rpm
* 安装软件源 rpm -Uvh platform-and-version-specific-package-name.rpm
* 安装mysql服务端  yum install  -y  mysql-community-server
* 启动myssql service mysqld start

### 查看MySql数据库物理文件存放位置
* mysql> show global variables like "%datadir%"
    * Windows中一般存储在C：\ProgramData\MySQL Server 5.6\Data\
    * Linux中在/var/lib/mysql/
* 同理，可以显示全部的全局变量
    * show global variables;