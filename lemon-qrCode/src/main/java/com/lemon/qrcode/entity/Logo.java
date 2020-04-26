package com.lemon.qrcode.entity;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码LOGO
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class Logo {
    private byte[] iamge;

    private int imageType = BufferedImage.TYPE_INT_RGB;

    private boolean compress = true;


    private String path;

    /**
     * LOGO宽度
     */
    private int width = 60;

    /**
     * LOGO高度
     */
    private int height = 60;

    private int hints = Image.SCALE_SMOOTH;

    private Color color = new Color(200, 0, 200);
    private float alpha = 0.5f;
    // alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
    private Composite composite;
    private ImageObserver observer;

    public Logo() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
    }
}
