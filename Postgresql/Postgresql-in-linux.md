#Basic Operation


### 查看postgresql版本信息
    psql --version

### 获取帮助
    psql --help

### 查看postgresql进程
    ps aux|grep postgres
`lukechen 16756  0.0  0.0 112640  1004 pts/1    S+   17:06   0:00 grep --color=auto postgres`

    其中S+指进程的状态
    ps工具标识进程的5种状态码:
    * D 不可中断 uninterruptible sleep (usually IO)
    * R 运行 runnable (on run queue)
    * S 中断 sleeping
    * T 停止 traced or stopped
    * Z 僵死 a defunct (”zombie”) process

### 启动/停止/重启/尝试重启/重新加载/强制加载/状态
    service postgresql-9.4 XXX
    start, stop, restart, try-restart, reload, force-reload, status

### 切换到postgresql用户
    sudo su postgres

### 切换到DB
    psql [dbname]

### 退出psql客户端程序
    \q

### 添加新用户和数据库

### 创建新用户(指定为superuser)
    sudo -u postgres createuser --superuser dbuser

### 更改数据库的所有者
    alter database adempiere owner to adempiere;

### 登陆到psql控制台
    sudo -u postgres psql

#### 创建数据库
    sudo -u postgres createdb [dbname] -O [owner]

### 删除数据库
    dropdb [dbname]

### 指定用户连接到指定的数据库
    psql -U username -d dbname -h 127.0.0.1 -p 5432

## Basic Operation in Postgresql
    \h：查看SQL命令的解释，比如\h select。
    \?：查看psql命令列表。
    \l：列出所有数据库。
    \c [database_name]：连接其他数据库。
    \d：列出当前数据库的所有表格。
    \d [table_name]：列出某一张表格的结构。
    \du：列出所有用户。
    \e：打开文本编辑器。
    \conninfo：列出当前数据库和连接的信息。
### 创建模式
    CREATE SCHEME name;

### 创建新表
    CREATE TABLE user(name VARCHAR(20), signup_date DATE);

### 插入数据

* 语法：
    INSERT INTO TABLE_NAME (
        column1,
        column2,
        column3,
        ...
        columnN)
    VALUES (
        value1,
        value2,
        value3,
        ...
        valueN);

* 示例:
    INSERT INTO user(
        name,
        signup_date)
    VALUES(
        'test',
        '2013-12-22');

### 选择记录
* 语法
    SELECT column1, column2, ...
    FROM schema.table;

* 注意
    避免使用SELECT * 来增加查询的复杂度

### 更新数据
* 语法
    UPDATE user set name = '李四' WHERE name = '张三';

### 删除记录
    DELETE FROM user WHERE name = '李四' ;

### 添加栏位
    ALTER TABLE user ADD email VARCHAR(40);

### 更新结构
    ALTER TABLE user ALTER COLUMN signup_date SET NOT NULL;

### 更名栏位
    ALTER TABLE user RENAME COLUMN signup_date TO signup;

### 删除栏位
    ALTER TABLE user DROP COLUMN email;

### 表格更名
    ALTER TABLE user RENAME TO backup_tbl;

### 删除表格
    DROP TABLE IF EXISTS backup_tbl;

### WHERE语句


### EXPLAIN SELECT * FROM user_ limit 10 offset 2;












# Question and Solution
> psql: FATAL: Ident authentication failed for user "username" Error and Solution

To fix this error open PostgreSQL client authentication configuration file /var/lib/pgsql/data/pg_hba.conf :
    # vi /var/lib/pgsql/data/pg_hba.conf

    local	all	all	            peer
    host	all	127.0.0.1/32	peer

1. Which hosts are allowed to connect
2. Which databases they can access
3. Which PostgreSQL user names they can use
4. How clients are authenticated

    [Document](https://www.postgresql.org/docs/9.1/static/auth-methods.html)

    * trust:无条件允许连接
    * peer:The peer authentication method works by obtaining the client's operating system user name from the kernel and using it as the allowed database user name (with optional user name mapping).
    * md5:要求客户端提供一个MD5加密的口令进行验证
    * crypt:类似MD5的一种老式的加密认证
    * reject(无条件拒绝)

Modify
    local	all	all	            md5
    host	all	127.0.0.1/32	md5

Save and clost file. Restart server:
    service postgresql-9.4 restart

Then use:
    psql -U dbuser -d dbname -h 127.0.0.1 -p 5432
