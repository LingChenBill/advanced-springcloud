package com.lc.controller;

import com.lc.dto.Instance;
import com.lc.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源访问控制器。
 *
 * @author lingchenbill
 * @description:
 */
@RestController
@Slf4j
public class ResourceController {

    // 默认用户名。
    private final static String DEFAULT_NAME = "lingchenbill";
    // 服务ID。
    private static String DEFAULT_SERVICE_ID = "application";
    // host。
    private static String DEFAULT_HOST = "localhost";
    // 端口。
    private static int DEFAULT_PORT = 8080;

    /**
     * 访问受保护的资源。
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable("userId") String userId) {
        log.info("Get User by UserId: {}", userId);
        return new User(userId, DEFAULT_NAME);
    }

    /**
     * 访问不受保护的资源。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId) {
        log.info("Get Instance by serviceId: {}", serviceId);
        return new Instance(serviceId, DEFAULT_HOST, DEFAULT_PORT);
    }



}
