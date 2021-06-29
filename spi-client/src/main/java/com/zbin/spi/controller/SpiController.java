package com.zbin.spi.controller;

import com.zbin.spi.enums.RobotType;
import com.zbin.spi.model.RobotParam;
import com.zbin.spi.model.RobotResult;
import com.zbin.spi.service.RobotSpi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zbin
 * @date 2021/6/28
 */
@RestController
public class SpiController {

    @Resource
    private RobotSpi robotSpiService;

    @GetMapping("/robot")
    public String getRobot(@RequestParam(value = "type", required = false,defaultValue = "BUMBLE_BEE") String type,
                           @RequestParam(value = "color", required = false, defaultValue = "blank") String color) {
        RobotParam param = new RobotParam();
        param.setType(RobotType.valueOf(type));
        param.setColor(color);
        RobotResult result = robotSpiService.invoke(param);
        System.out.println(robotSpiService.getConfig(param).getName());
        return result.getMessage();
    }
}
