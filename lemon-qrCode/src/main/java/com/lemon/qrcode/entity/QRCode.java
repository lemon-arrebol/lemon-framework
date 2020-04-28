package com.lemon.qrcode.entity;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lemon.qrcode.strategy.DefaultImageRenderStrategy;
import com.lemon.qrcode.strategy.ImageRenderStrategy;
import com.lemon.qrcode.util.ConstantUtil;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class QRCode {
    /**
     * 二维码保存字节数组
     */
    private byte[] destIamge;

    /**
     * 二维码保存路径
     */
    private String destPath;

    /**
     * 二维码输出流
     */
    private OutputStream destOutput;

    /**
     * 二维码内容
     */
    private String content;

    /**
     * 二维码宽度
     */
    private int width = 300;

    /**
     * 二维码高度
     */
    private int height = 300;

    private int imageType = BufferedImage.TYPE_INT_RGB;

    private Map<EncodeHintType, Object> hints = Maps.newHashMap();

    private BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

    private ImageRadius imageRadius;

    private ImageRenderStrategy imageRenderStrategy = new DefaultImageRenderStrategy();

    private Supplier<Color> onColor = () -> Color.BLACK;

    private Supplier<Color> offColor = () -> Color.WHITE;

    public QRCode() {
        // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, ConstantUtil.DEFAULT_CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
    }
}
