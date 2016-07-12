function m1() {
    console.log('function m1');
}

function m2() {
    console.log('function m2');
}
/*
模块就是实现特定功能的一组方法。
只要把不同的函数（以及记录状态的变量）简单地放在一起，就算是一个模块。
原始写法

上面的函数m1()和m2()，组成一个模块。使用的时候，直接调用就行了。
这种做法的缺点很明显："污染"了全局变量，无法保证不与其他模块发生变量名冲突，而且模块成员之间看不出直接关系。
 */

var module1 = new Object({
    _count : 0,
    
    m1 : function() {
        console.log('module1 function m1');
    },
    
    m2 : function() {
        console.log('module 1 function m2');
    }
    
});
/*
对象写法
为了解决上面的缺点，可以把模块写成一个对象，所有的模块成员都放到这个对象里面。
上面的函数m1()和m2(），都封装在module1对象里。使用的时候，就是调用这个对象的属性。
module1.m1();
但是，这样的写法会暴露所有模块成员，内部状态可以被外部改写。比如，外部代码可以直接改变内部计数器的值。
module1._count = 5;
 */

var module1 = (function() {
    var _count = 0;
    
    var m1 = function() {
        console.log('module1 function m1');
    };
    
    var m2 = function() {
        console.log('module1 function m2');
    };
    
    return {
        m1 : m1,
        m2 : m2
    };
})();

/*
立即执行函数写法
使用"立即执行函数"（Immediately-Invoked Function Expression，IIFE），可以达到不暴露私有成员的目的。
使用上面的写法，外部代码无法读取内部的_count变量。
　　console.info(module1._count); //undefined
module1就是Javascript模块的基本写法。下面，再对这种写法进行加工。
 */

var module1 = (function(mod){
    mod.m3 = function() {
        console.log('module1 function m3');
    };
    
    return mod;
})(module1);

/*
放大模式
如果一个模式很大，必须分成几个部分，或者一个模块需要继承另一个模块，这时就必须采用放大模式
上面的代码为module1模块添加了一个新的方法m3，饭后返回新的module1模块

var module1 = (function (mod){
　　　　mod.m3 = function () {
　　　　　　//...
　　　　};
　　　　return mod;
　　})(module1);
已知模块module1,在匿名函数内别名mod。追加m3对象后，返回mod。这时候module1也就有了m3对象。
 */

var module1 = (function(mod){
    mod.m4 = function() {
        console.log('module1 function m4');
    };
    
    return mod;
})(window.module1 || {});

/*
宽放大模式（Loose augmentation）
在浏览器环境中，模块的各个部分通常都是从网上获取的，有时无法知道哪个部分会先加载。如果采用上一节的写法，第一个执行的部分有可能加载一个不存在空对象，这时就要采用"宽放大模式"。
与"放大模式"相比，＂宽放大模式＂就是"立即执行函数"的参数可以是空对象。
打开jQuery源码，首先你会看到这样的代码结构：
(function( window, undefined ) {
// jquery code
})(window);
1. 这是一个自调用匿名函数。什么东东呢？在第一个括号内，创建一个匿名函数；第二个括号，立即执行
2. 为什么要创建这样一个“自调用匿名函数”呢？
通过定义一个匿名函数，创建了一个“私有”的命名空间，该命名空间的变量和方法，不会破坏全局的命名空间。这点非常有用也是一个JS框架必须支持的功能，jQuery被应用在成千上万的JavaScript程序中，必须确保jQuery创建的变量不能和导入他的程序所使用的变量发生冲突。
3. 匿名函数从语法上叫函数直接量，JavaScript语法需要包围匿名函数的括号，事实上自调用匿名函数有两种写法：
(function() {
console.info( this );
console.info( arguments );
}( window ) );
------------------------------------
(function() {
console.info( this );
console.info( arguments );
})( window );

引用nakseuksa 的发言：
http://www.adequatelygood.com/2010/3/JavaScript-Module-Pattern-In-Depth
上面链接里的例子如下：
(function () {
// ... all vars and functions are in this scope only
// still maintains access to all globals
}());
和一峰给的代码的“（）”的用法不一样。请问这两种写法有什么区别咧？

(function(){}())是使用了强制运算符执行函数调用运算，(function(){})()是通过函数调用运算符操作函数引用。两者功能上是一致的，只是运算过程不同。
 */

