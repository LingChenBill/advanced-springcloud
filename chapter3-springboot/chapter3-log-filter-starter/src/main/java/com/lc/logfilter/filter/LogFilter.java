package com.lc.logfilter.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * LogFilter地址日志过滤器。
 *
 * @author lingchen.
 */
public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("LogFilter init......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("Url {} is working.......", request.getRequestURI());
        chain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        logger.info("LogFilter destory......");
    }
}
