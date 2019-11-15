package com.lc.api;

import com.lc.dto.Instance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * FeignClient指定调用的远程服务接口
 *
 * @author lingchen.
 *
 */
@FeignClient("feign-service")
@RequestMapping("/feign-service")
public interface FeignServiceClient {

    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId);

}
