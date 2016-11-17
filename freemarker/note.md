### FreeMarker模板的组成

### FTL指令规则
### Built-in Reference

FreeMarker模板文件主要由如下4个部分组成:

1. 文本:直接输出的部分
2. 注释:<#-- ... -->格式部分,不会输出
3. 插值:即${...}或#{...}格式的部分,将使用数据模型中的部分替代输出
4. FTL指令:FreeMarker指定,和HTML标记类似,名字前加#予以区分,不会输出

下面是一个FreeMarker模板的例子,包含了以上所说的4个部分

```html
<html>
  <head>
    <title>Welcome!</title>
  </head>
  <body>
    <#-- 注释部分 -->
    <#-- 下面使用插值 -->
    <h1>Welcome ${user} !</h1>
    <p>We have these animals:
    <u1>
    <#-- 使用FTL指令 -->
    <#list animals as being>
      <li>${being.name} for ${being.price} Euros
    <#list>
    <u1>
  </body>
</html>
```

### FTL指令规则

在FreeMarker中,使用FTL标签来使用指令,FreeMarker有3种FTL标签,这和HTML标签是完全类似的.
1,开始标签:<#directivename parameter>
2,结束标签:</#directivename>
3,空标签:<#directivename parameter/>

实际上,使用标签时前面的符号#也可能变成@,如果该指令是一个用户指令而不是系统内建指令时,应将#符号改成@符号.
使用FTL标签时, 应该有正确的嵌套,而不是交叉使用,这和XML标签的用法完全一样.如果全用不存在的指令,FreeMarker不会使用模板输出,而是产生一个错误消息.
FreeMarker会忽略FTL标签中的空白字符.值得注意的是< , /> 和指令之间不允许有空白字符.

#### include指令
include指令的作用类似于JSP的包含指令,用于包含指定页.include指令的语
<#include "../init.ftl">

#### import指令
该指令用于导入FreeMarker模板中的所有变量,并将该变量放置在指定的Map对象中,import指令的语法格式如下:
<#import "/lib/common.ftl" as com>
上面的代码将导入/lib/common.ftl模板文件中的所有变量,交将这些变量放置在一个名为com的Map对象中.

### Built-in Reference

#### Alphabetical index
[Freemarker 关键字](http://freemarker.org/docs/ref_builtins_alphaidx.html)

#### Built-ins for strings

##### 第一个字母大写

    ${"a bc"} //A bc

##### 首字母都大写

    ${"jim ren"?capitalize} // Jim Ren

##### contains

    <#if "test"?contains("tes")> Yes</#if>

##### date, time, datetime

可以通过设置`date_format`, `time_format`, `datetime_formate`来设置格式化时间输出, 具体参考[date](http://freemarker.org/docs/ref_builtins_date.html)

##### ends_with

    ${"head"?ends_with("head")} // boolean result: true

##### ensure_ends_with
如果string不是以substring结束，返回的结果将添加substring，否则返回原string

    ${"foo"?ensure_ends_with("/")} // foo/

