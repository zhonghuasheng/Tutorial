## 本着学习自用的态度
* 参考文章：https://www.cnblogs.com/leton/p/12037696.html
* 激活工具：链接：https://pan.baidu.com/s/1gzjNQe2bJ-wQeRTT_mdFGg 提取码：gf3j

## 常用设置
* 插件 Lombook， Easy Code(https://blog.csdn.net/qq_38225558/article/details/84479653)
* RestfulToolkit 将url收集显示，点击url可转到代码
* Grep Console插件能让你的Console丰富多彩，并且还能够过滤控制台输出(过滤的时候先选中要过滤的关键字，然后右键选择Grep)
* Rainbow Brackets 让花括号用不同的颜色标记，别于代码阅读区分
* SequenceDiagram:一键生成时序图.一般用它来生成简单的方法时序图，方便我们阅读代码，特别是在代码的调用层级比较多的时候。
使用方法很简单，选中方法名（注意不要选类名），然后点击鼠标右键，选择 Sequence Diagram 选项即可！
* EasyCode: Easycode 可以直接对数据的表生成entity、controller、service、dao、mapper无需任何编码，简单而强大。

### 显示空格
```
开始没开启显示空格的选项（Setting->Editor->Appearance->show whitespaces）
```

### 社区版IDEA如何添加tomcat服务器
1. 在pom.xml中plugins节点下添加tomcat-maven-plugin，目前比较稳定的是tomcat7-maven-plugin, tomcat8-maven-plugin支持不是很好
```xml
<plugins>
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
        <port>8080</port>
        <path>/</path>
        <uriEncoding>UTF-8</uriEncoding>
        <server>tomcat7</server>
        </configuration>
    </plugin>
</plugins>
```

2. 安装smart tomcat插件
```
1. Go to File -> Settings
2. Click Plugins, search smart tomcat
3. Install and restart IDEA
4. Edit Configurations(在IDEA右上边，Run左边的下拉框)
5. Click +, select Smart Tomcat
6. Config Tomcat Server, Deployment, Context Path
```

## 快捷键
* idea常用快捷键设置（改为eclipse相似）: File-> Settings -> search eclipse -> Keymap(change to Eclipse)
```
ctrl + alt +B 查看接口的实现类
ctrl + e 查看Recent Files
Alt + Ins 实现接口的方法，toString(), 构造函数，重写方法
double shift调出Everywhere Search
```
## FAQ
### requested without authorization,  you can copy URL and open it in browser to trust it
```
Settings > Build, Execution.. > Debugger 勾选Allow unsigned requests
```
### IDEA 无法找到jdk，只能找到jre解决方式
```
在IDEA的菜单栏中选中File, 选中Project Structure, 然后，弹出来的对话框左边有一个SDK单击它, 在中间的一栏中有个+号单击它，一单击会出现Add New SDK然后选中第一个JDK；
```
### IntelliJ IDEA：无法创建Java Class文件
```
右键新建不了java class文件
File -> Project Structure -> Project Settings -> Modules
选中 mian folder，然后邮件设置为Sources
保存
```
