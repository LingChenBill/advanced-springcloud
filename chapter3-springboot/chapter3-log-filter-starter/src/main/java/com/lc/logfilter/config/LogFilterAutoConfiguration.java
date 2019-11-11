package com.lc.logfilter.config;

import com.lc.logfilter.filter.LogFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义一个自动配置类将LogFilterRegistrationBean注入到Spring的上下文中。
 * {
 *     @ConditionalOnClass: 声明只有当某个或某些class位于类路径中，才会实例化一个Bean.
 *     @ConditionalOnMissingBean: 声明仅仅在当前Spring上下文中不存在某个对象时，才会实例化一个Bean.
 * }
 *
 * @author lingchen.
 */
@Configuration
@ConditionalOnClass({LogFilterRegistrationBean.class, LogFilter.class})
public class LogFilterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogFilterRegistrationBean.class)
    public LogFilterRegistrationBean logFilterRegistrationBean() {
        return new LogFilterRegistrationBean();
    }
}
