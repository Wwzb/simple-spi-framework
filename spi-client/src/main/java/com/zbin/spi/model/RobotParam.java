package com.zbin.spi.model;

import com.zbin.spi.enums.RobotType;
import lombok.Data;

/**
 * @author Zbin
 * @date 2021/6/29
 */
@Data
public class RobotParam {

    private RobotType type;

    private String color;
}
