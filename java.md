### JVM
* JVM初始分配的堆内存由-Xms指定，默认是物理内存的1/64；JVM的最大分配的堆内存由-Xmx指定，默认是物理内存的1/4.默认空余堆内存小于40%时，JVM就会增大堆直到-Xmx最大限制；空余堆内存大于70%时，JVM就会减少堆直到-Xms的最小限制。因此，服务器端一般设置-Xms、-Xmx相等以避免在每次GC后调整堆的大小。

### JConsole
* Java 5开始引入JConsole

### 深入拆解Java虚拟机
* 可以使用-XX:+HeapDumpOnOutOfMemoryError参数来让虚拟机出现OOM的时候自动生成dump文件
* ClassLoader的具体作用就是将class文件加载到jvm虚拟机中去，程序就可以正确运行了。但是，jvm启动的时候，并不会一次性加载所有的class文件，而是根据需要去动态加载。
* Java 虚拟机是如何判定两个 Java 类是相同的。Java 虚拟机不仅要看类的全名是否相同，还要看加载此类的类加载器是否一样。只有两者都相同的情况，才认为两个类是相同的。即便是同样的字节代码，被不同的类加载器加载之后所得到的类，也是不同的。
* ClassLoader
    * BootStrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库，如：rt.jar、resources.jar、charsets.jar等
    * Extension ClassLoader：称为扩展类加载器，负责加载Java的扩展类库，默认加载JAVA_HOME/jre/lib/ext/目下的所有jar。
    * App ClassLoader：称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件。
        * 除了Java默认提供的三个ClassLoader之外，用户还可以根据需要定义自已的ClassLoader，而这些自定义的ClassLoader都必须继承自java.lang.ClassLoader类，也包括Java提供的另外二个ClassLoader（Extension ClassLoader和App ClassLoader）在内，但是Bootstrap ClassLoader不继承自ClassLoader，因为它不是一个普通的Java类，底层由C++编写，已嵌入到了JVM内核当中，当JVM启动后，Bootstrap ClassLoader也随着启动，负责加载完核心类库后，并构造Extension ClassLoader和App ClassLoader类加载器。
