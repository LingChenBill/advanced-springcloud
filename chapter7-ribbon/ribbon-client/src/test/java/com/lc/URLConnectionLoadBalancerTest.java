package com.lc;

import com.lc.api.URLConnectionLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

/**
 * Ribbon API test class.
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/25
 */
@Slf4j
public class URLConnectionLoadBalancerTest {

    public static void main(String[] args) throws Exception {
        URLConnectionLoadBalancer urlConnectionLoadBalancer = new URLConnectionLoadBalancer(
                Lists.newArrayList(
                        new Server("www.baidu.com", 80),
                        new Server("www.linkedin.com", 80),
                        new Server("www.yahoo.com", 80)
                )
        );

        for (int i = 0; i < 6; i++) {
            log.info("urlConnectionLoadBalancer call: {}", urlConnectionLoadBalancer.call("/"));
        }

        log.info("===Load balancer stats ===");
        log.info("balancer stats: {}", urlConnectionLoadBalancer.getLoadBalancerStats());
    }

}
