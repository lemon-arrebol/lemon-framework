package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.QRCodeConfig;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码生成器装饰器
 * @date Create by lemon on 2020-04-29 14:08
 */
public abstract class AbstractQRCodeDecorator implements QRCodeGenerator {
    protected final QRCodeGenerator qrCodeGenerator;

    public AbstractQRCodeDecorator(QRCodeGenerator qrCodeGenerator) {
        this.qrCodeGenerator = qrCodeGenerator;
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
        this.qrCodeGenerator.generator(qrCodeConfig);
    }
}
