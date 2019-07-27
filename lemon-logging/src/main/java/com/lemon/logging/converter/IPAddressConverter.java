package com.lemon.logging.converter;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @param
 * @author houjuntao
 * @description logback日志配置文件中获当前取服务器IP
 * @return
 * @date 2019-07-27 20:24
 */
@Slf4j
public class IPAddressConverter extends ClassicConverter {

    private static String serverIp;

    static {
        serverIp = getServerIp();
    }

    /**
     * @param
     * @return
     * @description 获取服务器IP
     * @author Mcdull
     * @date 2018/11/12
     */
    public static String getServerIp() {
        String ip = "UNKNOWN";

        try {
            // 根据网卡取本机配置的IP 获得网络接口
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            // 声明一个InetAddress类型ip地址
            InetAddress inetAddress;

            // 遍历所有的网络接口
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                // 同样再定义网络地址枚举类
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    inetAddress = addresses.nextElement();
                    // logger.debug(String.format("NetworkInterface: %s, IP: %s", netInterface.getName(), inetAddress.getHostAddress()));

                    if (inetAddress.isLoopbackAddress()) {
                        continue;
                    }

                    // InetAddress类包括Inet4Address和Inet6Address
                    if (inetAddress != null && (inetAddress instanceof Inet4Address)) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            log.error("fetch host address failed", e);
        }

        return ip;
    }

    /**
     * @param
     * @return
     * @description
     * @author Mcdull
     * @date 2018/10/25
     */
    @Override
    public String convert(ILoggingEvent event) {
        return serverIp;
    }
}