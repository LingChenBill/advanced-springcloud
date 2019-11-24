package com.lc.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Ribbon config。
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/24
 */
public class RibbonConfig {

    @Autowired
    IClientConfig ribbonClientConfig;

    @Bean
    public IRule ribbonRule() {
        // 设置负载均衡规则，随机规则。
        return new RandomRule();
    }
}
