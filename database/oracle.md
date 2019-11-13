* 查询数据库占用存储空间大小
```sql
SELECT SUM(bytes)/1024/1024/1024 as "SIZE(G)" from dba_segments WHERE owner='账户名/数据库名';
```

* 查询表数据占用存储空间大小
```sql
SELECT SUM(bytes)/1024/1024 as "SIZE(M)" FROM user_segments WHERE SEGMENT_NAME=upper('TAG_VALUE');
```

* 查询占用存储空间最大的N张表
```sql
SELECT * FROM
  (SELECT SEGMENT_NAME, SUM(BYTES)/1024/1024 AS MB
    FROM DBA_SEGMENTS
    WHERE TABLESPACE_NAME = upper('SYSAUX')
    GROUP BY SEGMENT_NAME
    ORDER BY 2 DESC)
    WHERE ROWNUM < 10>) --可以通过调整ROWNUM来显示多少行结果
```

# 参考
* https://blog.csdn.net/sayyy/article/details/80006939