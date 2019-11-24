package com.lc.controller;

import com.lc.config.RibbonConfig;
import com.lc.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ribbon order服务调用接口。
 *
 * @description:
 * @author lingchen
 * @date 2019/11/24
 */
@RestController
@RibbonClient(value = "ribbon-client", configuration = RibbonConfig.class)
@RequestMapping("/ribbon-client")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 获取order.
     *
     * @return
     */
    @GetMapping("/order")
    public String getOrderDetail() {
        Order order = this.restTemplate.getForObject("http://ribbon-client/order", Order.class);

        return String.format("The order id is %s, The content is %s!", order.getId(), order.getContent());
    }
}
