package com.lemon.qrcode.entity;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码LOGO
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class Logo {
    private int x;

    private int y;

    private String formatName = "png";

    /**
     * 图片字节数组
     */
    private byte[] srcIamge;

    /**
     * 图片源路径
     */
    private String srcPath;

    /**
     * 图片输入流
     */
    private InputStream srcInput;

    /**
     * 图片保存字节数组
     */
    private byte[] destIamge;

    /**
     * 图片保存路径
     */
    private String destPath;

    /**
     * 图片输出流
     */
    private OutputStream destOutput;

    private int imageType = BufferedImage.TYPE_INT_RGB;

    private boolean compress = true;

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
    private ImageRadius imageRadius;
    private LogoShape logoShape = new LogoShape();
    private LogoStroke logoStroke = new LogoStroke();

    public Logo() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
    }
}
