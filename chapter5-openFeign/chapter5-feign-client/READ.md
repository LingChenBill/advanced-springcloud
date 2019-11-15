1. OpenFeign服务消费类

2. OpenFeign相关依赖:  
   spring-cloud-starter-openfeign  

2. FeignClient指定调用的远程服务接口  
   @FeignClient("feign-service")
   注意该注解中不要加"/"  
   
   指定接口的path:  
   @RequestMapping("/feign-service")  
   @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)  

4. controller层中通过接口调用:  
   feignServiceClient.getInstanceByServiceId(serviceId);  

3. 启动类中加入注解: @EnableFeignClients  

4. 访问path: 
   http://localhost:8770/feign-client/instance/3  