package com.lc.controller;

import com.lc.dto.Instance;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网络API接口。
 *
 * @author lingchen.
 *
 */
@RestController
@RequestMapping("/feign-service")
public class FeignServiceController {

    private String DEFAULT_HOST = "localhost";
    private int DEFAULT_PORT = 8080;

    /**
     * 获取网络实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId){
        return new Instance(serviceId, DEFAULT_HOST, DEFAULT_PORT);
    }

}
