## 常用设置

### 显示空格
```
开始没开启显示空格的选项（Setting->Editor->Appearance->show whitespaces）
```
## 快捷键
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