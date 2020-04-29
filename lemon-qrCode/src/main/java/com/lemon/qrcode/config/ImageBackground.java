package com.lemon.qrcode.config;

import lombok.Data;

/**
 * @author lemon
 * @version 1.0
 * @description: 背景图片
 * @date Create by lemon on 2020-04-26 09:10
 */
@Data
public class ImageBackground extends BaseConfig {
    private ImageRadius imageRadius;
    private LogoShape logoShape = new LogoShape();
    private LogoStroke logoStroke = new LogoStroke();
}
