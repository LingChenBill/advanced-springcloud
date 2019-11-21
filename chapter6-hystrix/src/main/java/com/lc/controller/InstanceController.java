package com.lc.controller;

import com.lc.dto.Instance;
import com.lc.service.InstanceService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 实例控制器类。
 *
 * @description:
 * @author zhuyangze
 * @date 2019/11/20
 */
@RestController
@RequestMapping("/instance")
@Slf4j
public class InstanceController {

    @Autowired
    private InstanceService instanceService;

    /**
     * 获取实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/rest-template/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdWithRestTemplate(@PathVariable("serviceId") String serviceId) {
        log.info("Get Instance by serviceId {}", serviceId);
        return instanceService.getInstanceByServiceIdWithRestTemplate(serviceId);
    }

    /**
     * 通过Feign接口获取实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/feign/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdWithFeign(@PathVariable("serviceId") String serviceId) {
        log.info("Get Instance by serviceId {}", serviceId);
        return instanceService.getInstanceByServiceIdWithFeign(serviceId);
    }

    /**
     * Hystrix请求合并操作。
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/batch/test1", method = RequestMethod.GET)
    public Instance getInstanceBatch() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Future<Instance> futureOne = instanceService.getInstanceByServiceId("test1");
        Future<Instance> futureTwo = instanceService.getInstanceByServiceId("test2");
        Future<Instance> futureThree = instanceService.getInstanceByServiceId("test3");

        futureOne.get();
        futureTwo.get();
        futureThree.get();

        TimeUnit.MILLISECONDS.sleep(1000);
        Future<Instance> futureFour = instanceService.getInstanceByServiceId("test4");
        Instance instance = futureFour.get();
        context.close();
        return instance;
    }


}
