package com.lc.logfilter.annotation;

import com.lc.logfilter.config.LogFilterAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启过滤器日志
 * 通过注解的方式将配置类引入项目的Spring扫描范围中。
 *
 * @author lingchen.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(LogFilterAutoConfiguration.class)
public @interface EnableLogFilter {
}
