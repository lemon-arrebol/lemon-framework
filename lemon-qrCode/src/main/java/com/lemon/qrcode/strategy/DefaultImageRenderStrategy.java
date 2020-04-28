package com.lemon.qrcode.strategy;

import com.google.zxing.common.BitMatrix;
import com.lemon.qrcode.entity.QRCode;

import java.awt.image.BufferedImage;

/**
 * @author houjuntao
 * @version 1.0
 * @description: 默认渲染二维码策略
 * @date Create by houjuntao on 2020-04-28 09:31
 */
public class DefaultImageRenderStrategy implements ImageRenderStrategy {
    /**
     * @param bitMatrix
     * @param qrCode
     * @return void
     * @description 渲染二维码
     * @author houjuntao
     * @date 2020-04-28 09:30
     */
    @Override
    public BufferedImage render(BitMatrix bitMatrix, QRCode qrCode) {
        // 获取二维码宽高
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        // 将二维码放入缓冲流，空白图片
        BufferedImage bufferedImage = new BufferedImage(width, height, qrCode.getImageType());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 循环将二维码内容定入图片
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? qrCode.getOnColor().get().getRGB() : qrCode.getOffColor().get().getRGB());
            }
        }

        return bufferedImage;
    }
}
