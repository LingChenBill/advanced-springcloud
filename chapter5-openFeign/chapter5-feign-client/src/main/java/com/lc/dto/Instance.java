package com.lc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 实例Bean.
 *
 * @author lingchen.
 *
 */
@Data
@AllArgsConstructor
public class Instance {

    private String serviceId;

    private String host;

    private int port;
}
