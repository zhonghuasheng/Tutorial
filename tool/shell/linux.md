### 学习笔记
* [Zero-Copy的前世今生](http://note.youdao.com/noteshare?id=872d76d04ec285d105c3d31d68dbcc00&sub=FF49E845C211478B9777CBBAEFA6A753)

* 清空文件 sudo sh -c "echo '' > a.txt"
* 重命名 mv folderAName newFolderName
* 创建软链 ln -s sourcefile targetfile
    > ln -s /xxx/xx/eclipse eclipseLink
* 使用代理下载文件 wget -e use_proxy=yes -e http_proxy=x.x.x.x:1080
* 设置ulimit sudo sh -c "ulimit -n 65535 && exec su userA"
## 网络篇
### ssh
* lsof -i:22
* 安装基础工具
* sudo apt-get install openssh-server openssh-client
### 查看所有开放的端口
* 可以通过`netstat -anp`来查看哪些端口被打开
    * -a 显示所有
    * -n 不用别名显示，只用数字显示
    * -p 显示进程号和进程名
    * `netstat -natpl` 显示tcp的侦听端口
    * `netstat -naupl` 显示udp的侦听端口
### CentOS7开放端口：CentOS7已经使用firewall作为防火墙，不再使用iptables
* root用户
* 开启防火墙 systemctl start firewalld.service
* 开启端口 firewall-cmd --zone=public --add-port=50013/tcp --permanent
    * --zone-public:表作用域的公共
    * --add-port=8080/tcp：添加tcp协议的端口为8080
    * --permanent: 永久生效，无此参数表示临时生效
* 重启防火墙 systemctl restart firewalld.service
* 重新载入配置 firewall-cmd --reload
* firewall-cmd --list-ports

### 测试域名能不能解析
* nslookup server
    * eg: `nslookup www.baidu.com`

### 测试端口是否开放
* `telnet ip port`

### Linux邮件服务器：Postfix
http://cn.linux.vbird.org/linux_server/0380mail.php

* Linux输出重定向
    ```
    linux 环境中支持输入输出重定向，用符号<和>来表示。
    0、1和2分别表示标准输入、标准输出和标准错误信息输出，
    将一个脚本的执行过程及执行结果打印到日志的常用命令：
    ./myscript.sh 2>&1 | tee mylog.log
    可以用来指定需要重定向的标准输入或输出，比如 2>a.txt 表示将错误信息输出到文件a.txt中。
    同时，还可以在这三个标准输入输出之间实现重定向，比如将错误信息重定向到标准输出，可以用 2>&1来实现。
    Linux下还有一个特殊的文件/dev/null，它就像一个无底洞，所有重定向到它的信息都会消失得无影无踪。这一点非常有用，当我们不需要回显程序的所有信息时，就可以将输出重定向到/dev/null。
    ```
* 查看环境变量 env | grep -E 'M2|MAVEN'

### Shadowsocks服务器端安装
* 安装pip `curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py` 有可能python的版本低了
* python get-pip.py
* pip install shadowsocks
* 配置shadowsocks
* vi /etc/shadowsocks.json
  ```json
{
  "server":"0.0.0.0",
  "server_port":0,
  "local_port":1080,
  "password":"xxx",
  "timeout":600,
  "method":"aes-256-cfb"
}
  ```
* 将shadowsocks加入系统服务
* vi /etc/systemd/system/shadowsocks.service
  ```xml
[Unit]
Description=Shadowsocks
[Service]
TimeoutStartSec=0
ExecStart=/usr/local/bin/ssserver -c /etc/shadowsocks.json
[Install]
WantedBy=multi-user.target
  ```
  ```shell
  # 设置开机自启命令
  systemctl enable shadowsocks

  # 启动命令
  systemctl start shadowsocks

  #查看状态命令
  systemctl status shadowsocks
  ```
* 客户端
* 创建一个sh脚本，加入 `nohup sslocal -s remoteserverip -p remoteserverport -b 127.0.0.1 -l localserverport -k password -m aes-256-cfb >/dev/null 2>&1 &`

nohup sslocal -s  3.14.3.65 -p 15432 -b 127.0.0.1 -l 1080 -k abc123_ -m aes-256-cfb >ssl.log 2>&1 &

# 记录服务器CPU和内存的实时使用情况
```shell
#!/bin/bash
fileName=$1

echo "CPU%,MEM%,TIME" > $fileName
for (( i = 0; i < 3000; i++ )) do
    output=`top -b -n1 | grep "Cpu(s)" | awk '{print $2 ","}' | tr -d '\n' && free -m | grep 'Mem' | awk '{print $3/$2 * 100 ","}' | tr -d '\n' && date | awk '{print $4}'`>temp
    echo "$output" >> $fileName
    sleep 1
done
```
* cat redis.conf | grep -v "#" | grep -v "^$" 查看配置文件，去除所有的#，去除所有的空格
# Linux中禁止修改目录及内部文件，这个命令我用在了缩小nginx的权限，将静态文件放在tmp目录下，nginx user能够访问
单个文件 chattr +i aaa.txt
去除则chattr -i aaa.txt
目录及文件 chattr -R +i ttt
使用 chattr -R +i A (-R 递归地修改目录以及其下内容的属性) , 即可保护好A目录下的所有内容.
设置了’i’属性的文件不能进行修改,只有超级用户可以设置或清除该属性.

# Linux命令在tomcat中的应用
* 模板

`192.168.1.189 - - [03/May/2019:19:47:21 +0000] "GET /html/css/main.css?browserId=other HTTP/1.1" 200 19858`

`注意`
1. access log的配置，格式不一定和上面的log一样
2. cut -f1 -d " "是按照空格来截取字符串的， -fn表示第几个

* 访问量排名前20的IP地址
```shell
cat access_log.2019-0* | cut -f1 -d " " | sort | uniq -c | sort -k 1 -n -r | head -20
```

* 访问量排名前20的页面url
```shell
cat access_log.2019-0* | cut -f7 -d " " | sort | uniq -c | sort -k 1 -n -r | head -20
```
* 查看最耗时页面
```shell
cat access_log.2019-0* | sort -k 10 -n -r | head -20
```

* 统计4xx/5xx 请求的占比
```shell
total requests: wc -l access_log.2019-0*
404 requests: awk '$9=='404'' access_log.2019-0* | wc -l
500 requests: awk '$9=='500'' access_log.2019-0* | wc -l
```

* 查找包含某个关键字的log
```shell
awk '/keywords/' access_log* | head -10
awk '/keywords/{print $5,$6}' access_log* | head -10 // 查找包含keywords的关键字并打印第5/6列
```
### vim编辑文件
* 现在还不是在编辑模式,需要在键盘上输入i键进入
* 比如删除第六行,将光标移至要删除的第六行,连续输入两次小写的d,即dd

### CentOS查看实时网络带宽占用
* 安装iftop， yum install iftop
* 查看网卡 ifconfig
* iftop -i eth0

### 百问
> 65535怎么来的
计算机是按照二进制储存数据的，一般用 unsigned int 这种数据类型来储存正整数
在计算机中，每个整数都是用 16 位 2 进制数来表示的，所以最大的数就是 16 个 1，也就是 11111111 11111111
把二进制数 11111111 11111111 转化位十进制数就是 65535

> Linux下profile和bashrc区别
1. /etc/profile
用来设置系统环境参数，比如$PATH. 这里面的环境变量是对系统内所有用户生效的。
2. /etc/bashrc
这个文件设置系统bash shell相关的东西，对系统内所有用户生效。只要用户运行bash命令，那么这里面的东西就在起作用。
3. ~/.bash_profile
用来设置一些环境变量，功能和/etc/profile 类似，但是这个是针对用户来设定的，也就是说，你在/home/user1/.bash_profile 中设定了环境变量，那么这个环境变量只针对 user1 这个用户生效.
4. ~/.bashrc
作用类似于/etc/bashrc, 只是针对用户自己而言，不对其他用户生效。
另外/etc/profile中设定的变量(全局)的可以作用于任何用户,而~/.bashrc等中设定的变量(局部)只能继承/etc/profile中的变量,他们是”父子”关系.
