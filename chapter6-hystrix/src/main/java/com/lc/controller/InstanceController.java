package com.lc.controller;

import com.lc.dto.Instance;
import com.lc.service.CustomCollapseCommand;
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

    /**
     * 异步调用。
     *
     * @param serviceId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/async/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdAsync(@PathVariable("serviceId") String serviceId) throws ExecutionException, InterruptedException {
        log.info("Get Instance by ServiceId {}", serviceId);
        Instance instance = instanceService.getInstanceByServiceIdAsync(serviceId).get();
        return instance;
    }

    /**
     * 异步回调执行命令。
     *
     * @param serviceId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/observable/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdObservable(@PathVariable("serviceId") String serviceId) throws ExecutionException, InterruptedException {
        log.info("Get Instance by ServiceId {}", serviceId);
        Instance instance = instanceService.getInstanceByServiceIdObservable(serviceId).toBlocking().single();
        return instance;
    }

    /**
     * 自己继承HystrixCommand,获取实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/custom/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdCustom(@PathVariable("serviceId") String serviceId) {
        log.info("Get Instance by ServiceId {}", serviceId);
        Instance instance = instanceService.getInstanceByServiceIdCustom(serviceId);
        return instance;
    }

    /**
     * 继承HystrixObservableCommand来获取实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/customObservable/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdCustomObservable(@PathVariable("serviceId") String serviceId) {

        log.info("Get Instance by ServiceId {}", serviceId);

        return instanceService.getInstanceByServiceIdCustomObservable(serviceId);

    }

    /**
     * 继承HystrixCollapser，请求合并。
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/customBatch/test", method = RequestMethod.GET)
    public Instance getInstanceByCustomBatch() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        CustomCollapseCommand c1 = new CustomCollapseCommand("test1");
        CustomCollapseCommand c2 = new CustomCollapseCommand("test2");
        CustomCollapseCommand c3 = new CustomCollapseCommand("test3");
        CustomCollapseCommand c4 = new CustomCollapseCommand("test4");

        Future<Instance> future1 = c1.queue();
        Future<Instance> future2 = c2.queue();
        Future<Instance> future3 = c3.queue();
        future1.get();
        future2.get();
        future3.get();

        TimeUnit.MILLISECONDS.sleep(1000);

        Future<Instance> future4 = c4.queue();
        Instance instance = future4.get();
        context.close();

        return instance;
    }

}
