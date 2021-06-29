package com.zbin.spi.core;

import com.zbin.spi.annotation.SPI;
import com.zbin.spi.support.SpiBase;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zbin
 * @date 2021/6/28
 */
@SuppressWarnings("rawtypes")
@Component
public class SpiManager implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<Class<?>, List<SpiBase>> SPI_PROVIDER_MAP = new ConcurrentHashMap<>();
    private static volatile boolean loaded = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (loaded) {
            return;
        }
        Map<String, Object> spiInstanceMap = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(SPI.class);

        for (Object spiInstance : spiInstanceMap.values()) {
            Class<?>[] interfaces = AopUtils.getTargetClass(spiInstance).getInterfaces();
            Class<?> spiInterface = interfaces[0];
            if (SPI_PROVIDER_MAP.get(spiInterface) != null && SPI_PROVIDER_MAP.get(spiInterface).size() > 0) {
                SPI_PROVIDER_MAP.get(spiInterface).add((SpiBase) spiInstance);
            } else {
                List<SpiBase> spiProviderList = new ArrayList<>();
                spiProviderList.add((SpiBase) spiInstance);
                SPI_PROVIDER_MAP.put(spiInterface, spiProviderList);
            }
        }

        for (List<SpiBase> list : SPI_PROVIDER_MAP.values()) {
            list.sort(new SpiComparator());
        }

        loaded = true;
    }

    public static List<SpiBase> getSpiProviderMap(Class spiInterface) {
        return SPI_PROVIDER_MAP.get(spiInterface);
    }
}
