### 学习计划
* 为什么要使用NoSQL?传统数据库瓶颈在哪里？ http://note.youdao.com/noteshare?id=f87e61fe5c72edd4c3a0abc053b0f5dc&sub=wcp1579224031170661
* 一网打尽当下NoSQL类型、适用场景及使用公司 http://note.youdao.com/noteshare?id=412c497a77dd9470e6acd50cabad27d8&sub=wcp1579230147093121
* 什么是NoSQL数据库？都有哪些使用场景？ https://blog.csdn.net/u013213157/article/details/74453443
* MongoDB教程 https://www.runoob.com/mongodb/nosql.html
* MongoDB的索引
* MongoDB分片技术
* MongoDB数据备份
* MongoDB监控
* MongoDB高级教程

### 学习笔记
* nosql是not only sql的意思。是近今年新发展起来的存储系统。当前使用最多的是key-value模型，是一种非关系型数据库，主要是解决是海量数据下的数据库性能和扩展能力。它最大的特点在于要求的数据量大，对事物的要求低。
* 存储结构是面向对象的，但是关系型数据库却是关系的，所以在每次存储或者查询数据时，我们都需要做转换。
* 当下已经存在很多的NoSQL数据库，比如MongoDB、Redis、Riak、HBase、Cassandra等等。每一个都拥有以下几个特性中的一个：
    * 不再使用SQL语言，比如MongoDB、Cassandra就有自己的查询语言
    * 通常是开源项目
    * 为集群运行而生
    * 弱结构化——不会严格的限制数据结构类型
* NoSQL可以大体上分为4个种类：Key-value、Document-Oriented、Column-Family Databases以及 Graph-Oriented Databases。
    * Key-Value: 键值数据库就像在传统语言中使用的哈希表。你可以通过key来添加、查询或者删除数据，鉴于使用主键访问，所以会获得不错的性能及扩展性。
        * 产品：Riak、Redis、Memcached、Amazon’s Dynamo、Project Voldemort
        * 有谁在使用：GitHub （Riak）、BestBuy （Riak）、Twitter （Redis和Memcached）、StackOverFlow （Redis）、 Instagram （Redis）、Youtube （Memcached）、Wikipedia（Memcached）
        * 适用的场景
            * 储存用户信息，比如会话、配置文件、参数、购物车等等。这些信息一般都和ID（键）挂钩，这种情景下键值数据库是个很好的选择。
        * 不适用场景
            1. 取代通过键查询，而是通过值来查询。Key-Value数据库中根本没有通过值查询的途径。
            2. 需要储存数据之间的关系。在Key-Value数据库中不能通过两个或以上的键来关联数据。
            3. 事务的支持。在Key-Value数据库中故障产生时不可以进行回滚。
    * 面向文档（Document-Oriented）数据库: 面向文档数据库会将数据以文档的形式储存。每个文档都是自包含的数据单元，是一系列数据项的集合。每个数据项都有一个名称与对应的值，值既可以是简单的数据类型，如字符串、数字和日期等；也可以是复杂的类型，如有序列表和关联对象。数据存储的最小单位是文档，同一个表中存储的文档属性可以是不同的，数据可以使用XML、JSON或者JSONB等多种形式存储。
        * 产品：MongoDB、CouchDB、RavenDB
        * 有谁在使用：SAP （MongoDB）、Codecademy （MongoDB）、Foursquare （MongoDB）、NBC News （RavenDB）
        * 适用的场景
            1. 日志。企业环境下，每个应用程序都有不同的日志信息。Document-Oriented数据库并没有固定的模式，所以我们可以使用它储存不同的信息。
            2. 分析。鉴于它的弱模式结构，不改变模式下就可以储存不同的度量方法及添加新的度量。
        * 不适用场景
            * 在不同的文档上添加事务。Document-Oriented数据库并不支持文档间的事务，如果对这方面有需求则不应该选用这个解决方案。
    * 列存储（Wide Column Store/Column-Family）数据库: 列存储数据库将数据储存在列族（column family）中，一个列族存储经常被一起查询的相关数据。举个例子，如果我们有一个Person类，我们通常会一起查询他们的姓名和年龄而不是薪资。这种情况下，姓名和年龄就会被放入一个列族中，而薪资则在另一个列族中。
        * 产品：Cassandra、HBase
        * 有谁在使用：Ebay （Cassandra）、Instagram （Cassandra）、NASA （Cassandra）、Twitter （Cassandra and HBase）、Facebook （HBase）、Yahoo!（HBase）
        * 适用的场景
            1. 日志。因为我们可以将数据储存在不同的列中，每个应用程序可以将信息写入自己的列族中。
            2. 博客平台。我们储存每个信息到不同的列族中。举个例子，标签可以储存在一个，类别可以在一个，而文章则在另一个。
        * 不适用场景
            1. 如果我们需要ACID事务。Vassandra就不支持事务。
            2. 原型设计。如果我们分析Cassandra的数据结构，我们就会发现结构是基于我们期望的数据查询方式而定。在模型设计之初，我们根本不可能去预测它的查询方式，而一旦查询方式改变，我们就必须重新设计列族。
    * 图（Graph-Oriented）数据库：图数据库允许我们将数据以图的方式储存。实体会被作为顶点，而实体之间的关系则会被作为边。比如我们有三个实体，Steve Jobs、Apple和Next，则会有两个“Founded by”的边将Apple和Next连接到Steve Jobs。
        * 产品：Neo4J、Infinite Graph、OrientDB
        * 有谁在使用：Adobe （Neo4J）、Cisco （Neo4J）、T-Mobile （Neo4J）
        * 适用的场景
            1. 在一些关系性强的数据中
            2. 推荐引擎。如果我们将数据以图的形式表现，那么将会非常有益于推荐的制定
        * 不适用场景
            * 不适合的数据模型。图数据库的适用范围很小，因为很少有操作涉及到整个图。
    * XML数据库：高效的存储XML数据，并支持XML的内部查询语法，比如XQuery,Xpath。
        * 产品：Berkeley DB XML、BaseX
    * 对象存储：通过类似面向对象语言的语法操作数据库，通过对象的方式存取数据。
        * db4o、Versant
