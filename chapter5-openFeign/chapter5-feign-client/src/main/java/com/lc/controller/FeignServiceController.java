package com.lc.controller;

import com.lc.api.FeignServiceClient;
import com.lc.dto.Instance;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/feign-client")
public class FeignServiceController {

    @Autowired
    public FeignServiceClient feignServiceClient;

    /**
     * 获取网络实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId) {
        return feignServiceClient.getInstanceByServiceId(serviceId);
    }

}
