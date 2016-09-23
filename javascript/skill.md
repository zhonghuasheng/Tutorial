## JavaScript使用技巧

### 获取兄弟节点
* $('#id').siblings() 当前元素的所有兄弟节点
* $('#id').prev() 当前元素的前一个兄弟节点
* $('#id').prevAll() 当前元素之前的所有兄弟节点
* $('#id').next() 当前元素之后的第一个兄弟节点
* $('#id').nextAll() 当前元素之后所有的行的节点

### 判断赋值

* 三目运算符

```javascript
    var b = null;
    var a = b == null ? 'test' : b;
```


* 或运算

```javascript
    var b = null;
    var a = b || 'test';
```

* 判断赋值

```javascript
    var b = null;
    var a = b && 'success' || 'fail';

    result: fail

    var b = 'a';
    var a = b && 'success' || 'fail';

    result: success
```

### 调用匿名function

```javascript
    ~function(){console.log('done')}();
```

### 数字的toString()

```javascript
    5.toString(); // Uncaught SyntaxError: Unexpected token ILLEGAL(…)
    /*
    * Javascript解析器中试图将数字的点操作符解析为浮点数字的一部分
    *
    *
    */
    5 .toString(); // "5"
    (5).toString(); // "5"
```

### 删除属性

删除属性的唯一方法是使用delete操作浮，设置属性为undefined或者null并不能真正删除属性，而仅仅是移除属性和值的关联

```javascript
    var user = {
        id: 1,
        name: 'test',
        age: 18
    };

    user.age = undefined; //Object {id: 1, name: "test", age: undefined}
    user.age = null; //Object {id: 1, name: "test", age: null}
    delete user.age && 'a' || 'b'; // result is true, print user: Object {id: 1, name: "test"}
```

### 查找对象自身函数而不是原型链上的属性

```javascript
    Object.prototype.bar = 1;
    var foo = {goo: undefined};

    foo.bar; // 1
    'bar' in foo; // true

    foo.hasOwnProperty('bar'); //false
    foo.hasOwnProperty('goo'); // true
    /*
     * 只有 hasOwnProperty 可以给出正确和期望的结果，这在遍历对象的属性时会很有用。 没有其它方法可以用来排除原型链上的属性，而不是定义在对象自身上的属性。
     */
```

```javascript
   /*
    * JavaScript 不会保护 hasOwnProperty 被非法占用，因此如果一个对象碰巧存在这个属性， 就需要使用外部的 hasOwnProperty 函数来获取正确的结果。
    */
    var foo = {
        hasOwnProperty: function() {
            return false;
        },

        bar: 'test'
    };

    foo.hasOwnProperty('bar'); // false
    // 使用对象({}) -> Object, ({foo})->foo的hasOwnProperty,将其上下文设置为foo
    ({}).hasOwnProperty.call(foo, 'bar');
    /*
     * 当检查对象上某个属性是否存在时，hasOwnProperty 是唯一可用的方法。 同时在使用 for in loop 遍历对象时，推荐总是使用 hasOwnProperty 方法， 这将会避免原型对象扩展带来的干扰。
     */
```

```javascript
    Object.prototype.bar = 1;

    var foo = {
        moo: 2
    }

    //
    for (var i in foo) {
        console.log(i); // bar moo, 和 in 操作符一样，for in 循环同样在查找对象属性时遍历原型链上的所有属性。
    }

    for (var i in foo) {
        if (foo.hasOwnProperty(i)) {
            console.log(i); // moo
        }
    }
```