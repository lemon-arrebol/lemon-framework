package com.lemon.cloud.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author lemon
 * @version 1.0
 * @description: 普通类获取Spring容器中的bean
 * @date Create by lemon on 2019-04-25 09:32
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * @param
     * @return
     * @description 获取applicationContext
     * @author lemon
     * @date 2019-04-25 09:33
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param
     * @return
     * @description 通过name获取 Bean
     * @author lemon
     * @date 2019-04-25 09:33
     */
    public static Object getBean(String name) {
        return getApplicationContext()
                .getBean(name);
    }

    /**
     * @param
     * @return
     * @description 通过class获取Bean
     * @author lemon
     * @date 2019-04-25 09:33
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext()
                .getBean(clazz);
    }

    /**
     * @param
     * @return
     * @description 通过name, 以及Clazz返回指定的Bean
     * @author lemon
     * @date 2019-04-25 09:33
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext()
                .getBean(name, clazz);
    }
}
