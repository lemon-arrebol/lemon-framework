package com.lemon.common.util;

/**
 * @author lemon
 * @version 1.0
 * @description: 字符串工具类
 * @date Create by lemon on 2019-07-31 18:52
 */
public class StringUtils {
    /**
     * @param
     * @return
     * @description str中index开始是否有匹配substring子串
     * @author lemon
     * @date 2019-07-31 18:52
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        if (index + substring.length() > str.length()) {
            return false;
        } else {
            for (int i = 0; i < substring.length(); ++i) {
                if (str.charAt(index + i) != substring.charAt(i)) {
                    return false;
                }
            }

            return true;
        }
    }
}
