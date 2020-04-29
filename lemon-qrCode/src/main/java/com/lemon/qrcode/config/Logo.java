package com.lemon.qrcode.config;

import lombok.Data;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码LOGO配置信息
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class Logo extends BaseConfig {
    private boolean compress = true;
    private ImageRadius imageRadius;
    private LogoShape logoShape = new LogoShape();
    private LogoStroke logoStroke = new LogoStroke();
}
