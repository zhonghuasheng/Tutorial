### 配置与安装
tar -zxvf apache-maven-3.5.3-bin.tar.gz /usr/local/
vi ~/.bashrc
export M2_HOME=/usr/local/apache-maven-3.5.3
export PATH=${M2_HOME}/bin:$PATH
source ~/.bashrc
mvn -v
### 添加阿里镜像
找到.m2/settings.xml文件

```
<mirrors>
  <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
     <mirrorOf>central</mirrorOf>
  </mirror>
</mirrors>
```

### Configure Proxy

* Configure in settings.xml

```
<proxies>
    <proxy>
        <id>myhttpproxy</id>
        <active>true</active>
        <protocol>http</protocol>
        <host>192.168.1.2</host>
        <port>3128</port>
        <nonProxyHosts>localhost</nonProxyHosts>
    </proxy>
    <proxy>
        <id>myhttpsproxy</id>
        <active>true</active>
        <protocol>https</protocol>
        <host>192.168.1.2</host>
        <port>3128</port>
        <nonProxyHosts>localhost</nonProxyHosts>
    </proxy>
</proxies>
```

* Using mvn command

```
-Dhttps.proxyHost=x.x.x.x -Dhttps.proxyPort=?

`example`  mvn install -Dhttp.proxyHost=10.10.0.100 -Dhttp.proxyPort=8080 -Dhttp.nonProxyHosts=localhost|127.0.0.1

```