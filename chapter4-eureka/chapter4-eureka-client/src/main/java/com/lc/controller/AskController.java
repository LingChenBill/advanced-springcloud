package com.lc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 向eureka client server请求sayHello的请求
 *
 * @author lingchen.
 */
@RestController
public class AskController {

    @Value("${spring.application.name}")
    private String name;

    @Autowired
    public RestTemplate restTemplate;

    /**
     * 请求sayHello的请求
     *
     * @return
     */
    @RequestMapping(value = "/ask")
    public String ask() {
        String askHelloFromService = restTemplate.getForEntity("http://EUREKA-CLIENT-SERVER/hello/{name}",
                String.class, name).getBody();

        return askHelloFromService;
    }

    /**
     * 注入一个可以进行负载均衡的RestTemplate用于服务间调用。
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
