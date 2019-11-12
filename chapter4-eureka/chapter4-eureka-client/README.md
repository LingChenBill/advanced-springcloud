1. Eureka client服务调用者  
相关依赖:  
spring-cloud-starter-netflix-eureka-client  
spring-boot-starter-web  

2. 从服务提供者中请求sayHello服务  
restTemplate.getForEntity("http://EUREKA-CLIENT-SERVER/hello/{name}",
                String.class, name).getBody();  

注意: EUREKA-CLIENT-SERVER是服务提供者中对应的instance-id中的值。

3. 通过RestTemplate进行负载均衡  
@LoadBalanced  

4. 服务调用接口  
http://localhost:8765/ask  
                