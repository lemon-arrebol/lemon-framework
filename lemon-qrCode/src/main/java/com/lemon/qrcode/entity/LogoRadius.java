package com.lemon.qrcode.entity;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * @author lemon
 * @version 1.0
 * @description: 图片切圆角
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class LogoRadius {
    private int width = 60;

    private int height = 60;

    /**
     * 角度（根据实测效果，一般建议为图片宽度的1/4）, 0表示直角
     */
    private double arcw = 10;

    /**
     * 角度（根据实测效果，一般建议为图片宽度的1/4）, 0表示直角
     */
    private double arch = 10;

    private int imageType = BufferedImage.TYPE_INT_ARGB;

    RenderingHints.Key hintKey = RenderingHints.KEY_ANTIALIASING;

    Object hintValue = RenderingHints.VALUE_ANTIALIAS_ON;

    private Color color = new Color(255, 164, 14);

    private Composite composite = AlphaComposite.Src;

    private ImageObserver observer;
}
