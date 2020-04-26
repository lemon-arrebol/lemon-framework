package com.lemon.qrcode.entity;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class QRCode {
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

    public QRCode() {
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
    }
}
