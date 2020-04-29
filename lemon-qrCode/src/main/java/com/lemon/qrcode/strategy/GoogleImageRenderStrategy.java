package com.lemon.qrcode.strategy;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lemon.qrcode.config.QRCode;

import java.awt.image.BufferedImage;

/**
 * @author lemon
 * @version 1.0
 * @description: 采用Google封装的类渲染二维码策略
 * @date Create by lemon on 2020-04-28 09:31
 */
public class GoogleImageRenderStrategy implements ImageRenderStrategy {
    /**
     * @param bitMatrix
     * @param qrCode
     * @return void
     * @description 渲染二维码
     * @author lemon
     * @date 2020-04-28 09:30
     */
    @Override
    public BufferedImage render(BitMatrix bitMatrix, QRCode qrCode) {
        // 二维码为红色，背景色为绿色
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,
                new MatrixToImageConfig(qrCode.getOnColor().get().getRGB(), qrCode.getOffColor().get().getRGB()));
        return bufferedImage;
    }
}
