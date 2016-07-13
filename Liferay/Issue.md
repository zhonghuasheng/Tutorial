1.
ERROR [localhost-startStop-1][Cache:120] Unable to set localhost. This prevents creation of a GUID. Cause was: yt00335-116.augmentum.com.cn: yt00335-116.augmentum.com.cn
java.net.UnknownHostException: yt00335-116.augmentum.com.cn: yt00335-116.augmentum.com.cn
	at java.net.InetAddress.getLocalHost(InetAddress.java:1475)


[lukechen@yt00335-116 Desktop]$ vi /etc/hosts
[lukechen@yt00335-116 Desktop]$ cat /etc/hosts
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4 yt00335-116.augmentum.com.cn
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6 yt00335-116.augmentum.com.cn

2. Install wars && direct-deploy
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Server version:        Apache Tomcat/7.0.62
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Server built:          May 7 2015 17:14:55 UTC
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Server number:         7.0.62.0
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: OS Name:               Linux
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: OS Version:            3.10.0-229.el7.x86_64
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Architecture:          amd64
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Java Home:             /usr/java/jdk1.7.0_79/jre
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: JVM Version:           1.7.0_79-b15
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: JVM Vendor:            Oracle Corporation
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: CATALINA_BASE:         /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: CATALINA_HOME:         /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:32799
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -XX:PermSize=128M
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -XX:MaxPermSize=256M
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Duser.timezone=GMT
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dfile.encoding=UTF8
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Djava.net.preferIPv4Stack=true
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Djava.util.logging.config.file=/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/conf/logging.properties
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Djava.io.tmpdir=/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dexternal-properties=/home/lukechen/software/liferay-portal-6.2-ce-ga6/portal-ide.properties
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dcatalina.base=/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dcatalina.home=/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dwtp.deploy=/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Djava.endorsed.dirs=/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/endorsed
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Xmx1024m
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -XX:MaxPermSize=512m
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dfreemarker.debug.password=liferay
Jul 11, 2016 7:43:51 AM org.apache.catalina.startup.VersionLoggerListener log
INFO: Command line argument: -Dfreemarker.debug.port=57676
Jul 11, 2016 7:43:51 AM org.apache.catalina.core.AprLifecycleListener lifecycleEvent
INFO: The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: /usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib
Jul 11, 2016 7:43:52 AM org.apache.coyote.AbstractProtocol init
INFO: Initializing ProtocolHandler ["http-bio-8080"]
Jul 11, 2016 7:43:52 AM org.apache.coyote.AbstractProtocol init
INFO: Initializing ProtocolHandler ["ajp-bio-8009"]
Jul 11, 2016 7:43:52 AM org.apache.catalina.startup.Catalina load
INFO: Initialization processed in 892 ms
Jul 11, 2016 7:43:52 AM org.apache.catalina.core.StandardService startInternal
INFO: Starting service Catalina
Jul 11, 2016 7:43:52 AM org.apache.catalina.core.StandardEngine startInternal
INFO: Starting Servlet Engine: Apache Tomcat/7.0.62
Jul 11, 2016 7:43:52 AM org.apache.catalina.startup.HostConfig deployDescriptor
INFO: Deploying configuration descriptor /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/conf/Catalina/localhost/ROOT.xml
Jul 11, 2016 7:43:59 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
Loading jar:file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/ROOT/WEB-INF/lib/portal-impl.jar!/system.properties
Loading jar:file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/ROOT/WEB-INF/lib/portal-impl.jar!/portal.properties
Loading file:/home/lukechen/Desktop/portal-ext.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/portal-setup-wizard.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/portal-ide.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/ROOT/WEB-INF/classes/portal-developer.properties
Jul 11, 2016 7:44:01 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
07:44:04,086 INFO  [localhost-startStop-1][DialectDetector:71] Determine dialect for PostgreSQL 9
07:44:04,108 INFO  [localhost-startStop-1][DialectDetector:136] Found dialect org.hibernate.dialect.PostgreSQLDialect
Starting Liferay Portal Community Edition 6.2 CE GA6 (Newton / Build 6205 / January 6, 2016)
07:44:25,407 INFO  [localhost-startStop-1][StartupAction:97] There are no patches installed
07:44:25,935 INFO  [localhost-startStop-1][BaseDB:481] Database supports case sensitive queries
07:44:26,329 INFO  [localhost-startStop-1][ServerDetector:140] Server supports hot deploy
07:44:26,333 INFO  [localhost-startStop-1][PluginPackageUtil:1013] Reading plugin package for the root context
07:44:41,532 INFO  [localhost-startStop-1][AutoDeployDir:139] Auto deploy scanner started for /home/lukechen/software/liferay-portal-6.2-ce-ga6/deploy
Jul 11, 2016 7:44:44 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring FrameworkServlet 'Remoting Servlet'
Jul 11, 2016 7:44:44 AM org.apache.catalina.startup.HostConfig deployDescriptor
INFO: Deployment of configuration descriptor /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/conf/Catalina/localhost/ROOT.xml has finished in 52,619 ms
Jul 11, 2016 7:44:44 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/marketplace-portlet
Jul 11, 2016 7:44:46 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:44:46,364 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying marketplace-portlet from queue
07:44:46,365 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for marketplace-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/portlet.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/portlet.properties
Jul 11, 2016 7:44:46 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/service.properties
07:44:46,949 INFO  [localhost-startStop-1][HookHotDeployListener:709] Registering hook for marketplace-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/portal.properties
07:44:47,033 INFO  [localhost-startStop-1][HookHotDeployListener:851] Hook for marketplace-portlet is available for use
07:44:47,035 INFO  [localhost-startStop-1][PortletHotDeployListener:344] Registering portlets for marketplace-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/0-marketplace-portlet/WEB-INF/classes/portlet.properties
07:44:47,123 INFO  [localhost-startStop-1][PortletHotDeployListener:497] 3 portlets for marketplace-portlet are available for use
Jul 11, 2016 7:44:47 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/marketplace-portlet has finished in 2,189 ms
Jul 11, 2016 7:44:47 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/sync-web
Jul 11, 2016 7:44:49 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:44:49,137 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin sync-web requires marketplace-portlet
07:44:49,137 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying sync-web from queue
07:44:49,138 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for sync-web
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/1-sync-web/WEB-INF/classes/portlet.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/1-sync-web/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/1-sync-web/WEB-INF/classes/portlet.properties
Jul 11, 2016 7:44:49 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/1-sync-web/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/1-sync-web/WEB-INF/classes/service.properties
07:44:52,558 INFO  [com.liferay.portal.kernel.deploy.auto.AutoDeployScanner][AutoDeployDir:204] Processing hsf-plugins-shared-ext-ext-1.0.0.war
07:44:52,561 INFO  [com.liferay.portal.kernel.deploy.auto.AutoDeployScanner][ExtAutoDeployListener:50] Copying extension environment plugin for /home/lukechen/software/liferay-portal-6.2-ce-ga6/deploy/hsf-plugins-shared-ext-ext-1.0.0.war
07:44:52,574 INFO  [com.liferay.portal.kernel.deploy.auto.AutoDeployScanner][BaseDeployer:863] Deploying hsf-plugins-shared-ext-ext-1.0.0.war
  Expanding: /home/lukechen/software/liferay-portal-6.2-ce-ga6/deploy/hsf-plugins-shared-ext-ext-1.0.0.war into /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/20160711074452575
