# 目录
* [基础概念](#基础概念)
    * [进程与线程](#进程与线程)
    * [单线程与多线程](#单线程与多线程)
    * [实现线程的4中方式](#实现线程的4中方式)
        * [Thread和Runnable的异同](#Thread和Runnable的异同)
        * [thread.start()和runnable.run()的区别](#thread.start()和runnable.run()的区别)

## 基础概念

### 进程与线程
`进程（Process）`是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。 在当代面向线程设计的计算机结构中，进程是线程的容器。程序是指令、数据及其组织形式的描述，进程是程序的实体。是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。程序是指令、数据及其组织形式的描述，进程是程序的实体。进程之间通过TCP/IP的端口来实现相互交互。

`线程（thread）`是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务，多个线程共享本进程的资源。线程的通信就比较简单，有一大块共享的内存，只要大家的指针是同一个就可以看到各自的内存。

`小结`：
1. 进程要分配一大部分的内存，而线程只需要分配一部分栈就可以了
2. 一个程序至少有一个进程,一个进程至少有一个线程
3. 进程是资源分配的最小单位，线程是程序执行的最小单位
4. 一个线程可以创建和撤销另一个线程，同一个进程中的多个线程之间可以并发执行

`并发`： 对于单核cpu来说，多线程并不是同时进行的，操作系统将时间分成了多个时间片，大概均匀的分配给线程，到达某个线程的时间段，该线程运行，其余时间待命，这样从微观上看，一个线程是走走停停的，宏观感官上，在某一时刻似乎所有线程都在运行。并发是针对时间片段来说的，在某个时间段内多个线程处于runnable到running之间，但每个时刻只有一个线程在running，这叫做并发。

### 单线程与多线程
单线程就是进程中只有一个线程。单线程在程序执行时，所走的程序路径按照连续顺序排下来，前面的必须处理好，后面的才会执行。
```java
// 单线程实例
public class SingleThread {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

多个线程组成的程序称为多线程程序。常见的多线程程序如：GUI应用程序、I/O操作、网络容器等。

### 实现线程的4中方式
1. 继承Thread类
2. 实现Runnable接口
3. 实现Callable接口(有返回值)
4. 通过线程池来实现ExecuteService

```java
public class NewThreadThread {

    public static void main(String[] args) {
        new NewThread().start();
    }
}

// 继承自Thread类
class NewThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread running");
    }
}
```
```java
public class NewThreadRunnable {

    public static void main(String[] args) {
        // run方法运行
        new NewRunnable().run();

        // start方法运行
        NewRunnable newRunnable = new NewRunnable();
        Thread thread = new Thread(newRunnable);
        thread.start();
        // 输出
        /**
         * main running
         * Thread-0 running
         */
    }
}

// 实现runnable接口
class NewRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " running");
    }
}
```
```java
public class NewThreadCallable {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable newCallable = new NewCallable();
        FutureTask futureTask = new FutureTask(newCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        Object result = futureTask.get();
        System.out.println(String.valueOf(result));

        Callable<Integer> newCallable2 = new NewCallable2();
        FutureTask<Integer> task = new FutureTask(newCallable2);
        new Thread(task).start();
        Integer i = task.get();
        System.out.println(i);
    }
}
// 实现Callable接口
class NewCallable implements Callable {

    @Override
    public Object call() throws Exception {
        return "Hello World";
    }
}

class NewCallable2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 1;
    }
}
```
```java
public class NewThreadExecutorService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        pool.shutdown();
    }
}
```
#### Thread和Runnable的异同
* Thread 和 Runnable 的相同点：都是“多线程的实现方式”。
* Thread 和 Runnable 的不同点：
    * Thread 是类，而Runnable是接口；Thread本身是实现了Runnable接口的类。我们知道“一个类只能有一个父类，但是却能实现多个接口”，因此Runnable具有更好的扩展性。此外，Runnable还可以用于“资源的共享”。即，多个线程都是基于某一个Runnable对象建立的，它们会共享Runnable对象上的资源（变量）。通常，建议通过“Runnable”实现多线程！

#### thread.start()和runnable.run()的区别
Thread类继承了Runnable接口，调用start()方法会启动一个新的线程来执行相应的run()方法；run()方法和普通的成员方法一样，可以被重复调用，会在当前线程中执行该方法，而不会启动新的线程。(参考上面通过实现Runnable接口来实现新线程)

### 线程的状态与转换
线程共包括以下5种状态。
1. 新建状态(New): 创建了线程对象但尚未调用start()方法时的状态。
2. 就绪状态(Runnable): 也被称为“可执行状态”。线程对象被创建后，其它线程调用了该对象的start()方法，从而来启动该线程。例如，thread.start()。处于就绪状态的线程，随时可能被CPU调度执行。
3. 阻塞状态(Blocked): 阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：
    (01) 等待阻塞 -- 通过调用线程的wait()方法，让线程等待某工作的完成。
    (02) 同步阻塞 -- 线程在获取synchronized同步锁失败(因为锁被其它线程所占用)，它会进入同步阻塞状态。
    (03) 其他阻塞 -- 通过调用线程的sleep()或join()或发出了I/O请求时，线程会进入到阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。
4. 等待状态(Waiting)：线程处于等待状态，处于该状态标识的当前线程需要等待其他线程做出一些特定的操作来唤醒自己。
5. 超时等待状态(Time Waiting)：超时等待状态，与Waiting不同，在等待指定的时间后会自行返回。
6. 终止状态(Terminated): 线程执行完了或者因异常退出了run()方法，该线程结束生命周期。
![](img/thread-states.png)

### 线程的优先级与守护线程
> 线程的优先级
Java中的线程优先级的范围是1～10，默认的优先级是5，10极最高。线程的优先级具有以下特性：
`概率性`: “高优先级线程”被分配CPU的概率高于“低优先级线程”
`随机性`: 根据时间片轮循调度，能够并发执行,无论是是级别相同还是不同，线程调用都不会绝对按照优先级执行，每次执行结果都不一样，调度算法无规律可循，所以线程之间不能有先后依赖关系。无时间片轮循机制时，高级别的线程优先执行，如果低级别的线程正在运行时，有高级别线程可运行状态，则会执行完低级别线程，再去执行高级别线程。如果低级别线程处于等待、睡眠、阻塞状态，或者调用yield()函数让当前运行线程回到可运行状态，以允许具有相同优先级或者高级别的其他线程获得运行机会。因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。结论：yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，yield()将导致线程从运行状态转到可运行状态，但有可能没有效果。
> 用户线程与守护线程
在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程)
用个比较通俗的比如，任何一个守护线程都是整个JVM中所有非守护线程的保姆。只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 `GC (垃圾回收器)`，它就是一个很称职的守护者。User和Daemon两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：如果 User Thread已经全部退出运行了，只剩下Daemon Thread存在了，虚拟机也就退出了。 因为没有了被守护者，Daemon也就没有工作可做了，也就没有继续运行程序的必要了。
值得一提的是，守护线程并非只有虚拟机内部提供，用户在编写程序时也可以自己设置守护线程。下面的方法就是用来设置守护线程的。
```java
// 设定 daemonThread 为 守护线程，default false(非守护线程)
daemonThread.setDaemon(true);

