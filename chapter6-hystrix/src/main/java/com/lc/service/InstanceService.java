package com.lc.service;

import com.lc.client.api.InstanceClient;
import com.lc.dto.Instance;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.functions.Action1;

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

    /**
     * 异步以及异步回调执行命令。
     *
     * @param serviceId
     * @return
     */
    @HystrixCommand(fallbackMethod = "instanceInfoGetFailAsync")
    public Future<Instance> getInstanceByServiceIdAsync(String serviceId) {
        log.info("Can not get Instance by serviceId {}", serviceId);

        return new AsyncResult<Instance>() {
            @Override
            public Instance invoke() {
                return restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}", Instance.class, serviceId).getBody();
            }
        };
    }

    /**
     * 异步回调的失败回滚方法。
     *
     * @param serviceId
     * @return
     */
    @HystrixCommand
    public Future<Instance> instanceInfoGetFailAsync(String serviceId) {
        log.info("Can not get Instance by serviceId {}", serviceId);

        return new AsyncResult<Instance>() {
            @Override
            public Instance invoke() {
                return new Instance("error", "error", 0);
            }
        };
    }

    /**
     * 异步回调执行命令。
     *
     * @param serviceId
     * @return
     */
    @HystrixCommand(fallbackMethod = "instanceInfoGetFailObservable",
                    observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<Instance> getInstanceByServiceIdObservable(String serviceId) {
        return Observable.create(
                subscriber -> {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}",
                                Instance.class, serviceId).getBody());
                        subscriber.onCompleted();
                    }
                }
        );
    }

    /**
     * 异步回调执行命令的失败回滚方法。
     *
     * @param serviceId
     * @return
     */
    public Observable<Instance> instanceInfoGetFailObservable(String serviceId) {
        log.info("Can not get Instance by serviceId {}", serviceId);
        return Observable.create(
                subscriber -> {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(new Instance("error", "error", 0));
                        subscriber.onCompleted();
                    }
                }
        );
    }

    /**
     * 通过继承HystrixCommand的方式来获取Instance实例。
     *
     * @param serviceId
     * @return
     */
    public Instance getInstanceByServiceIdCustom(String serviceId) {
        CustomHystrixCommand customHystrixCommand = new CustomHystrixCommand(restTemplate, serviceId);
        Instance instance = customHystrixCommand.execute();
        return instance;
    }

    /**
     * 通过继承HystrixObservableCommand方式来获取Instance实例。
     *
     * @param serviceId
     * @return
     */
    public Instance getInstanceByServiceIdCustomObservable(String serviceId) {
        // 每次创建都要创建一个新的命令。
        CustomHystrixObservableCommand observableCommand = new CustomHystrixObservableCommand(restTemplate, serviceId);
        Observable<Instance> observe = observableCommand.observe();
        Instance instance = observe.toBlocking().single();

        CustomHystrixObservableCommand observableCommand2 = new CustomHystrixObservableCommand(restTemplate, serviceId);
        Observable toObservable = observableCommand2.toObservable();

        // 通过订阅的方式定义回调函数获取执行结果。
        toObservable.subscribe(new Action1<Instance>() {
            @Override
            public void call(Instance instance1) {
                log.info("Instance observable: {}", instance1);
            }
        });

        return instance;
    }

}
