Liferay的AUI框架如何学习
2012年11月15日 Liferay 暂无评论 阅读 15,094 views 次

* 一、AUI是什么？

Liferay中的AUI框架，官方叫做Alloy，是一个UI超框架，能为浏览器的三方面，结构，风格和表现建立网络应用提供连续的简单的API。
它融合了三种设计语言：HTML, CSS 和 JAVA。
准确来说AUI是Liferay在YUI的基础上进行的Liferay扩展，以方便Liferay的一些实际应用和扩展。
AUI的很多语法结构、使用方法都都和YUI相关不太多。熟悉YUI的能够快速熟悉AUI。

* 二、  如何学习

学习AUI最简单的方法就是将官方的Alloy包下载下来，这个需要到Liferay官网上下载，没有和Liferay的下载包整合在一起。
下载地址：http://www.liferay.com/downloads/liferay-projects/alloy-ui
Github
https://github.com/liferay/alloy-ui.git

3. JSP include

    <%@include file="/html/init.jsp" %>

4. Code
    * 这里引入Java的Class
    <%@ page import="com.siemens.sw360.datahandler.thrift.users.User" %>
    <%@ page import="com.siemens.sw360.datahandler.thrift.users.UserGroup" %>
    <%@ page import="com.siemens.sw360.portal.common.PortalConstants" %>
    * 这里include JSP文件
    <%@include file="/html/init.jsp" %>

    <portlet:defineObjects/>
    <liferay-theme:defineObjects/>
    * 指定URL，在form的action里面使用
    <portlet:actionURL var="createAccountURL" name="createAccount">
    </portlet:actionURL>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sw360.css">
    <script src="<%=request.getContextPath()%>/js/external/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/external/jquery.validate.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/external/additional-methods.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/external/jquery-ui.min.js"></script>
    <h5>Sign Up For an Account<h5>
    <core_rt:if test="${themeDisplay.signedIn}">
        <p style="font-weight: bold;">You do not need to sign up.</p>
    </core_rt:if>
    <core_rt:if test="${not themeDisplay.signedIn}">
    <div id="createAccount">
        <form action="<%=createAccountURL%>" id="signup_form" method="post">
            <table>
                ...
            <tr>
                <td>
                    <label class="textlabel mandatory" for="last_name">Last Name</label>
                    * 这里就使用了Java Class中的属性， 由于input没有使用<aui:input>标签，所以name中要自己加上“<portlet:namespace/>”
                    <input type="text" name="<portlet:namespace/><%=User._Fields.LASTNAME%>" required=""
                           value="${newuser.lastname}" id="last_name">
                    * 这样的话在后台可以使用actionRequest.getParameter("userName")来获取值
                    <input type="text" name="<portlet:namespace/>userName"/>
                    * 这里使用了AUI的tag，可以省略“<portlet:namespace/>”
                    <aui:input type="text" name="userName" />
                </td>
            </tr>
        ....
        <script>
            $(document).ready(function () {
                $('#signup_form').validate({
                    rules: {
                        "<portlet:namespace/><%=PortalConstants.PASSWORD%>": "required",
                        "<portlet:namespace/><%=PortalConstants.PASSWORD_REPEAT%>": {
                            equalTo: '#password'
                        }
                    },
                    messages: {
                        "<portlet:namespace/><%=PortalConstants.PASSWORD_REPEAT%>": {
                            equalTo: "Passwords must match."
                        }
                    }
                });
            });
        </script>

5. Src
    <img src="<%=request.getContextPath()%>/images/search.png" alt="CleanUp"
    onclick="findDuplicates()" width="25px" height="25px" >

    alt 如果无法显示图像，浏览器将显示替代文本

CSS 指层叠样式表 (Cascading Style Sheets)
CSS 规则由两个主要的部分构成：选择器，以及一条或多条声明:

Place holder in select
select id="birthday-month" label="" cssClass="span5 birthday-month" name="birthdayMonth" required="true">
      <option value="" disabled selected>Month</option>

6. 如何禁用IE10的明文显示密码和快速清除功能
 IE10针对 <input>及<input type="password"> 分别提供了快速清除钮(X图标)以及密码文字显示钮(小眼睛图标)的功能：
快速清除钮可取代触控进行全选并删除的一连串复杂动作，而密码显示钮可协助用户确认输入内容，弥补触控打字慢、错误率高的困扰。但是基于Web系统安全的考虑，需要禁用该功能，特别是密码明文显示的功能。具体可以通过控制页面视图和CSS样式来实现。

1、首先在Web页面的HTML头里面加入如下代码，强制IE浏览器不使用兼容性视图：

<meta http-equiv="X-UA-Compatible" content="edge" />

2、通过过CSS虚拟元素(::-ms-clear、::-ms-reveal)禁用X和眼睛图标，具体代码如下：

input::-ms-clear{display:none;}

input[type="password"]::-ms-reveal{display:none;}
3. 修改本地计算机策略-用户配置-管理模板-Windows组件-凭据用户界面中，可以对全局的密码显示按钮进行设置。
双击“不显示密码显示按钮”，

