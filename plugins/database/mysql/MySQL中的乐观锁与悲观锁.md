* https://blog.csdn.net/truelove12358/article/details/54963037
* https://blog.csdn.net/yanghan1222/article/details/80449528

悲观锁与乐观锁是两种常见的资源并发锁设计思路，也是并发编程中一个非常基础的概念。本文将对这两种常见的锁机制在数据库数据上的实现进行比较系统的介绍。

### 悲观锁（Pessimistic Lock）
悲观锁的特点是先获取锁，再进行业务操作，即“悲观”的认为获取锁是非常有可能失败的，因此要先确保获取锁成功再进行业务操作。通常所说的“一锁二查三更新”即指的是使用悲观锁。通常来讲在数据库上的悲观锁需要数据库本身提供支持，即通过常用的select … for update操作来实现悲观锁。当数据库执行select for update时会获取被select中的数据行的行锁，因此其他并发执行的select for update如果试图选中同一行则会发生排斥（需要等待行锁被释放），因此达到锁的效果。select for update获取的行锁会在当前事务结束时自动释放，因此必须在事务中使用。

这里需要注意的一点是不同的数据库对select for update的实现和支持都是有所区别的，例如oracle支持select for update no wait，表示如果拿不到锁立刻报错，而不是等待，mysql就没有no wait这个选项。另外mysql还有个问题是select for update语句执行中所有扫描过的行都会被锁上，这一点很容易造成问题。因此如果在mysql中用悲观锁务必要确定走了索引，而不是全表扫描。

### 乐观锁（Optimistic Lock）
乐观锁的特点先进行业务操作，不到万不得已不去拿锁。即“乐观”的认为拿锁多半是会成功的，因此在进行完业务操作需要实际更新数据的最后一步再去拿一下锁就好。
乐观锁在数据库上的实现完全是逻辑的，不需要数据库提供特殊的支持。一般的做法是在需要锁的数据上增加一个版本号，或者时间戳，然后按照如下方式实现：

复制代码
```sql
1. SELECT data AS old_data, version AS old_version FROM …;
2. 根据获取的数据进行业务操作，得到new_data和new_version
3. UPDATE SET data = new_data, version = new_version WHERE version = old_version
if (updated row > 0) {
    // 乐观锁获取成功，操作完成
} else {
    // 乐观锁获取失败，回滚并重试
}
```
复制代码

乐观锁是否在事务中其实都是无所谓的，其底层机制是这样：在数据库内部update同一行的时候是不允许并发的，即数据库每次执行一条update语句时会获取被update行的写锁，直到这一行被成功更新后才释放。因此在业务操作进行前获取需要锁的数据的当前版本号，然后实际更新数据时再次对比版本号确认与之前获取的相同，并更新版本号，即可确认这之间没有发生并发的修改。如果更新失败即可认为老版本的数据已经被并发修改掉而不存在了，此时认为获取锁失败，需要回滚整个业务操作并可根据需要重试整个过程。

总结
乐观锁在不发生取锁失败的情况下开销比悲观锁小，但是一旦发生失败回滚开销则比较大，因此适合用在取锁失败概率比较小的场景，可以提升系统并发性能

乐观锁还适用于一些比较特殊的场景，例如在业务操作过程中无法和数据库保持连接等悲观锁无法适用的地方

参考资料 Internal Locking Methods



乐观锁  优缺点：
乐观锁：
乐观锁（ Optimistic Locking ） 相对悲观锁而言，乐观锁机制采取了更加宽松的加锁机制。悲观锁大多数情况下依靠数据库的锁机制实现，以保证操作最大程度的独占性。但随之而来的就是数据库性能的大量开销，特别是对长事务而言，这样的开销往往无法承受。而乐观锁机制在一定程度上解决了这个问题。乐观锁，大多是基于数据版本（ Version ）记录机制实现。何谓数据版本？即为数据增加一个版本标识，在基于数据库表的版本解决方案中，一般是通过为数据库表增加一个 “version” 字段来实现。读取出数据时，将此版本号一同读出，之后更新时，对此版本号加一。更新数据时将读出的版本号作为一个条件，如果数据库表中版本号与此版本号相同则能更新成功，否则更新失败。

例如一个金融系统，当某个操作员读取用户的数据，并在读出的用户数据的基础上进行修改时（如更改用户帐户余额），如果采用悲观锁机制，也就意味着整个操作过 程中（从操作员读出数据、开始修改直至提交修改结果的全过程，甚至还包括操作 员中途去煮咖啡的时间），数据库记录始终处于加锁状态，可以想见，如果面对几百上千个并发，这样的情况将导致怎样的后果。

乐观锁机制在一定程度上解决了这个问题。乐观锁，大多是基于数据版本 （ Version ）记录机制实现。何谓数据版本？即为数据增加一个版本标识，在基于数据库表的版本解决方案中，一般是通过为数据库表增加一个 “version” 字段来实现（也可以采用另一种方式，同样是在需要乐观锁控制的table中增加一个字段，名称无所谓，字段类型使用时间戳timestamp, 和上面的version类似，也是在更新的时候检查当前数据库中数据的时间戳和自己更新前取到的时间戳进行对比，如果一致则OK，否则就是版本冲突）。

优点：
从上面的例子可以看出，乐观锁机制避免了长事务中的数据库加锁开销，大大提升了大并发量下的系统整体性能表现。

