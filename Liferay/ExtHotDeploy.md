[Java][Liferay] 解决Liferay ext项目deploy的问题
Liferay ext project在install war包之后需要重启服务器，重启服务器中会执行ExtHotDeployListener中的逻辑，这里有一个坑，如果是第二次以后install ext war包，会发现新修改的文件不起作用，原因如下

    ExtHotDeployListener.java
```java

    protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
        throws Exception {

        ServletContext servletContext = hotDeployEvent.getServletContext();

        String servletContextName = servletContext.getServletContextName();

        if (_log.isDebugEnabled()) {
            _log.debug("Invoking deploy for " + servletContextName);
        }

        String xml = HttpUtil.URLtoString(
            servletContext.getResource(
                "/WEB-INF/ext-" + servletContextName + ".xml"));

        if (xml == null) {
            return;
        }

        if (_log.isInfoEnabled()) {
            _log.info(
                "Registering extension environment for " + servletContextName);
        }

        /*
        * Ext 项目在重启tomcat的时候会checkd liferay-portal/tomcat-xxx/webapps/ROOT/WEB-INF/ext-project-***-ext.xml是否存在
        * 如果存在，就不执行后面的逻辑，也就不会执行installExt(***)，那么也不会重新拷贝替换的文件
        *
        */
        if (ExtRegistry.isRegistered(servletContextName)) {
            if (_log.isInfoEnabled()) {
                _log.info(
                    "Extension environment for " + servletContextName +
                        " has been applied.");
            }

            return;
        }

        Map<String, Set<String>> conflicts = ExtRegistry.getConflicts(
            servletContext);

        if (!conflicts.isEmpty()) {
            StringBundler sb = new StringBundler();

            sb.append(
                "Extension environment for " + servletContextName +
                    " cannot be applied because of detected conflicts:");

            for (Map.Entry<String, Set<String>> entry : conflicts.entrySet()) {
                String conflictServletContextName = entry.getKey();
                Set<String> conflictFiles = entry.getValue();

                sb.append("\n\t");
                sb.append(conflictServletContextName);
                sb.append(":");

                for (String conflictFile : conflictFiles) {
                    sb.append("\n\t\t");
                    sb.append(conflictFile);
                }
            }

            _log.error(sb.toString());

            return;
        }

        installExt(servletContext, hotDeployEvent.getContextClassLoader());

        FileAvailabilityUtil.reset();

        if (_log.isInfoEnabled()) {
            _log.info(
                "Extension environment for " + servletContextName +
                    " has been applied. You must reboot the server and " +
                        "redeploy all other plugins.");
        }
    }
```

#### 解决方案
1. 去除掉doInvokeDeploy中这段逻辑，不管是否包含ext-project-***-ext.xml都执行install

```java

if (ExtRegistry.isRegistered(servletContextName)) {
    if (_log.isInfoEnabled()) {
        _log.info(
            "Extension environment for " + servletContextName +
                " has been applied.");
    }

    return;
}
```

2. 在第二次install ext project war包之后需要删除liferay-portal/tomcat-xxx/webapps/ROOT/WEB-INF/ext-project-***-ext.xml，然后重启tomcat

<?xml version="1.0"?>

<ext-info>
	<servlet-context-name>hsf-plugins-shared-ext</servlet-context-name>
	<files>
		<file>xxxx-ext.xml</file>
		<file>ext-impl/classes/com/liferay/portal/action/xxx.class</file>
                ....
		<file>ext-web/docroot/html/js/xxx.js</file>
		<file>web.xml</file>
	</files>
</ext-info>


javascript.fast.load=false
javascript.log.enabled=false

freemarker.engine.cache.storage=soft:1
freemarker.engine.resource.modification.check.interval=0

com.liferay.portal.servlet.filters.cache.CacheFilter=false
com.liferay.portal.servlet.filters.etag.ETagFilter=false
com.liferay.portal.servlet.filters.header.HeaderFilter=false
com.liferay.portal.servlet.filters.themepreview.ThemePreviewFilter=true

