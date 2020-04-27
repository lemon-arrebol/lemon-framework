package com.lemon.qrcode.entity;

import lombok.Data;

import java.io.OutputStream;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码信息构建
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class QRCodeContainer {
    private String destPath;
    private OutputStream output;
    private String charset = "utf-8";
    private String formatName = "png";
    private QRCode qrCode = new QRCode();
    private Logo logo = new Logo();
    private LogoShape logoShape = new LogoShape();
    private LogoStroke logoStroke = new LogoStroke();
    private WaterMark waterMark = new WaterMark();
}
