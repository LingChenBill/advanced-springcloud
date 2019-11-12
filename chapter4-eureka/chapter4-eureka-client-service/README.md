1. Eureka服务提供者  
依赖:  
spring-cloud-starter-netflix-eureka-client  

2. application.yml中添加Eureka Server的地址:  
defaultZone: http://localhost:8761/eureka/  

3. 提供服务接口:  
http://localhost:8760/hello/client-service