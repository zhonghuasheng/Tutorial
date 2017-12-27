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
```