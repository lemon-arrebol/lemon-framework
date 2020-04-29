package com.lemon.qrcode.config;

import lombok.Data;

import java.io.InputStream;

/**
 * @author lemon
 * @version 1.0
 * @description: 输入输出配置
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class InoutConfig extends OutputConfig {
    /**
     * 图片字节数组
     */
    private byte[] srcIamge;

    /**
     * 图片源路径
     */
    private String srcPath;

    /**
     * 图片输入流
     */
    private InputStream srcInput;
}
