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

### Redis中查看所有数据库的命令
* config get databases

### I/O多路复用技术(multiplexing)
关于I/O多路复用(又被称为“事件驱动”)，首先要理解的是，操作系统为你提供了一个功能，当你的某个socket可读或者可写的时候，它可以给你一个通知。这样当配合非阻塞的socket使用时，只有当系统通知我哪个描述符可读了，我才去执行read操作，可以保证每次read都能读到有效数据而不做纯返回-1和EAGAIN的无用功。写操作类似。操作系统的这个功能通过select/poll/epoll/kqueue之类的系统调用函数来使用，这些函数都可以同时监视多个描述符的读写就绪状况，这样，多个描述符的I/O操作都能在一个线程内并发交替地顺序完成，这就叫I/O多路复用，这里的“复用”指的是复用同一个线程。

下面举一个例子，模拟一个tcp服务器处理30个客户socket。假设你是一个老师，让30个学生解答一道题目，然后检查学生做的是否正确，你有下面几个选择：1. 第一种选择：按顺序逐个检查，先检查A，然后是B，之后是C、D。。。这中间如果有一个学生卡主，全班都会被耽误。这种模式就好比，你用循环挨个处理socket，根本不具有并发能力。2. 第二种选择：你创建30个分身，每个分身检查一个学生的答案是否正确。 这种类似于为每一个用户创建一个进程或者线程处理连接。3. 第三种选择，你站在讲台上等，谁解答完谁举手。这时C、D举手，表示他们解答问题完毕，你下去依次检查C、D的答案，然后继续回到讲台上等。此时E、A又举手，然后去处理E和A。。。 这种就是IO复用模型，Linux下的select、poll和epoll就是干这个的。将用户socket对应的fd注册进epoll，然后epoll帮你监听哪些socket上有消息到达，这样就避免了大量的无用操作。此时的socket应该采用非阻塞模式。这样，整个过程只在调用select、poll、epoll这些调用的时候才会阻塞，收发客户消息是不会阻塞的，整个进程或者线程就被充分利用起来，这就是事件驱动，所谓的reactor模式。