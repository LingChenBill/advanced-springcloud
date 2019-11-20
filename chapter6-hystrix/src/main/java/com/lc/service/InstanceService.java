package com.lc.service;

import com.lc.dto.Instance;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

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
}
