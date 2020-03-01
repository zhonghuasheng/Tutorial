# SpringBoot中集成SSL

> 测试时使用的是1.5.x的版本，2.x的版本会有一些类的写法不一样

## 目录
* [生成SSL](#生成SSL)
* [SpringBoot中配置SSL](#SpringBoot中配置SSL)
* [参考文章](#参考文章)

### 生成SSL
```shell
keytool -genkey -alias test -keyalg RSA -keystore /home/user/test.keystore
```

### SpringBoot中配置SSL
```properties
server.port=8443
server.ssl.key-store=classpath:ssl/test.keystore #classpath是springboot中的resources所在的路径
server.ssl.key-store-password=testpwd
server.ssl.keyStoreType=JKS
server.ssl.keyAlias=test
```

启动项目，验证https

## 配置http自动跳转https
参考网上的写法会有个问题，http的post请求会被跳转为https的get请求，报错信息是Get方法不被支持。如下是我使用的解决方案：
1. 配置接收http请求和开通8080端口
   ```java
    @SpringBootApplication
    public class Application {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

        @Bean
        public EmbeddedServletContainerFactory servletContainer() {
            TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
            tomcat.addAdditionalTomcatConnectors(httpConnector());

            return tomcat;
        }


        @Bean
        public Connector httpConnector() {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(8080);
            connector.setSecure(false);

            return connector;
        }
    }
   ```
2. 添加一个HttpsFilter用来将Http请求转换为Https请求
    ```java
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    import javax.servlet.*;
    import javax.servlet.annotation.WebFilter;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.net.URL;

    @Component
    @WebFilter(urlPatterns = "/*", filterName = "HttpsFilter")
    public class HttpsFilter implements Filter {

        private static final String HTTPS = "https";

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            if (request.getScheme().equals(HTTPS)) {
                chain.doFilter(request, response);
            } else {
                URL newUrl = null;
                HttpServletRequest httpRequest = (HttpServletRequest)request;
                HttpServletResponse httpResponse = (HttpServletResponse)response;
                String queryString = httpRequest.getQueryString()==null ? "":"?"+httpRequest.getQueryString();
                httpResponse.setStatus(307);
                String requestUrl = httpRequest.getRequestURL().toString();
                URL reqUrl = new URL(requestUrl+queryString);
                newUrl = new URL(HTTPS, reqUrl.getHost(), 8443, reqUrl.getFile());
                // 进行重定向
                httpResponse.setHeader("Location", newUrl.toString());
                httpResponse.setHeader("Connection", "close");
                // 允许所有跨域请求
                httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            }
        }

        @Override
        public void destroy() {
        }
    }
    ```

## 参考文章
* [Springboot配置使用ssl，使用https](https://www.cnblogs.com/duanxz/p/9155509.html)
* [Http 重定向到Https,post请求被强制转换为get请求的解决方案](https://blog.csdn.net/u011242657/article/details/80114074)