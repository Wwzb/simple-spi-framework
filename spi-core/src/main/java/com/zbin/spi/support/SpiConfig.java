package com.zbin.spi.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zbin
 * @date 2021/6/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpiConfig {

    private int priority;

    private boolean mutex;

    private String name;
}
