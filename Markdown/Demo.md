#Table的写法
<table>
    <tr>
        <td>Foo</td>
        <td>Foo</td>
        <td>Foo</td>
    </tr>
    <tr>
        <td>Foo</td>
        <td>Foo</td>
        <td>Foo</td>
    </tr>
    <tr>
        <td>Foo</td>
        <td>Foo</td>
        <td>Foo</td>
    </tr>
</table>

##< 写成 &lt; &写成&amp;
## © 写成 &copy;

#区域块引用Blockquotes
>This is a block quotes
可以不换行
通过加不同数量的>来实现

>>Please have a test

>测试一下
>>>Thank you

>##标题
>
>1. 第一行
>2. 第二行
>
>第三行

#列表:使用*
* Red
* Green
* Blue
###等同于使用+
+ Red
+ Green
+ Blue
###也等同于使用
- Red
- Green
- Blue


#在行首出现数字-句点-空白，可以在句点前面加上反斜杠
1234\. Just a test.

#代码区块
这是一个代码区块，使用enter空一行，然后使用tab：

    这是一个代码块
    测试
    用例
    <div class="header">
        Hello World
    </div>
    
#分隔线

***

* * *

-----------

---

#超链接
See my [About](/about/) page for details.

[This link](http://example.net/) has no title attribute.

*single asterisks*

***single asterisks***



_double asterisks_

__double underscores__


Use the `printf()` function.

``There is a literal backtick (`) here.``

A single backtick in a code span: `foo`

#Image
![Alt text](/path/to/img.jpg)

![Alt text](/path/to/img.jpg "Optional title")