07:50:27,462 INFO  [localhost-startStop-1][HookHotDeployListener:709] Registering hook for sync-web
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/1-sync-web/WEB-INF/classes/portal.properties
07:50:27,841 ERROR [localhost-startStop-1][VerifyUtil:264] com.liferay.portlet.documentlibrary.NoSuchFileException: /home/lukechen/software/liferay-portal-6.2-ce-ga6/data/document_library/20155/26717/2/1.0
com.liferay.portlet.documentlibrary.NoSuchFileException: /home/lukechen/software/liferay-portal-6.2-ce-ga6/data/document_library/20155/26717/2/1.0
	at com.liferay.portlet.documentlibrary.store.FileSystemStore.getFileAsStream(FileSystemStore.java:224)
	at com.liferay.portlet.documentlibrary.store.StoreProxyImpl.getFileAsStream(StoreProxyImpl.java:178)
	at com.liferay.portlet.documentlibrary.store.SafeFileNameStoreWrapper.getFileAsStream(SafeFileNameStoreWrapper.java:264)
	at com.liferay.portlet.documentlibrary.store.DLStoreImpl.getFileAsStream(DLStoreImpl.java:300)
	at com.liferay.portlet.documentlibrary.store.DLStoreUtil.getFileAsStream(DLStoreUtil.java:452)
	at com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl.getFileAsStream(DLFileEntryLocalServiceImpl.java:1101)
	at com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl.getFileAsStream(DLFileEntryLocalServiceImpl.java:1084)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:115)
	at com.liferay.portal.spring.transaction.DefaultTransactionExecutor.execute(DefaultTransactionExecutor.java:62)
	at com.liferay.portal.spring.transaction.TransactionInterceptor.invoke(TransactionInterceptor.java:51)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ChainableMethodAdvice.invoke(ChainableMethodAdvice.java:56)
	at com.liferay.portal.spring.aop.ServiceBeanMethodInvocation.proceed(ServiceBeanMethodInvocation.java:111)
	at com.liferay.portal.spring.aop.ServiceBeanAopProxy.invoke(ServiceBeanAopProxy.java:175)
	at com.sun.proxy.$Proxy201.getFileAsStream(Unknown Source)
	at com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil.getFileAsStream(DLFileEntryLocalServiceUtil.java:613)
	at com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl.getContentStream(DLFileVersionImpl.java:53)
	at com.liferay.sync.util.SyncUtil.getChecksum(SyncUtil.java:161)
	at com.liferay.sync.util.SyncUtil.toSyncDLObject(SyncUtil.java:453)
	at com.liferay.sync.util.SyncUtil.toSyncDLObject(SyncUtil.java:384)
	at com.liferay.sync.util.VerifyUtil$2.performAction(VerifyUtil.java:258)
	at com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery.performActionsInSingleInterval(BaseActionableDynamicQuery.java:324)
	at com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery.performActions(BaseActionableDynamicQuery.java:46)
	at com.liferay.sync.util.VerifyUtil.verifyDLFileEntriesAndFolders(VerifyUtil.java:278)
	at com.liferay.sync.util.VerifyUtil.doVerify(VerifyUtil.java:103)
	at com.liferay.sync.util.VerifyUtil.verify(VerifyUtil.java:52)
	at com.liferay.sync.hook.events.StartupAction.doRun(StartupAction.java:44)
	at com.liferay.sync.hook.events.StartupAction.run(StartupAction.java:30)
	at com.liferay.portal.kernel.events.InvokerSimpleAction.run(InvokerSimpleAction.java:42)
	at com.liferay.portal.deploy.hot.HookHotDeployListener.initEvent(HookHotDeployListener.java:1390)
	at com.liferay.portal.deploy.hot.HookHotDeployListener.initEvents(HookHotDeployListener.java:1453)
	at com.liferay.portal.deploy.hot.HookHotDeployListener.initPortalProperties(HookHotDeployListener.java:1775)
	at com.liferay.portal.deploy.hot.HookHotDeployListener.doInvokeDeploy(HookHotDeployListener.java:720)
	at com.liferay.portal.deploy.hot.HookHotDeployListener.invokeDeploy(HookHotDeployListener.java:314)
	at com.liferay.portal.deploy.hot.HotDeployImpl.doFireDeployEvent(HotDeployImpl.java:230)
	at com.liferay.portal.deploy.hot.HotDeployImpl.fireDeployEvent(HotDeployImpl.java:96)
	at com.liferay.portal.kernel.deploy.hot.HotDeployUtil.fireDeployEvent(HotDeployUtil.java:28)
	at com.liferay.portal.kernel.servlet.PluginContextListener.fireDeployEvent(PluginContextListener.java:164)
	at com.liferay.portal.kernel.servlet.PluginContextListener.doPortalInit(PluginContextListener.java:154)
	at com.liferay.portal.kernel.util.BasePortalLifecycle.portalInit(BasePortalLifecycle.java:44)
	at com.liferay.portal.kernel.util.PortalLifecycleUtil.register(PortalLifecycleUtil.java:74)
	at com.liferay.portal.kernel.util.PortalLifecycleUtil.register(PortalLifecycleUtil.java:58)
	at com.liferay.portal.kernel.util.BasePortalLifecycle.registerPortalLifecycle(BasePortalLifecycle.java:54)
	at com.liferay.portal.kernel.servlet.PluginContextListener.contextInitialized(PluginContextListener.java:116)
	at com.liferay.portal.kernel.servlet.SecurePluginContextListener.contextInitialized(SecurePluginContextListener.java:151)
	at org.apache.catalina.core.StandardContext.listenerStart(StandardContext.java:5016)
	at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5528)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:901)
	at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:877)
	at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:652)
	at org.apache.catalina.startup.HostConfig.deployDirectory(HostConfig.java:1263)
	at org.apache.catalina.startup.HostConfig$DeployDirectory.run(HostConfig.java:1948)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471)
	at java.util.concurrent.FutureTask.run(FutureTask.java:262)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.io.FileNotFoundException: /home/lukechen/software/liferay-portal-6.2-ce-ga6/data/document_library/20155/26717/2/1.0 (No such file or directory)
	at java.io.FileInputStream.open(Native Method)
	at java.io.FileInputStream.<init>(FileInputStream.java:146)
	at com.liferay.portlet.documentlibrary.store.FileSystemStore.getFileAsStream(FileSystemStore.java:221)
	... 77 more
