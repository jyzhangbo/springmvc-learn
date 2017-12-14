## springmvc contact

##### MyDispatcherServlet自己的mvc框架
1. annotation自己的注解
2. MyDispatcherServlet自己定义的servlet

##### springboot的四种监听事件
1. ApplicationStartedEvent
2. ApplicationEnvironmentPreparedEvent
3. ApplicationPreparedEvent
4. ApplicationFailedEvent

##### https配置
1. 生成自己的.keystore文件
2. 在配置文件里面配置：

	server:
	    port: 8980
	    ssl: 
	        keyAlias: tomcat
	        keyPassword: 123456
	        keyStoryType: RSA
	        keyStore: classpath:.keystore
	        
3. 在TomcatConfig里面配置http重定向到https

##### 使用aop来打印日志
1. GlobalExceptionLogHandler
2. GlobalRequestLogHandler

##### @ControllerAdvice处理全局异常
1. GlobalHandlerAdvice
