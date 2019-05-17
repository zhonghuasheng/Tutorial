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
            * [TreeHashMap源码解析和使用](#TreeHashMap源码解析和使用)
        * [HashTable源码解析和使用](#HashTable源码解析和使用)
    * [Concurrent包下常用实现类详解](#Concurrent包下常用实现类详解)

* [3. 集合框架中体现的设计模式和编程规范](#3.集合框架中体现的设计模式和编程规范)
    * [迭代器模式](#迭代器模式)
    * [适配器模式](#适配器模式)

* [4. 其他](#4.其他)
    * [fail-fast机制](#fail-fast机制)
    * [MarkerInterface](#Marker Interface)
    * [Collections工具类-操作集合]()
    * [Arrays工具类-操作数组]()
* [5. 圈重点](#5.圈重点)

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
<p>
Map 是一个键值对(key-value)映射接口。Map映射中不能包含重复的键；每个键最多只能映射到一个值。
Map 接口提供三种collection 视图，允许以键集(keySet())、值集(values())或键-值(entrySet())映射关系集的形式查看某个映射的内容。
Map 映射顺序。有些实现类，可以明确保证其顺序，如 TreeMap；另一些映射实现则不保证顺序，如 HashMap 类。
Map 的实现类应该提供2个“标准的”构造方法：第一个，void（无参数）构造方法，用于创建空映射；第二个，带有单个 Map 类型参数的构造方法，用于创建一个与其参数具有相同键-值映射关系的新映射。实际上，后一个构造方法允许用户复制任意映射，生成所需类的一个等价映射。尽管无法强制执行此建议（因为接口不能包含构造方法），但是 JDK 中所有通用的映射实现都遵从它。
</p>

[Map的三种Collection视图例子 MapTest01.java](https://github.com/zhonghuasheng/JAVA/blob/master/basic/src/main/java/com/zhonghuasheng/basic/util/MapTest01.java)

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
HashSet是非同步的，因此如果多线程同时访问一个HashSet，而其中至少有一个线程修改了该HashSet夺得话，那么需要保持外部同步，通常可以对该Set的对象封装来完成同步操作，也可以使用Collections.synchronizedSet方法来完成。
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
#### AbstractMap接口源码解析
##### HashMap源码解析和使用
##### WeakHashMap源码解析和使用
##### TreeHashMap源码解析和使用
##### HashTable源码解析和使用
## Concurrent包下常用实现类详解

# 3.集合框架中体现的设计模式和编程规范
## 迭代器模式
## 适配器模式

# 4.其他
## fail-fast机制
## Marker Interface

# 5.圈重点
* Collection集合用于存Object的，不支持存储基础数据类型，这是由Collection接口的定义决定的： Collection<E>
* iterator.remove()方法必须要在调用了next()方法之后，否则会报IllegalStateException

# 参考资料
* 集合框架图 https://img-blog.csdn.net/20160124221843905
* Java集合框架概述 https://www.cnblogs.com/xiaoxi/p/6089984.html

# Definationis
* Iterator: public interface Iterator<E>
* Iterable: public interface Iterable<T>
* Collection: public interface Collection<E> extends Iterable<E>
    * List:
        * ArrayList
        * LinkedList
        * Stack
        * Vector
    * Set:
        * HashSet
        * LinkedHashSet
        * TreeSet
    * Queue:
        * PriorityQueue
        * ArrayDeque
        * LinkedList(Deque)
* Map
    * HashMap
    * HashLinkedMap
    * HashTable
    * TreeMap
https://www.cnblogs.com/skywang12345/p/3308498.html
https://www.journaldev.com/1260/collections-in-java-tutorial
https://www.cnblogs.com/skywang12345/p/3308762.html
https://www.cnblogs.com/skywang12345/tag/%E9%9B%86%E5%90%88/
# 1. Java集合概述



## Collection集合概述
## Map集合概述
## Concurrent包下的集合概述

# 2. 集合详解
## Collection集合下常用实现类详解
## Map集合下常用实现类详解
## Concurrent包下常用实现类详解

# 3. 集合框架中体现的设计模式和编程规范
[迭代器模式](#迭代器模式)
[适配器模式](#适配器模式)

# 其他

### 1. Set

- TreeSet：基于红黑树实现，支持有序性操作，例如根据一个范围查找元素的操作。但是查找效率不如 HashSet，HashSet 查找的时间复杂度为 O(1)，TreeSet 则为 O(logN)。

- HashSet：基于哈希表实现，支持快速查找，但不支持有序性操作。并且失去了元素的插入顺序信息，也就是说使用 Iterator 遍历 HashSet 得到的结果是不确定的。

- LinkedHashSet：具有 HashSet 的查找效率，且内部使用双向链表维护元素的插入顺序。

### 2. List

- ArrayList：基于动态数组实现，支持随机访问。

- Vector：和 ArrayList 类似，但它是线程安全的。

- LinkedList：基于双向链表实现，只能顺序访问，但是可以快速地在链表中间插入和删除元素。不仅如此，LinkedList 还可以用作栈、队列和双向队列。

### 3. Queue

- LinkedList：可以用它来实现双向队列。

- PriorityQueue：基于堆结构实现，可以用它来实现优先队列。

## Map

<div align="center"> <img src="pics/774d756b-902a-41a3-a3fd-81ca3ef688dc.png" width="500px"> </div><br>

- TreeMap：基于红黑树实现。

- HashMap：基于哈希表实现。

- HashTable：和 HashMap 类似，但它是线程安全的，这意味着同一时刻多个线程可以同时写入 HashTable 并且不会导致数据不一致。它是遗留类，不应该去使用它。现在可以使用 ConcurrentHashMap 来支持线程安全，并且 ConcurrentHashMap 的效率会更高，因为 ConcurrentHashMap 引入了分段锁。

- LinkedHashMap：使用双向链表来维护元素的顺序，顺序为插入顺序或者最近最少使用（LRU）顺序。


# 二、容器中的设计模式

## 迭代器模式

<div align="center"> <img src="pics/93fb1d38-83f9-464a-a733-67b2e6bfddda.png" width="600px"> </div><br>

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

# 三、源码分析

如果没有特别说明，以下源码分析基于 JDK 1.8。

在 IDEA 中 double shift 调出 Search EveryWhere，查找源码文件，找到之后就可以阅读源码。

## ArrayList


### 1. 概览

因为 ArrayList 是基于数组实现的，所以支持快速随机访问(可以使用下标来访问，随机指的是下标是随机的 get(int index))。RandomAccess 接口标识着该类支持快速随机访问。

ArrayList类中存在两个私有成员（当然不仅仅只有两个），elementData是一个Object类型的数组，用来保存元素；size表示ArrayList中实际的元素数目（所以size()方法返回的是实际元素的数目）。

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

数组的默认大小为 10。

```java
private static final int DEFAULT_CAPACITY = 10;

public ArrayList() {
    this(10);
}
```

<div align="center"> <img src="pics/52a7744f-5bce-4ff3-a6f0-8449334d9f3d.png" width="400px"> </div><br>

### 2. 扩容

添加元素时使用 ensureCapacityInternal() 方法来保证容量足够，如果不够时，需要使用 grow() 方法进行扩容，新容量的大小为 `oldCapacity + (oldCapacity >> 1)`，也就是旧容量的 1.5 倍。

扩容操作需要调用 `Arrays.copyOf()` 把原数组整个复制到新数组中，这个操作代价很高，因此最好在创建 ArrayList 对象时就指定大概的容量大小，减少扩容操作的次数。

```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}

private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
    modCount++;
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

看了这几段代码，ensureCapacity(minCapacity)的作用就显而易见了，就是增加数组列表的容量，原理很简单，首先判断该参数是否大于0并且是否大于当前容量，然后就到了grow方法，其他的语句先别管，看最后一句，elementData = Arrays.copyOf(elementData, newCapacity); 这条语句先按指定参数创建一个更大的数组，然后把原数组的元素一个个拷贝到大数组中，最后将elementData 的引用指向这个大数组。这样，就增大了数组列表的容量，而小数组也会被gc回收。所以数据量很大的时候还是建议初始化的时候就指定容量，提高效率


### 3. 删除元素

需要调用 System.arraycopy() 将 index+1 后面的元素都复制到 index 位置上，该操作的时间复杂度为 O(N)，可以看出 ArrayList 删除元素的代价是非常高的。

```java
public E remove(int index) {
    rangeCheck(index);
    modCount++;
    E oldValue = elementData(index);
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    elementData[--size] = null; // clear to let GC do its work
    return oldValue;
}
```

### 4. Fail-Fast

modCount 用来记录 ArrayList 结构发生变化的次数。结构发生变化是指添加或者删除至少一个元素的所有操作，或者是调整内部数组的大小，仅仅只是设置元素的值不算结构发生变化。

在进行序列化或者迭代等操作时，需要比较操作前后 modCount 是否改变，如果改变了需要抛出 ConcurrentModificationException。

```java
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
```

### 5. 序列化

ArrayList 基于数组实现，并且具有动态扩容特性，因此保存元素的数组不一定都会被使用，那么就没必要全部进行序列化。

保存元素的数组 elementData 使用 transient 修饰，该关键字声明数组默认不会被序列化。

```java
transient Object[] elementData; // non-private to simplify nested class access
```

ArrayList 实现了 writeObject() 和 readObject() 来控制只序列化数组中有元素填充那部分内容。

```java
private void readObject(java.io.ObjectInputStream s)
    throws java.io.IOException, ClassNotFoundException {
    elementData = EMPTY_ELEMENTDATA;

    // Read in size, and any hidden stuff
    s.defaultReadObject();

    // Read in capacity
    s.readInt(); // ignored

    if (size > 0) {
        // be like clone(), allocate array based upon size not capacity
        ensureCapacityInternal(size);

        Object[] a = elementData;
        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            a[i] = s.readObject();
        }
    }
}
```

```java
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
```

序列化时需要使用 ObjectOutputStream 的 writeObject() 将对象转换为字节流并输出。而 writeObject() 方法在传入的对象存在 writeObject() 的时候会去反射调用该对象的 writeObject() 来实现序列化。反序列化使用的是 ObjectInputStream 的 readObject() 方法，原理类似。

```java
ArrayList list = new ArrayList();
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
oos.writeObject(list);
```

## Vector

### 1. 同步

它的实现与 ArrayList 类似，但是使用了 synchronized 进行同步。

```java
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}

public synchronized E get(int index) {
    if (index >= elementCount)
        throw new ArrayIndexOutOfBoundsException(index);

    return elementData(index);
}
```

### 2. 与 ArrayList 的比较

- Vector 是同步的，因此开销就比 ArrayList 要大，访问速度更慢。最好使用 ArrayList 而不是 Vector，因为同步操作完全可以由程序员自己来控制；
- Vector 每次扩容请求其大小的 2 倍空间，而 ArrayList 是 1.5 倍。

### 3. 替代方案

可以使用 `Collections.synchronizedList();` 得到一个线程安全的 ArrayList。

```java
List<String> list = new ArrayList<>();
List<String> synList = Collections.synchronizedList(list);
```

也可以使用 concurrent 并发包下的 CopyOnWriteArrayList 类。

```java
List<String> list = new CopyOnWriteArrayList<>();
```

## CopyOnWriteArrayList

### 读写分离

写操作在一个复制的数组上进行，读操作还是在原始数组中进行，读写分离，互不影响。

写操作需要加锁，防止并发写入时导致写入数据丢失。

写操作结束之后需要把原始数组指向新的复制数组。

```java
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}

final void setArray(Object[] a) {
    array = a;
}
```

```java
@SuppressWarnings("unchecked")
private E get(Object[] a, int index) {
    return (E) a[index];
}
```

### 适用场景

CopyOnWriteArrayList 在写操作的同时允许读操作，大大提高了读操作的性能，因此很适合读多写少的应用场景。

但是 CopyOnWriteArrayList 有其缺陷：

- 内存占用：在写操作时需要复制一个新的数组，使得内存占用为原来的两倍左右；
- 数据不一致：读操作不能读取实时性的数据，因为部分写操作的数据还未同步到读数组中。

所以 CopyOnWriteArrayList 不适合内存敏感以及对实时性要求很高的场景。

## LinkedList

### 1. 概览

基于双向链表实现，使用 Node 存储链表节点信息。

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;
}
```

每个链表存储了 first 和 last 指针：

```java
transient Node<E> first;
transient Node<E> last;
```

<div align="center"> <img src="pics/c8563120-cb00-4dd6-9213-9d9b337a7f7c.png" width="500px"> </div><br>

### 2. 与 ArrayList 的比较

- ArrayList 基于动态数组实现，LinkedList 基于双向链表实现；
- ArrayList 支持随机访问，LinkedList 不支持；
- LinkedList 在任意位置添加删除元素更快。

## HashMap

为了便于理解，以下源码分析以 JDK 1.7 为主。

### 1. 存储结构

内部包含了一个 Entry 类型的数组 table。

```java
transient Entry[] table;
```

Entry 存储着键值对。它包含了四个字段，从 next 字段我们可以看出 Entry 是一个链表。即数组中的每个位置被当成一个桶，一个桶存放一个链表。HashMap 使用拉链法来解决冲突，同一个链表中存放哈希值相同的 Entry。

<div align="center"> <img src="pics/9420a703-1f9d-42ce-808e-bcb82b56483d.png" width="550px"> </div><br>

```java
static class Entry<K,V> implements Map.Entry<K,V> {
    final K key;
    V value;
    Entry<K,V> next;
    int hash;

    Entry(int h, K k, V v, Entry<K,V> n) {
        value = v;
        next = n;
        key = k;
        hash = h;
    }

    public final K getKey() {
        return key;
    }

    public final V getValue() {
        return value;
    }

    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry e = (Map.Entry)o;
        Object k1 = getKey();
        Object k2 = e.getKey();
        if (k1 == k2 || (k1 != null && k1.equals(k2))) {
            Object v1 = getValue();
            Object v2 = e.getValue();
            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
```

### 2. 拉链法的工作原理

```java
HashMap<String, String> map = new HashMap<>();
map.put("K1", "V1");
map.put("K2", "V2");
map.put("K3", "V3");
```

- 新建一个 HashMap，默认大小为 16；
- 插入 &lt;K1,V1> 键值对，先计算 K1 的 hashCode 为 115，使用除留余数法得到所在的桶下标 115%16=3。
- 插入 &lt;K2,V2> 键值对，先计算 K2 的 hashCode 为 118，使用除留余数法得到所在的桶下标 118%16=6。
- 插入 &lt;K3,V3> 键值对，先计算 K3 的 hashCode 为 118，使用除留余数法得到所在的桶下标 118%16=6，插在 &lt;K2,V2> 前面。

应该注意到链表的插入是以头插法方式进行的，例如上面的 &lt;K3,V3> 不是插在 &lt;K2,V2> 后面，而是插入在链表头部。

查找需要分成两步进行：

- 计算键值对所在的桶；
- 在链表上顺序查找，时间复杂度显然和链表的长度成正比。

<div align="center"> <img src="pics/e0870f80-b79e-4542-ae39-7420d4b0d8fe.png" width="550px"> </div><br>

### 3. put 操作

```java
public V put(K key, V value) {
    if (table == EMPTY_TABLE) {
        inflateTable(threshold);
    }
    // 键为 null 单独处理
    if (key == null)
        return putForNullKey(value);
    int hash = hash(key);
    // 确定桶下标
    int i = indexFor(hash, table.length);
    // 先找出是否已经存在键为 key 的键值对，如果存在的话就更新这个键值对的值为 value
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
    // 插入新键值对
    addEntry(hash, key, value, i);
    return null;
}
```

HashMap 允许插入键为 null 的键值对。但是因为无法调用 null 的 hashCode() 方法，也就无法确定该键值对的桶下标，只能通过强制指定一个桶下标来存放。HashMap 使用第 0 个桶存放键为 null 的键值对。

```java
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
```

使用链表的头插法，也就是新的键值对插在链表的头部，而不是链表的尾部。

```java
void addEntry(int hash, K key, V value, int bucketIndex) {
    if ((size >= threshold) && (null != table[bucketIndex])) {
        resize(2 * table.length); // resize的时候double size
        hash = (null != key) ? hash(key) : 0;
        bucketIndex = indexFor(hash, table.length);
    }

    createEntry(hash, key, value, bucketIndex);
}

void createEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
    // 头插法，链表头部指向新的键值对
    table[bucketIndex] = new Entry<>(hash, key, value, e);
    size++;
}
```

```java
Entry(int h, K k, V v, Entry<K,V> n) {
    value = v;
    next = n;
    key = k;
    hash = h;
}
```

### 4. 确定桶下标

很多操作都需要先确定一个键值对所在的桶下标。

```java
int hash = hash(key);
int i = indexFor(hash, table.length);
```

**4.1 计算 hash 值**

```java
final int hash(Object k) {
    int h = hashSeed;
    if (0 != h && k instanceof String) {
        return sun.misc.Hashing.stringHash32((String) k);
    }

    h ^= k.hashCode();

    // This function ensures that hashCodes that differ only by
    // constant multiples at each bit position have a bounded
    // number of collisions (approximately 8 at default load factor).
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}
```

```java
public final int hashCode() {
    return Objects.hashCode(key) ^ Objects.hashCode(value);
}
```

**4.2 取模**

令 x = 1<<4，即 x 为 2 的 4 次方，它具有以下性质：

```
x   : 00010000
x-1 : 00001111
```

令一个数 y 与 x-1 做与运算，可以去除 y 位级表示的第 4 位以上数：

```
y       : 10110010
x-1     : 00001111
y&(x-1) : 00000010
```

这个性质和 y 对 x 取模效果是一样的：

```
y   : 10110010
x   : 00010000
y%x : 00000010
```

我们知道，位运算的代价比求模运算小的多，因此在进行这种计算时用位运算的话能带来更高的性能。

确定桶下标的最后一步是将 key 的 hash 值对桶个数取模：hash%capacity，如果能保证 capacity 为 2 的 n 次方，那么就可以将这个操作转换为位运算。

```java
static int indexFor(int h, int length) {
    return h & (length-1);
}
```

### 5. 扩容-基本原理

设 HashMap 的 table 长度为 M，需要存储的键值对数量为 N，如果哈希函数满足均匀性的要求，那么每条链表的长度大约为 N/M，因此平均查找次数的复杂度为 O(N/M)。

为了让查找的成本降低，应该尽可能使得 N/M 尽可能小，因此需要保证 M 尽可能大，也就是说 table 要尽可能大。HashMap 采用动态扩容来根据当前的 N 值来调整 M 值，使得空间效率和时间效率都能得到保证。

和扩容相关的参数主要有：capacity、size、threshold 和 load_factor。

| 参数 | 含义 |
| :--: | :-- |
| capacity | table 的容量大小，默认为 16。需要注意的是 capacity 必须保证为 2 的 n 次方。|
| size | 键值对数量。 |
| threshold | size 的临界值，当 size 大于等于 threshold 就必须进行扩容操作。 |
| loadFactor | 装载因子，table 能够使用的比例，threshold = capacity * loadFactor。|

```java
static final int DEFAULT_INITIAL_CAPACITY = 16;

static final int MAXIMUM_CAPACITY = 1 << 30;

static final float DEFAULT_LOAD_FACTOR = 0.75f;

transient Entry[] table;

transient int size;

int threshold;

final float loadFactor;

transient int modCount;
```

从下面的添加元素代码中可以看出，当需要扩容时，令 capacity 为原来的两倍。

```java
void addEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
    table[bucketIndex] = new Entry<>(hash, key, value, e);
    if (size++ >= threshold)
        resize(2 * table.length);
}
```

扩容使用 resize() 实现，需要注意的是，扩容操作同样需要把 oldTable 的所有键值对重新插入 newTable 中，因此这一步是很费时的。

```java
void resize(int newCapacity) {
    Entry[] oldTable = table;
    int oldCapacity = oldTable.length;
    if (oldCapacity == MAXIMUM_CAPACITY) {
        threshold = Integer.MAX_VALUE;
        return;
    }
    Entry[] newTable = new Entry[newCapacity];
    transfer(newTable);
    table = newTable;
    threshold = (int)(newCapacity * loadFactor);
}

void transfer(Entry[] newTable) {
    Entry[] src = table;
    int newCapacity = newTable.length;
    for (int j = 0; j < src.length; j++) {
        Entry<K,V> e = src[j];
        if (e != null) {
            src[j] = null;
            do {
                Entry<K,V> next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            } while (e != null);
        }
    }
}
```

### 6. 扩容-重新计算桶下标

在进行扩容时，需要把键值对重新放到对应的桶上。HashMap 使用了一个特殊的机制，可以降低重新计算桶下标的操作。

假设原数组长度 capacity 为 16，扩容之后 new capacity 为 32：

```html
capacity     : 00010000
new capacity : 00100000
```

对于一个 Key，

- 它的哈希值如果在第 5 位上为 0，那么取模得到的结果和之前一样；
- 如果为 1，那么得到的结果为原来的结果 +16。

### 7. 计算数组容量

HashMap 构造函数允许用户传入的容量不是 2 的 n 次方，因为它可以自动地将传入的容量转换为 2 的 n 次方。

先考虑如何求一个数的掩码，对于 10010000，它的掩码为 11111111，可以使用以下方法得到：

```
mask |= mask >> 1    11011000
mask |= mask >> 2    11111110
mask |= mask >> 4    11111111
```

mask+1 是大于原始数字的最小的 2 的 n 次方。

```
num     10010000
mask+1 100000000
```

以下是 HashMap 中计算数组容量的代码：

```java
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

### 8. 链表转红黑树

从 JDK 1.8 开始，一个桶存储的链表长度大于 8 时会将链表转换为红黑树。

### 9. 与 HashTable 的比较

- HashTable 使用 synchronized 来进行同步。
- HashMap 可以插入键为 null 的 Entry，HashTable不可以。
- HashMap 的迭代器是 fail-fast 迭代器。
- HashMap 不能保证随着时间的推移 Map 中的元素次序是不变的。

## ConcurrentHashMap

### 1. 存储结构

```java
static final class HashEntry<K,V> {
    final int hash;
    final K key;
    volatile V value;
    volatile HashEntry<K,V> next;
}
```

ConcurrentHashMap 和 HashMap 实现上类似，最主要的差别是 ConcurrentHashMap 采用了分段锁（Segment），每个分段锁维护着几个桶（HashEntry），多个线程可以同时访问不同分段锁上的桶，从而使其并发度更高（并发度就是 Segment 的个数）。

Segment 继承自 ReentrantLock。

```java
static final class Segment<K,V> extends ReentrantLock implements Serializable {

    private static final long serialVersionUID = 2249069246763182397L;

    static final int MAX_SCAN_RETRIES =
        Runtime.getRuntime().availableProcessors() > 1 ? 64 : 1;

    transient volatile HashEntry<K,V>[] table;

    transient int count;

    transient int modCount;

    transient int threshold;

    final float loadFactor;
}
```

```java
final Segment<K,V>[] segments;
```

默认的并发级别为 16，也就是说默认创建 16 个 Segment。

```java
static final int DEFAULT_CONCURRENCY_LEVEL = 16;
```

<div align="center"> <img src="pics/db808eff-31d7-4229-a4ad-b8ae71870a3a.png" width="550px"> </div><br>

### 2. size 操作

每个 Segment 维护了一个 count 变量来统计该 Segment 中的键值对个数。

```java
/**
 * The number of elements. Accessed only either within locks
 * or among other volatile reads that maintain visibility.
 */
transient int count;
```

在执行 size 操作时，需要遍历所有 Segment 然后把 count 累计起来。

ConcurrentHashMap 在执行 size 操作时先尝试不加锁，如果连续两次不加锁操作得到的结果一致，那么可以认为这个结果是正确的。

尝试次数使用 RETRIES_BEFORE_LOCK 定义，该值为 2，retries 初始值为 -1，因此尝试次数为 3。

如果尝试的次数超过 3 次，就需要对每个 Segment 加锁。

```java

/**
 * Number of unsynchronized retries in size and containsValue
 * methods before resorting to locking. This is used to avoid
 * unbounded retries if tables undergo continuous modification
 * which would make it impossible to obtain an accurate result.
 */
static final int RETRIES_BEFORE_LOCK = 2;

public int size() {
    // Try a few times to get accurate count. On failure due to
    // continuous async changes in table, resort to locking.
    final Segment<K,V>[] segments = this.segments;
    int size;
    boolean overflow; // true if size overflows 32 bits
    long sum;         // sum of modCounts
    long last = 0L;   // previous sum
    int retries = -1; // first iteration isn't retry
    try {
        for (;;) {
            // 超过尝试次数，则对每个 Segment 加锁
            if (retries++ == RETRIES_BEFORE_LOCK) {
                for (int j = 0; j < segments.length; ++j)
                    ensureSegment(j).lock(); // force creation
            }
            sum = 0L;
            size = 0;
            overflow = false;
            for (int j = 0; j < segments.length; ++j) {
                Segment<K,V> seg = segmentAt(segments, j);
                if (seg != null) {
                    sum += seg.modCount;
                    int c = seg.count;
                    if (c < 0 || (size += c) < 0)
                        overflow = true;
                }
            }
            // 连续两次得到的结果一致，则认为这个结果是正确的
            if (sum == last)
                break;
            last = sum;
        }
    } finally {
        if (retries > RETRIES_BEFORE_LOCK) {
            for (int j = 0; j < segments.length; ++j)
                segmentAt(segments, j).unlock();
        }
    }
    return overflow ? Integer.MAX_VALUE : size;
}
```

### 3. JDK 1.8 的改动

JDK 1.7 使用分段锁机制来实现并发更新操作，核心类为 Segment，它继承自重入锁 ReentrantLock，并发度与 Segment 数量相等。

JDK 1.8 使用了 CAS 操作来支持更高的并发度，在 CAS 操作失败时使用内置锁 synchronized。

并且 JDK 1.8 的实现也在链表过长时会转换为红黑树。
CAS的全称为Compare-And-Swap，直译就是对比交换。  简单解释：CAS操作需要输入两个数值，一个旧值（期望操作前的值）和一个新值，在操作期间先比较下在旧值有没有发生变化，如果没有发生变化，才交换成新值，发生了变化则不交换。
## LinkedHashMap

### 存储结构

继承自 HashMap，因此具有和 HashMap 一样的快速查找特性。

```java
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>
```

内部维护了一个双向链表，用来维护插入顺序或者 LRU 顺序。

```java
/**
 * The head (eldest) of the doubly linked list.
 */
transient LinkedHashMap.Entry<K,V> head;

/**
 * The tail (youngest) of the doubly linked list.
 */
transient LinkedHashMap.Entry<K,V> tail;
```

accessOrder 决定了顺序，默认为 false，此时维护的是插入顺序。

```java
final boolean accessOrder;
```

LinkedHashMap 最重要的是以下用于维护顺序的函数，它们会在 put、get 等方法中调用。

```java
void afterNodeAccess(Node<K,V> p) { }
void afterNodeInsertion(boolean evict) { }
```

### afterNodeAccess()

当一个节点被访问时，如果 accessOrder 为 true，则会将该节点移到链表尾部。也就是说指定为 LRU 顺序之后，在每次访问一个节点时，会将这个节点移到链表尾部，保证链表尾部是最近访问的节点，那么链表首部就是最近最久未使用的节点。

```java
void afterNodeAccess(Node<K,V> e) { // move node to last
    LinkedHashMap.Entry<K,V> last;
    if (accessOrder && (last = tail) != e) {
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.after = null;
        if (b == null)
            head = a;
        else
            b.after = a;
        if (a != null)
            a.before = b;
        else
            last = b;
        if (last == null)
            head = p;
        else {
            p.before = last;
            last.after = p;
        }
        tail = p;
        ++modCount;
    }
}
```

### afterNodeInsertion()

在 put 等操作之后执行，当 removeEldestEntry() 方法返回 true 时会移除最晚的节点，也就是链表首部节点 first。

evict 只有在构建 Map 的时候才为 false，在这里为 true。

```java
void afterNodeInsertion(boolean evict) { // possibly remove eldest
    LinkedHashMap.Entry<K,V> first;
    if (evict && (first = head) != null && removeEldestEntry(first)) {
        K key = first.key;
        removeNode(hash(key), key, null, false, true);
    }
}
```

removeEldestEntry() 默认为 false，如果需要让它为 true，需要继承 LinkedHashMap 并且覆盖这个方法的实现，这在实现 LRU 的缓存中特别有用，通过移除最近最久未使用的节点，从而保证缓存空间足够，并且缓存的数据都是热点数据。

```java
protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
    return false;
}
```

### LRU 缓存

以下是使用 LinkedHashMap 实现的一个 LRU 缓存：

- 设定最大缓存空间 MAX_ENTRIES  为 3；
- 使用 LinkedHashMap 的构造函数将 accessOrder 设置为 true，开启 LRU 顺序；
- 覆盖 removeEldestEntry() 方法实现，在节点多于 MAX_ENTRIES 就会将最近最久未使用的数据移除。

```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_ENTRIES = 3;

    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }

    LRUCache() {
        super(MAX_ENTRIES, 0.75f, true);
    }
}
```

```java
public static void main(String[] args) {
    LRUCache<Integer, String> cache = new LRUCache<>();
    cache.put(1, "a");
    cache.put(2, "b");
    cache.put(3, "c");
    cache.get(1);
    cache.put(4, "d");
    System.out.println(cache.keySet());
}
```

```html
[3, 1, 4]
```

## WeakHashMap

### 存储结构

WeakHashMap 的 Entry 继承自 WeakReference，被 WeakReference 关联的对象在下一次垃圾回收时会被回收。

WeakHashMap 主要用来实现缓存，通过使用 WeakHashMap 来引用缓存对象，由 JVM 对这部分缓存进行回收。

```java
private static class Entry<K,V> extends WeakReference<Object> implements Map.Entry<K,V>
```

### ConcurrentCache

Tomcat 中的 ConcurrentCache 使用了 WeakHashMap 来实现缓存功能。

ConcurrentCache 采取的是分代缓存：

- 经常使用的对象放入 eden 中，eden 使用 ConcurrentHashMap 实现，不用担心会被回收（伊甸园）；
- 不常用的对象放入 longterm，longterm 使用 WeakHashMap 实现，这些老对象会被垃圾收集器回收。
- 当调用  get() 方法时，会先从 eden 区获取，如果没有找到的话再到 longterm 获取，当从 longterm 获取到就把对象放入 eden 中，从而保证经常被访问的节点不容易被回收。
- 当调用 put() 方法时，如果 eden 的大小超过了 size，那么就将 eden 中的所有对象都放入 longterm 中，利用虚拟机回收掉一部分不经常使用的对象。

```java
public final class ConcurrentCache<K, V> {

    private final int size;

    private final Map<K, V> eden;

    private final Map<K, V> longterm;

    public ConcurrentCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size);
        this.longterm = new WeakHashMap<>(size);
    }

    public V get(K k) {
        V v = this.eden.get(k);
        if (v == null) {
            v = this.longterm.get(k);
            if (v != null)
                this.eden.put(k, v);
        }
        return v;
    }

    public void put(K k, V v) {
        if (this.eden.size() >= size) {
            this.longterm.putAll(this.eden);
            this.eden.clear();
        }
        this.eden.put(k, v);
    }
}
```


# 参考资料

- Eckel B. Java 编程思想 [M]. 机械工业出版社, 2002.
- [Java Collection Framework](https://www.w3resource.com/java-tutorial/java-collections.php)
- [Iterator 模式](https://openhome.cc/Gossip/DesignPattern/IteratorPattern.htm)
- [Java 8 系列之重新认识 HashMap](https://tech.meituan.com/java_hashmap.html)
- [What is difference between HashMap and Hashtable in Java?](http://javarevisited.blogspot.hk/2010/10/difference-between-hashmap-and.html)
- [Java 集合之 HashMap](http://www.zhangchangle.com/2018/02/07/Java%E9%9B%86%E5%90%88%E4%B9%8BHashMap/)
- [The principle of ConcurrentHashMap analysis](http://www.programering.com/a/MDO3QDNwATM.html)
- [探索 ConcurrentHashMap 高并发性的实现机制](https://www.ibm.com/developerworks/cn/java/java-lo-concurrenthashmap/)
- [HashMap 相关面试题及其解答](https://www.jianshu.com/p/75adf47958a7)
- [Java 集合细节（二）：asList 的缺陷](http://wiki.jikexueyuan.com/project/java-enhancement/java-thirtysix.html)
- [Java Collection Framework – The LinkedList Class](http://javaconceptoftheday.com/java-collection-framework-linkedlist-class/)
- [Java Concurrent模块](https://blog.csdn.net/xkzju2010/article/details/52023539)
