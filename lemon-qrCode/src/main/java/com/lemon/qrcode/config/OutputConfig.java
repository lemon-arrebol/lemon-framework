package com.lemon.qrcode.config;

import lombok.Data;

import java.io.OutputStream;

/**
 * @author lemon
 * @version 1.0
 * @description: 输出配置
 * @date Create by lemon on 2020-04-25 20:30
 */
@Data
public class OutputConfig {
    /**
     * 图片保存字节数组
     */
    private byte[] destIamge;

    /**
     * 图片保存路径
     */
    private String destPath;

    /**
     * 图片输出流
     */
    private OutputStream destOutput;
}
