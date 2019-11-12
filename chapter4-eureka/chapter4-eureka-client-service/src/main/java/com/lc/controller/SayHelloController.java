package com.lc.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供者: 提供服务接口
 *
 * @author lingchen.
 *
 */
@RestController
public class SayHelloController {

    @RequestMapping(value = "/hello/{name}")
    public  String sayHello(@PathVariable("name") String name) {
        return "Hello, ".concat(name).concat(" , eureka client server!");
    }
}
