package com.lemon.qrcode.strategy;

import com.google.zxing.common.BitMatrix;
import com.lemon.qrcode.config.QRCode;

import java.awt.image.BufferedImage;

/**
 * @author lemon
 * @version 1.0
 * @description: 渲染二维码策略
 * @date Create by lemon on 2020-04-28 09:29
 */
public interface ImageRenderStrategy {
    /**
     * @param bitMatrix
     * @param qrCode
     * @return java.awt.image.BufferedImage
     * @description 渲染二维码
     * @author lemon
     * @date 2020-04-28 11:43
     */
    BufferedImage render(BitMatrix bitMatrix, QRCode qrCode);
}
