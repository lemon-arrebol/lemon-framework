package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.QRCodeConfig;
import com.lemon.qrcode.util.ImageLogoUtil;

import java.awt.image.BufferedImage;

/**
 * @author lemon
 * @version 1.0
 * @description: 图片中插入LOGO
 * @date Create by lemon on 2020-04-29 14:12
 */
public class LogoDecorator extends AbstractQRCodeDecorator {
    public LogoDecorator(QRCodeGenerator qrCodeGenerator) {
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
        ImageLogoUtil.insertLogoImage(bufferedImage, qrCodeConfig.getLogo());
    }
}