auth.login.url=/sign-in
auth.login.site.url=/sign-in

spring.configs=\
        META-INF/base-spring.xml,\
        \
        META-INF/hibernate-spring.xml,\
        META-INF/infrastructure-spring.xml,\
        META-INF/management-spring.xml,\
        \
        META-INF/util-spring.xml,\
        \
        META-INF/jpa-spring.xml,\
        \
        META-INF/executor-spring.xml,\
        \
        META-INF/audit-spring.xml,\
        META-INF/cluster-spring.xml,\
        META-INF/editor-spring.xml,\
        META-INF/jcr-spring.xml,\
        META-INF/ldap-spring.xml,\
        META-INF/messaging-core-spring.xml,\
        META-INF/messaging-misc-spring.xml,\
        META-INF/mobile-device-spring.xml,\
        META-INF/notifications-spring.xml,\
        META-INF/poller-spring.xml,\
        META-INF/rules-spring.xml,\
        META-INF/scheduler-spring.xml,\
        META-INF/search-spring.xml,\
        META-INF/workflow-spring.xml,\
        \
        META-INF/counter-spring.xml,\
        META-INF/mail-spring.xml,\
        META-INF/portal-spring.xml,\
        META-INF/portlet-container-spring.xml,\
        META-INF/staging-spring.xml,\
        META-INF/virtual-layouts-spring.xml,\
        \
        META-INF/monitoring-spring.xml,\
        \
        #META-INF/dynamic-data-source-spring.xml,\
        #META-INF/shard-data-source-spring.xml,\
        #META-INF/memcached-spring.xml,\
        \
        classpath*:META-INF/ext-spring.xml