07:50:27,982 INFO  [localhost-startStop-1][HookHotDeployListener:851] Hook for sync-web is available for use
Jul 11, 2016 7:50:28 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/sync-web has finished in 340,991 ms
Jul 11, 2016 7:50:28 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/calendar-portlet
Jul 11, 2016 7:50:30 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:50:30,011 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin calendar-portlet requires marketplace-portlet
07:50:30,011 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying calendar-portlet from queue
07:50:30,011 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for calendar-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/portlet.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/portlet.properties
Jul 11, 2016 7:50:30 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/service.properties
  Copying 1 file to /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/20160711074452575/WEB-INF
  Copying 1 file to /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/20160711074452575/WEB-INF/classes
  Copying 1 file to /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/20160711074452575/WEB-INF/classes
07:52:20,641 INFO  [com.liferay.portal.kernel.deploy.auto.AutoDeployScanner][BaseDeployer:2391] Modifying Servlet 2.4 /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/20160711074452575/WEB-INF/web.xml
  Copying 1 file to /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/ROOT/WEB-INF/lib
  Warning: META-INF/MANIFEST.MF modified in the future.
  Warning: META-INF/maven/net.hsf3/hsf-plugins-shared-ext-ext/pom.properties modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portal/deploy/dependencies/ext-hsf-plugins-shared-ext-ext-util-bridges.jar modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portal/deploy/dependencies/ext-hsf-plugins-shared-ext-ext-util-java.jar modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portal/deploy/dependencies/ext-hsf-plugins-shared-ext-ext-util-taglib.jar modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/dependencies/alloy/option.ftl modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/dependencies/ddm/checkbox-group.ftl modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/util/DDMImpl.class modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/util/DDMImplOrigin.class modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/util/DDMXSDImpl.class modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/util/DDMXSDImplOrigin$1.class modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/util/DDMXSDImplOrigin.class modified in the future.
  Warning: WEB-INF/ext-impl/classes/dtd/liferay-ddm-structure_6_2_0.xsd modified in the future.
  Warning: WEB-INF/ext-impl/ext-impl.jar modified in the future.
  Warning: WEB-INF/ext-service/ext-service.jar modified in the future.
  Warning: WEB-INF/ext-util-bridges/ext-util-bridges.jar modified in the future.
  Warning: WEB-INF/ext-util-java/ext-util-java.jar modified in the future.
  Warning: WEB-INF/ext-util-taglib/ext-util-taglib.jar modified in the future.
  Warning: META-INF modified in the future.
  Warning: META-INF/maven modified in the future.
  Warning: META-INF/maven/net.hsf3 modified in the future.
  Warning: WEB-INF/ext-impl modified in the future.
  Warning: WEB-INF/ext-impl/classes modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portal modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portal/deploy modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portal/deploy/dependencies modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping modified in the future.
  Warning: WEB-INF/ext-impl/classes/com/liferay/portlet/dynamicdatamapping/dependencies modified in the future.
  Warning: WEB-INF/ext-impl/classes/dtd modified in the future.
  Warning: WEB-INF/ext-lib modified in the future.
  Warning: WEB-INF/ext-lib/global modified in the future.
  Warning: WEB-INF/ext-lib/portal modified in the future.
  Warning: WEB-INF/ext-service/classes modified in the future.
  Warning: WEB-INF/ext-util-bridges modified in the future.
  Warning: WEB-INF/ext-util-java modified in the future.
  Warning: WEB-INF/ext-util-taglib modified in the future.
  Warning: WEB-INF/ext-web modified in the future.
  Warning: WEB-INF/ext-web/docroot modified in the future.
  Warning: WEB-INF/ext-web/docroot/WEB-INF/classes modified in the future.
  Warning: WEB-INF/ext-web/docroot/html modified in the future.
  Warning: WEB-INF/ext-web/docroot/html/portlet modified in the future.
  Warning: WEB-INF/sql modified in the future.
  Copying 38 files to /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-ext
  Copied 39 empty directories to 4 empty directories under /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-ext
  Copying 2 files to /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-ext
  Deleting directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/20160711074452575
