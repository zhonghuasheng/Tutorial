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

``` sql
UPDATE test_xml SET xml=replace(xml::text, '</stages>', '<stage stageId="16" status="not start" completeTime=""></stage></stages>')::xml;
```

Visit [here](https://stackoverflow.com/questions/42419720/inserting-xml-nodes-using-plpgsql)

### Add attribute in XML node

```sql
UPDATE test_xml SET xml(column name)=replace(xml(column name)::text, 'stageId="4"'(attribute), 'stageId="4" selected="false"'(new attribute))::xml(change to xml) WHERE XXX;

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
    IF (select exists(SELECT unnest(xpath('/stages/stage[@attribute1=4][@attribute2="test"]', cast(test.xml as xml))) as text FROM test_xml as test WHERE userid=uid)) THEN
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

```sql
\i C:/batchexc/batch.sql
```

