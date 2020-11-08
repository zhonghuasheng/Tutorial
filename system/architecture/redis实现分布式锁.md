### 需求
在多个服务间保证同一时刻同一时间段内只有一个用户能获取到锁

### 面临的挑战
1. 死锁问题：线程A获取到了锁，但是程序执行异常，导致锁未及时释放。可通过try catch finally来在finally中执行程序异常释放锁的问题；另外，通过`set key value px nx`仅当key不存在的时候设置key，并设置过期时间（建议毫秒）来保证程序执行超时，锁自动释放。
2. 释放锁问题：线程A获取到了锁，线程A在某个操作上长时间执行，导致锁过期，自动释放；线程B获取到了这个锁；线程A执行完毕，准备释放锁，因为设置的`value`值一样，所以就释放了线程B的锁。这就要求释放锁的线程必须是加锁的线程，也就是说要给线程加标记，保证锁的唯一性，其实是区分线程，可以使用ThreadLocal+UUID来保证，使用lua脚本删除key，保证释放锁的原子操作。
3. 集群下的故障转移问题：Redis在进行主从复制时是异步完成的，线程A在master获取到了锁，但是在复制数据到slave的过程中master挂了，导致这个锁没有复制到slave中；然后redis选举一个升级为master，那么这个新的master中没有线程A的那个锁，这时候其他线程是可以获取到锁的，导致互斥失效。思路：原master上有线程A的锁，现master上有线程B的锁，那怎么办呢？如果线程B是在线程A获取的锁过期后获取的，就不存在这个互斥问题，或者线程B在px毫秒之后再获取锁，也不存在互斥问题
4. 多节点redis实现的分布式锁算法(RedLock)： 有效防止单点故障。思路就是在线程A尝试去这N个节点拿锁，每次去一个节点拿锁的时间不能超过M毫秒，当拿到锁的个数超过总个数/2+1个，就认为拿锁成功。

### 最低保证分布式锁的有效性和安全性的要求
1. 互斥：任何时刻只能有一个client获取锁
2. 释放/死锁：释放锁的线程必须是加锁的线程；服务器宕机情况下，也能释放锁
3. 容错：只要多数（一半以上）redis节点在使用，client就可以获取和释放锁

### 实现方式
1. Redis中的Redlock
2. 参考代码
```lua
// 释放锁
if redis.call('get',KEYS[1]) == ARGV[1] then
    return redis.call('del', KEYS[1]);
else
    return 0;
end;
```

```lua
// 减库存
if (redis.call('exists', KEYS[1]) == 1) then
    local stock = tonumber(redis.call('get', KEYS[1]));
    if (stock == -1) then
        return -1;
    end;
    if (stock > 0) then
        redis.call('incrby', KEYS[1], -1);
        return stock;
    end;
    return 0;
end;
return -1;
```

```java

@Slf4j
@Component
public class RedisRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置RedisTemplate的序列化
     * @param redisTemplate
     */
    public RedisRepository(RedisTemplate redisTemplate) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        this.redisTemplate = redisTemplate;
    }


    /**
     * 获取分布式锁
     * @param key 锁
     * @param requestId 锁标识
     * @param expireTime 过期时间（毫秒）
     * @return 加锁是否成功
     */
    public boolean lock(String key, String requestId, int expireTime) {
        if (null == key || null == requestId || expireTime < 0) {
            return false;
        }

        boolean locked = false;
        int tryCount = 3;
        while (!locked && tryCount > 0) {
            locked = redisTemplate.opsForValue().setIfAbsent(key, requestId, expireTime, TimeUnit.MICROSECONDS);
            tryCount--;

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                log.error("获取分布式锁失败, {}", e);
            }
        }

        return locked;
    }

    /**
     * @param key 锁
     * @param requestId 锁标识
     * @return 释放锁是否成功
     */
    public boolean unlockLua(String key, String requestId) {
        if (null == key || null == requestId) {
            return false;
        }

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        // 用于解锁的lua脚本位置
        redisScript.setScriptText("if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end");
        redisScript.setResultType(Long.class);
        // 没有指定序列化方式，默认使用上面配置的
        Object result = redisTemplate.execute(redisScript, Arrays.asList(key), requestId);
        return result.equals(Long.valueOf(1));
    }
```

### 参考文章
* https://cloud.tencent.com/developer/article/1431873