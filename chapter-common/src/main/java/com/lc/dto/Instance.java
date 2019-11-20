package com.lc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuyangze
 * @description:
 * @date 2019/11/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instance {

    private String serviceId;

    private String host;

    private int port;
}
