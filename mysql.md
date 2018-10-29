### 查看MySql数据库物理文件存放位置
* mysql> show global variables like "%datadir%"
    * Windows中一般存储在C：\ProgramData\MySQL Server 5.6\Data\
    * Linux中在/var/lib/mysql/
* 同理，可以显示全部的全局变量
    * show global variables;