##
## JavaScript
##

    #
    # Set a list of JavaScript files that will be loaded automatically in
    # /html/common/themes/top_js.jsp.
    #
    # There are two lists of files specified in the properties
    # "javascript.barebone.files" and "javascript.everything.files".
    #
    # As the name suggests, the barebone list is the minimum list of JavaScript
    # files required for most cases. The everything list includes everything
    # else not listed in the barebone list.
    #
    # The two lists of files exist for performance reasons because
    # unauthenticated users usually do not utilize all the JavaScript that is
    # available. See the property "javascript.barebone.enabled" for more
    # information on the logic of when the barebone list is used and when the
    # everything list is used and how to customize that logic.
    #
    # The list of files are also merged and packed for further performance
    # improvements. See the property "javascript.fast.load" for more details.
    #

    #
    # Specify the list of barebone files.
    #
    # The ordering of the JavaScript files is important.
    #
    # The Liferay scripts are grouped in such a way, that the first grouping
    # denotes utility scripts that are used by the second and third groups. The
    # second grouping denotes utility classes that rely on the first group, but
    # does not rely on the second or third group. The third grouping denotes
    # modules that rely on the first and second group.
    #
    javascript.barebone.files=\
        \
        #
        # YUI core
        #
        \
        aui/aui/aui.js,\
        aui/aui-base-html5-shiv/aui-base-html5-shiv.js,\
        \
        #
        # Liferay module definitions
        #
        \
        liferay/browser_selectors.js,\
        liferay/modules.js,\
        \
        #
        # AUI sandbox
        #
        \
        liferay/aui_sandbox.js,\
        \
        #
        # YUI modules
        #
        \
        aui/arraylist-add/arraylist-add.js,\
        aui/arraylist-filter/arraylist-filter.js,\
        aui/arraylist/arraylist.js,\
        aui/array-extras/array-extras.js,\
        aui/array-invoke/array-invoke.js,\
        aui/attribute-base/attribute-base.js,\
        aui/attribute-complex/attribute-complex.js,\
        aui/attribute-core/attribute-core.js,\
        aui/attribute-observable/attribute-observable.js,\
        aui/attribute-extras/attribute-extras.js,\
        aui/base-base/base-base.js,\
        aui/base-pluginhost/base-pluginhost.js,\
        aui/classnamemanager/classnamemanager.js,\
        aui/datatype-xml-format/datatype-xml-format.js,\
        aui/datatype-xml-parse/datatype-xml-parse.js,\
        aui/dom-base/dom-base.js,\
        aui/dom-core/dom-core.js,\
        aui/dom-screen/dom-screen.js,\
        aui/dom-style/dom-style.js,\
        aui/event-base/event-base.js,\
        aui/event-custom-base/event-custom-base.js,\
        aui/event-custom-complex/event-custom-complex.js,\
        aui/event-delegate/event-delegate.js,\
        aui/event-focus/event-focus.js,\
        aui/event-hover/event-hover.js,\
        aui/event-key/event-key.js,\
        aui/event-mouseenter/event-mouseenter.js,\
        aui/event-mousewheel/event-mousewheel.js,\
        aui/event-outside/event-outside.js,\
        aui/event-resize/event-resize.js,\
        aui/event-simulate/event-simulate.js,\
        aui/event-synthetic/event-synthetic.js,\
        aui/intl/intl.js,\
        aui/io-base/io-base.js,\
        aui/io-form/io-form.js,\
        aui/io-queue/io-queue.js,\
        aui/io-upload-iframe/io-upload-iframe.js,\
        aui/io-xdr/io-xdr.js,\
        aui/json-parse/json-parse.js,\
        aui/json-stringify/json-stringify.js,\
        aui/node-base/node-base.js,\
        aui/node-core/node-core.js,\
        aui/node-event-delegate/node-event-delegate.js,\
        aui/node-event-simulate/node-event-simulate.js,\
        aui/node-focusmanager/node-focusmanager.js,\
        aui/node-pluginhost/node-pluginhost.js,\
        aui/node-screen/node-screen.js,\
        aui/node-style/node-style.js,\
        aui/oop/oop.js,\
        aui/plugin/plugin.js,\
        aui/pluginhost-base/pluginhost-base.js,\
        aui/pluginhost-config/pluginhost-config.js,\
        aui/querystring-stringify-simple/querystring-stringify-simple.js,\
        aui/queue-promote/queue-promote.js,\
        aui/selector-css2/selector-css2.js,\
        aui/selector-css3/selector-css3.js,\
        aui/selector-native/selector-native.js,\
        aui/selector/selector.js,\
        aui/widget-base/widget-base.js,\
        aui/widget-htmlparser/widget-htmlparser.js,\
        aui/widget-skin/widget-skin.js,\
        aui/widget-uievents/widget-uievents.js,\
        aui/yui-throttle/yui-throttle.js,\
        \
        #
        # Alloy core
        #
        \
        aui/aui-base-core/aui-base-core.js,\
        aui/aui-base-lang/aui-base-lang.js,\
        \
        #
        # Alloy modules
        #
        \
        aui/aui-classnamemanager/aui-classnamemanager.js,\
        aui/aui-component/aui-component.js,\
        aui/aui-debounce/aui-debounce.js,\
        aui/aui-delayed-task-deprecated/aui-delayed-task-deprecated.js,\
        aui/aui-event-base/aui-event-base.js,\
        aui/aui-event-input/aui-event-input.js,\
        aui/aui-form-validator/aui-form-validator.js,\
        aui/aui-node-base/aui-node-base.js,\
        aui/aui-node-html5/aui-node-html5.js,\
        aui/aui-selector/aui-selector.js,\
        aui/aui-timer/aui-timer.js,\
        \
        #
        # Liferay base utility scripts
        #
        \
        liferay/dependency.js,\
        liferay/events.js,\
        liferay/language.js,\
        liferay/liferay.js,\
        liferay/util.js,\
        \
        #
        # Liferay utility scripts
        #
        \
        liferay/portal.js,\
        liferay/portlet.js,\
        liferay/portlet_sharing.js,\
        liferay/workflow.js,\
        \
        #
        # Liferay modules
        #
        \
        liferay/form.js,\
        liferay/form_placeholders.js,\
        liferay/icon.js,\
        liferay/menu.js,\
        liferay/notice.js,\
        liferay/poller.js,\
        \
        #
        # Ext modules
        #
        \
        extends/extends.js