<#macro cssAndJavaScript public useMin>
    <#assign suffix = "" />

    <#if useMin>
        <#assign suffix = ".min" />
    </#if>

    <#if public>
        <link rel="stylesheet" type="text/css" href="${base}/css/public/base${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/public/webpageLayout${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/public/forms${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/public/publicWebpages${suffix}.css" media="screen, projection" />
        <link rel="stylesheet" type="text/css" href="${base}/css/public/login${suffix}.css" media="screen, projection" />
    <#else>
        <link rel="stylesheet" type="text/css" href="${base}/css/ui-lightness/jquery-ui-1.8.6.custom${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/jcrop/jquery.jcrop${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/base${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/webpageLayout${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/forms${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/entityListing${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/entityView${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/questions${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/categoryQuestions${suffix}.css" media="screen, projection"/>
        <link rel="stylesheet" type="text/css" href="${base}/css/application/miniScoreCard${suffix}.css" media="screen, projection"/>
    </#if>

    <script src="${base}/js/jquery/jquery-1.4.2${suffix}.js"></script>
    <script src="${base}/js/jquery/jquery-ui-1.8.6.custom${suffix}.js"></script>
    <script src="${base}/js/jquery/jquery.tmpl${suffix}.js"></script>
    <script src="${base}/js/jquery/jquery.jcrop${suffix}.js"></script>
    <script src="${base}/js/json/json2${suffix}.js"></script>
    <script src="${base}/js/jwplayer/jwplayer${suffix}.js"></script>
    <script src="${base}/js/core${suffix}.js"></script>
</#macro>

jquery操作复选框(checkbox)的12个小技巧总结

作者： 字体：[增加 减小] 类型：转载 时间：2014-02-04 我要评论
本篇文章主要是对jquery操作复选框(checkbox)的12个小技巧进行了总结介绍，需要的朋友可以过来参考下，希望对大家有所帮助
1、获取单个checkbox选中项(三种写法)
$("input:checkbox:checked").val()
或者
$("input:[type='checkbox']:checked").val();
或者
$("input:[name='ck']:checked").val();
2、 获取多个checkbox选中项
$('input:checkbox').each(function() {
        if ($(this).attr('checked') ==true) {
                alert($(this).val());
        }
});
3、设置第一个checkbox 为选中值
$('input:checkbox:first').attr("checked",'checked');
或者
$('input:checkbox').eq(0).attr("checked",'true');
4、设置最后一个checkbox为选中值
$('input:radio:last').attr('checked', 'checked');
或者
$('input:radio:last').attr('checked', 'true');
5、根据索引值设置任意一个checkbox为选中值
$('input:checkbox).eq(索引值).attr('checked', 'true');索引值=0,1,2....
或者
$('input:radio').slice(1,2).attr('checked', 'true');
6、选中多个checkbox同时选中第1个和第2个的checkbox
$('input:radio').slice(0,2).attr('checked','true');
7、根据Value值设置checkbox为选中值
$("input:checkbox[value='1']").attr('checked','true');
8、删除Value=1的checkbox
$("input:checkbox[value='1']").remove();
9、删除第几个checkbox
$("input:checkbox").eq(索引值).remove();索引值=0,1,2....
如删除第3个checkbox:
$("input:checkbox").eq(2).remove();
10、遍历checkbox
$('input:checkbox').each(function (index, domEle) {
//写入代码
});
11、全部选中
$('input:checkbox').each(function() {
        $(this).attr('checked', true);
});
12、全部取消选择
$('input:checkbox').each(function () {
        $(this).attr('checked',false);
});

<@aui.script use="aui-base">
	var field = A.one('#${portletNamespace}${namespacedFieldName}');

	var form = field.get('form');

	if (form) {
		var handler = Liferay.on(
			'submitForm',
			function(event) {
				if (event.form.compareTo(form)) {
					field.val(window['${portletNamespace}${namespacedFieldName}Editor'].getHTML());
				}
			}
		);

		Liferay.DDM.RepeatableFields.eventHandlers['${portletNamespace}${namespacedFieldName}'] = [handler];
	}
</@>

/指定到ROOT目录
<#import "/html/js/aui/yui/yui-min.js" as YUI/>
<#assign aui = PortalJspTagLibs["/WEB-INF/tld/aui.tld"] />

<#include "../init.ftl">

<#if required>
    <#assign label = label + " (" + languageUtil.get(requestedLocale, "required") + ")">
</#if>
<#assign id = fieldStructure.type + "_">


<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label)>
    ${fieldStructure.children}
    <#if "${fieldStructure.type}" == "ddm-checkbox-group" && "${fieldStructure.selectAll}" == "true">
        <@aui.input id="${tagRandomId}" cssClass="selectAll" label="Select All" name="${tagRandomId}" type="checkbox" value="selectAll">
        </@aui.input>
    </#if>
</@>
<@liferay.js file_name="/html/portlet/dynamic_data_lists/js/checkbox-group.js" />

##Placeholder
Code Sample
<script src="http://yui.yahooapis.com/3.4.1/build/yui/yui-min.js"></script>
YUI({
    //Last Gallery Build of this module
    gallery: 'gallery-2012.06.20-20-07'
}).use('gallery-placeholder', function(Y) {
  var ipt = Y.one('#my-input');
  ipt.plug(Y.Plugin.Placeholder, {
    text : 'First Name',
    hideOnFocus : false
  });
});

<#if fieldStructure.rule??>
    <@aui.script>
        AUI({
            modules: {
                'ext_all': {
                    fullPath: 'html/js/extjs/ext_all.js',
                    type: 'js',
                    requires: ['base']
                }
            }
        }).use('ext_all', function(E) {
            if ('${fieldStructure.rule}' != '') {
                E.bindingRule(JSON.parse('${fieldStructure.rule}'), '${namespacedFieldName}', '${fieldStructure.type}');
            }
        });
        </@aui.script>
</#if>