package com.lc.service;

import com.lc.dto.Instance;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * 继承HystrixCommand.
 *
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/23
 */
@Slf4j
public class CustomHystrixCommand extends HystrixCommand<Instance> {

    private RestTemplate restTemplate;

    private String serviceId;

    /**
     * 构造函数，传递实现相关参数。
     *
     * @param restTemplate
     * @param serviceId
     */
    protected CustomHystrixCommand(RestTemplate restTemplate, String serviceId) {
        // 在构造HystrixCommand时至少要为它指定一个HystrixCommandGroupKey。
        //super(HystrixCommandGroupKey.Factory.asKey("CustomServiceGroup"));

        // 通过HystrixCommand#Setter的方式在构造函数中对CustomHystrixCommand的默认配置进行修改。
        // 设置信号量资源隔离策略。
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomServiceGroup"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(20)
                        .withCircuitBreakerErrorThresholdPercentage(80)
                )
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(20))
        );

        this.restTemplate = restTemplate;
        this.serviceId = serviceId;
    }

    /**
     * 被保护的包装函数。
     * { 需要进行包装的远程调用函数，是必须要实现的抽象方法。}
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Instance run() throws Exception {

        // 在使用CustomHystrixCommand时，会发现无法在#run方法中传递参数，所以需要在构造器中携带#run方法的相关参数。
        Instance instance = restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}",
                Instance.class, serviceId).getBody();
        return instance;
    }

    /**
     * 失败回滚函数。
     *
     * @return
     */
    @Override
    protected Instance getFallback() {
        log.info("Can not get Instance by serviceId {}", serviceId);
        return new Instance("error", "error", 0);
    }
}
