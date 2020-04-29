package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.QRCodeConfig;

/**
 * @author lemon
 * @version 1.0
 * @description: 二维码生成器
 * @date Create by lemon on 2020-04-29 13:52
 */
public interface QRCodeGenerator {
    /**
     * @param qrCodeConfig
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-29 14:13
     */
    void generator(QRCodeConfig qrCodeConfig) throws Exception;
}
