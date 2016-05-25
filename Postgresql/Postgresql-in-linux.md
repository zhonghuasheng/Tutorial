#Basic


### 查看postgresql版本信息
    psql --version

### 获取帮助
    psql --help

### 查看postgresql进程
    ps aux|grep postgres
`lukechen 16756  0.0  0.0 112640  1004 pts/1    S+   17:06   0:00 grep --color=auto postgres`

其中S+指进程的状态
ps工具标识进程的5种状态码:
* D 不可中断 uninterruptible sleep (usually IO)
* R 运行 runnable (on run queue)
* S 中断 sleeping
* T 停止 traced or stopped
* Z 僵死 a defunct (”zombie”) process

###service postgresql-9.4 start
start, stop, restart, try-restart, reload, force-reload, status