07:52:20,712 INFO  [com.liferay.portal.kernel.deploy.auto.AutoDeployScanner][ExtAutoDeployListener:57] Extension environment for /home/lukechen/software/liferay-portal-6.2-ce-ga6/deploy/hsf-plugins-shared-ext-ext-1.0.0.war copied successfully. Deployment will start in a few seconds.
07:52:35,758 INFO  [localhost-startStop-1][HookHotDeployListener:709] Registering hook for calendar-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/portal.properties
07:52:37,136 INFO  [localhost-startStop-1][HookHotDeployListener:851] Hook for calendar-portlet is available for use
07:52:37,145 INFO  [localhost-startStop-1][PortletHotDeployListener:344] Registering portlets for calendar-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/2-calendar-portlet/WEB-INF/classes/portlet.properties
07:52:37,241 INFO  [localhost-startStop-1][PortletHotDeployListener:492] 1 portlet for calendar-portlet is available for use
07:52:37,242 INFO  [localhost-startStop-1][SocialHotDeployListener:94] Registering social for calendar-portlet
07:52:37,244 INFO  [localhost-startStop-1][SocialHotDeployListener:103] Social for calendar-portlet is available for use
Jul 11, 2016 7:52:37 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/calendar-portlet has finished in 129,274 ms
Jul 11, 2016 7:52:37 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-theme
Jul 11, 2016 7:52:37 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:52:37,994 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin hsf-plugins-shared-theme requires marketplace-portlet
07:52:37,994 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying hsf-plugins-shared-theme from queue
07:52:37,995 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for hsf-plugins-shared-theme
Jul 11, 2016 7:52:38 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
07:52:53,631 INFO  [localhost-startStop-1][ThemeHotDeployListener:98] Registering themes for hsf-plugins-shared-theme
07:52:54,186 INFO  [localhost-startStop-1][ThemeHotDeployListener:113] 1 theme for hsf-plugins-shared-theme is available for use
Jul 11, 2016 7:52:54 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-theme has finished in 16,803 ms
Jul 11, 2016 7:52:54 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-ext-ext-web-1.0.0
Jul 11, 2016 7:52:54 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:52:54,708 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin hsf-plugins-shared-ext-ext-web-1.0.0 requires marketplace-portlet
07:52:54,708 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying hsf-plugins-shared-ext-ext-web-1.0.0 from queue
07:52:54,708 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for hsf-plugins-shared-ext-ext-web-1.0.0
07:52:54,708 WARN  [localhost-startStop-1][PluginPackageUtil:1114] Plugin package on context hsf-plugins-shared-ext-ext-web-1.0.0 cannot be tracked because this WAR does not contain a liferay-plugin-package.xml file
Jul 11, 2016 7:52:54 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Jul 11, 2016 7:53:03 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/hsf-plugins-shared-ext-ext-web-1.0.0 has finished in 9,085 ms
Jul 11, 2016 7:53:03 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/resources-importer-web
Jul 11, 2016 7:53:03 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:53:03,918 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin resources-importer-web requires marketplace-portlet
07:53:03,918 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying resources-importer-web from queue
07:53:03,918 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for resources-importer-web
Jul 11, 2016 7:53:03 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
07:53:04,430 INFO  [localhost-startStop-1][HookHotDeployListener:709] Registering hook for resources-importer-web
07:53:04,466 INFO  [localhost-startStop-1][HookHotDeployListener:851] Hook for resources-importer-web is available for use
Jul 11, 2016 7:53:04 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/resources-importer-web has finished in 1,196 ms
Jul 11, 2016 7:53:04 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/notifications-portlet
Jul 11, 2016 7:53:06 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:53:06,002 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin notifications-portlet requires marketplace-portlet
07:53:06,003 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying notifications-portlet from queue
07:53:06,003 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for notifications-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/portlet.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/portlet.properties
Jul 11, 2016 7:53:06 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/service.properties
07:53:06,702 INFO  [localhost-startStop-1][HookHotDeployListener:709] Registering hook for notifications-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/portal.properties
07:53:06,945 INFO  [localhost-startStop-1][HookHotDeployListener:851] Hook for notifications-portlet is available for use
07:53:06,946 INFO  [localhost-startStop-1][PortletHotDeployListener:344] Registering portlets for notifications-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/4-notifications-portlet/WEB-INF/classes/portlet.properties
07:53:07,000 INFO  [localhost-startStop-1][PortletHotDeployListener:497] 2 portlets for notifications-portlet are available for use
Jul 11, 2016 7:53:07 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/notifications-portlet has finished in 2,501 ms
Jul 11, 2016 7:53:07 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/opensocial-portlet
Jul 11, 2016 7:53:09 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:53:09,968 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin opensocial-portlet requires marketplace-portlet
07:53:09,968 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying opensocial-portlet from queue
07:53:09,968 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for opensocial-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/portlet.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/portlet.properties
Jul 11, 2016 7:53:10 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/service.properties
07:53:13,918 INFO  [localhost-startStop-1][HookHotDeployListener:709] Registering hook for opensocial-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/portal.properties
07:53:13,975 INFO  [localhost-startStop-1][HookHotDeployListener:851] Hook for opensocial-portlet is available for use
07:53:14,002 INFO  [localhost-startStop-1][PortletHotDeployListener:344] Registering portlets for opensocial-portlet
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/5-opensocial-portlet/WEB-INF/classes/portlet.properties
07:53:14,104 INFO  [localhost-startStop-1][PortletHotDeployListener:497] 4 portlets for opensocial-portlet are available for use
Jul 11, 2016 7:53:14 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deployment of web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/opensocial-portlet has finished in 7,350 ms
Jul 11, 2016 7:53:14 AM org.apache.catalina.startup.HostConfig deployDirectory
INFO: Deploying web application directory /home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/webapps/kaleo-web
Jul 11, 2016 7:53:14 AM org.apache.shindig.config.JsonContainerConfig loadContainers
INFO: Loading resources from: containers/default/container.js
Jul 11, 2016 7:53:14 AM org.apache.shindig.config.JsonContainerConfig loadResources
INFO: Reading container config: containers/default/container.js
Jul 11, 2016 7:53:15 AM org.apache.shindig.gadgets.features.FeatureRegistry register
INFO: Loading resources from: res:features-extras/features.txt
Jul 11, 2016 7:53:15 AM org.apache.shindig.common.xml.XmlUtil <clinit>
INFO: Reusing document builders
Jul 11, 2016 7:53:15 AM org.apache.shindig.gadgets.features.FeatureRegistry register
INFO: Loading resources from: res:features/features.txt
Jul 11, 2016 7:53:17 AM org.apache.catalina.startup.TldConfig execute
INFO: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
07:53:17,059 INFO  [localhost-startStop-1][HotDeployEvent:145] Plugin kaleo-web requires marketplace-portlet
07:53:17,059 INFO  [localhost-startStop-1][HotDeployImpl:217] Deploying kaleo-web from queue
07:53:17,059 INFO  [localhost-startStop-1][PluginPackageUtil:1016] Reading plugin package for kaleo-web
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/6-kaleo-web/WEB-INF/classes/portlet.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/6-kaleo-web/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/6-kaleo-web/WEB-INF/classes/portlet.properties
Jul 11, 2016 7:53:17 AM org.apache.catalina.core.ApplicationContext log
INFO: Initializing Spring root WebApplicationContext
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/6-kaleo-web/WEB-INF/classes/service.properties
Loading file:/home/lukechen/software/liferay-portal-6.2-ce-ga6/tomcat-7.0.62/temp/6-kaleo-web/WEB-INF/classes/service.properties
Jul 11, 2016 7:53:21 AM org.apache.shindig.gadgets.http.BasicHttpFetcher fetch
INFO: Timeout for http://www.google.com/ig/lib/libanalytics.js Exception: java.net.SocketTimeoutException - Read timed out - 5011ms
Jul 11, 2016 7:53:21 AM org.apache.shindig.gadgets.features.FeatureResourceLoader$UriResource getContent
WARNING: Unable to retrieve remote library from http://www.google.com/ig/lib/libanalytics.js
Jul 11, 2016 7:53:26 AM org.apache.shindig.gadgets.http.BasicHttpFetcher fetch
INFO: Timeout for http://www.google.com/ig/lib/libga.js Exception: java.net.SocketTimeoutException - Read timed out - 5006ms
Jul 11, 2016 7:53:26 AM org.apache.shindig.gadgets.features.FeatureResourceLoader$UriResource getContent
WARNING: Unable to retrieve remote library from http://www.google.com/ig/lib/libga.js
Jul 11, 2016 7:53:26 AM org.apache.shindig.gadgets.servlet.CajaContentRewriter <init>
INFO: Cajoled cache createdorg.apache.shindig.common.cache.ehcache.EhConfiguredCache@1655e1af
Jul 11, 2016 7:53:26 AM org.apache.shindig.gadgets.servlet.CajaContentRewriter <init>
INFO: Cajoled cache createdorg.apache.shindig.common.cache.ehcache.EhConfiguredCache@1655e1af
Jul 11, 2016 7:53:26 AM org.apache.shindig.gadgets.oauth.OAuthModule$OAuthCrypterProvider <init>
INFO: Using random key for OAuth client-side state encryption