var module1 = (function() {
    console.log('test');
})(jQuery, YAHOO);

/*
上面的module1模块需要使用jQuery库和YUI库，就把这两个库（其实是两个模块）当作参数输入module1。这样做除了保证模块的独立性，还使得模块之间的依赖关系变得明显。
 */

/*
七、模块的规范
先想一想，为什么模块很重要？
因为有了模块，我们就可以更方便地使用别人的代码，想要什么功能，就加载什么模块。
但是，这样做有一个前提，那就是大家必须以同样的方式编写模块，否则你有你的写法，我有我的写法，岂不是乱了套！考虑到Javascript模块现在还没有官方规范，这一点就更重要了。
目前，通行的Javascript模块规范共有两种：CommonJS和AMD。我主要介绍AMD，但是要先从CommonJS讲起。
八、CommonJS
2009年，美国程序员Ryan Dahl创造了node.js项目，将javascript语言用于服务器端编程。
这标志"Javascript模块化编程"正式诞生。因为老实说，在浏览器环境下，没有模块也不是特别大的问题，毕竟网页程序的复杂性有限；但是在服务器端，一定要有模块，与操作系统和其他应用程序互动，否则根本没法编程。
node.js的模块系统，就是参照CommonJS规范实现的。在CommonJS中，有一个全局性方法require()，用于加载模块。假定有一个数学模块math.js，就可以像下面这样加载。
　　var math = require('math');
然后，就可以调用模块提供的方法：
　　var math = require('math');
　　math.add(2,3); // 5
因为这个系列主要针对浏览器编程，不涉及node.js，所以对CommonJS就不多做介绍了。我们在这里只要知道，require()用于加载模块就行了。
九、浏览器环境
有了服务器端模块以后，很自然地，大家就想要客户端模块。而且最好两者能够兼容，一个模块不用修改，在服务器和浏览器都可以运行。
但是，由于一个重大的局限，使得CommonJS规范不适用于浏览器环境。还是上一节的代码，如果在浏览器中运行，会有一个很大的问题，你能看出来吗？
　var math = require('math');
　　math.add(2, 3);
第二行math.add(2, 3)，在第一行require('math')之后运行，因此必须等math.js加载完成。也就是说，如果加载时间很长，整个应用就会停在那里等。
这对服务器端不是一个问题，因为所有的模块都存放在本地硬盘，可以同步加载完成，等待时间就是硬盘的读取时间。但是，对于浏览器，这却是一个大问题，因为模块都放在服务器端，等待时间取决于网速的快慢，可能要等很长时间，浏览器处于"假死"状态。
因此，浏览器端的模块，不能采用"同步加载"（synchronous），只能采用"异步加载"（asynchronous）。这就是AMD规范诞生的背景。
十、 AMD
AMD是"Asynchronous Module Definition"的缩写，意思就是"异步模块定义"。它采用异步方式加载模块，模块的加载不影响它后面语句的运行。所有依赖这个模块的语句，都定义在一个回调函数中，等到加载完成之后，这个回调函数才会运行。
AMD也采用require()语句加载模块，但是不同于CommonJS，它要求两个参数：
　　require([module], callback);
第一个参数[module]，是一个数组，里面的成员就是要加载的模块；第二个参数callback，则是加载成功之后的回调函数。如果将前面的代码改写成AMD形式，就是下面这样：
require(['math'], function (math) {
　　　　math.add(2, 3);
　　});

math.add()与math模块加载不是同步的，浏览器不会发生假死。所以很显然，AMD比较适合浏览器环境。
目前，主要有两个Javascript库实现了AMD规范：require.js和curl.js。本系列的第三部分，将通过介绍require.js，进一步讲解AMD的用法，以及如何将模块化编程投入实战。

这个系列的第一部分和第二部分，介绍了Javascript模块原型和理论概念，今天介绍如何将它们用于实战。
我采用的是一个非常流行的库require.js。

一、为什么要用require.js？
最早的时候，所有Javascript代码都写在一个文件里面，只要加载这一个文件就够了。后来，代码越来越多，一个文件不够了，必须分成多个文件，依次加载。下面的网页代码，相信很多人都见过。
　　<script src="1.js"></script>
　　<script src="2.js"></script>
　　<script src="3.js"></script>
　　<script src="4.js"></script>
　　<script src="5.js"></script>
　　<script src="6.js"></script>
这段代码依次加载多个js文件。
这样的写法有很大的缺点。首先，加载的时候，浏览器会停止网页渲染，加载文件越多，网页失去响应的时间就会越长；其次，由于js文件之间存在依赖关系，因此必须严格保证加载顺序（比如上例的1.js要在2.js的前面），依赖性最大的模块一定要放到最后加载，当依赖关系很复杂的时候，代码的编写和维护都会变得困难。
require.js的诞生，就是为了解决这两个问题：

（1）实现js文件的异步加载，避免网页失去响应；
（2）管理模块之间的依赖性，便于代码的编写和维护。

二、require.js的加载
使用require.js的第一步，是先去官方网站下载最新版本。
下载后，假定把它放在js子目录下面，就可以加载了。
　　<script src="js/require.js"></script>
有人可能会想到，加载这个文件，也可能造成网页失去响应。解决办法有两个，一个是把它放在网页底部加载，另一个是写成下面这样：

<script src="js/require.js" defer async="true" ></script>
async属性表明这个文件需要异步加载，避免网页失去响应。IE不支持这个属性，只支持defer，所以把defer也写上。
加载require.js以后，下一步就要加载我们自己的代码了。假定我们自己的代码文件是main.js，也放在js目录下面。那么，只需要写成下面这样就行了：
　　<script src="js/require.js" data-main="js/main"></script>

data-main属性的作用是，指定网页程序的主模块。在上例中，就是js目录下面的main.js，这个文件会第一个被require.js加载。由于require.js默认的文件后缀名是js，所以可以把main.js简写成main。

三、主模块的写法
上一节的main.js，我把它称为"主模块"，意思是整个网页的入口代码。它有点像C语言的main()函数，所有代码都从这儿开始运行。
下面就来看，怎么写main.js。
如果我们的代码不依赖任何其他模块，那么可以直接写入javascript代码。
　　// main.js
　　alert("加载成功！");
但这样的话，就没必要使用require.js了。真正常见的情况是，主模块依赖于其他模块，这时就要使用AMD规范定义的的require()函数。
　　// main.js
　　require(['moduleA', 'moduleB', 'moduleC'], function (moduleA, moduleB, moduleC){
　　　　// some code here
　　});

require()函数接受两个参数。第一个参数是一个数组，表示所依赖的模块，上例就是['moduleA', 'moduleB', 'moduleC']，即主模块依赖这三个模块；第二个参数是一个回调函数，当前面指定的模块都加载成功后，它将被调用。加载的模块会以参数形式传入该函数，从而在回调函数内部就可以使用这些模块。
require()异步加载moduleA，moduleB和moduleC，浏览器不会失去响应；它指定的回调函数，只有前面的模块都加载成功后，才会运行，解决了依赖性的问题。
下面，我们看一个实际的例子。
假定主模块依赖jquery、underscore和backbone这三个模块，main.js就可以这样写：

　require(['jquery', 'underscore', 'backbone'], function ($, _, Backbone){
　　　　// some code here
　　});
require.js会先加载jQuery、underscore和backbone，然后再运行回调函数。主模块的代码就写在回调函数中。

四、模块的加载
上一节最后的示例中，主模块的依赖模块是['jquery', 'underscore', 'backbone']。默认情况下，require.js假定这三个模块与main.js在同一个目录，文件名分别为jquery.js，underscore.js和backbone.js，然后自动加载。
使用require.config()方法，我们可以对模块的加载行为进行自定义。require.config()就写在主模块（main.js）的头部。参数就是一个对象，这个对象的paths属性指定各个模块的加载路径。
　　require.config({
　　　　paths: {
　　　　　　"jquery": "jquery.min",
　　　　　　"underscore": "underscore.min",
　　　　　　"backbone": "backbone.min"
　　　　}
　　});
上面的代码给出了三个模块的文件名，路径默认与main.js在同一个目录（js子目录）。如果这些模块在其他目录，比如js/lib目录，则有两种写法。一种是逐一指定路径。

如js/lib目录，则有两种写法。一种是逐一指定路径。
　　require.config({
　　　　paths: {
　　　　　　"jquery": "lib/jquery.min",
　　　　　　"underscore": "lib/underscore.min",
　　　　　　"backbone": "lib/backbone.min"
　　　　}
　　});
另一种则是直接改变基目录（baseUrl）。
　　require.config({
　　　　baseUrl: "js/lib",
　　　　paths: {
　　　　　　"jquery": "jquery.min",
　　　　　　"underscore": "underscore.min",
　　　　　　"backbone": "backbone.min"
　　　　}
　　});
如果某个模块在另一台主机上，也可以直接指定它的网址，比如：
　　require.config({
　　　　paths: {
　　　　　　"jquery": "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min"
　　　　}
　　});
require.js要求，每个模块是一个单独的js文件。这样的话，如果加载多个模块，就会发出多次HTTP请求，会影响网页的加载速度。因此，require.js提供了一个优化工具，当模块部署完毕以后，可以用这个工具将多个模块合并在一个文件中，减少HTTP请求数。
五、AMD模块的写法
require.js加载的模块，采用AMD规范。也就是说，模块必须按照AMD的规定来写。
具体来说，就是模块必须采用特定的define()函数来定义。如果一个模块不依赖其他模块，那么可以直接定义在define()函数之中。
假定现在有一个math.js文件，它定义了一个math模块。那么，math.js就要这样写：
　　// math.js
　　define(function (){
　　　　var add = function (x,y){
　　　　　　return x+y;
　　　　};
　　　　return {
　　　　　　add: add
　　　　};
　　});
加载方法如下：
　　// main.js
　　require(['math'], function (math){
　　　　alert(math.add(1,1));
　　});
如果这个模块还依赖其他模块，那么define()函数的第一个参数，必须是一个数组，指明该模块的依赖性。
　　define(['myLib'], function(myLib){
　　　　function foo(){
　　　　　　myLib.doSomething();
　　　　}
　　　　return {
　　　　　　foo : foo
　　　　};
　　});
当require()函数加载上面这个模块的时候，就会先加载myLib.js文件。
六、加载非规范的模块
理论上，require.js加载的模块，必须是按照AMD规范、用define()函数定义的模块。但是实际上，虽然已经有一部分流行的函数库（比如jQuery）符合AMD规范，更多的库并不符合。那么，require.js是否能够加载非规范的模块呢？
回答是可以的。
这样的模块在用require()加载之前，要先用require.config()方法，定义它们的一些特征。
举例来说，underscore和backbone这两个库，都没有采用AMD规范编写。如果要加载它们的话，必须先定义它们的特征。
　　require.config({
　　　　shim: {

　　　　　　'underscore':{
　　　　　　　　exports: '_'
　　　　　　},
　　　　　　'backbone': {
　　　　　　　　deps: ['underscore', 'jquery'],
　　　　　　　　exports: 'Backbone'
　　　　　　}
　　　　}
　　});
require.config()接受一个配置对象，这个对象除了有前面说过的paths属性之外，还有一个shim属性，专门用来配置不兼容的模块。具体来说，每个模块要定义（1）exports值（输出的变量名），表明这个模块外部调用时的名称；（2）deps数组，表明该模块的依赖性。
比如，jQuery的插件可以这样定义：
　　shim: {
　　　　'jquery.scroll': {
　　　　　　deps: ['jquery'],
　　　　　　exports: 'jQuery.fn.scroll'
　　　　}
　　}
七、require.js插件
require.js还提供一系列插件，实现一些特定的功能。
domready插件，可以让回调函数在页面DOM结构加载完成后再运行。
　　require(['domready!'], function (doc){
　　　　// called once the DOM is ready
　　});
text和image插件，则是允许require.js加载文本和图片文件。
　　define([
　　　　'text!review.txt',
　　　　'image!cat.jpg'
　　　　],

　　　　function(review,cat){
　　　　　　console.log(review);
　　　　　　document.body.appendChild(cat);
　　　　}
　　);
类似的插件还有json和mdown，用于加载json文件和markdown文件。
 

JavaScript的Array可以包含任意数据类型，并通过索引来访问每个元素。

要取得Array的长度，直接访问length属性：

var arr = [1, 2, 3.14, 'Hello', null, true];
arr.length; // 6
请注意，直接给Array的length赋一个新的值会导致Array大小的变化：

var arr = [1, 2, 3];
arr.length; // 3
arr.length = 6;
arr; // arr变为[1, 2, 3, undefined, undefined, undefined]

indexOf

与String类似，Array也可以通过indexOf()来搜索一个指定的元素的位置：

var arr = [10, 20, '30', 'xyz'];
arr.indexOf(10); // 元素10的索引为0

slice

slice()就是对应String的substring()版本，它截取Array的部分元素，然后返回一个新的Array：

var arr = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
arr.slice(0, 3); // 从索引0开始，到索引3结束，但不包括索引3: ['A', 'B', 'C']
arr.slice(3); // 从索引3开始到结束: ['D', 'E', 'F', 'G']

push和pop

push()向Array的末尾添加若干元素，pop()则把Array的最后一个元素删除掉：

var arr = [1, 2];
arr.push('A', 'B'); // 返回Array新的长度: 4
arr; // [1, 2, 'A', 'B']
arr.pop(); // pop()返回'B'
arr; // [1, 2, 'A']
arr.pop(); arr.pop(); arr.pop(); // 连续pop 3次
arr; // []
arr.pop(); // 空数组继续pop不会报错，而是返回undefined
arr; // []

unshift和shift

如果要往Array的头部添加若干元素，使用unshift()方法，shift()方法则把Array的第一个元素删掉：

var arr = [1, 2];
arr.unshift('A', 'B'); // 返回Array新的长度: 4
arr; // ['A', 'B', 1, 2]
arr.shift(); // 'A'
arr; // ['B', 1, 2]
arr.shift(); arr.shift(); arr.shift(); // 连续shift 3次
arr; // []
arr.shift(); // 空数组继续shift不会报错，而是返回undefined
arr; // []

sort

sort()可以对当前Array进行排序，它会直接修改当前Array的元素位置，直接调用时，按照默认顺序排序：

var arr = ['B', 'C', 'A'];
arr.sort();
arr; // ['A', 'B', 'C']

reverse

reverse()把整个Array的元素给掉个个，也就是反转：

var arr = ['one', 'two', 'three'];
arr.reverse(); 
arr; // ['three', 'two', 'one']

splice() 方法向/从数组中添加/删除项目，然后返回被删除的项目。
注释：该方法会改变原始数组。
splice()方法是修改Array的“万能方法”，它可以从指定的索引开始删除若干元素，然后再从该位置添加若干元素：

var arr = ['Microsoft', 'Apple', 'Yahoo', 'AOL', 'Excite', 'Oracle'];
// 从索引2开始删除3个元素,然后再添加两个元素:
arr.splice(2, 3, 'Google', 'Facebook'); // 返回删除的元素 ['Yahoo', 'AOL', 'Excite']
arr; // ['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']
// 只删除,不添加:
arr.splice(2, 2); // ['Google', 'Facebook']
arr; // ['Microsoft', 'Apple', 'Oracle']
// 只添加,不删除:
arr.splice(2, 0, 'Google', 'Facebook'); // 返回[],因为没有删除任何元素
arr; // ['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']

concat

concat()方法把当前的Array和另一个Array连接起来，并返回一个新的Array：

var arr = ['A', 'B', 'C'];
var added = arr.concat([1, 2, 3]);
added; // ['A', 'B', 'C', 1, 2, 3]
arr; // ['A', 'B', 'C']
请注意，concat()方法并没有修改当前Array，而是返回了一个新的Array。

实际上，concat()方法可以接收任意个元素和Array，并且自动把Array拆开，然后全部添加到新的Array里：

join

join()方法是一个非常实用的方法，它把当前Array的每个元素都用指定的字符串连接起来，然后返回连接后的字符串：

var arr = ['A', 'B', 'C', 1, 2, 3];
arr.join('-'); // 'A-B-C-1-2-3'
如果Array的元素不是字符串，将自动转换为字符串后再连接。

如果Array的元素不是字符串，将自动转换为字符串后再连接。

JavaScript的对象是一种无序的集合数据类型，它由若干键值对组成。

JavaScript的对象用于描述现实世界中的某个对象。例如，为了描述“小明”这个淘气的小朋友，我们可以用若干键值对来描述他：

var xiaoming = {
    name: '小明',
    birth: 1990,
    school: 'No.1 Middle School',
    height: 1.70,
    weight: 65,
    score: null
};

JavaScript用一个{...}表示一个对象，键值对以xxx: xxx形式申明，用,隔开。注意，最后一个键值对不需要在末尾加,，如果加了，有的浏览器（如低版本的IE）将报错。

上述对象申明了一个name属性，值是'小明'，birth属性，值是1990，以及其他一些属性。最后，把这个对象赋值给变量xiaoming后，就可以通过变量xiaoming来获取小明的属性了：

xiaoming.name; // '小明'
xiaoming.birth; // 1990
访问属性是通过.操作符完成的，但这要求属性名必须是一个有效的变量名。如果属性名包含特殊字符，就必须用''括起来：

var xiaohong = {
    name: '小红',
    'middle-school': 'No.1 Middle School'
};

xiaohong的属性名middle-school不是一个有效的变量，就需要用''括起来。访问这个属性也无法使用.操作符，必须用['xxx']来访问：

xiaohong['middle-school']; // 'No.1 Middle School'
xiaohong['name']; // '小红'
xiaohong.name; // '小红'

也可以用xiaohong['name']来访问xiaohong的name属性，不过xiaohong.name的写法更简洁。我们在编写JavaScript代码的时候，属性名尽量使用标准的变量名，这样就可以直接通过object.prop的形式访问一个属性了。

实际上JavaScript对象的所有属性都是字符串，不过属性对应的值可以是任意数据类型。

如果访问一个不存在的属性会返回什么呢？JavaScript规定，访问不存在的属性不报错，而是返回undefined：

var xiaoming = {
    name: '小明'
};
xiaoming.age; // undefined

由于JavaScript的对象是动态类型，你可以自由地给一个对象添加或删除属性：

var xiaoming = {
    name: '小明'
};
xiaoming.age; // undefined
xiaoming.age = 18; // 新增一个age属性
xiaoming.age; // 18
delete xiaoming.age; // 删除age属性
xiaoming.age; // undefined
delete xiaoming['name']; // 删除name属性
xiaoming.name; // undefined
delete xiaoming.school; // 删除一个不存在的school属性也不会报错
如果我们要检测xiaoming是否拥有某一属性，可以用in操作符：

如果我们要检测xiaoming是否拥有某一属性，可以用in操作符：

var xiaoming = {
    name: '小明',
    birth: 1990,
    school: 'No.1 Middle School',
    height: 1.70,
    weight: 65,
    score: null
};
'name' in xiaoming; // true
'grade' in xiaoming; // false
不过要小心，如果in判断一个属性存在，这个属性不一定是xiaoming的，它可能是xiaoming继承得到的：

'toString' in xiaoming; // true
因为toString定义在object对象中，而所有对象最终都会在原型链上指向object，所以xiaoming也拥有toString属性。

要判断一个属性是否是xiaoming自身拥有的，而不是继承得到的，可以用hasOwnProperty()方法：

var xiaoming = {
    name: '小明'
};
xiaoming.hasOwnProperty('name'); // true
xiaoming.hasOwnProperty('toString'); // false

Map和Set

阅读: 67464
JavaScript的默认对象表示方式{}可以视为其他语言中的Map或Dictionary的数据结构，即一组键值对。

但是JavaScript的对象有个小问题，就是键必须是字符串。但实际上Number或者其他数据类型作为键也是非常合理的。

为了解决这个问题，最新的ES6规范引入了新的数据类型Map。要测试你的浏览器是否支持ES6规范，请执行以下代码，如果浏览器报ReferenceError错误，那么你需要换一个支持ES6的浏览器：

'use strict';
var m = new Map();
var s = new Set();
alert('你的浏览器支持Map和Set！');

Map

Map是一组键值对的结构，具有极快的查找速度。

举个例子，假设要根据同学的名字查找对应的成绩，如果用Array实现，需要两个Array：
var m = new Map(); // 空Map
m.set('Adam', 67); // 添加新的key-value
m.set('Bob', 59);
m.has('Adam'); // 是否存在key 'Adam': true
m.get('Adam'); // 67
m.delete('Adam'); // 删除key 'Adam'
m.get('Adam'); // undefined

Map和Set

阅读: 67464
JavaScript的默认对象表示方式{}可以视为其他语言中的Map或Dictionary的数据结构，即一组键值对。

但是JavaScript的对象有个小问题，就是键必须是字符串。但实际上Number或者其他数据类型作为键也是非常合理的。

为了解决这个问题，最新的ES6规范引入了新的数据类型Map。要测试你的浏览器是否支持ES6规范，请执行以下代码，如果浏览器报ReferenceError错误，那么你需要换一个支持ES6的浏览器：

'use strict';
var m = new Map();
var s = new Set();
alert('你的浏览器支持Map和Set！');

// 直接运行测试

 Run
Map

Map是一组键值对的结构，具有极快的查找速度。

举个例子，假设要根据同学的名字查找对应的成绩，如果用Array实现，需要两个Array：

var names = ['Michael', 'Bob', 'Tracy'];
var scores = [95, 75, 85];
给定一个名字，要查找对应的成绩，就先要在names中找到对应的位置，再从scores取出对应的成绩，Array越长，耗时越长。

如果用Map实现，只需要一个“名字”-“成绩”的对照表，直接根据名字查找成绩，无论这个表有多大，查找速度都不会变慢。用JavaScript写一个Map如下：

var m = new Map([['Michael', 95], ['Bob', 75], ['Tracy', 85]]);
m.get('Michael'); // 95
初始化Map需要一个二维数组，或者直接初始化一个空Map。Map具有以下方法：

var m = new Map(); // 空Map
m.set('Adam', 67); // 添加新的key-value
m.set('Bob', 59);
m.has('Adam'); // 是否存在key 'Adam': true
m.get('Adam'); // 67
m.delete('Adam'); // 删除key 'Adam'
m.get('Adam'); // undefined
由于一个key只能对应一个value，所以，多次对一个key放入value，后面的值会把前面的值冲掉：

var m = new Map();
m.set('Adam', 67);
m.set('Adam', 88);
m.get('Adam'); // 88

Set

Set和Map类似，也是一组key的集合，但不存储value。由于key不能重复，所以，在Set中，没有重复的key。

要创建一个Set，需要提供一个Array作为输入，或者直接创建一个空Set：

var s1 = new Set(); // 空Set
var s2 = new Set([1, 2, 3]); // 含1, 2, 3
重复元素在Set中自动被过滤：

var s = new Set([1, 2, 3, 3, '3']);
s; // Set {1, 2, 3, "3"}

注意数字3和字符串'3'是不同的元素。

通过add(key)方法可以添加元素到Set中，可以重复添加，但不会有效果：

>>> s.add(4)
>>> s
{1, 2, 3, 4}
>>> s.add(4)
>>> s
{1, 2, 3, 4}
通过delete(key)方法可以删除元素：

var s = new Set([1, 2, 3]);
s; // Set {1, 2, 3}
s.delete(3);
s; // Set {1, 2}

遍历Array可以采用下标循环，遍历Map和Set就无法使用下标。为了统一集合类型，ES6标准引入了新的iterable类型，Array、Map和Set都属于iterable类型。

具有iterable类型的集合可以通过新的for ... of循环来遍历。

for ... of循环是ES6引入的新的语法，请测试你的浏览器是否支持：

'use strict';

























*/