* MongoDB 是由C++语言编写的，是一个基于分布式文件存储的开源数据库系统。在高负载的情况下，添加更多的节点，可以保证服务器性能。MongoDB 旨在为WEB应用提供可扩展的高性能数据存储解决方案。MongoDB 将数据存储为一个文档，数据结构由键值(key=>value)对组成。MongoDB 文档类似于 JSON 对象。字段值可以包含其他文档，数组及文档数组。
    `优点`
    * MongoDB 是一个面向文档存储的数据库，操作起来比较简单和容易。
    * 你可以在MongoDB记录中设置任何属性的索引 (如：FirstName="Sameer",Address="8 Gandhi Road")来实现更快的排序。
    * 你可以通过本地或者网络创建数据镜像，这使得MongoDB有更强的扩展性。
    * 如果负载的增加（需要更多的存储空间和更强的处理能力） ，它可以分布在计算机网络中的其他节点上这就是所谓的分片。
    * Mongo支持丰富的查询表达式。查询指令使用JSON形式的标记，可轻易查询文档中内嵌的对象及数组。
    * MongoDb 使用update()命令可以实现替换完成的文档（数据）或者一些指定的数据字段 。
    * Mongodb中的Map/reduce主要是用来对数据进行批量处理和聚合操作。
    * Map和Reduce。Map函数调用emit(key,value)遍历集合中所有的记录，将key与value传给Reduce函数进行处理。
    * Map函数和Reduce函数是使用Javascript编写的，并可以通过db.runCommand或mapreduce命令来执行MapReduce操作。
    * GridFS是MongoDB中的一个内置功能，可以用于存放大量小文件。
    * MongoDB允许在服务端执行脚本，可以用Javascript编写某个函数，直接在服务端执行，也可以把函数的定义存储在服务端，下次直接调用即可。
    * MongoDB支持各种编程语言:RUBY，PYTHON，JAVA，C++，PHP，C#等多种语言。
