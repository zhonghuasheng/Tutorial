### Centos安装Redis

* wget http://download.redis.io/releases/redis-4.0.11.tar.gz
* tar xzf redis-4.0.11.tar.gz
* cd redis-4.0.11
* make
    * Error 1: /bin/sh: cc: command not found
        * 需要安装gcc：
            * yum -y install gcc gcc-c++ libstdc++-devel
    * Error 2: error: jemalloc/jemalloc.h: No such file or directory
        * 说关于分配器allocator， 如果有MALLOC  这个 环境变量， 会有用这个环境变量的 去建立Redis。
            * make MALLOC=libc