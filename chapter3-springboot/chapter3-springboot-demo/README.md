1. spring boot demo  
http://localhost:8008/test  

2. spring info path:  
http://localhost:8008/actuator/info

3. spring boot's config file priority:  
当前目录的"/config"的子目录  
当前目录  
classpath中的"/config"包  
classpath  

另外，应用jar文件之外的属性文件，可通过:  
java -jar xxx.jar --spring.config.location=/opt/config/application.yml  

4. 自动载入外部属性到Bean  
@ConfigurationProperties(prefix = "sms")  
@Configuration  

application.yml中设置属性值。  