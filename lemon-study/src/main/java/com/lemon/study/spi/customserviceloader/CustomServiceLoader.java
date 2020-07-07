package com.lemon.study.spi.customserviceloader;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;

/**
 * @author lemon
 * @version 1.0
 * @description:
 * 1、手动创建 META-INF/services/CustomServiceLoader.PREFIX + service.getName()
 * 2、使用CustomServiceLoader读取
 * @date Create by lemon on 2019-08-02 17:11
 */
@Slf4j
public class CustomServiceLoader {
    private static final String PREFIX = "META-INF/services/";

    /**
     * @param
     * @return
     * @description 获取所有clazz的实现类
     * @author lemon
     * @date 2019-08-02 17:21
     */
    public static <T> List<T> load(Class<T> service) {
        String fullName = CustomServiceLoader.PREFIX + service.getName();
        // 由于一个接口的实现类可能存在多个jar包中的META-INF目录下，所以下面使用getResources返回一个URL数组
        Enumeration<URL> configFileUrls;

        try {
            configFileUrls = CustomServiceLoader.class.getClassLoader().getResources(fullName);
        } catch (Exception e) {
            String tip = String.format("%s: Error locating configuration files", service.getName());
            log.error(tip, e);
            throw new RuntimeException(tip);
        }

        if (configFileUrls == null) {
            if (log.isDebugEnabled()) {
                log.debug("Not find services file {}", fullName);
            }

            return Lists.newArrayList();
        }

        List<T> serviceList = Lists.newArrayList();

        try {
            while (configFileUrls.hasMoreElements()) {
                URL configFileUrl = configFileUrls.nextElement();
                String configContent = IOUtils.toString(configFileUrl.openStream(), Charsets.UTF_8);
                String[] serviceNameArray = configContent.split("\n");

                for (String serviceName : serviceNameArray) {
                    Class serviceClass = CustomServiceLoader.class.getClassLoader().loadClass(serviceName);
                    Object serviceInstance = serviceClass.newInstance();
                    serviceList.add((T) serviceInstance);

                    if (log.isDebugEnabled()) {
                        log.debug("jar:{}, service interface:{}, serviceName:{}", configFileUrl, service.getName(), serviceName);
                    }
                }
            }
        } catch (Exception e) {
            String tip = String.format("%s: Error parsing configuration files", service.getName());
            log.error(tip, e);
            throw new RuntimeException(tip);
        }

        return serviceList;
    }
}
