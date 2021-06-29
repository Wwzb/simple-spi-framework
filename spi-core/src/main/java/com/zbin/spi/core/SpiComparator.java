package com.zbin.spi.core;

import com.zbin.spi.support.SpiBase;

import java.util.Comparator;

/**
 * @author Zbin
 * @date 2021/6/28
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SpiComparator implements Comparator<SpiBase> {

    @Override
    public int compare(SpiBase spiBase1, SpiBase spiBase2) {
        return spiBase2.getConfig(null).getPriority() - spiBase1.getConfig(null).getPriority();
    }
}
