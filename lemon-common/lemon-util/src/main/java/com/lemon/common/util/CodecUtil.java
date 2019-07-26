package com.lemon.common.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lemon
 * @version 1.0
 * @description: 编解码工具类
 * @date Create by lemon on 2019-07-26 11:22
 */
public class CodecUtil {
    /**
     * @param
     * @return
     * @description base64 编码
     * @author lemon
     * @date 2019-07-26 11:18
     */
    public static String encodeBase64(byte[] textArray) {
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(textArray), "Data not allowed to be empty");

        return BaseEncoding.base64().encode(textArray);
    }

    /**
     * @param
     * @return
     * @description base64 解码
     * @author lemon
     * @date 2019-07-26 11:18
     */
    public static byte[] decodeBase64(String text) {
        Preconditions.checkArgument(StringUtils.isNotBlank(text), "Data not allowed to be empty");

        return BaseEncoding.base64().decode(text);
    }

    /**
     * @param
     * @return
     * @description UTF-8 编码
     * @author lemon
     * @date 2019-07-26 11:24
     */
    public static String encodeUtf8(byte[] textArray) {
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(textArray), "Data not allowed to be empty");

        return new String(textArray, Charsets.UTF_8);

    }

    /**
     * @param
     * @return
     * @description UTF-8 解码
     * @author lemon
     * @date 2019-07-26 11:25
     */
    public static byte[] decodeUtf8(String text) {
        Preconditions.checkArgument(StringUtils.isNotBlank(text), "Data not allowed to be empty");

        return text.getBytes(Charsets.UTF_8);
    }

    /**
     * @param
     * @return
     * @description hex 编码
     * @author lemon
     * @date 2019-07-26 16:54
     */
    public static String encodeHex(byte[] data) {
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(data), "Data not allowed to be empty");

        return BaseEncoding.base16().encode(data);
    }

    /**
     * @param
     * @return
     * @description hex 解码
     * @author lemon
     * @date 2019-07-26 16:54
     */
    public static byte[] decodeHex(String data) {
        Preconditions.checkArgument(StringUtils.isNotBlank(data), "Data not allowed to be empty");

        return BaseEncoding.base16().decode(data);
    }
}
