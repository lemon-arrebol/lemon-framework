package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.QRCodeConfig;
import com.lemon.qrcode.util.ImageRadiusUtil;

import java.awt.image.BufferedImage;

/**
 * @author lemon
 * @version 1.0
 * @description: 图片圆角
 * @date Create by lemon on 2020-04-29 14:12
 */
public class RadiusDecorator extends AbstractQRCodeDecorator {
    public RadiusDecorator(QRCodeGenerator qrCodeGenerator) {
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
        bufferedImage = ImageRadiusUtil.cornerRadius(bufferedImage, qrCodeConfig.getImageRadius());
        qrCodeConfig.setQrBufferedImage(bufferedImage);
    }
}
