package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.QRCodeConfig;
import com.lemon.qrcode.util.ImageAlphaUtil;

import java.awt.image.BufferedImage;

/**
 * @author lemon
 * @version 1.0
 * @description: 图片透明度
 * @date Create by lemon on 2020-04-29 14:12
 */
public class AlphaDecorator extends AbstractQRCodeDecorator {
    public AlphaDecorator(QRCodeGenerator qrCodeGenerator) {
        super(qrCodeGenerator);
    }

    /**
     * @param qrCodeConfig
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-29 14:14
     */
    @Override
    public void generator(QRCodeConfig qrCodeConfig) throws Exception {
        super.generator(qrCodeConfig);
        BufferedImage bufferedImage = qrCodeConfig.getQrBufferedImage();
        bufferedImage = ImageAlphaUtil.insertImageAlpha(bufferedImage, qrCodeConfig.getImageAlpha());
        qrCodeConfig.setQrBufferedImage(bufferedImage);
    }
}
