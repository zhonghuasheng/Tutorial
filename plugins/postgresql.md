### 学习笔记
* [Postgresql在Linux中的安装以及常用命令](database/postgresql/postgresql_note.md)
* [解决Postgresql RDS CPU使用率过高的问题](database/postgresql/PostgreSQL_CPU_Usage_High.md)

Default path: /var/lib/pgsql/9.5/data

1. [How to install postgresql in linux](./file/postgresql/postgresql_note.md#how-to-install-postgresql-in-linux)
### How to install Postgresql in Linux

* Go to page https://yum.postgresql.org/9.4/redhat/rhel-6-x86_64/
* URL may find by `https://yum.postgresql.org/version`, like `https://yum.postgresql.org/9.4/`
* Find rpm url about
    `postgresql94-libs-9.4.7-1PGDG.rhel7.x86_64.rpm`
    `postgresql94-9.4.7-1PGDG.rhel7.x86_64.rpm`
    `postgresql94-contrib-9.4.7-1PGDG.rhel7.x86_64.rpm`
    `postgresql94-server-9.4.7-1PGDG.rhel7.x86_64.rpm`
* wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-libs-9.4.7-1PGDG.rhel7.x86_64.rpm
  wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-9.4.7-1PGDG.rhel7.x86_64.rpm
  wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-contrib-9.4.7-1PGDG.rhel7.x86_64.rpm
  wget http://yum.postgresql.org/9.4/redhat/rhel-7-x86_64/postgresql94-server-9.4.7-1PGDG.rhel7.x86_64.rpm
* yum install -y postgresql94-libs-9.4.7-1PGDG.rhel7.x86_64.rpm
  yum install -y postgresql94-9.4.7-1PGDG.rhel7.x86_64.rpm
  yum install -y postgresql94-contrib-9.4.7-1PGDG.rhel7.x86_64.rpm
  yum install -y postgresql94-server-9.4.7-1PGDG.rhel7.x86_64.rpm
* execute `service postgresql-9.4 initdb`
* execute `service postgresql-9.4 start`

## XML

### Add Node in XML

Visit [here](https://stackoverflow.com/questions/42419720/inserting-xml-nodes-using-plpgsql)

### Add attribute in XML node

```sql
UPDATE test_xml SET xml(column name)=replace(xml(column name)::text, 'xx="4"'(attribute), 'xx="4" yy="false"'(new attribute))::xml(change to xml) WHERE XXX;

### Update attribute value in XML node

```sql
SELECT replace_attribute(
    '<foo id="blah">hi</foo>',
    'id',
    'whatever'
);
```

### Select Node

SELECT
  column1,
  column2,
  unnest(xpath('/node1/node2[@attribute1=4]/@attribute2', cast(request.column as xml))) as x,
  unnest(xpath('/node1/node2[@attribute1=4]/@status', cast(request.column as xml))) as y,
  unnest(xpath('/node1/node2[@attribute1=4]/@selected', cast(request.column as xml))) as z
FROM table as request;;

### Loop array

```sql
DO
$do$
DECLARE
  uid bigint;
  userid_array bigint[] := array[2, 3, 4];
  userid_array_len int;
BEGIN
  userid_array_len := array_upper(userid_array, 1);
  FOR uid IN 1 .. userid_array_len
  LOOP
    IF (select exists(SELECT unnest(xpath('/aa/bb[@attribute1=4][@attribute2="test"]', cast(test.xml as xml))) as text FROM test_xml as test WHERE userid=uid)) THEN
        UPDATE test_xml SET xml=replace(xml::text, 'attribute1="4"', 'attribute1="4" selected="true"')::xml WHERE userid=uid;
        RAISE NOTICE 'r % e', uid;
    END IF;
  END LOOP;
END
$do$
```

#### How to dump data from remote database table

```sql
pg_dump --host server1 --encoding=utf8 --no-owner --username=foo --password -t table_name db_name > server1_db.sql
```

#### Run sql file in pql command

```sql
psql -h hostname -d dbname -U username -p 5432 -a -q -f /filepath/filename.sql
-a: all echo
-q: quiet
-f: file
```

#### Output result into a file

```sql
psql -U username -h host -d my_db_name -t -A -F"," -f input-file.sql -o output-file.csv
```

```sql
COPY (SELECT * FROM table) TO '/some_destdir/mycsv.csv' WITH CSV HEADER;
```
#### Select * table
\dt hsf*

## PostgreSQL简史
现在被称为PostgreSQL的对象-关系型数据库管理系统是从美国加州大学伯克利 分校编写的POSTGRES软件包发展而来的。经过二十几年的发展，PostgreSQL 是目前世界上可以获得的最先进的开放源码数据库系统。

## 格式约定
下面的格式用于命令的大纲：方括弧([ 和 ])表示可选的部分(在Tcl命令里使用的是问号 (?)。花括弧({ 和 }) 和竖条(|)表示你必须选取一个候选。连续点(...) 表示前面的元素可以重复。
如果能提高清晰度，那么SQL命令使用前缀提示符=>， 而shell命令使用前缀提示符$。不过，通常是不显示提示符的。

### 体系基本概念
在我们开始讲解之前，我们应该先了解PostgreSQL 系统的基本体系。理解PostgreSQL的组件之间的相 互关系将会使本节显得更清晰一些。
按照数据库术语来说，PostgreSQL使用一种客户端/服务器 的模式。一次PostgreSQL会话由下列相关的进程(程序)组成：
一个服务器进程，它管理数据库文件，接受来自客户端应用与数据库的连接，并且代表客户端在数据库上执行操作。数据库服务器程序叫postgres。
那些需要执行数据库操作的用户的客户端(前端)应用。客户端应用可能本身就是 多种多样的：它们可以是一个字符界面的工具，也可以是一个图形界面的应用， 或者是一个通过访问数据库来显示网页的 web 服务器，或者是一个特殊的数据库 管理工具。一些客户端应用是和PostgreSQL发布一 起提供的，但绝大部分是用户开发的。
和典型的客户端/服务器应用(C/S应用)一样，这些客户端和服务器可以在不同 的主机上。这时它们通过 TCP/IP 网络连接通讯。你应该记住的是，在客户机 上可以访问的文件未必能够在数据库服务器机器上访问(或者只能用不同的文件 名进行访问)。
PostgreSQL服务器可以处理来自客户端的多个并发连接。 因此，它为每个连接启动("forks")一个新的进程。从这个时候开始，客户端和新服务 器进程就不再经过最初的postgres进程进行通讯。因此，主服务器总是在运行，等 待客户端连接，而客户端及其相关联的服务器进程则是起起停停。（当然，用户是肯定看不到 这些事情的。我们在这儿谈这些主要是为了完整。）

~~~sql
CREATE TABLE weather (
    city            varchar(80),
    temp_lo         int,           -- low temperature
    temp_hi         int,           -- high temperature
    prcp            real,          -- precipitation
    date            date
);

CREATE TABLE cities (
    name            varchar(80),
    location        point
);
INSERT INTO weather VALUES ('San Francisco', 46, 50, 0.25, '1994-11-27');
INSERT INTO cities VALUES ('San Francisco', '(-194.0, 53.0)');
INSERT INTO weather (city, temp_lo, temp_hi, prcp, date)
    VALUES ('San Francisco', 43, 57, 0.0, '1994-11-29');
INSERT INTO weather (date, city, temp_hi, temp_lo)
    VALUES ('1994-11-29', 'Hayward', 54, 37);
~~~

varchar(80)声明一个可以存储最长 80 个字符的任意字符串的数据类型。
PostgreSQL支持标准的SQL类型： int, smallint, real, double precision, char(N), varchar(N), date, time, timestamp,和 interval，还支持其它的通用类型和丰富的几何类型。PostgreSQL 允许你自定义任意数量的数据类型。因而类型名并不是语法关键字，除了SQL 标准要求支持的特例外。

CREATE TABLE cities (
    name            varchar(80),
    location        point
);

DROP TABLE tablename;

INSERT INTO weather (city, temp_lo, temp_hi, prcp, date)
    VALUES ('San Francisco', 43, 57, 0.0, '1994-11-29');

你还可以使用COPY从文本文件中装载大量数据。这么干通常更快， 因为COPY命令就是为这类应用优化的，只是比INSERT 少一些灵活性。比如：

COPY weather FROM '/home/user/weather.txt';

和大多数其它关系数据库产品一样，PostgreSQL支持聚合函数。 一个聚合函数从多个输入行中计算出一个结果。比如，我们有在一个行集合上计算count(数目), sum(总和),avg(均值),max(最大值), min(最小值)的函数。

SELECT city, max(temp_lo)
    FROM weather
    WHERE city LIKE 'S%'
    GROUP BY city
    HAVING max(temp_lo) < 40;

理解聚合和SQL的WHERE和HAVING 子句之间的关系非常重要。WHERE和HAVING的基本区别如下： WHERE在分组和聚合计算之前选取输入行(它控制哪些行进入聚合计算)， 而HAVING在分组和聚合之后选取输出行。因此，WHERE 子句不能包含聚合函数；因为试图用聚合函数判断那些行将要输入给聚合运算是没有意义的。 相反，HAVING子句总是包含聚合函数。当然，你可以写不使用聚合的HAVING 子句，但这样做没什么好处，因为同样的条件用在WHERE阶段会更有效。

UPDATE weather
    SET temp_hi = temp_hi - 2,  temp_lo = temp_lo - 2
    WHERE date > '1994-11-28';

DELETE FROM weather WHERE city = 'Hayward';

回头看看第 2.6 节里的查询。假设你的应用对天气记录和城市位置的 组合列表特别感兴趣，而你又不想每次键入这些查询。那么你可以在这个查询上创建一个视图， 它给这个查询一个名字，你可以像普通表那样引用它。

CREATE VIEW myview AS
    SELECT city, temp_lo, temp_hi, prcp, date, location
        FROM weather, cities
        WHERE city = name;

SELECT * FROM myview;
自由地运用视图是好的 SQL 数据库设计的一个关键要素。视图允许我们把表结构的细节封装起来， 这些表可能随你的应用进化而变化，但这些变化却可以隐藏在一个一致的接口后面。

视图几乎可以在一个真正的表可以使用的任何地方使用。在其它视图上面再创建视图也并非罕见。

3.3. 外键
回忆一下第 2 章里的weather和cities表。 考虑下面的问题：你想确保没有人可以在weather表里插入一条在cities 表里没有匹配记录的数据行。这就叫维护表的参照完整性。在简单的数据库系统里， 实现(如果也叫实现)这个特性的方法通常是先看看cities表里是否有匹配的记录， 然后插入或者拒绝新的weather记录。这个方法有许多问题，而且非常不便， 因此PostgreSQL可以为你做这些。

新的表声明看起来会像下面这样：

CREATE TABLE cities (
        city     varchar(80) primary key,
        location point
);

CREATE TABLE weather (
        city      varchar(80) references cities(city),
        temp_lo   int,
        temp_hi   int,
        prcp      real,
        date      date
);
然后我们试图插入一条非法的记录：

INSERT INTO weather VALUES ('Berkeley', 45, 53, 0.0, '1994-11-28');
ERROR:  insert or update on table "weather" violates foreign key constraint "weather_city_fkey"
DETAIL:  Key (city)=(Berkeley) is not present in table "cities".
外键的行为可以根据你的应用仔细调节。在这份教程里我们就不再多说了，请你参考第 5 章 以获取更多的信息。正确使用外键无疑将改进你的数据库应用，所以我们强烈建议你学习它们。



### 读取文件中的命令

mydb=> \i basics.sql --\i命令从指定的文件中读取命令




### 导出查询的数据到CSV

~~~sh
psql -h hostname -U postgres -d databasename -c "COPY(select firstname from user limit 10) to stdout with csv header" > /home/yourname/workspace/test.csv
~~~

### 导出查询的XML数据到html

~~~sh
psql -h hostname -U postgres -d databasename --html -c "select xmlcolumn from xmltable" > /home/yourname/workspace/test.html
~~~

### 查询XML数据
~~~sql
select
  contentid,
  cast(
    unnest(
      xpath('/root/node[@attributename=''xxxxxx'']/node/text()', cast(c.xml as xml))
    ) as text
  ) as "asf"
from ddmcontent c
~~~

### 死锁

多个线程（多个WebService类似）同时更新一张表，是不会存在死锁的情况！因为同时更新数据库时的同一张表时，操作是有先后顺序的, 第1个线程操作完后，释放锁，然后第2个线程继续操作。
出现死锁的情况是，线程的操作数据库时，需要同时用到2个资源（比如2张表）。
比如：线程A、B都需要用到表1和表2， 当线程A锁定了表1， 需要用到表2的时候。  刚好线程B，先锁定表2， 线程B刚好需要用到表1， 这时候就出现死锁。

Music(歌曲)
    * uuid
    * id
    * name
    * genreid
    * albumid
    *
Genre(流派)
    * uuid
    * id
    * name
    * description
Album(专辑)
    * uuid
    * id
    * artistid
Artist(艺术家)
    * uuid
    * id
    * name


User_
Order
Order Detail


####

"/var/lib/pgsql/9.5/data/" is missing or empty

service postgresql initdb The service command supports only basic LSB actions (start, stop, restart, try-restart, reload, force-reload, status). For other actions, please try to use systemctl.

* /usr/pgsql-9.5/bin/postgresql95-setup initdb


####
psql: FATAL:  Ident authentication failed for user "xxx"
=======
```sql
\i C:/batchexc/batch.sql
```

### Disconnect all connections for a database
select pg_terminate_backend(pg_stat_activity.pid) from pg_stat_activity where datname='databasename' AND pid<>pg_backend_pid();

2. [Analysis for PostgreSQL CPU usage is high](./file/postgresql/PostgreSQL_CPU_Usage_High.md)
### CPU usage grow

对于数据库CPU使用率很高的情况，一般情况是从后端数据库开始排查，追溯到具体的SQL，然后定位到具体的业务逻辑。有以下几点可以分析：
1. 访问站点的人数增多，造成数据库连接数增加和查询增多，进而导致数据库CPU使用率过高。
2. 访问站点的人数不多，那就要考虑因某些Slow Query被大量执行导致CPU使用率过高。

#### 访问站点的人数增多，造成数据库连接数增加和查询增多
* 查看数据库的连接数
```sql
select count( * ) from pg_stat_activity where state not like '%idle'; // 查询状态不是空闲的数量，可以好好看看pg_stat_activity这张表
```

#### Slow Query被大量执行导致CPU使用率过高
* 通过查询pg_stat_activity视图来看当前长时间执行，一直不结束的SQL

```sql
SELECT datname, usename, client_addr, application_name, state, backend_start, xact_start, xact_stay, query_start, query_stay, replace(query, chr(10), ' ') as query from (select pgsa.datname as datname, pgsa.usename as usename, pgsa.client_addr client_addr, pgsa.application_name as application_name, pgsa.state as state, pgsa.backend_start as backend_start, pgsa.xact_start as xact_start, extract(epoch from (now() - pgsa.xact_start)) as xact_stay, pgsa.query_start as query_start, extract(epoch from (now() - pgsa.query_start)) as query_stay , pgsa.query as query from pg_stat_activity as pgsa where pgsa.state != 'idle' and pgsa.state != 'idle in transaction' and pgsa.state != 'idle in transaction (aborted)') idleconnections order by query_stay desc limit 5;
```

* 通过查询哪些表被扫描（Table Scan）到，并且这些表缺失索引。数据表如果缺失索引，大部分热数据又都是存在内存时（例如内存8G，热数据6G），此时数据库只能使用表扫描，并需要处理已经在内存中的大量的无关的记录，这些都是要消耗CPU的。

    * 查询使用表扫描最多的表
    ```sql
    select * from pg_stat_user_tables where n_live_tup > 100000 and seq_scan > 0 order by seq_tup_read desc limit 10;
    ```

    * 查询当前正在运行的访问到上述表的慢查询
    ```sql
    select * from pg_stat_activity where query ilike '%<table name>%' and query_start - now() > interval '10 seconds'; // 替换<table name>
    ```sql

* 通过pg_stat_statements模块来追踪这段时间内所有SQL语句的执行信息，类似SQL Server中的执行计划（Execution Plan）
    * https://www.postgresql.org/docs/9.0/static/pgstatstatements.html