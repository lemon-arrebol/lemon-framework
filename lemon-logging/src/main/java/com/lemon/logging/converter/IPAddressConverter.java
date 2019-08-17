package com.lemon.logging.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.lemon.common.util.InetAddressUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @param
 * @author lemon
 * @description logback日志配置文件中获当前取服务器IP
 * @return
 * @date 2019-07-27 20:24
 */
@Slf4j
public class IPAddressConverter extends ClassicConverter {

    private static String serverIp = InetAddressUtil.getServerIp();

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2018/10/25
     */
    @Override
    public String convert(ILoggingEvent event) {
        return serverIp;
    }
}