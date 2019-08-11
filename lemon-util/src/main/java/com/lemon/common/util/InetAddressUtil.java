package com.lemon.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author lemon
 * @version 1.0
 * @description: 获取服务器IP
 * @date Create by lemon on 2019-04-25 14:16
 */
@Slf4j
public class InetAddressUtil {
    private static String serverIp;

    static {
        try {
            serverIp = getServerIp();
        } catch (Throwable e) {
            log.error("fetch localhost host address failed", e);
            serverIp = "UNKNOWN";
        }
    }

    /**
     * @description 获取服务器IP
     * @author lemon
     * @date 2019-04-25 14:15
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
}
