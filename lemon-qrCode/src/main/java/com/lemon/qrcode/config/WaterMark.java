package com.lemon.qrcode.config;

import lombok.Data;

import java.awt.*;
import java.util.function.Supplier;

/**
 * @author lemon
 * @version 1.0
 * @description: 水印
 * @date Create by lemon on 2020-04-26 09:10
 */
@Data
public class WaterMark extends BaseConfig {
    /**
     * 水印内容
     */
    private String content;
    private Font font = new Font("微软雅黑", Font.PLAIN, 20);
    private Supplier<Color> background = () -> Color.YELLOW;
}
