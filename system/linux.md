* 清空文件 sudo sh -c "echo '' > a.txt"
* 重命名 mv folderAName newFolderName
* 创建软链 ln -s sourcefile targetfile
    > ln -s /xxx/xx/eclipse eclipseLink
* 使用代理下载文件 wget -e use_proxy=yes -e http_proxy=x.x.x.x:1080
* 设置ulimit sudo sh -c "ulimit -n 65535 && exec su userA"
## 网络篇
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