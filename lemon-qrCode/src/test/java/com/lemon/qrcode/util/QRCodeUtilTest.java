package com.lemon.qrcode.util;

public class QRCodeUtilTest {
    public static void main(String[] args) throws Exception {
        String text = "https://www.yuque.com/ningmeng-rylxs/wzy51x/fw678g";
        String logoPath = "/Users/lemon/qrCode/成本4.0生产数据库账号龙信审批.png";
        String destPath = "/Users/lemon/qrCode";
        System.out.println(QRCodeUtil.encode(text, logoPath, destPath, true));
    }
}