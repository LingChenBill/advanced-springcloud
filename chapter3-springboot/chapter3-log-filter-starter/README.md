1. 自制一个日志过滤器Starter  
LogFilter -> LogFilterRegistrationBean -> LogFilterAutoConfiguration -> EnableLogFilter  

2. 工程引入自制日志过滤器starter.  
chapter3-springboot/chapter3-springboot-demo的pom引入依赖: chapter3-log-filter-starter。  

3. chapter3-springboot-demo启动类中开启自制日志过滤器。  
启动类(Chapter3SpringbootDemoApplication)中添加注解: @EnableLogFilter  
