### 学习计划
* Nginx常用来干什么
* Nginx实现动静分离 https://www.jianshu.com/p/037a088eca4f
* [Nginx在CentOS中的安装](plugins/nginx.md##安装)
* [Nginx反向代理服务器搭建](plugins/nginx.md)
* [Nginx实现动静分离](plugins/nginx.md)

### 学习笔记
* Nginx实现动静分离笔记
配置
```
#拦截静态资源
location ~ .*\.(html|htm|gif|jpg|jpeg|bmp|png|ico|js|css)$ {
    root /Users/dalaoyang/Downloads/static;
}
```
```
2020/01/16 16:56:06 [error] 32656#32656: *10 open() "/root/xxx/static/musicstore/static/js/jquery-3.4.1.min.js" failed (13: Permission denied), client: xx.xx.228.168, server: localhost, request: "GET /musicstore/static/js/jquery-3.4.1.min.js HTTP/1.1", host: "musicstore.anythy.cn", referrer: "http://musicstore.anythy.cn/musicstore/"
```
* 拦截的是监听端口下所有的符合规则的静态资源【能否拦截指定URL下的静态资源？】，从nginx日志中可以看到请求会按照url的路径在指定的静态资源文件夹下找到该文件，因此需要在static目录下按照url的规则建立。
* 日志中出现了Permission denied的error，原因是nginx的进程用户是nginx，而我们创建的static文件夹路径的用户是root，因此需要修改用户。有两周方案，修改将ngix赋予static路径下所有文件及文件夹的可读权限，方案二修改nginx的进程用户`vim /etc/nginx/nginx.conf:`。应该采用方案一最小权限原则
```
# user www-data;
user root;
worker_processes auto;
pid /run/nginx.pid;
```
实践发现，Niginx用户是不能Login的，导致了如果不赋予其足够的权限，它是访问不了root创建的文件夹，可放入/tmp目录下，谁都能访问。

## 安装
### 官方推荐安装
* http://nginx.org/en/linux_packages.html#stable
* create the file named /etc/yum.repos.d/nginx.repo with the following contents:
  * [nginx]
    name=nginx repo
    baseurl=http://nginx.org/packages/centos/7/$basearch/
    gpgcheck=0
    enabled=1
* yum install nginx
* nginx -V
*  查看安装目录  rpm -ql nginx
*  配置文件在 /etc/nginx/conf.d/default.conf
*  启动 nginx
*  nginx -s signal     : send signal to a master process: stop, quit, reopen, reload

### 源码编译安装
1. 安装编译工具及库文件 `yum -y install make zlib zlib-devel gcc-c++ libtool  openssl openssl-devel`
2. 安装PCRE（可以使Nginx支持Rewrite功能）
   ```shell
   cd /usr/local/src/
   wget http://downloads.sourceforge.net/project/pcre/pcre/8.35/pcre-8.35.tar.gz
   tar zxvf pcre-8.35.tar.gz
   cd pcre-8.35
   // 编译安装
   ./configure
   make && make install
   // 查看版本
   pcre-config --version
   ```
3. 安装Nginx
   ```shell
   cd /usr/local/src/
   wget http://nginx.org/download/nginx-1.6.2.tar.gz
   tar zxvf nginx-1.6.2.tar.gz
   cd nginx-1.6.2
   ./configure --prefix=/usr/local/webserver/nginx --with-http_stub_status_module --with-http_ssl_module --with-pcre=/usr/local/src/pcre-8.35
   make && make install
   /usr/local/webserver/nginx/sbin/nginx -v
   ```
4. 配置Nginx
 * 创建Nginx运行的用户 www（目的是最小权限原则）
   ```shell
   /usr/sbin/groupadd www
   /usr/sbin/useradd -g www www
   ```
 * 配置nginx.conf文件

    ```conf
    user www www;
    worker_processes 2; #设置值和CPU核心数一致
    error_log /usr/local/webserver/nginx/logs/nginx_error.log crit; #日志位置和日志级别
    pid /usr/local/webserver/nginx/nginx.pid;
    #Specifies the value for maximum file descriptors that can be opened by this process.
    worker_rlimit_nofile 65535;
    events
    {
    use epoll;
    worker_connections 65535;
    }
    http
    {
    include mime.types;
    default_type application/octet-stream;
    log_format main  '$remote_addr - $remote_user [$time_local] "$request" '
                '$status $body_bytes_sent "$http_referer" '
                '"$http_user_agent" $http_x_forwarded_for';

    #charset gb2312;

    server_names_hash_bucket_size 128;
    client_header_buffer_size 32k;
    large_client_header_buffers 4 32k;
    client_max_body_size 8m;

    sendfile on;
    tcp_nopush on;
    keepalive_timeout 60;
    tcp_nodelay on;
    fastcgi_connect_timeout 300;
    fastcgi_send_timeout 300;
    fastcgi_read_timeout 300;
    fastcgi_buffer_size 64k;
    fastcgi_buffers 4 64k;
    fastcgi_busy_buffers_size 128k;
    fastcgi_temp_file_write_size 128k;
    gzip on;
    gzip_min_length 1k;
    gzip_buffers 4 16k;
    gzip_http_version 1.0;
    gzip_comp_level 2;
    gzip_types text/plain application/x-javascript text/css application/xml;
    gzip_vary on;

    #limit_zone crawler $binary_remote_addr 10m;
    #下面是server虚拟主机的配置
    server
    {
        listen 80;#监听端口
        server_name localhost;#域名
        index index.html index.htm index.php;
        root /usr/local/webserver/nginx/html;#站点目录
        location ~ .*\.(php|php5)?$
        {
        #fastcgi_pass unix:/tmp/php-cgi.sock;
        fastcgi_pass 127.0.0.1:9000;
        fastcgi_index index.php;
        include fastcgi.conf;
        }
        location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|ico)$
        {
        expires 30d;
    # access_log off;
        }
        location ~ .*\.(js|css)?$
        {
        expires 15d;
    # access_log off;
        }
        access_log off;
    }

    }
    ```
 * 检查nginx.conf的正确性 `/usr/local/webserver/nginx/sbin/nginx -t`
 * 启动Nginx `/usr/local/webserver/nginx/sbin/nginx`
 * IP地址访问站点，建议替换html内容，这样可以屏蔽网站的技术信息
 * 其他命令
   ```shell
   /usr/local/webserver/nginx/sbin/nginx -s reload            # 重新载入配置文件
   /usr/local/webserver/nginx/sbin/nginx -s reopen            # 重启 Nginx
   /usr/local/webserver/nginx/sbin/nginx -s stop              # 停止 Nginx
   ```
### Nginx通过域名转发请求
```
server {
    listen       80;
    server_name  musicstore.anythy.cn;

    access_log  /var/log/nginx/musicstore.host.access.log  main;

    location /musicstore/ {
        proxy_set_header  Host  $host;
        proxy_set_header  X-real-ip $remote_addr;
        proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass   http://xx.107.153.xx:8018/;
    }

    location ~ .*\.(jpg|jpeg|png|ico|js|css|gif)$ {
        root /tmp/static;
    }
    location s.anythy.cn.*\.(jpg|jpeg|png|ico|js|css|gif)$ {
        root /tmp/static;
    }
}

server {
    listen       80;
    server_name  seckill.anythy.cn;

    access_log  /var/log/nginx/musicstore.host.access.log  main;

    location / {
        proxy_set_header  Host  $host;
        proxy_set_header  X-real-ip $remote_addr;
        proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass   http://xx.107.153.xx:8019/;
    }
}
```