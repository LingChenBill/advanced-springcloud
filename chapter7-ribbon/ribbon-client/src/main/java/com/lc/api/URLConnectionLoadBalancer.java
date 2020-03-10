package com.lc.api;

import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.loadbalancer.*;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import rx.Observable;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Ribbon API 实现。
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/25
 */
public class URLConnectionLoadBalancer {

    private final ILoadBalancer loadBalancer;

    private final RetryHandler retryHandler = new DefaultLoadBalancerRetryHandler(0, 0, true);

    public URLConnectionLoadBalancer(List<Server> serverList) {
        // 使用LoadBalancerBuilder的接口来创建ILoadBalancer实例。
        loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
    }

    /**
     * 使用LoadBalancerCommand.builder接口来配置Command实例，然后在回调方法中使用选中的服务器信息发送http请求。
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String call(final String path) throws Exception {
        return LoadBalancerCommand.<String>builder()
                .withLoadBalancer(loadBalancer)
                .withRetryHandler(retryHandler)
                .build()
                .submit(new ServerOperation<String>() {
                    @Override
                    public Observable<String> call(Server server) {

                        URL url;

                        try {
                            url = new URL("http://" + server.getHost() + ":" + server.getPort() + path);
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            return Observable.just(conn.getResponseMessage());
                        } catch (Exception e) {
                            return Observable.error(e);
                        }

                    }
                }).toBlocking().first();
    }

    public LoadBalancerStats getLoadBalancerStats() {
        return ((BaseLoadBalancer)loadBalancer).getLoadBalancerStats();
    }
}
