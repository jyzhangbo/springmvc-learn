## springmvc contact

#### 在spingboot中使用jsp
1. 在pom.xm中添加依赖

			<dependency>
	            <groupId>org.springframework.boot</groupId>
	            <artifactId>spring-boot-starter-tomcat</artifactId>
	            <scope>provided</scope>
	        </dependency>
			 <dependency>
	            <groupId>org.apache.tomcat.embed</groupId>
	            <artifactId>tomcat-embed-jasper</artifactId>
	            <scope>provided</scope>
	        </dependency>
	        <dependency>
	            <groupId>javax.servlet</groupId>
	            <artifactId>jstl</artifactId>
	        </dependency>

2. jsp文件处理

			<!-- 打包时将jsp文件拷贝到META-INF目录下-->  
	            <resource>  
	                <!-- 指定resources插件处理哪个目录下的资源文件 -->  
	                <directory>src/main/webapp</directory>  
	                <!--注意此次必须要放在此目录下才能被访问到-->  
	                <targetPath>META-INF/resources</targetPath>  
	                <includes>  
	                    <include>**/**</include>  
	                </includes>  
	            </resource>  
	            
3. 打包版本，需要使用1.4.2

				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<version>1.4.2.RELEASE</version>	            
	            
> 1.其中2和3，可以直接改成war包的形式也能解决jsp在打包后不能访问的问题
 
4. 修改配置文件，添加jsp前缀和后缀

	sping:
	    mvc:
	       view: 
	          prefix: /WEB-INF/pages/
	          suffix: .jsp
	           
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
