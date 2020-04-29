package com.lemon.qrcode.config;

import lombok.Data;

/**
 * @author lemon
 * @version 1.0
 * @description: 图片切圆角
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class ImageRadius extends BaseConfig {
    /**
     * 角度（根据实测效果，一般建议为图片宽度的1/4）, 0表示直角
     */
    private double arcw = 10;

    /**
     * 角度（根据实测效果，一般建议为图片宽度的1/4）, 0表示直角
     */
    private double arch = 10;
}
