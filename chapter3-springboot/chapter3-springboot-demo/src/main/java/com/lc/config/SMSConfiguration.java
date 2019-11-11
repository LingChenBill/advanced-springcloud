package com.lc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动载入外部属性到Bean.
 * {@ConfigurationProperties(prefix = "sms"): 设置SMSConfiguration Bean 外部配置的Bean前缀。
 *  @Configuration: 将SMSConfiguration Bean配置到spring boot 上下文中。 }
 *
 * @author lingchen.
 */
@ConfigurationProperties(prefix = "sms")
@Configuration
@Data
public class SMSConfiguration {

    private int retryLimitationMinutes;

    private int validityMinutes;

    private final List<String> types = new ArrayList<>();

}
