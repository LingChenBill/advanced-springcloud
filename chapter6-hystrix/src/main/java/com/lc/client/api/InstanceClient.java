package com.lc.client.api;

import com.lc.client.fallback.InstanceClientFallBack;
import com.lc.dto.Instance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhuyangze
 * @description:
 * @date 2019/11/21
 */
@FeignClient(value = "feign-service", fallback = InstanceClientFallBack.class)
public interface InstanceClient {

    @RequestMapping(value = "feign-service/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId);
}
