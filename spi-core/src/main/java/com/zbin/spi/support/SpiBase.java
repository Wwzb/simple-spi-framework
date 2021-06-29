package com.zbin.spi.support;

/**
 * @author Zbin
 * @date 2021/6/28
 */
public interface SpiBase<T, R> {

    boolean supports(T param);

    R invoke(T param);

    SpiConfig getConfig(T param);
}
