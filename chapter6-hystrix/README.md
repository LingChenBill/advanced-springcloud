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

3. 使用OpenFeign自带的Hystrix获取实例  
依赖:  
spring-cloud-starter-openfeign  
添加注解:  
@EnableFeignClients  

启动jar:  
java -jar chapter4-eureka-server-0.0.1-SNAPSHOT.jar  
java -jar chapter5-feign-service-1.0-SNAPSHOT.jar  
java -jar chapter6-hystrix-1.0-SNAPSHOT.jar  

访问:  
http://localhost:8876/instance/feign/my-application  

4. 服务雪崩  
服务雪崩效应是一种因服务提供者的不可用导致服务调用者的不可用，并将不可用逐渐放大的过程。  

5. 断路器  
断路器将远程方法调用包装到一个断路器对象中，用于监控方法调用过程的失败。  
一旦该方法调用发生的失败次数在一段时间内达到一定的阀值，那么这个断路器将会跳闸,  
在接下来的时间里再次调用该方法将会被断路器直接返回异常，而不再发生该方法的真实调用。  
避免大量的同步等待，减少服务调用者的资源消耗。  

合理的断路器应该具备一定的开关转化逻辑，它需要一个机制来控制它的重新闭合。  

6. 资源隔离  
在Hystrix中，也采用了舱壁模式，将系统中服务提供者隔离起来，一个服务提供者延迟升高或者失败，  
并不会导致整个系统的失败，同时也能够控制调用这些服务的并发度。  

线程与线程池--服务隔离  
信号量--限制单个服务提供者的并发量  

7. HystrixCommand配置  
一般来说，对于HystrixCommand的配置，仅需要关注fallbackMethod方法。  

8. HystrixCollapser请求合并  
http://localhost:8876/instance/batch/test1  

9. 资源隔离  
在Hystrix中，主要有两种策略进行资源隔离。  
信号量隔离策略  
线程隔离策略  

10. 异步与异步回调执行命令  
注解:  
@HystrixCommand(fallbackMethod = "instanceInfoGetFailAsync")  
指定回滚方法: instanceInfoGetFailAsync  

http://localhost:8876/instance/async/my-app  

http://localhost:8876/instance/observable/my-app  

11. 继承HystrixCommand  
extends HystrixCommand<Instance>   
访问:  
http://localhost:8876/instance/custom/my-app  

通过HystrixCommand#Setter的方式在构造函数中对CustomHystrixCommand的默认配置进行修改。  

12. 继承HystrixObservableCommand来获取实例  

extends HystrixObservableCommand  
访问:  
http://localhost:8876/instance/customObservable/my-app  

13. 继承HystrixCollapser  
extends HystrixCollapser<List<Instance>, Instance, String>  

访问:  
http://localhost:8876/instance/customBatch/test  




