package com.lc.controller;

import com.lc.dto.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ribbon order服务提供接口。
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/24
 */
@RestController
public class OrderController {

    /**
     * 获取order.
     *
     * @return
     */
    @GetMapping("/order")
    public Order getOrderDetail() {
        Order order = new Order();
        order.setId(1L);
        order.setContent("from server");
        return order;
    }
}
