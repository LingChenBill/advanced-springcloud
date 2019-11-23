package com.lc.service;

import com.lc.dto.Instance;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 继承HystrixCollapser，实现请求合并。
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/23
 */
@Slf4j
public class CustomCollapseCommand extends HystrixCollapser<List<Instance>, Instance, String> {

    public String serviceId;

    public CustomCollapseCommand(String serviceId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("CustomServiceGroup")));

        this.serviceId = serviceId;
    }

    @Override
    public String getRequestArgument() {
        return serviceId;
    }

    @Override
    protected HystrixCommand<List<Instance>> createCommand(Collection<CollapsedRequest<Instance, String>> collapsedRequests) {

        List<String> ids = collapsedRequests.stream().map(CollapsedRequest::getArgument)
                .collect(Collectors.toList());

        return new InstanceBatchCommand(ids);
    }

    @Override
    protected void mapResponseToRequests(List<Instance> batchResponse, Collection<CollapsedRequest<Instance, String>> collapsedRequests) {

        int count = 0;

        for (CollapsedRequest<Instance, String> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }


    private static final class InstanceBatchCommand extends HystrixCommand<List<Instance>> {

        private List<String> serviceIds;

        private static String DEFAULT_SERVICE_ID = "my-app";

        private static String DEFAULT_HOST = "localhost";

        private static int DEFAULT_PORT = 8080;

        protected InstanceBatchCommand(List<String> serviceIds) {
            super(HystrixCommandGroupKey.Factory.asKey("instanceBatchGroup"));
            this.serviceIds = serviceIds;
        }

        @Override
        protected List<Instance> run() throws Exception {
            List<Instance> instances = new ArrayList<>();
            log.info("start batch!.....");

            for (String s : serviceIds) {
                instances.add(new Instance(s, DEFAULT_HOST, DEFAULT_PORT));
            }

            return instances;
        }
    }
}
