1. 断路器  
依赖:  
spring-cloud-starter-netflix-eureka-client  
spring-cloud-starter-netflix-hystrix  

入口类添加注解:  
@EnableCircuitBreaker  

服务接口添加注解:  
@HystrixCommand(fallbackMethod = "instanceInfoGetFail")  
服务断路时，执行失败回滚方法:instanceInfoGetFail


2. 访问path:  
先启动jar:  
java -jar chapter4-eureka-server-0.0.1-SNAPSHOT.jar  
java -jar chapter5-feign-service-1.0-SNAPSHOT.jar  
java -jar chapter6-hystrix-1.0-SNAPSHOT.jar  

访问链接:  
http://localhost:8876/instance/rest-template/my-app  
