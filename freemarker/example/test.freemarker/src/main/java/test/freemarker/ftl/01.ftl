<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Insert title here</title>
</head>
  <body>
    你好，${username}

    <#list maps?keys as key>
        <p>key: ${key}, value: ${maps[key]}</p>
    </#list>
    <#list mapss?keys as key>
        <p>key: ${key}</p>
        <#assign values = mapss[key]>
        <#list values?keys as k>
            <a>key:${k}, value: ${values[k]}</a>
        </#list>
    </#list>

    <p>User: ${user.getName()}, ${user.getAge()}, ${user.toString()}</p>

    <#noparse>
        noparse指令指定FreeMarker不处理该指定里包含的内容
        ${asdf}
    </#noparse>
    <#noparse>
        FreeMarker的插值有如下两种类型:1,通用插值${expr};2,数字格式化插值:#{expr}或#{expr;format} 
        ${book.name?if_exists }  //用于判断如果存在,就输出这个值 
        ${book.name?default(‘xxx’)}//默认值xxx 
        ${book.name!"xxx"}//默认值xxx 
        ${book.date?string('yyyy-MM-dd')} //日期格式 
        ${book?string.number}  20 //三种不同的数字格式 
        ${book?string.currency}--<#-- $20.00 --> 
        ${book?string.percent}—<#-- 20% --> 
    </#noparse>
    User2.name是否存在
    ${user2.name?if_exists}, ${user.name?if_exists}
    ${user2.name!('xxx')},${user2.name?default('xdfs')}
  </body>
</html>