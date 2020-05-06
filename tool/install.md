### JDK安装
* 解压
```
    mkdir /usr/lib/jvm
    tar -zxvf jdk-8u192-linux-x64.tar.gz -C /usr/lib/jvm
```
* 文件末尾追加如下内容
```
    vim ~/.bashrc

    #set oracle jdk environment
    export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_181  ## 这里要注意目录要换成自己解压的jdk 目录
    export JRE_HOME=${JAVA_HOME}/jre
    export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
    export PATH=${JAVA_HOME}/bin:$PATH
```
* 使环境变量生效
```
    source ~/.bashrc
```
* 验证
```
    java -version
```