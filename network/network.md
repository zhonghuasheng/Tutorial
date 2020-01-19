WebSocket 建立连接需要先通过一个 http 请求进行和服务端握手。握手通过后连接就建立并保持了。
WebSocket 是为了满足基于 Web 的日益增长的实时通信需求而产生的。在传统的 Web 中，要实现实时通信，通用的方式是采用 HTTP 协议不断发送请求。但这种方式即浪费带宽（HTTP HEAD 是比较大的），又消耗服务器 CPU 占用（没有信息也要接受请求）
* WebSocket（1）-- WebSocket API简介 https://blog.csdn.net/yl02520/article/details/7296223
* WebSocket（2）--为什么引入WebSocket协议 https://blog.csdn.net/yl02520/article/details/7298309
* WebSocket与http长连接的区别 https://www.jianshu.com/p/0c61f0e0cd1a
* Socket 与 WebSocket https://blog.zengrong.net/post/socket-and-websocket/
* WebSocket（二）-WebSocket、Socket、TCP、HTTP区别 https://www.cnblogs.com/merray/p/7918977.html
* TCP连接、Http连接与Socket连接的区别 http://note.youdao.com/noteshare?id=d045b8abde4101cb28892b3dd33f24c4&sub=wcp1578547786163252
* Socket封包、拆包、粘包 http://note.youdao.com/noteshare?id=20da81a278dff7065c2ee932f1be41d6&sub=wcp1578550254412174