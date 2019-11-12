1. 搭建Eureka服务注册中心  
依赖:  
spring-cloud-starter-netflix-eureka-server  

2. 启动类注解  
@EnableEurekaServer  

3. 一台主机可以启动多个属于同一服务的服务实例  
instance-id: ${spring.application.name}:${vcap.application.instance-id:${spring.application.instance-id:${random.value}}}  

4. Eureka Server与Eureka Client之间的联系是通过心跳（HeartBeat）的方式来实现的。  
即Eureka Client定时向Eureka Server汇报本服务实例当前的状态，维护本服务实例在注册表中租赁的有效性。  

