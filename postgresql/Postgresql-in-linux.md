#Basic Operation

## Install and start server
### 1. 查看postgresql版本信息
    psql --version

### 2. 获取帮助
    psql --help

### 3. 查看postgresql进程
    ps aux|grep postgres
`lukechen 16756  0.0  0.0 112640  1004 pts/1    S+   17:06   0:00 grep --color=auto postgres`

    其中S+指进程的状态
    ps工具标识进程的5种状态码:
    * D 不可中断 uninterruptible sleep (usually IO)
    * R 运行 runnable (on run queue)
    * S 中断 sleeping
    * T 停止 traced or stopped
    * Z 僵死 a defunct (”zombie”) process

### 4. 启动/停止/重启/尝试重启/重新加载/强制加载/状态
    service postgresql-9.4 XXX
    start, stop, restart, try-restart, reload, force-reload, status

## 添加新用户和数据库

### 1. 创建新用户(指定为superuser)
    sudo -u postgres createuser --superuser dbuser

### 2. 更改数据库的所有者
    alter database adempiere owner to adempiere;

### 3. 创建数据库
    sudo -u postgres createdb [dbname] -O [owner]

### 4. 删除数据库
    dropdb [dbname]

### 5. 指定用户连接到指定的数据库
    psql -U username -d dbname -h 127.0.0.1 -p 5432

### 6. 退出psql客户端程序
    \q

### 7. 登陆到psql控制台
    sudo -u postgres psql

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

### 模式Schema
    * 创建模式
    CREATE SCHEME [schemaname   ];

    * 删除模式
    DROP SCHEMA [schemaname];

    * 如果Schema下有Table，执行删除
    DROP SCHEMA [schemaname] CASCADE； #cascade 串联，瀑布流

### 数据表Table
    * 创建数据表
    CREATE TABLE [tablename](
        column1 VARCHAR(20),
        column2 DATE);

    * 删除数据表
    DROP TABLE [tablename];

### 3. 插入数据

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

### 4. 查询记录
* 语法
    SELECT column1, column2, ...
    FROM schema.table;

* 注意
    避免使用SELECT * 来增加查询的复杂度

### 5. 更新数据
* 语法
    UPDATE user set name = '李四' WHERE name = '张三';

### 6. 删除记录
    DELETE FROM user WHERE name = '李四' ;

### 7. 添加列
    ALTER TABLE user ADD email VARCHAR(40);

### 8. 更新列
    ALTER TABLE user ALTER COLUMN signup_date SET NOT NULL;

### 9. 更新列名
    ALTER TABLE user RENAME COLUMN signup_date TO signup;

### 10. 删除列名
    ALTER TABLE user DROP COLUMN email;

### 11. 更新表名
    ALTER TABLE user RENAME TO backup_tbl;

### 12. 删除表格
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

> How to config Postgresql to record log

There are three logs in PostgreSQL, they are `pg_log`, `pg_xlog`, `pg_clog`

    * pg_log  : 数据库运行日志（Default is off）
    * pg_xlog : WAR日志，即重做日志
    * pg_clog ：事务提交日志

Config pg_log and start it, location is postgresql.conf(var/lib/pgsql/9.4/data/postgresql.conf)

    log_destination = 'csvlog'
    logging_collector = on    # require restart service
    log_directory = 'pg_log'
    log_filename = 'postgresql-%Y-%m-%d_%H%M%S.log'
    log_rotation_age = 1d
    log_rotation_size = 100MB
    log_min_messages = info
    # 记录执行慢的SQL
    log_min_duration_statement = 60
    log_checkpoints = on
    log_connections = on
    log_disconnections = on
    log_duration = on
    log_line_prefix = '%m'
    # 监控数据库中长时间的锁
    log_lock_waits = on
    # 记录DDL操作
    log_statement = 'ddl'