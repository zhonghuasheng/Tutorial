## Redis基础数据类型
Redis包含`String`、`Hash`、`List`、`Set`、`ZSet`、`Stream`、`Bitmap`、`HLL`、`GEO`、`Bitfield`这些数据类型

String
------
* 简单动态字符串(simple dynamic string, SDS)Redis没有直接使用C语言的传统字符串表示，而是自己构建了一种名为简单动态字符串（Simple Dynamic String, SDS）的抽象类型，并将SDS用作Redis的默认字符串表示。
* String底层对应的数据结构是SDS，有好几个结构体，大致在往db插入值前，会判断value的长度，然后决定使用哪个结构体来存值
    ```shell
    struct __attribute__ ((__packed__)) sdshdr8 {
        uint8_t len; /* used */ hdr5已经不足以描述数据长度了，定义一个8bit的 len来描述 2^5 - 2^8 - 1的字符创长度
        uint8_t alloc; /* excluding the header and null terminator */ 
        /**已分配的总长度， 用于对已存在的字符串追加的场景，3.2之前叫free
        * 比如 set a luke01， len=6, 那最开始分给我的时候是字符创长度的2倍，12，现在我要追加 xx，变为luke01xx，就不用重新分配空间，而是在
        * 现有的字符数组追加元素即可，通过空间预分配，极大程度的解决了内存分配产生的问题（碎片等）
        * 作者为啥这么做，据说是因为大家使用redis极大部分存的值都是字符串，但是我觉得append命令我用的不多
        *  **/
        unsigned char flags; /* 3 lsb of type, 5 unused bits */
        char buf[]; //SDS遵循C字符串以空字符结尾的惯例
    };
    struct __attribute__ ((__packed__)) sdshdr64 {
        uint64_t len; /* used */
        uint64_t alloc; /* excluding the header and null terminator */
        unsigned char flags; /* 3 lsb of type, 5 unused bits */
        char buf[]; 
    };
    ```
* SDS相比C语言字符创具有以下优点：
    1. 获取字符串长度的复杂度为O(1)。C语言并不记录自身的长度信息，所以为了获取一个C字符串的长度，要遍历整个字符数组，但是SDS会记录字符长度，可以通过len直接在内存中拿取到整块的数据。
    2. 杜绝缓存溢出。除了获取字符串长度的复杂度高之外，C字符串不记录自身长度带来的另外一个问题是容易造成缓冲区溢出(buffer overflow)。SDS在赋值前会判断剩余的长度，如果长度不够了，就会重新分配空间。
    3. 减少修改字符串长度时所需的内存重分配的次数。C语言中每次值的修改都会触发内存重新分配，SDS通过空间预分配和记录剩余字节数量free来减少字符串append(追加)造成的内存重分配，同时通过惰性删除来减少字符串缩短的操作造成的内存重分配。
        * 通过未使用空间，SDS实现了空间预分配和惰性空间释放两种优化策略
            * 空间预分配： 空间预分配用于优化SDS的字符串增长操作：当SDS的API对一个SDS进行修改，并且需要对SDS进行空间扩展的时候，程序不仅会为SDS分配修改所必须的空间，还会为SDS分配额外的未使用空间。通过空间预分配策略，Redis可以减少连续执行字符串增长操作所需的内存重分配次数。
            * 惰性空间释放：惰性空间释放用于优化SDS的字符串缩短操作：当SDS的API需要缩短SDS保存的字符串时，程序并不立即使用内存重分配来回收缩短后多出来的字节，而是可以通过alloc-len来计算未使用的长度，并等待将来使用。3.2之前有个free属性记录剩余长度，3.2之后改为alloc记录总长度。
    4. 二进制安全。SDS以二进制的方式处理buf数组中的数据，写的时候是什么样，读的时候也是什么样。即使写入\0也能正常读出来
    5. 兼容部分C字符串函数。遵循C字符串以空字符结尾的惯例
* `String`使用场景
    * Redis String可以包含任何数据，最大能存储512M
    * 可以用在计数器/分布式锁/session