* 教程
    * 创建数据库目录
        * MongoDB的数据存储在data目录的db目录下，但是这个目录在安装过程不会自动创建，所以你需要手动创建data目录，并在data目录中创建db目录。以下实例中我们将data目录创建于根目录下(/)。注意：/data/db 是 MongoDB 默认的启动的数据库路径(--dbpath)。`mkdir -p /data/db`
    * MongoDB概念解析
        * database: 数据库
        * collection: 数据库表/集合，相当于ORM DB的table
        * document: 数据记录行/文档，相当于ORM DB的row
        * field: 数据字段/域，相当于ORM DB的column
        * index: 索引
        * MongoDB不支持表连接，MongoDB主动将_id字段设置为主键
    * 常用命令：
        * show dbs 显示所有数据库
        * db 显示当前连接的数据库
        * use dbname 切换数据库，如果dbname不存在，就会创建一个，当创建的db中存了数据时才会出现在show dbs列表中
        * db.dropDatabase() 删除数据库
        * db.createCollection(name, option) 创建集合
        * show collections 显示集合
        * db.collection.drop() 删除集合
        * db.COLLECTION_NAME.insert(document) 或者 db.col.save(document) 插入文档
        * db.COLLECTION_NAME.find() 查找所有文档
        * db.COLLECTION_NAME.findOne() 查找一个文档
        * db.collection.update(<query>, <update>, {upsert: <boolean>, multi: <boolean>, writeConcern: <document>})
            * upsert如果不存在更新的记录，是否插入obj,默认false; multi默认为false,只更新找到的第一条记录，如果为true，更新找到的全部记录；writeConcern是否抛出异常级别
            ```
            只更新第一条记录：
            db.col.update( { "count" : { $gt : 1 } } , { $set : { "test2" : "OK"} } );
            全部更新：
            db.col.update( { "count" : { $gt : 3 } } , { $set : { "test2" : "OK"} },false,true );
            只添加第一条：
            db.col.update( { "count" : { $gt : 4 } } , { $set : { "test5" : "OK"} },true,false );
            全部添加进去:
            db.col.update( { "count" : { $gt : 5 } } , { $set : { "test5" : "OK"} },true,true );
            全部更新：
            db.col.update( { "count" : { $gt : 15 } } , { $inc : { "count" : 1} },false,true );
            只更新第一条记录：
            db.col.update( { "count" : { $gt : 10 } } , { $inc : { "count" : 1} },false,false );
            ```
        * db.collection.remove(<query>, {justOne: <boolean>, writeConcern: <document>})
            * query删除的文档条件
            * justOne默认为false，删除所有匹配的文档；否则设置为true或1，只删除匹配的第一个文档
            * writeConcern抛出异常的级别
            db.collection.remove({})删除所有文档，remove() 方法 并不会真正释放空间。官方推荐使用deleteOne()和deleteMany()
        * db.collection.find(query, projection)
            * db.collectin.find({"name":"Luke"}, {name: 1, age: 1, _id:0})返回name和age字段，从结果中去掉_id字段
            * AND OR > <条件查询 https://www.runoob.com/mongodb/mongodb-query.html
        * mongodb://username:password@ip:port/database 连接数据库
        * $type查询 db.col.find({"title" : {$type : 'string'}}) 如果想获取 "col" 集合中 title 为 String 的数据
        * db.COLLECTION_NAME.find().limit(NUMBER) 如果你需要在MongoDB中读取指定数量的数据记录，可以使用MongoDB的Limit方法，limit()方法接受一个数字参数，该参数指定从MongoDB中读取的记录条数。
        * db.COLLECTION_NAME.find().limit(NUMBER).skip(NUMBER) 使用limit()方法来读取指定数量的数据外，还可以使用skip()方法来跳过指定数量的数据，skip方法同样接受一个数字参数作为跳过的记录条数。
        * db.COLLECTION_NAME.find().sort({KEY:1}) 在 MongoDB 中使用 sort() 方法对数据进行排序，sort() 方法可以通过参数指定排序的字段，并使用 1 和 -1 来指定排序的方式，其中 1 为升序排列，而 -1 是用于降序排列。
        * db.collection.createIndex(keys, options) 语法中 Key 值为你要创建的索引字段，1 为指定按升序创建索引，如果你想按降序来创建索引指定为 -1 即可。db.col.createIndex({"title":1,"description":-1})
            * options: 多个选项 https://www.runoob.com/mongodb/mongodb-indexing.html
                * background 建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加 "background" 可选参数。 "background" 默认值为false。
                * unique 建立的索引是否唯一。指定为true创建唯一索引。默认值为false.
                ```
                db.values.createIndex({open: 1, close: 1}, {background: true})
                ```
        * db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION) 聚合https://www.runoob.com/mongodb/mongodb-aggregate.html
        *
* MongoDB复制是将数据同步在多个服务器的过程。复制提供了数据的冗余备份，并在多个服务器上存储数据副本，提高了数据的可用性， 并可以保证数据的安全性。复制还允许您从硬件故障和服务中断中恢复数据。mongodb的复制至少需要两个节点。其中一个是主节点，负责处理客户端请求，其余的都是从节点，负责复制主节点上的数据。mongodb各个节点常见的搭配方式为：一主一从、一主多从。主节点记录在其上的所有操作oplog，从节点定期轮询主节点获取这些操作，然后对自己的数据副本执行这些操作，从而保证从节点的数据与主节点一致。
* Install on CentOS
    * 确保系统是X86_64架构的 `uname -a`
    * 添加mongodb repo `/etc/yum.repos.d`
    * 安装 参考官文:https://docs.mongodb.com/manual/tutorial/install-mongodb-on-red-hat/