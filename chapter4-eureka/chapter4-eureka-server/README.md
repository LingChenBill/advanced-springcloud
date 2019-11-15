1. 搭建Eureka服务注册中心  
依赖:  
spring-cloud-starter-netflix-eureka-server  

2. 启动类注解  
@EnableEurekaServer  

3. 一台主机可以启动多个属于同一服务的服务实例  
instance-id: ${spring.application.name}:${vcap.application.instance-id:${spring.application.instance-id:${random.value}}}  

4. Eureka Server与Eureka Client之间的联系是通过心跳（HeartBeat）的方式来实现的。  
即Eureka Client定时向Eureka Server汇报本服务实例当前的状态，维护本服务实例在注册表中租赁的有效性。  

5. Eureka Server是一个开箱即用的服务注册中心  
功能:   
   服务注册  
   接受服务心跳  
   服务剔除  
   服务下线  
   集群同步  
   获取注册表中服务实例信息  

6. Eureka设计了"自我保护机制"  
在Eureka Server中，如果出现大量的服务实例过期被剔除的现象，那么该Server节点将进入自我保护模式，  
保护注册表中的信息不再被剔除。在通信稳定后再退出该模式。  
"自我保护机制"的设计大大提高了Eureka的可用性。  