缺点：
乐观锁机制往往基于系统中的数据存储逻辑，因此也具备一定的局限性，如在上例中，由于乐观锁机制是在我们的系统中实现，来自外部系统的更新操作不受我们系统的控制，因此可能会造成脏数据被更新到数据库中。在系统设计阶段，应该充分考虑到这些情况出现的可能性，并进行相应调整（如将乐观锁策略在数据库存储过程中实现，对外只开放基于此存储过程的数据更新途径，而不是将数据库表直接对外公开）。


使用实例：



MySQL中的隔离级别和悲观锁及乐观锁示例

1，MySQL的事务支持
MySQL的事务支持不是绑定在MySQL服务器本身，而是与存储引擎相关：



MyISAM：不支持事务，用于只读程序提高性能
InnoDB：支持ACID事务、行级锁、并发
Berkeley DB：支持事务

2，隔离级别
隔离级别决定了一个session中的事务可能对另一个session的影响、并发session对数据库的操作、一个session中所见数据的一致性
ANSI标准定义了4个隔离级别，MySQL的InnoDB都支持：

Java代码

READ UNCOMMITTED：最低级别的隔离，通常又称为dirty read，它允许一个事务读取还没commit的数据，这样可能会提高性能，但是dirty read可能不是我们想要的
READ COMMITTED：在一个事务中只允许已经commit的记录可见，如果session中select还在查询中，另一session此时insert一条记录，则新添加的数据不可见
REPEATABLE READ：在一个事务开始后，其他session对数据库的修改在本事务中不可见，直到本事务commit或rollback。在一个事务中重复select的结果一样，除非本事务中update数据库。
SERIALIZABLE：最高级别的隔离，只允许事务串行执行。为了达到此目的，数据库会锁住每行已经读取的记录，其他session不能修改数据直到前一事务结束，事务commit或取消时才释放锁。
可以使用如下语句设置MySQL的session隔离级别：

[c-sharp] view plain copy
1.  SET TRANSACTION ISOLATION LEVEL {READ UNCOMMITTED | READ COMMITTED | REPEATABLE READ | SERIALIZABLE}
MySQL默认的隔离级别是REPEATABLE READ，在设置隔离级别为READ UNCOMMITTED或SERIALIZABLE时要小心，READ UNCOMMITTED会导致数据完整性的严重问题，而SERIALIZABLE会导致性能问题并增加死锁的机率

3，隔离级别
乐观所和悲观锁策略：
悲观锁：在读取数据时锁住那几行，其他对这几行的更新需要等到悲观锁结束时才能继续
乐观所：读取数据时不锁，更新时检查是否数据已经被更新过，如果是则取消当前更新
一般在悲观锁的等待时间过长而不能接受时我们才会选择乐观锁
悲观锁的例子：

[c-sharp] view plain copy
CREATE PROCEDURE tfer_funds
       (from_account INT, to_account INT,tfer_amount NUMERIC(10,2),
        OUT status INT, OUT message VARCHAR(30))
BEGIN
    DECLARE from_account_balance NUMERIC(10,2);

    START TRANSACTION;


    SELECT balance
      INTO from_account_balance
      FROM account_balance
     WHERE account_id=from_account
       FOR UPDATE;

    IF from_account_balance>=tfer_amount THEN

         UPDATE account_balance
            SET balance=balance-tfer_amount
          WHERE account_id=from_account;

         UPDATE account_balance
            SET balance=balance+tfer_amount
          WHERE account_id=to_account;
         COMMIT;

         SET status=0;
         SET message='OK';
    ELSE
         ROLLBACK;
         SET status=-1;
         SET message='Insufficient funds';
    END IF;
END;
乐观锁的例子：

[c-sharp] view plain copy
CREATE PROCEDURE tfer_funds
    (from_account INT, to_account INT, tfer_amount NUMERIC(10,2),
        OUT status INT, OUT message VARCHAR(30) )

BEGIN

    DECLARE from_account_balance    NUMERIC(8,2);
    DECLARE from_account_balance2   NUMERIC(8,2);
    DECLARE from_account_timestamp1 TIMESTAMP;
    DECLARE from_account_timestamp2 TIMESTAMP;

    SELECT account_timestamp,balance
        INTO from_account_timestamp1,from_account_balance
            FROM account_balance
            WHERE account_id=from_account;

    IF (from_account_balance>=tfer_amount) THEN

        -- Here we perform some long running validation that
        -- might take a few minutes */
        CALL long_running_validation(from_account);

        START TRANSACTION;

        -- Make sure the account row has not been updated since
        -- our initial check
        SELECT account_timestamp, balance
            INTO from_account_timestamp2,from_account_balance2
            FROM account_balance
            WHERE account_id=from_account
            FOR UPDATE;

        IF (from_account_timestamp1 <> from_account_timestamp2 OR
            from_account_balance    <> from_account_balance2)  THEN
            ROLLBACK;
            SET status=-1;
            SET message=CONCAT("Transaction cancelled due to concurrent update",
                " of account"  ,from_account);
        ELSE
            UPDATE account_balance
                SET balance=balance-tfer_amount
                WHERE account_id=from_account;

            UPDATE account_balance
                SET balance=balance+tfer_amount
                WHERE account_id=to_account;

            COMMIT;

            SET status=0;
            SET message="OK";
        END IF;

    ELSE
        ROLLBACK;
        SET status=-1;
        SET message="Insufficient funds";
    END IF;
END$$  ；
