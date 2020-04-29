package com.lemon.qrcode.config;

import com.google.zxing.common.BitMatrix;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码信息构建
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class QRCodeConfig {
    private String destPath;
    private OutputStream output;
    private String charset = "utf-8";
    private String formatName = "png";

    private QRCode qrCode = new QRCode();
    private Logo logo = new Logo();
    private LogoShape logoShape = new LogoShape();
    private LogoStroke logoStroke = new LogoStroke();
    private WaterMark waterMark = new WaterMark();
    private ImageAlpha imageAlpha = new ImageAlpha();
    private ImageRadius imageRadius = new ImageRadius();
    private ImageBackground imageBackground = new ImageBackground();

    private BitMatrix qrBitMatrix;
    private BufferedImage qrBufferedImage;
}
