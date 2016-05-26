### Error
ERROR [localhost-startStop-1][Cache:120] Unable to set localhost. This prevents creation of a GUID. Cause was: yt00335-116.augmentum.com.cn: yt00335-116.augmentum.com.cn
java.net.UnknownHostException: yt00335-116.augmentum.com.cn: yt00335-116.augmentum.com.cn
	at java.net.InetAddress.getLocalHost(InetAddress.java:1475)


[lukechen@yt00335-116 Desktop]$ vi /etc/hosts
[lukechen@yt00335-116 Desktop]$ cat /etc/hosts
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4 yt00335-116.augmentum.com.cn
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6 yt00335-116.augmentum.com.cn