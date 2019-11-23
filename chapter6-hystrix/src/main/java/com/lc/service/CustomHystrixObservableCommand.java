package com.lc.service;

import com.lc.dto.Instance;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

/**
 * 继承HystrixObservableCommand
 *
 * @description:
 * @author lingchen
 * @date 2019/11/23
 */
@Slf4j
public class CustomHystrixObservableCommand extends HystrixObservableCommand {

    private RestTemplate restTemplate;

    private String serviceId;

    protected CustomHystrixObservableCommand(RestTemplate restTemplate, String serviceId) {
        super(HystrixCommandGroupKey.Factory.asKey("CustomServiceGroup"));
        this.restTemplate = restTemplate;
        this.serviceId = serviceId;
    }


    @Override
    protected Observable construct() {
        return Observable.create(
                subscriber -> {
                    if(!subscriber.isUnsubscribed()) {
                        subscriber.onNext(restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}",
                                Instance.class, serviceId).getBody());
                        subscriber.onCompleted();
                    }
                }
        );
    }

    protected Observable<Instance> resumeWithFallback() {
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

}
