# 目录
* [基础概念](#基础概念)
    * [进程与线程](#进程与线程)

## 基础概念

### 进程与线程
`进程（Process）`是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。 在当代面向线程设计的计算机结构中，进程是线程的容器。程序是指令、数据及其组织形式的描述，进程是程序的实体。是计算机中的程序关于某数据集合上的一次运行活动，是系统进行资源分配和调度的基本单位，是操作系统结构的基础。程序是指令、数据及其组织形式的描述，进程是程序的实体。进程之间通过TCP/IP的端口来实现相互交互。

`线程（thread）`是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务，多个线程共享本进程的资源。线程的通信就比较简单，有一大块共享的内存，只要大家的指针是同一个就可以看到各自的内存。

`小结`：
1. 进程要分配一大部分的内存，而线程只需要分配一部分栈就可以了
2. 一个程序至少有一个进程,一个进程至少有一个线程
3. 进程是资源分配的最小单位，线程是程序执行的最小单位
4. 一个线程可以创建和撤销另一个线程，同一个进程中的多个线程之间可以并发执行

### 单线程与多线程
单线程就是进程中只有一个线程。单线程在程序执行时，所走的程序路径按照连续顺序排下来，前面的必须处理好，后面的才会执行。
```java
public class SingleThread {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

多个线程组成的程序称为多线程程序。常见的多线程程序如：GUI应用程序、I/O操作、网络容器等。

### 实现线程的5中方式
1. 继承Thread类
```java
class NewThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread running");
    }
}
```
2. 实现Runnable接口
```java
public class NewThreadRunnable {

    public static void main(String[] args) {
        // run方法运行
        new NewRunnable().run();

        // start方法运行
        NewRunnable newRunnable = new NewRunnable();
        Thread thread = new Thread(newRunnable);
        thread.start();
    }
}

class NewRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread running");
    }
}
```
3. 实现Callable接口
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

### 线程的状态与转换
### 线程的优先级与守护线程
### 线程的基本操作
### synchronized关键字
### 生产者消费者问题
