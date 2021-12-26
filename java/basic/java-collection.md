# 目录
* [0. Java集合框架历史](#Java集合框架历史)
* [1. Java集合概述](#1.Java集合概述)
    * [Collection集合概述](Collection集合概述)
    * [Map集合概述](#Map集合概述)
    * [Concurrent包下的集合概述](#Concurrent包下的集合概述)

* [2. Java集合详解](#2.Java集合详解)
    * [Collection集合下常用实现类详解](#Collection集合下常用实现类详解)
	* [Iterator接口源码解析](#Iterator接口源码解析)
        * [Collection接口源码解析](#Collection接口源码解析)
        * [List接口源码解析](#List接口源码解析)
            * [AbstractCollection抽象类源码解析](#AbstractCollection抽象类源码解析)
                * [AbstractList抽象类源码解析](#AbstractList源码解析)
		            * [ArrayList源码解析和使用](#ArrayList源码解析和使用)
                    * [Vector源码解析和使用](#Vector源码解析和使用)
                        * [Stack源码解析和使用](#Stack源码解析和使用)
                    * [AbstractSequentialList抽象类源码解析](#AbstractSequentialList抽象类源码解析)
                        * [LinkedList源码解析和使用](#LinkedList源码解析和使用)
        * [Set接口源码解析](#Set接口源码解析)
            * [AbstractSet源码解析](#AbstractSet源码解析)
                * [HashSet源码解析和使用](#HashSet源码解析和使用)
                * [TreeSet源码解析和使用](#TreeSet源码解析和使用)
        * [Queue接口源码解析](#Queue接口源码解析)
            * [Deque接口源码解析](#Deque接口源码解析)
                * [LinkedList使用](#LinkedList使用)
    * [Map集合下常用实现类详解](#Map集合下常用实现类详解)
        * [AbstractMap接口源码解析](#AbstractMap接口源码解析)
            * [HashMap源码解析和使用](#HashMap源码解析和使用)
            * [WeakHashMap源码解析和使用](#WeakHashMap源码解析和使用)
            * [TreeMap源码解析和使用](#TreeMap源码解析和使用)
        * [Hashtable源码解析和使用](#Hashtable源码解析和使用)
    * [Concurrent包下常用实现类详解](#Concurrent包下常用实现类详解)

* [3. 集合框架中体现的设计模式和编程规范](#3.集合框架中体现的设计模式和编程规范)
    * [迭代器模式](#迭代器模式)
    * [适配器模式](#适配器模式)

* [4. 其他](#4.其他)
    * [fail-fast机制](#fail-fast机制)
    * [MarkerInterface](#Marker-Interface)
    * [Collections工具类-操作集合]()
    * [Arrays工具类-操作数组]()
* [5. 圈重点](#5.圈重点)
* [6. Stream](#6.Stream)

* [参考资料](#参考资料)

# 0.Java集合框架历史

摘自Wikipedia [Java Collection Framework](https://en.wikipedia.org/wiki/Java_collections_framework):
```
Collection implementations in pre-JDK 1.2 versions of the Java platform included few data structure classes, but did not contain a collections framework.[3] The standard methods for grouping Java objects were via the array, the Vector, and the Hashtable classes, which unfortunately were not easy to extend, and did not implement a standard member interface.[4]

To address the need for reusable collection data structures, several independent frameworks were developed,[3] the most used being Doug Lea's Collections package,[5] and ObjectSpace Generic Collection Library (JGL),[6] whose main goal was consistency with the C++ Standard Template Library (STL).[7]
```

可知，早期的Java Group通过Array, Vector, HashTable这些类来实现，但是他们难扩展，后期大神们创建了独立的Java Data Structure Framework，并且在日后这些framework被build进入JDK中，慢慢形成了Java Collection Framework。


# 1.Java集合概述
Java的集合类位于java.util.*包下，大体分为2类，Collection和Map，另外就是2个工具类。Concurrent是jdk1.5引入的（在这之前java语言内置对多线程的支持比较有限），主要代码由Doug Lea完成。


## Collection集合概述

1. 概述

* Collection包含3个分支
    ```
    AbstractCollection是抽象类，实现了部分Collection中的API，如contains，toArray, remove, toString等方法。
    ```
    * Queue
        ```
        队列是一种特殊的线性表，允许在表的头部进行删除操作，在表的尾部进行插入操作。有2个继承接口，BlockingQueue(阻塞队列)和Deque(双向队列)。AbstractQueue是抽象类，实现了Queue中的大部分API，常见实现类有LinkedQueue。
        ```
    * List
        ```
        List是一个有序的队列，每个元素都有它的索引，第一个元素的索引值为0。AbstractList是抽象类，实现了List中的大部分API，常见实现类有LinkedList, ArrayList, Vector, Stack。
        ```
    * Set
        ```
        Set是一个不允许有重复元素的集合。 AbstractSet是抽象类，实现了Set中的大部分API，常见的实现类有HashSet, TreeSet。
        ```

## Map集合概述
1. 概述
* Map包含1个分支
    ```
    Map是一个映射接口，即key-value的键值对。AbstractMap是抽象类，实现了Map中的大部分API，HashMap, TreeMap, WeakHashMap是其实现类。
    ```

2. 源码阅读
`接口定义`
```java
public interface Map<K,V>
```

`常用方法`
```java
abstract void                 clear()
abstract boolean              containsKey(Object key)
abstract boolean              containsValue(Object value)
abstract Set<Entry<K, V>>     entrySet()
abstract boolean              equals(Object object)
abstract V                    get(Object key)
abstract int                  hashCode()
abstract boolean              isEmpty()
abstract Set<K>               keySet()
abstract V                    put(K key, V value)
abstract void                 putAll(Map<? extends K, ? extends V> map)
abstract V                    remove(Object key)
abstract int                  size()
abstract Collection<V>        values()
```
```
Map 是一个键值对(key-value)映射接口。Map映射中不能包含重复的键；每个键最多只能映射到一个值。
Map 接口提供三种collection 视图，允许以键集(keySet())、值集(values())或键-值(entrySet())映射关系集的形式查看某个映射的内容。
Map 映射顺序。有些实现类，可以明确保证其顺序，如 TreeMap；另一些映射实现则不保证顺序，如 HashMap 类。
Map 的实现类应该提供2个“标准的”构造方法：第一个，void（无参数）构造方法，用于创建空映射；第二个，带有单个 Map 类型参数的构造方法，用于创建一个与其参数具有相同键-值映射关系的新映射。实际上，后一个构造方法允许用户复制任意映射，生成所需类的一个等价映射。尽管无法强制执行此建议（因为接口不能包含构造方法），但是 JDK 中所有通用的映射实现都遵从它。
```

[Map的三种Collection视图例子 MapTest01.java]
```java
    // key视图
    Set<String> keys = hashMap.keySet();
    Iterator<String> iteratorKeys = keys.iterator();
    while (iteratorKeys.hasNext()) {
        System.out.println(iteratorKeys.next());
    }

    // value视图
    Collection<String> values = hashMap.values();
    Iterator<String> iteratorValues = values.iterator();
    while (iteratorValues.hasNext()) {
        System.out.println(iteratorValues.next());
    }

    // key-value视图
    Set<Entry<String, String>> entrySets = hashMap.entrySet();
    Iterator<Entry<String, String>> iteratorEntrySets = entrySets.iterator();
    while (iteratorEntrySets.hasNext()) {
        Entry<String, String> entry = iteratorEntrySets.next();
        System.out.println(String.format("key: %s, value: %s", entry.getKey(), entry.getValue()));
    }
```
## Concurrent包下的集合概述

1. 概述
* Concurrent主要有3个package组成
    * java.util.concurrent
        ```
        提供大部分关于并发的接口和类，如BlockingQueue, ConcurrentHashMap, ExecutorService等
        ```
    * java.util.concurrent.atomic
        ```
        提供所有的原子类操作，如AtomicInteger, AtomicLong等
        ```
    * java.util.concurrent.locks
        ```
        提供锁相关的类，如Lock, ReentrantLock, ReadWriteLock, Confition等
        ```

# 2.Java集合详解
## Collection集合下常用实现类详解
### Iterator接口源码解析
`总结`
```
iterator.remove()方法必须要在调用了next()方法之后，否则会报IllegalStateException。
if the next method has not yet been called, or the remove method has already been called after the last call to the next method
```

`常用方法`
```java
boolean hasNext()
E next()
void remove()
```

### Collection接口源码解析

`接口定义`：
```java
/* 说明：
Collection集合用于存Object的，不支持存储基础数据类型，这是由Collection接口的定义决定的： Collection<E>
这样写会报错： Syntax error, insert "Dimensions" to complete ReferenceType
List<int> ints = new ArrayList<int>();
*/
public interface Collection<E> extends Iterable<E>

```

`常用方法`
```java
abstract boolean         add(E object)
// addAll参数为E或E的子类
abstract boolean         addAll(Collection<? extends E> collection)
abstract void            clear()
abstract boolean         contains(Object object)
abstract boolean         containsAll(Collection<?> collection)
abstract boolean         equals(Object object)
abstract int             hashCode()
abstract boolean         isEmpty()
abstract Iterator<E>     iterator()
abstract boolean         remove(Object object)
abstract boolean         removeAll(Collection<?> collection)
abstract boolean         retainAll(Collection<?> collection)
/*
* Returns the number of elements in this collection.  If this collection
* contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
* <tt>Integer.MAX_VALUE</tt>.
* Integer.MIN_VALUE是-（2的31次方），Integer.MAX_VALUE是2的31次方减1
*/
abstract int             size()
abstract <T> T[]         toArray(T[] array)
abstract Object[]        toArray()
```

### List接口源码解析
`总结`
```
List是有序的，支持随机访问（通过索引下标访问）
List允许重复的值（原因是它的数据存储方式）

```

`常用方法`
```java
boolean add(E)
void add(int, E)
boolean addAll(int, Collection<? extends E>)
boolean addAll(Collection<? extends E>)
void clear()
boolean contains(Object) //AbstractCollection中通过iterator迭代遍历判断 ArrayList中通过调用indexOf(o) >= 0 来判断是否包含
boolean containsAll(Collection<?>)
boolean equals(Object)
E get(int)
int hashCode()
int indexOf(Object)
boolean isEmpty()
Iterator<E> iterator()
int lastIndexOf(Object)
ListIterator<E> listIterator()
ListIterator<E> listIterator(int)
E remove(int)
boolean remove(Object)
boolean removeAll(Collection<?>)
boolean retainAll(Collection<?> a) // b.retainAll(a) 移除b中有而a中没有的所有元素，所以结果是a的子集
E set(int, E)
int size()
List<E> subList(int, int)
Object[] toArray()
T[] toArray(T[])
```

```java
ListIterator.java
boolean hasNext()
boolean hasPrevioud()
E next()
E previous()
int nextIndex()
int previousIndex()
```

#### AbstractCollection抽象类源码解析
`总结`
```
AbstractCollection是个抽象类，继承自Collection接口，并实现了其中的方法，同时添加了toString()方法
```
`常用方法`
```java
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8; // 减8的原因是某些VMs存储了数据的header
private static <T> T[] finishToArry(T[] r, Iterator<?> it) // 用于将集合转换为数组
private static int hugeCapacity(int minCapacity) // 用于finishToArry
boolean add(E e) // 未实现
boolean addAll(int index, Collection<? extends E> c) // 未实现
void clear()
boolean contains(Object o) // 迭代遍历判断
boolean containsAll(Collection<?> c) // for循环遍历c，然后通过调用contains判断
boolean isEmpty() // 通过判断size()==0，size()未实现
Iterator<E> iterator() // 未实现
boolean remove(Object o) //迭代器遍历删除
boolean removeAll(Collection<?> c)
boolean retainAll(Collection<?> c)
int size();
Object[] toArray()
T[] toArray(T[])
String toString() // 迭代遍历集合，通过StringBuilder接收组合输出
```

#### AbstractList源码解析
`总结`
```
AbstractList中有Itr(继承Iterator)和ListItr(继承ListIterator)两个内部类
在迭代遍历过程中，如果出现对集合的写的行为（list.remove(obj)），会报出ConcurrentModificationException。
AbstractList中仍然没有实现add方法
```
```java
    // list调用remove方法会导致modCount的值改变
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    // 通过调用iterator.remove()可以保证不会报ConcurrentModificationException
    public void remove() {
    if (lastRet < 0)
        throw new IllegalStateException();
    checkForComodification();

    try {
        AbstractList.this.remove(lastRet);
        if (lastRet < cursor)
            cursor--;
        lastRet = -1;
        expectedModCount = modCount;
    } catch (IndexOutOfBoundsException e) {
        throw new ConcurrentModificationException();
    }
```

##### ArrayList源码解析和使用
`总结`
1. ArrayList是一个数据集合，相当于一个动态数组(Object[] elementData)。与Java中的数据相比，它的容量能动态增长，默认长度时10(DEFAULT_CAPACITY)，扩容时新的容量=原始容量 + 原始容量>>1
2. ArrayList实现了RandomAccess接口（Marker Interface），又由于其数据以数据存储，因此支持快速随机查找，但是修改和删除效率不高
```java
// Collections中通过RandomAccess接口判断
    public static <T>
    int binarySearch(List<? extends Comparable<? super T>> list, T key) {
        if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
            return Collections.indexedBinarySearch(list, key);
        else
            return Collections.iteratorBinarySearch(list, key);
    }
```
3. ArrayList不是线程安全的，Vertor是线程安全的（其绝大部分方法都加了synchronized关键字），可在多线程下使用CopyOnWriteArryList。

##### Vector源码解析和使用
`总结`
```java
public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```
1. Vector是一个矢量队列，支持比本的添加、修改、删除、遍历等功能
2. Vector实现了RandomAccess接口，即支持随机访问功能（get(int index)）
3. Vector中的public API都是加了synchronized关键字来保证线程安全

##### Stack源码解析和使用
`总结`
```
Stack是栈，特性是先进后出(FILO, First In Last Out)
Stack是继承自Vector，也是通过数据实现的
```

`常用方法`
```java
Object push(Object element) // 把对象压入堆栈
Object pop() // 移除堆栈顶部对象，并作为此方法的返回值返回该对象
Object peek() // 查看堆栈顶部的对象，但不从堆栈中移除它
```

#### AbstractSequentialList抽象类源码解析
`总结`
```
这个抽象类没什么特别
```

##### LinkedList源码解析和使用
`总结`
```java
public class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, Serializable {}
```
```
LinkedList实现了Deque接口，能对它进行双向列表操作，也就是说顺序访问会非常高效，随机访问效率比较低
LinkedList不是线程安全的，可以通过List list = Collections.synchronizedList(new LinkedList(...))来转换，不过LinkedList的数据类型会丢失
LinkedList中使用Node对象来存储数据
```

`常用方法`
```java
boolean add(E element) // offer属于 offer in interface Deque<E>, add 属于 add in interface Collection<E>
void(int index, E element) // 判断index==size，=就linkLast, or linkBefore
void addFirst(E element)
void addLast(E element)
void clear() // 清空，注意GC
Object clone() // 浅拷贝 shallow copy
Iterator<E> descendingIterator() // 倒着输出
E element() // 获取第一个元素
E get(int index)
E getFirst();
E getLast();
int indexOf(Object object)
int lastIndexOf(Object object)
ListIterator<E> listIterator(int index)
boolean offer(E element) // offer属于 offer in interface Deque<E>, add 属于 add in interface Collection<E>
boolean offerFirst(E element)
boolean offerLast(E element)
E peek() // 返回链表中的第一个元素，并且不会移除
E peekFirst() // 和peek没啥区别
E peekLast()
E poll() // 返回链表中的第一个元素，并且会移除掉
E pollFirst() // 链表为空时会返回null
E pollLast()
E pop() // 链表为空时会报NoSuchElementException，这就是区别
void push(E e) // 添加到链表的第一个元素
E remove（） // 删除第一个元素
E remove
E set(int index, E element) // 替换指定位置的元素
```

LinkedList可以作为FIFO的队列，使用如下方法：
```java
// Queue
boolean add(e) // add比offer的区别在于add时如果capacity超了，会报错，而offer不会
boolean offer(e)
E remove() // 返回队列的第一个元素，并且删除，如果队列为空，报NoSuchElementException
E poll() // 返回队列的第一个元素，并且删除，如果队列为空，返回null
E element() // 返回队列的第一个元素，不删除，如果队列为空，报NoSuchElementException
E peek() // 返回队列的第一个元素，不删除，如果队列为空，返回null
```

LinkedList也可以作为FILO的栈，使用如下方法：
```java
push(e)
pop()
peek()
```

`源码解析`
```java
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```
LinkedList在内部定义了一个叫做Node类型的静态内部类，Node就是一个节点，链表中的节点，有3个属性。

```java
// 3个属性
transient int size = 0; // 集合链表内节点数量
transient Node<E> first; // 首节点
transient Node<E> last; // 尾节点
```

```java
// Appends the specified element to the end of this list
public boolean add(E e) {
    linkLast(e);
    return true;
}

void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}
...
```

### Set接口源码解析

`总结`
```
Set是继承自Collection的接口，是一个不允许有重复元素的结合。
AbstractSet是一个抽象类，继承自AbstractCollection，AbstractCollection实现了Set中的绝大部分函数
HashSet和TreeSet是Set的两个实现类
    HashSet依赖HashMap，它实际上是通过HashMap实现的，HashSet中的元素是无序的
    TreeSet依赖TreeMap，它实际上是通过TreeMap实现的，TreeSet中的元素是有序的
```

#### AbstractSet源码解析

AbstractSet没有对Set做多少的实现，其继承了AbstractCollection

`源码解析`
```java
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection c = (Collection) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }
```

##### HashSet源码解析和使用
`总结`
```
HashSet是一个没有重复元素的集合，它是由HashMap实现的（HashMap中key不能重复），不保证元素的顺序，而且HashSet允许使用null元素。
HashSet是非同步的，因此如果多线程同时访问一个HashSet，而其中至少有一个线程修改了该HashSet的话，那么需要保持外部同步，通常可以对该Set的对象封装来完成同步操作，也可以使用Collections.synchronizedSet方法来完成。
HashSet是通过Iterator迭代遍历的
```

`常用方法`
```java
HashSet()
HashSet(int initialCapacity)
HashSet(int initialCapacity, float loadFactor)
HashSet(int initialCapacity, float loadFactor, boolean dummy)
HashSet(Collection<? extends E> c)
boolean add(E)
void clear()
Object clone()
boolean contains(Object object)
boolean isEmpty()
Iterator<E> iterator()
boolean remove(Object object)
int size()
```

`源码解析`
```java
    private transient HashMap<E,Object> map;

    // Dummy value to associate with an Object in the backing Map
    private transient HashMap<E,Object> map;

    // new一个HashSet的时候其实是new的HashMap
    public HashSet() {
        map = new HashMap<>();
    }

    /*
    * Constructs a new set containing the elements in the specified
    * collection.  The <tt>HashMap</tt> is created with default load factor
    * (0.75) and an initial capacity sufficient to contain the elements in
    * the specified collection.
    *
    * @param c the collection whose elements are to be placed into this set
    * @throws NullPointerException if the specified collection is null
    调用的是Math.max((int) (c.size()/.75f) + 1, 16), 比较(int) (c.size()/.75f) + 1和16的大小
    选择加载因子为.75是出于效率的考虑（时间和空间成本）
    如果HashMap的当前size >= threadhold(也就是Math.max((int) (c.size()/.75f) + 1, 16))，那么在添加元素的时候，size是要翻倍的，也就是说HashMap的容量必须是2的指数，具体跟踪put方法
    */
    public HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

    public V put(K key, V value) {
        if (table == EMPTY_TABLE) {
            /*
            会重新计算capacity int capacity = roundUpToPowerOf2(toSize);
            roundUpToPowerOf2（toSize）
            return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;

            public static int highestOneBit(int i) {
                // HD, Figure 3-1
                i |= (i >>  1);
                i |= (i >>  2);
                i |= (i >>  4);
                i |= (i >>  8);
                i |= (i >> 16);
                return i - (i >>> 1);
            }
            */
            inflateTable(threshold);
        }
        if (key == null)
            return putForNullKey(value);
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, key, value, i); // 这里调用addEntry
        return null;
    }

    // 迭代遍历的时候遍历的是key
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    // add的时候插入的是key
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

    /** 浅拷贝
     * Returns a shallow copy of this <tt>HashSet</tt> instance: the elements
     * themselves are not cloned.
     *
     * @return a shallow copy of this set
     */
    public Object clone() {
        try {
            HashSet<E> newSet = (HashSet<E>) super.clone();
            newSet.map = (HashMap<E, Object>) map.clone();
            return newSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
    /**
     * Save the state of this <tt>HashSet</tt> instance to a stream (that is,
     * serialize it).
     *
     * @serialData The capacity of the backing <tt>HashMap</tt> instance
     *             (int), and its load factor (float) are emitted, followed by
     *             the size of the set (the number of elements it contains)
     *             (int), followed by all of its elements (each an Object) in
     *             no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        s.writeInt(map.capacity());
        s.writeFloat(map.loadFactor());

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet())
            s.writeObject(e);
    }

    /**
     * Reconstitute the <tt>HashSet</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in HashMap capacity and load factor and create backing HashMap
        int capacity = s.readInt();
        float loadFactor = s.readFloat();
        map = (((HashSet)this) instanceof LinkedHashSet ?
               new LinkedHashMap<E,Object>(capacity, loadFactor) :
               new HashMap<E,Object>(capacity, loadFactor));

        // Read in size
        int size = s.readInt();

        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }
```

##### TreeSet源码解析和使用
`总结`
```java
public class TreeSet<E> extends AbstractSet<E> implements NavigableSet<E>, Cloneable, Serializable {}
```
```
TreeSet是一个有序并且没有重复的Set集合，它是通过TreeMap实现的
TreeSet实现了NavigableSet接口，因此其支持集合的导航方法，如lower（返回小于）, floor（返回小于等于）, ceiling（返回大于等于）, higher（返回大于），如果不存在这样的元素，则返回null
```

`源码解析`
```java
    // The backing map. 数据存储在这个对象中
    private transient NavigableMap<E, Object> m;
    private static final Object ORESENT = new Obect();

    public TreeSet() {
        this(new TreeMap<E,Object>());
    }

    public TreeSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    // 带比较器的构造函数
    public TreeSet(Comparator<? super E> comparator) {
        this(new TreeMap<>(comparator));
    }

    TreeSet(NavigableMap<E,Object> m) {
        this.m = m;
    }

    public TreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }

    // 迭代器
    public Iterator<E> iterator() {
        return m.navigableKeySet().iterator();
    }

    public boolean add(E e) {
        return m.put(e, PRESENT)==null;
    }

    /* Returns the least key greater than or equal to the given key, or {@code null} if there is no such key.
    返回集合中大于等于给定元素的最小元素，有点绕，还是英文比较好理解
    */
    public E ceiling(E e) {
        return m.ceilingKey(e);
    }

    /*
    Returns the least key strictly greater than the given key, or {@code null} if there is no such key.
    返回集合中大于给定元素的最小元素
    */
    public E higher(E e) {
        return m.higherKey(e);
    }

    /*
    Returns the greatest key strictly less than the given key, or {@code null} if there is no such key.
    返回集合中小于给定元素的最大元素
    */
    public E lower(E e) {
        return m.lowerKey(e);
    }

    /*
    Returns the greatest key less than or equal to the given key, or {@code null} if there is no such key.
    返回几何中小于或等于给定元素的最大元素
    */
    public E floor(E e) {
        return m.floorKey(e);
    }
```

### Queue接口源码解析

`源码分析`
```java
public interface Queue<E> extends Collection<E> {
    boolean add(e) // add比offer的区别在于add时如果capacity超了，会报错，而offer不会
    boolean offer(e)
    E remove() // 返回队列的第一个元素，并且删除，如果队列为空，报NoSuchElementException
    E poll() // 返回队列的第一个元素，并且删除，如果队列为空，返回null
    E element() // 返回队列的第一个元素，不删除，如果队列为空，报NoSuchElementException
    E peek() // 返回队列的第一个元素，不删除，如果队列为空，返回null
}
```

### Deque接口源码解析

`总结`
```
双向队列，继承自Queue接口，同时提供了丰富的操作队列的方法，具体可参考LinkedList源码分析
```

##### LinkedList使用
Go to here [LinkedList源码解析和使用](#LinkedList源码解析和使用)

## Map集合下常用实现类详解
`总结`
```
Map是一个键值对的接口，Map<K, V>
AbstractMap实现了Map接口，但是几乎没有实现Map中的方法，但是却定义了一些常用的方法
SortedMap继承自Map接口，SortedMap中的内容是排序了的键值对，排序的方法是通过比较器（Comparator）
NavigableMap是继承自SortedMap的接口，相对于SortedMap，NavigableMap有一系列的导航方法，如获取>, <, >=, <=的值
TreeMap继承自AbstractMap，实现了NavigableMap接口，因此，TreeMap是有序的键值对
HashMap继承自AbstractMap，没有实现SortedMap，因此，HashMap不是有序的键值对。HashMap允许插入key或者value为null的元素
Hashtable没有继承自AbstractMap，继承的是Dictionary，实现了Map接口，因此，Hashtable是无序的键值对。Hashtable不允许插入key或者value为null的元素，Hashtable的方法加了synchronized关键字，保证了线程的安全。
WeakhashMap继承自AbstractMap，大致来说它与HashMap的键类型不同，WeakHashMap使用的是“弱键”（内存不足时会被GC收掉）
```
`源码分析`
```java
void clear();
boolean containsKey(Object key);
boolean containsValue(Object value);
Set<Map.Entry<K, V> entrySet();
boolean equals(Object o);
V get(Object obj);
boolean isEmpty();
Set<K> keySet(); // 返回key的集合，因为key不重复，所以用Set接收
V put(K k, V v);
void putAll(Map<? extends K, ? extends V> m);
V remove(Object key);
int size();
Collections<V> values(); // 返回value的结合，values是重复的

// Map中还有一个内置的接口 Map#entrySet()
interface Entry<K,V> {
    K getKey();
    V getValue();
    int hashCode();
    V setValue(V);
    boolean equals(Object obj)
}
```

#### AbstractMap接口源码解析
AbstractMap实现了Map接口，实现了一些通用的方法

`源码解析`
```java
// 巧妙的写法
private static boolean eq(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
}
```

##### HashMap源码解析和使用
`定义`
```java
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {}
```

`构造函数`
```java
public HashMap() {
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR); // initial capacity=16 load factor=0.75
}

public HashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}

public HashMap(int initialCapacity, float loadFactor) {}

public HashMap(Map<? extends K, ? extends V> m) {}
```

`public method`
```java
void clear()
Object clone()
boolean containsKey(Object key)
boolean containsValue(Object value)
Set<Entry<K, V>> entrySet()
V get(Object obj)
boolean isEmpty()
Set<K> keySet()
V put(K k, V v)
void putAll(Map<? extends K, ? extends V>)
V remove(Object obj)
int size()
String toString()
Collection<V> values()
```

`源码解析`
```java
    // 数据存储在这里，一个叫table的变量中
    transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;
    // 看下Entry的定义，每一个Entry是一个单向链表
    static class Entry<K,V> implements Map.Entry<K,V> {
        final K key;
        V value;
        Entry<K,V> next;
        int hash;

        /**
         * Creates new entry.
         */
        Entry(int h, K k, V v, Entry<K,V> n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }
    }
    // 接下来我们就看下HashMap是如何存储值的。我们都知道HashMap中使用put(K k, V v)来存储值
    public V put(K key, V value) {
        if (table == EMPTY_TABLE) {
            inflateTable(threshold);
        }
        if (key == null)
            return putForNullKey(value); // 存储null key，直接调价到table[0]中
        int hash = hash(key); // 首先根据key计算出hash值
        int i = indexFor(hash, table.length); // 再根据hash值，计算出数据索引，也就是Entry<K, V>在table中的索引
        // 然后根据索引找到table中的Entry（是一个单向链表），通过e.next循环遍历单项链表
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            // 将key与每一个Entry的key进行对比，哈希值进行对比，如果key已经存在就替换掉旧值，并且返回旧值
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        // 如果key不存在Entry链表中，就新建一个Entry
        addEntry(hash, key, value, i);
        return null;
    }

    // addEntry的时候索引值为0，
    private V putForNullKey(V value) {
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }

    // 在table中的index是0，所以HashMap有一个固定存储key为null的值，此位置为table的第一个位置
    void addEntry(int hash, K key, V value, int bucketIndex) {
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length); // size翻倍
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        size++;
    }
// 插入null值之后，循环调用Map#put方法插入1-18
::hash:: 0 ::key:: null ::value:: 1 ::bucketIndex:: 0
::hash:: 50 ::key:: 1 ::value:: value 1 ::bucketIndex:: 2
::hash:: 49 ::key:: 2 ::value:: value 2 ::bucketIndex:: 1
::hash:: 48 ::key:: 3 ::value:: value 3 ::bucketIndex:: 0
::hash:: 55 ::key:: 4 ::value:: value 4 ::bucketIndex:: 7
::hash:: 54 ::key:: 5 ::value:: value 5 ::bucketIndex:: 6
::hash:: 53 ::key:: 6 ::value:: value 6 ::bucketIndex:: 5
::hash:: 52 ::key:: 7 ::value:: value 7 ::bucketIndex:: 4
::hash:: 59 ::key:: 8 ::value:: value 8 ::bucketIndex:: 11
::hash:: 58 ::key:: 9 ::value:: value 9 ::bucketIndex:: 10
::hash:: 1650 ::key:: 10 ::value:: value 10 ::bucketIndex:: 2
::hash:: 1614 ::key:: 11 ::value:: value 11 ::bucketIndex:: 14
::hash:: 1615 ::key:: 12 ::value:: value 12 ::bucketIndex:: 15
::hash:: 1612 ::key:: 13 ::value:: value 13 ::bucketIndex:: 12
::hash:: 1613 ::key:: 14 ::value:: value 14 ::bucketIndex:: 13
::hash:: 1610 ::key:: 15 ::value:: value 15 ::bucketIndex:: 10
::hash:: 1611 ::key:: 16 ::value:: value 16 ::bucketIndex:: 11
::hash:: 1608 ::key:: 17 ::value:: value 17 ::bucketIndex:: 8
```

`总结`
```
HashMap继承于AbstractMap类，实现了Map接口。Map是"key-value键值对"接口，AbstractMap实现了"键值对"的通用函数接口。
HashMap是通过"拉链法"实现的哈希表。它包括几个重要的成员变量：table, size, threshold, loadFactor, modCount。
    table是一个Entry[]数组类型，而Entry实际上就是一个单向链表。哈希表的"key-value键值对"都是存储在Entry数组中的。
    size是HashMap的大小，它是HashMap保存的键值对的数量。
    threshold是HashMap的阈值，用于判断是否需要调整HashMap的容量。threshold的值="容量*加载因子"，当HashMap中存储数据的数量达到threshold时，就需要将HashMap的容量加倍。
　　loadFactor就是加载因子。
　　modCount是用来实现fail-fast机制的。
```

##### WeakHashMap源码解析和使用
`定义`
```java
public class WeakhashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {}
```
`构造函数和HashMap类似`
```java
public WeakHashMap() {
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
}

public WeakHashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}

public WeakHashMap(int initialCapacity, float loadFactor) {}

public WeakHashMap(Map<? extends K, ? extends V> m) {}
```

`重要变量`
```java
private static final int DEFAULT_INITIAL_CAPACITY = 16;
private static final int MAXIMUM_CAPACITY = 1 << 30;
private static final float DEFAULT_LOAD_FACTOR = 0.75F;
Entry<K, V>[] table;
private int size;
private int threshold;
private final float loadFactor;
private final ReferenceQueue<Object> queue = new ReferenceQueue<>();
int modCount;
```

`总结`
```
WeakHashMap继承于AbstractMap，并且实现了Map接口。
WeakHashMap是哈希表，但是它的键是"弱键"。WeakHashMap中保护几个重要的成员变量：table, size, threshold, loadFactor, modCount, queue。
    table是一个Entry[]数组类型，而Entry实际上就是一个单向链表。哈希表的"key-value键值对"都是存储在Entry数组中的。
    size是Hashtable的大小，它是Hashtable保存的键值对的数量。
    threshold是Hashtable的阈值，用于判断是否需要调整Hashtable的容量。threshold的值="容量*加载因子"。
    loadFactor就是加载因子。
    modCount是用来实现fail-fast机制的
    queue保存的是“已被GC清除”的“弱引用的键”。

WeakHashMap和HashMap都是通过"拉链法"实现的散列表。它们的源码绝大部分内容都一样，这里就只是对它们不同的部分就是说明。
    WeakReference是“弱键”实现的哈希表。它这个“弱键”的目的就是：实现对“键值对”的动态回收。当“弱键”不再被使用到时，GC会回收它，WeakReference也会将“弱键”对应的键值对删除。
    “弱键”是一个“弱引用(WeakReference)”，在Java中，WeakReference和ReferenceQueue 是联合使用的。在WeakHashMap中亦是如此：如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。 接着，WeakHashMap会根据“引用队列”，来删除“WeakHashMap中已被GC回收的‘弱键’对应的键值对”。
    另外，理解上面思想的重点是通过 expungeStaleEntries() 函数去理解。
```

##### TreeMap源码解析和使用
`构造方法`
```java
public TreeMap() {
    comparator = null;
}

public TreeMap(Comparator<? super K> comparator) {
    this.comparator = comparator;
}

public TreeMap(Map<? extends K, ? extends V> m) {
    comparator = null;
    putAll(m);
}

public TreeMap(SortedMap<K, ? extends V> m) {}
```

`总结`
```
TreeMap 是一个有序的key-value集合，它是通过红黑树实现的。
TreeMap 继承于AbstractMap，所以它是一个Map，即一个key-value集合。
TreeMap 实现了NavigableMap接口，意味着它支持一系列的导航方法。比如返回有序的key集合。
TreeMap 实现了Cloneable接口，意味着它能被克隆。
TreeMap 实现了java.io.Serializable接口，意味着它支持序列化。

TreeMap基于红黑树（Red-Black tree）实现。该映射根据其键的自然顺序进行排序，或者根据创建映射时提供的 Comparator 进行排序，具体取决于使用的构造方法。
TreeMap的基本操作 containsKey、get、put 和 remove 的时间复杂度是 log(n) 。
另外，TreeMap是非同步的。 它的iterator 方法返回的迭代器是fail-fastl的
```
##### Hashtable源码解析和使用
`定义`
```java
public class Hashtable<K, V> extends Dictionary<K, V> implements Map<K, V>, Cloneable, java.io.Serializable {}
```

`构造函数`
```java
public Hashtable() {
    this(11, 0.75f);
}

public Hashtable(int initialCapacity) {
    this(initialCapacity, 0.75f);
}

public Hashtable(int initialCapacity, float loadFactor) {}

public Hashtable(Map<? extends K, ? extends V> t) {}
```

`总结`
```
和HashMap一样，Hashtable 也是一个散列表，它存储的内容是键值对(key-value)映射。
Hashtable 继承于Dictionary，实现了Map、Cloneable、java.io.Serializable接口。
Hashtable 的函数都是同步的，这意味着它是线程安全的。它的key、value都不可以为null。此外，Hashtable中的映射不是有序的。

Hashtable 的实例有两个参数影响其性能：初始容量 和 加载因子。容量 是哈希表中桶 的数量，初始容量 就是哈希表创建时的容量。注意，哈希表的状态为 open：在发生“哈希冲突”的情况下，单个桶会存储多个条目，这些条目必须按顺序搜索。加载因子 是对哈希表在其容量自动增加之前可以达到多满的一个尺度。初始容量和加载因子这两个参数只是对该实现的提示。关于何时以及是否调用 rehash 方法的具体细节则依赖于该实现。
通常，默认加载因子是 0.75, 这是在时间和空间成本上寻求一种折衷。加载因子过高虽然减少了空间开销，但同时也增加了查找某个条目的时间（在大多数 Hashtable 操作中，包括 get 和 put 操作，都反映了这一点）。
```
## Concurrent包下常用实现类详解

# 3.集合框架中体现的设计模式和编程规范

## 迭代器模式

Collection 继承了 Iterable 接口，其中的 iterator() 方法能够产生一个 Iterator 对象，通过这个对象就可以迭代遍历 Collection 中的元素。

从 JDK 1.5 之后可以使用 foreach 方法来遍历实现了 Iterable 接口的聚合对象。

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
for (String item : list) {
    System.out.println(item);
}
```

## 适配器模式

java.util.Arrays#asList() 可以把数组类型转换为 List 类型。

```java
@SafeVarargs
public static <T> List<T> asList(T... a)
```

应该注意的是 asList() 的参数为泛型的变长参数，不能使用基本类型数组作为参数，只能使用相应的包装类型数组。

```java
Integer[] arr = {1, 2, 3};
List list = Arrays.asList(arr);
```

也可以使用以下方式调用 asList()：

```java
List list = Arrays.asList(1, 2, 3);
```

# 4.其他
## fail-fast机制
## Marker Interface

# 5.圈重点
* Collection集合用于存Object的，不支持存储基础数据类型，这是由Collection接口的定义决定的： Collection<E>
* iterator.remove()方法必须要在调用了next()方法之后，否则会报IllegalStateException

# 6.Stream
* List转map，按某个字段分类
```java
    // group by City
    Map<String, List<Employee>> employeesByCity = employees.stream().collect( Collectors.groupingBy(Employee::getCity));
```

# 参考资料
* 集合框架图 https://img-blog.csdn.net/20160124221843905
* Java集合框架概述 https://www.cnblogs.com/xiaoxi/p/6089984.html