# Go语言答疑解惑

## channel
* 从一个关闭的 channel 仍然能读出数据吗
* channel 有哪些应用
* 如何优雅地关闭 channel
* channel 在什么情况下会引起资源泄漏
* 什么是 CSP
* channel 底层的数据结构是什么
* channel 发送和接收元素的本质是什么
* 关于 channel 的 happened-before 有哪些
* 向 channel 发送数据的过程是怎样的
* 从 channel 接收数据的过程是怎样的
* 操作 channel 的情况总结
* 关闭一个 channel 的过程是怎样的

## map
* map 的底层实现原理是什么
* 可以边遍历边删除吗
* map 的删除过程是怎样的
* 可以对 map 的元素取地址吗
* 如何比较两个 map 相等
* 如何实现两种 get 操作
* map 是线程安全的吗
* map 的遍历过程是怎样的
* map 中的 key 为什么是无序的
* float 类型可以作为 map 的 key 吗
* map 的赋值过程是怎样的
* map 的扩容过程是怎样的

## interface
* iface 和 eface 的区别是什么
* Go 接口与 C++ 接口有何异同
* 接口转换的原理
* 如何用 interface 实现多态
* Go 语言与鸭子类型的关系
* 值接收者和指针接收者的区别
* 接口的构造过程是怎样的
* 编译器自动检测类型是否实现接口
* 类型转换和断言的区别
* 接口的动态类型和动态值

## 标准库
* context 如何被取消
* context 是什么
* context 有什么作用
* context.Value 的查找过程是怎样的
* unsafe
* Go指针和unsafe.Pointer有什么区别
* 如何利用unsafe包修改私有成员
* 如何利用unsafe获取slice&map的长度
* 如何实现字符串和byte切片的零拷贝转换

## goroutine 调度器
* g0 栈何用户栈如何切换
* goroutine 如何退出
* goroutine 调度时机有哪些
* goroutine和线程的区别
* GPM 是什么
* M 如何找工作
* mian gorutine 如何创建
* schedule 循环如何启动
* schedule 循环如何运转
* sysmon 后台监控线程做了什么
* 一个调度相关的陷阱
* 什么是 go shceduler
* 什么是M:N模型
* 什么是workstealing
* 描述 scheduler 的初始化过程

## 编译和链接
* Go 程序启动过程是怎样的
* Go 编译相关的命令详解
* Go 编译链接过程概述
* GoRoot 和 GoPath 有什么用
* 逃逸分析是怎么进行的

## 反射
* Go 语言中反射有哪些应用
* Go 语言如何实现反射
* 什么情况下需要使用反射
* 什么是反射
* 如何比较两个对象完全相同

## 数组与切片
* 切片作为函数参数
* 切片的容量是怎样增长的
* 数组和切片有什么异同


## GC 的认识
* 什么是 GC，有什么作用？
* 根对象到底是什么？
* 常见的 GC 实现方式有哪些？Go 语言的 GC 使用的是
* 三色标记法是什么？
* STW 是什么意思？
* 如何观察 Go GC？
* 有了 GC，为什么还会发生内存泄露？
* 并发标记清除法的难点是什么？
* 什么是写屏障、混合写屏障，如何实现？
* Go 语言中 GC 的流程是什么？
* 触发 GC 的时机是什么？
* 如果内存分配速度超过了标记清除的速度怎么办？
* GC 关注的指标有哪些？
* Go 的 GC 如何调优？
* Go 的垃圾回收器有哪些相关的 API？其作用分
* Go 历史各个版本在 GC 方面的改进？
* Go GC 在演化过程中还存在哪些其他设计？为什么没有被采用？
* 目前提供 GC 的语言以及不提供 GC 的语言有哪些？GC 和 No GC 各自的优缺点是什么？
* Go 对比 Java、V8 中 JavaScript 的 GC 性能如何？
* 目前 Go 语言的 GC 还存在哪些问题？
