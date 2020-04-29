package com.lemon.qrcode.decorator;

import com.lemon.qrcode.config.QRCodeConfig;
import com.lemon.qrcode.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lemon
 * @version 1.0
 * @description: 默认二维码生成器
 * @date Create by lemon on 2020-04-29 13:55
 */
@Slf4j
public class DefaultQRCodeGenerator implements QRCodeGenerator {
    /**
     * @param qrCodeConfig
     * @return void
     * @description
     * @author lemon
     * @date 2020-04-29 14:54
     */
    @Override
    public void generator(QRCodeConfig qrCodeConfig) throws Exception {
        QRCodeUtil.generateQRCodeNative(qrCodeConfig);
    }
}
