package com.lc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gateway user controller.
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/25
 */
@RestController
public class GatewayController {

    @GetMapping("/user/")
    public String userTest() {
        return "Gateway user OK...";
    }

}
