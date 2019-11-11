package com.lc;

import com.lc.config.SMSConfiguration;
import com.lc.logfilter.annotation.EnableLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * chapter3 start application.
 * {
 *     @EnableLogFilter: 开启自定义的日志过滤器。pom文件中要引入自定义过滤器的chapter3-log-filter-starter工程。
 * }
 *
 * @author lingchen.
 */
@SpringBootApplication
@RestController
@Slf4j
@EnableLogFilter
public class Chapter3SpringbootDemoApplication {

    @Autowired
    public SMSConfiguration smsConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(Chapter3SpringbootDemoApplication.class, args);
    }


    @GetMapping("/test")
    public String test() {
        log.info("smsConfiguration is : {}", smsConfiguration);
        return "this is a demo spring boot.";
    }

}
