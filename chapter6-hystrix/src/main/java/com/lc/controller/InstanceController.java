package com.lc.controller;

import com.lc.dto.Instance;
import com.lc.service.InstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实例控制器类。
 *
 * @description:
 * @author zhuyangze
 * @date 2019/11/20
 */
@RestController
@RequestMapping("/instance")
@Slf4j
public class InstanceController {

    @Autowired
    private InstanceService instanceService;

    /**
     * 获取实例。
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/rest-template/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdWithRestTemplate(@PathVariable("serviceId") String serviceId) {
        log.info("Get Instance by serviceId {}", serviceId);
        return instanceService.getInstanceByServiceIdWithRestTemplate(serviceId);
    }

}
