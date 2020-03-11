package com.lc.controller;

import com.lc.dto.Instance;
import com.lc.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

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

    BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Autowired
    private ResourceServerTokenServices tokenServices;


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

    /**
     * 根据访问令牌获取用户认证信息的接口。
     *
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/user")
    public Principal userInfo(ServletRequest req) {

        final HttpServletRequest request = (HttpServletRequest) req;
        Authentication authentication = tokenExtractor.extract(request);
        String token = (String) authentication.getPrincipal();
        return tokenServices.loadAuthentication(token);

    }

}
