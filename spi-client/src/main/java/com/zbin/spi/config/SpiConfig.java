package com.zbin.spi.config;

import com.zbin.spi.core.SpiProviderFactory;
import com.zbin.spi.service.RobotSpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zbin
 * @date 2021/6/29
 */
@Configuration
public class SpiConfig {

    @Bean(name = "robotSpiService")
    public SpiProviderFactory<RobotSpi> queryParamSpiService() {
        return new SpiProviderFactory<>(RobotSpi.class);
    }
}
