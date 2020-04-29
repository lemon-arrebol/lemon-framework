package com.lemon.qrcode.strategy;

import com.google.zxing.common.BitMatrix;
import com.lemon.qrcode.config.QRCode;
import com.lemon.qrcode.util.QRCodeUtil;

import java.awt.image.BufferedImage;

/**
 * @author lemon
 * @version 1.0
 * @description: 默认渲染二维码策略
 * @date Create by lemon on 2020-04-28 09:31
 */
public class DefaultImageRenderStrategy implements ImageRenderStrategy {
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
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int finderWidth = QRCodeUtil.getPositionDetectPatternWidth(bitMatrix) + 3;
        int[] pixels = new int[width * height];
        // 空白图片
        BufferedImage bufferedImage = new BufferedImage(width, height, qrCode.getImageType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > 0 && x < finderWidth && y > 0 && y < finderWidth) {
                    // 二维码左上角探测图形颜色
                    pixels[y * width + x] = bitMatrix.get(x, y)
                            ? qrCode.getLeftTopFinderOnColor().get().getRGB() : qrCode.getOffColor().get().getRGB();
                    bufferedImage.setRGB(x, y, pixels[y * width + x]);
                } else if (x > (width - finderWidth) && x < width && y > 0 && y < finderWidth) {
                    // 二维码右上角探测图形颜色
                    pixels[y * width + x] = bitMatrix.get(x, y)
                            ? qrCode.getRightTopFinderOnColor().get().getRGB() : qrCode.getOffColor().get().getRGB();
                    bufferedImage.setRGB(x, y, pixels[y * width + x]);
                } else if (x > 0 && x < finderWidth && y > (height - finderWidth) && y < height) {
                    // 二维码左下角探测图形颜色
                    pixels[y * width + x] = bitMatrix.get(x, y)
                            ? qrCode.getLeftDownFinderOnColor().get().getRGB() : qrCode.getOffColor().get().getRGB();
                    bufferedImage.setRGB(x, y, pixels[y * width + x]);
                } else {
                    pixels[y * width + x] = bitMatrix.get(x, y) ? qrCode.getOnColor().get().getRGB() : qrCode.getOffColor().get().getRGB();
                    bufferedImage.setRGB(x, y, pixels[y * width + x]);
                }
            }
        }

        bufferedImage.getRaster().setDataElements(0, 0, width, height, pixels);
        return bufferedImage;
    }
}
