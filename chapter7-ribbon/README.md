1. Spring Cloud Netflix Ribbon  
Ribbon是管理HTTP和TCP服务客户端的负载均衡器。  
Ribbon作为Spring Cloud的负载均衡机制的实现，可以与OpenFeign和RestTemplate进行无缝对接，让二者具有负载均衡的能力。  

2. 负载均衡  
系统的负载均衡分为软件负载均衡和硬件负载均衡。  
软负载均衡分为:  
   基于DNS的负载均衡和基于IP的负载均衡。  

最为主流的负载均衡技术还是反向代理负载均衡。  
代理根据一定规则，将HTTP请求转发到服务器集群的单一服务器上。  

3. Ribbon使用的是客户端负载均衡。  

