package com.lemon.qrcode.strategy;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.lemon.qrcode.entity.QRCode;

import java.awt.image.BufferedImage;

/**
 * @author houjuntao
 * @version 1.0
 * @description: 采用Google封装的类渲染二维码策略
 * @date Create by houjuntao on 2020-04-28 09:31
 */
public class ImageDynamicRenderStrategy implements ImageRenderStrategy {
    /**
     * @param bitMatrix
     * @param qrCode
     * @return void
     * @description 渲染二维码
     * 参考：{@link com.google.zxing.client.j2se.MatrixToImageWriter#toBufferedImage(BitMatrix matrix, com.google.zxing.client.j2se.MatrixToImageConfig config)}
     * @author houjuntao
     * @date 2020-04-28 09:30
     */
    @Override
    public BufferedImage render(BitMatrix bitMatrix, QRCode qrCode) {
        // 获取二维码宽高
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        // 空白图片
        BufferedImage bufferedImage = new BufferedImage(width, height, qrCode.getImageType());
        int[] rowPixels = new int[width];
        BitArray row = new BitArray(width);

        for (int y = 0; y < height; y++) {
            row = bitMatrix.getRow(y, row);

            for (int x = 0; x < width; x++) {
                // row.get(x) true 表示二维码的黑色部分 false 表示白色背景部分。
                rowPixels[x] = row.get(x) ? qrCode.getOnColor().get().getRGB() : qrCode.getOffColor().get().getRGB();
            }

            // 上色
            bufferedImage.setRGB(0, y, width, 1, rowPixels, 0, width);
        }

        return bufferedImage;
    }
}
