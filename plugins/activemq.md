### Start in Linux
* Download http://activemq.apache.org/download-archives.html
* Start `cd [activemq_install_dir]/bin` `./activemq start`

### Set password
* ActiveMQ使用的是jetty服务器，设置密码时需要先将authenticate设置为true。在 {apache-activemq}/conf/jetty.xml文件中

```xml
<bean id="securityConstraint" class="org.eclipse.jetty.http.security.Constraint">
        <property name="name" value="BASIC" />
        <property name="roles" value="admin" />
        <property name="authenticate" value="true" />
</bean>
```
* 修改登陆的用户名和密码保护，在{apache-activemq}/conf/jetty-realm.properties文件中
```properties
# Defines users that can access the web (console, demo, etc.)
# username: password [,rolename ...]
admin: admin, admin
```