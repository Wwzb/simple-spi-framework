package com.zbin.spi.service.impl;

import com.zbin.spi.annotation.SPI;
import com.zbin.spi.enums.RobotType;
import com.zbin.spi.model.RobotParam;
import com.zbin.spi.model.RobotResult;
import com.zbin.spi.service.RobotSpi;
import com.zbin.spi.support.SpiConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zbin
 * @date 2021/6/29
 */
@Slf4j
@SPI
public class OptimusPrimeService implements RobotSpi {

    @Override
    public boolean supports(RobotParam param) {
        return param.getType() == RobotType.OPTIMUS_PRIME;
    }

    @Override
    public RobotResult invoke(RobotParam param) {
        RobotResult robotResult = new RobotResult();
        robotResult.setMessage("I'm optimus prime, and I'm " + param.getColor());
        return robotResult;
    }

    @Override
    public SpiConfig getConfig(RobotParam param) {
        return new SpiConfig(1, true, "OptimusPrimeService");
    }
}
