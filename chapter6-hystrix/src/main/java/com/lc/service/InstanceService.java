package com.lc.service;

import com.lc.client.api.InstanceClient;
import com.lc.dto.Instance;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Instance service.
 *
 * @description:
 * @author zhuyangze
 * @date 2019/11/20
 */
@Service
@Slf4j
public class InstanceService {

    // 服务ID.
    private static String DEFAULT_SERVICE_ID = "application";
    // IP.
    private static String DEFAULT_HOST = "localhost";
    // 端口.
    private static int DEFAULT_PORT = 8080;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InstanceClient instanceClient;

    @HystrixCommand(fallbackMethod = "instanceInfoGetFail")
    public Instance getInstanceByServiceIdWithRestTemplate(String serviceId) {
        Instance instance = restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}",
                Instance.class, serviceId).getBody();

        return instance;
    }

    /**
     * 指定熔断回滚的方法。
     *
     * @param serviceId
     * @return
     */
    private Instance instanceInfoGetFail(String serviceId) {
        log.info("Can not get Instance by serviceId {}", serviceId);
        return new Instance("Error", "error", 0);
    }

    /**
     * 通过FeignClient接口类获取Instance.
     *
     * @param serviceId
     * @return
     */
    public Instance getInstanceByServiceIdWithFeign(String serviceId) {
        return instanceClient.getInstanceByServiceId(serviceId);
    }

    @HystrixCollapser(batchMethod = "getInstanceByServiceIds", collapserProperties = {@HystrixProperty(name = HystrixPropertiesManager.TIMER_DELAY_IN_MILLISECONDS, value = "100")})
    public Future<Instance> getInstanceByServiceId(String serviceId) {
        return null;
    }

    @HystrixCommand
    public List<Instance> getInstanceByServiceIds(List<String> serviceIds) {
        List<Instance> instances = new ArrayList<>();
        for (String s : serviceIds) {
            instances.add(new Instance(s, DEFAULT_HOST, DEFAULT_PORT));
        }

        return instances;
    }

}
