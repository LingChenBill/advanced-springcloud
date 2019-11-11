package com.lc.logfilter.config;

import com.lc.logfilter.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * 定义LogFilterRegistrationBean将LogFilter过滤器封装成Spring Bean.
 *
 * @author lingchen.
 */
public class LogFilterRegistrationBean extends FilterRegistrationBean<LogFilter> {

    public LogFilterRegistrationBean() {
        super();
        // 添加LogFilter过滤器。
        this.setFilter(new LogFilter());
        // 匹配所有路径。
        this.addUrlPatterns("/*");
        // 定义过滤器名。
        this.setName("LogFilter");
        // 设置优先级。
        this.setOrder(1);
    }
}
