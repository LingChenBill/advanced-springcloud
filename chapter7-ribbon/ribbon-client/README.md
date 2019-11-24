1. Ribbon Client
jar启动:  
java -jar ribbon-client-1.0-SNAPSHOT.jar  

访问:  
http://localhost:8083/ribbon-client/order  

2. 设置RibbonConfig--Ribbon配置类  
通过配置类创建组件实例来覆盖Ribbon提供的默认组件实例  
重载了IRule实例，设置负载均衡规则，随机规则。  

3. application.yml配置  
将Eureka关闭，则Ribbon无法从Eureka中获取服务端列表信息。  
listOfServers可以设置服务端列表。  
使用者往往在项目开发和测试阶段使用该字段。  

4. controller层中指定Ribbon client  
configuration 指定Ribbon配置类  

@RibbonClient(value = "ribbon-client", configuration = RibbonConfig.class)  

@LoadBalanced  
@Bean  
public RestTemplate restTemplate()  
