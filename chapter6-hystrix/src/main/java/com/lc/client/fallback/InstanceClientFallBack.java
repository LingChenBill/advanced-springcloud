package com.lc.client.fallback;

import com.lc.client.api.InstanceClient;
import com.lc.dto.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * FeignClient的失败回滚类。
 *
 * @description:
 * @author lingchen.
 * @date 2019/11/21
 */
@Component
@Slf4j
public class InstanceClientFallBack implements InstanceClient {
    @Override
    public Instance getInstanceByServiceId(String serviceId) {

        log.info("Can not get Instance by serviceId {}.", serviceId);

        return new Instance("error", "error", 0);
    }
}
