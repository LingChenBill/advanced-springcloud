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

5. 在Eureka Client中注入DiscoveryClient,并从Eureka Server获取服务实例的信息  
@Autowired  
private DiscoveryClient discoveryClient;  

访问path:  
http://localhost:8765/service-instances/eureka-client  

6. DiscoveryClient  
DiscoveryClient是Spring Cloud中用来进行服务发现的顶级接口。  
功能:  
  注册服务实例到Eureka Server中  
  发送心跳更新与Eureka Server的租约  
  在服务关闭时从Eureka Server中取消租约，服务下线  
  查询在Eureka Server中注册的服务实例列表  

EurekaDiscoveryClient继承了DiscoveryClient接口  
LookupService <- EurekaClient <- DiscoveryClient  

DiscoveryClientr的构造函数:  
  相关配置的赋值，如ApplicationInfoManager、EurekaClientConfig等
  备份注册中心的初始化，默认没有实现  
  摘取Eureka Server注册表中信息  
  注册前的预处理  
  向Eureka Server注册自身  
  初始化心跳定时任务，缓存刷新和按需注册等定时任务  

7. Eureka中的事件模式是属于观察者模式  

8. 服务注册  
Eureka Client会将自身服务实例元数据（封装在instanceInfo中）发送到Eureka Server中请求服务注册，  
当返回204状态码时，说明服务注册成功。  



