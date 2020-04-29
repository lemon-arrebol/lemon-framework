package com.lemon.qrcode.config;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.function.Supplier;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码LOGO
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class BaseConfig extends InoutConfig {
    private int x;

    private int y;

    private String formatName = "png";

    private int imageType = BufferedImage.TYPE_INT_RGB;

    /**
     * L宽度
     */
    private int width = 60;

    /**
     * 高度
     */
    private int height = 60;

    private int hints = Image.SCALE_SMOOTH;

    private RenderingHints.Key hintKey = RenderingHints.KEY_ANTIALIASING;

    private Object hintValue = RenderingHints.VALUE_ANTIALIAS_ON;

    private Supplier<Color> color = () -> Color.YELLOW;

    /**
     * 透明度：选择值从0.0~1.0: 完全透明~完全不透明
     */
    private float alpha = 0.3f;

    /**
     * alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     */
    private Composite composite;

    private ImageObserver observer;

    public BaseConfig() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
    }
}
