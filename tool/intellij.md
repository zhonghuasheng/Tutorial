## 常用设置
* 插件 Lombook， Easy Code(https://blog.csdn.net/qq_38225558/article/details/84479653)

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