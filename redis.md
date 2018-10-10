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

### Redis中关于密码

设置密码
redis> CONFIG SET requirepass secret_password   # 将密码设置为 secret_password

OK

redis> QUIT                                     # 退出再连接，让新密码对客户端生效

[huangz@mypad]$ redis

redis> PING                                     # 未验证密码，操作被拒绝

(error) ERR operation not permitted

redis> AUTH wrong_password_testing              # 尝试输入错误的密码

(error) ERR invalid password

redis> AUTH secret_password                     # 输入正确的密码

OK

redis> PING                                     # 密码验证成功，可以正常操作命令了

PONG

清空密码

redis> CONFIG SET requirepass ""   # 通过将密码设为空字符来清空密码

OK

redis> QUIT

$ redis                            # 重新进入客户端

redis> PING                        # 执行命令不再需要密码，清空密码操作成功

PONG