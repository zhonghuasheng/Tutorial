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