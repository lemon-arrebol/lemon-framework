package com.lemon.qrcode.entity;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * @author lemon
 * @version 1.0
 * @description: 背景图片
 * @date Create by lemon on 2020-04-26 09:10
 */
@Data
public class ImageBackground {
    private int x;

    private int y;

    private int imageType = BufferedImage.TYPE_INT_RGB;

    private int hints = Image.SCALE_SMOOTH;

    private String formatName = "png";

    /**
     * 背景图字节数组
     */
    private byte[] srcIamge;

    /**
     * 背景图源路径
     */
    private String srcPath;

    /**
     * 背景图输入流
     */
    private InputStream srcInput;

    /**
     * 背景图保存字节数组
     */
    private byte[] destIamge;

    /**
     * 背景图保存路径
     */
    private String destPath;

    /**
     * 背景图输出流
     */
    private OutputStream destOutput;

    private Supplier<Color> color = () -> Color.YELLOW;

    /**
     * 透明度：选择值从0.0~1.0: 完全透明~完全不透明
     */
    private float alpha = 0.6f;
    // alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
    private Composite composite;
    private ImageObserver observer;
    private ImageRadius imageRadius;
    private LogoShape logoShape = new LogoShape();
    private LogoStroke logoStroke = new LogoStroke();

    public ImageBackground() {
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);
    }
}
