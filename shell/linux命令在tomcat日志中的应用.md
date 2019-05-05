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