// 验证当前线程是否为守护线程，返回 true 则为守护线程
daemonThread.isDaemon();
```
这里有几点需要注意：
1. thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
2. 在Daemon线程中产生的新线程也是Daemon的。
3. 不要认为所有的应用都可以分配给Daemon来进行服务，比如读写操作或者计算逻辑。

*那么守护线程的作用是什么？*
举例， GC垃圾回收线程：就是一个经典的守护线程，当我们的程序中不再有任何运行的Thread,程序就不会再产生垃圾，垃圾回收器也就无事可做，所以当垃圾回收线程是JVM上仅剩的线程时，垃圾回收线程会自动离开。它始终在低级别的状态中运行，用于实时监控和管理系统中的可回收资源。
应用场景：（1）来为其它线程提供服务支持的情况；（2） 或者在任何情况下，程序结束时，这个线程必须正常且立刻关闭，就可以作为守护线程来使用；反之，如果一个正在执行某个操作的线程必须要正确地关闭掉否则就会出现不好的后果的话，那么这个线程就不能是守护线程，而是用户线程。通常都是些关键的事务，比方说，数据库录入或者更新，这些操作都是不能中断的。
JVM 程序在什么情况下能够正常退出？
The Java Virtual Machine exits when the only threads running are all daemon threads.
上面这句话来自 JDK 官方文档，意思是：如果 JVM 中没有一个正在运行的非守护线程，这个时候，JVM 会退出。换句话说，守护线程拥有自动结束自己生命周期的特性，而非守护线程不具备这个特点


### 线程的基本操作
### synchronized关键字
### 生产者消费者问题

https://www.cnblogs.com/skywang12345/
https://www.cnblogs.com/walixiansheng/p/9588603.html
https://segmentfault.com/u/niteip/articles?sort=vote
https://www.cnblogs.com/qq1290511257/p/10645106.html
https://www.cnblogs.com/developer_chan/p/10391365.html
Java多线程中的钩子线程https://www.exception.site/java-concurrency/java-concurrency-hook-thread