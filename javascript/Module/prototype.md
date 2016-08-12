任何一个对象都有一个prototype的属性，在js中可以把它记为：__proto__
当初ECMAscript的发明者为了简化这门语言，同时又保持继承的属性，于是就设计了这个链表。。
在数据结构中学过链表不，链表中有一个位置相当于指针，指向下一个结构体。

于是乎__proto__也一样，每当你去定义一个prototype的时候，相当于把该实例的__proto__指向一个结构体，那么这个被指向结构体就称为该实例的原型。

文字说起来有点儿绕，看图说话
复制代码 代码如下:

var foo = {
x: 10,
y: 20
};

当我不指定__proto__的时候，foo也会预留一个这样的属性，

如果有明确的指向，那么这个链表就链起来啦。

很明显，下图中b和c共享a的属性和方法，同时又有自己的私有属性。

__proto__默认的也有指向。它指向的是最高级的object.prototype，而object.prototype的__proto__为空。

var a = {
x: 10,
calculate: function (z) {
return this.x + this.y + z
}
};
var b = {
y: 20,
__proto__: a
};

var c = {
y: 30,
__proto__: a
};

// call the inherited method
b.calculate(30); // 60

Figure 2. A prototype chain.
智能社_前端开发培训专家 【点击进入】
H5+CSS3+JS4多阶段课程,助您实现高薪梦想! 暑期培训班75折!
查 看


理解了__proto__这个属性链接指针的本质。。再来理解constructor。

当定义一个prototype的时候，会构造一个原形对象，这个原型对象存储于构造这个prototype的函数的原形方法之中.
复制代码 代码如下:

function Foo(y){
this.y = y ;
}

Foo.prototype.x = 10;

Foo.prototype.calculate = function(z){
return this.x+this.y+z;
};

var b = new Foo(20);

alert(b.calculate(30));


