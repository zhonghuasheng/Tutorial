### 问题描述
Apach服务器会将日志输出到一个文件导致该文件后期过大。思路是将该文件按天切到不同的文件中，cronolog能完美解决这个问题。

#### 下载工具 https://fossies.org/linux/www/old/cronolog-1.6.2.tar.gz/
* wget https://fossies.org/linux/www/old/cronolog-1.6.2.tar.gz

#### 安装
* cd cronolog-1.6.2
* ./configure --prefix=/usr/local/cronolog //安装路径自己指定
* make
* make install

#### 配置tomcat/bin/catalina.sh文件
将：
```sh
touch "$CATALINA_OUT"
if [ "$1" = "-security" ] ; then
    ......
    org.apache.catalina.startup.Bootstrap "$@" start \
      >> "$CATALINA_OUT" 2>&1 "&"
else
    ......
    org.apache.catalina.startup.Bootstrap "$@" start \
      >> "$CATALINA_OUT" 2>&1 "&"
fi
```
修改为：
```sh
if [ "$1" = "-security" ] ; then
    ......
    org.apache.catalina.startup.Bootstrap "$@" start 2>&1 \
      | /usr/local/cronolog/sbin/cronolog "$CATALINA_BASE"/logs/catalina.%Y-%m-%d.out >> /dev/null &
else
    ......
    org.apache.catalina.startup.Bootstrap "$@" start 2>&1 \
      | /usr/local/cronolog/sbin/cronolog "$CATALINA_BASE"/logs/catalina.%Y-%m-%d.out >> /dev/null &
fi
```
此配置会在tomcat启动之前加载cronolog服务，无需将cronolog服务加入系统service

#### 配置crontab job定期删除日志
30 3 * * * /bin/find /日志路径/logs/ -mtime +14 -type f -name "catalina*.*.out" -exec /bin/rm -f {} \;

建议删除14天以上的文件，根据阿里巴巴开发手册中建议，对于周期性发生的异常便于排查