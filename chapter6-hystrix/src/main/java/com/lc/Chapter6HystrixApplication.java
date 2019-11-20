package com.lc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Hystrix app start class.
 * { @EnableCircuitBreaker: 开启Hystrix. }
 *
 * @author lingchen.
 */
@SpringBootApplication
@EnableCircuitBreaker
public class Chapter6HystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter6HystrixApplication.class, args);
    }

    /**
     * 注入可以进行负载均衡的RestTemplate。
     *
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
