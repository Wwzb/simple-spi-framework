package com.zbin.spi.core;

import com.zbin.spi.support.SpiBase;
import com.zbin.spi.support.SpiConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Zbin
 * @date 2021/6/28
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@RequiredArgsConstructor
public class SpiProviderFactory<T> implements FactoryBean<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiProviderFactory.class);

    private final Class<T> targetClass;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }

    @Override
    public T getObject() {
        return this.newProxyBean();
    }

    private T newProxyBean() {
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            List<SpiBase> spiList = SpiManager.getSpiProviderMap(SpiProviderFactory.this.targetClass);
            Object result = null;

            for (SpiBase spi : spiList) {
                if (spi != null && args != null && spi.supports(args[0])) {
                    SpiConfig spiConfig = spi.getConfig(args[0]);
                    String spiName = null != spiConfig.getName() ? spiConfig.getName() : null;
                    if (SpiProviderFactory.this.doBefore(spiName)) {
                        try {
                            result = method.invoke(spi, args);
                        } catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        } finally {
                            SpiProviderFactory.this.doEnd(spiName);
                        }
                        if (spiConfig.isMutex()) {
                            break;
                        }
                    }
                }
            }
            return result;
        };
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{this.targetClass}, invocationHandler);
    }

    private boolean doBefore(String spiName) {
        try {
            LOGGER.debug("Spi Name: {}", spiName);
        } catch (Exception var3) {
            LOGGER.warn("doBefore failed. spiName:{}", spiName, var3);
        }
        return true;
    }

    private void doEnd(String spiName) {
        try {
            LOGGER.debug("Spi Name: {}", spiName);
        } catch (Exception var5) {
            LOGGER.error("doEnd failed. spiName:{}", spiName, var5);
        }
    }

}
