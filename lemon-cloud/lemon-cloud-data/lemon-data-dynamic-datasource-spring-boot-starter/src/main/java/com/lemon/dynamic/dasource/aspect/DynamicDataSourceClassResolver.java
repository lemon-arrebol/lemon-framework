package com.lemon.dynamic.dasource.aspect;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto: liuchao2018@boloni.com" >liuchao</a>
 * Created on 2019-07-05
 * <p>Description: [对mp支持]</p>
 * @version 1.0
 * Copyright (c) 2019 博洛尼 信息技术部
 */
public class DynamicDataSourceClassResolver {
    private boolean mpEnabled = false;

    private Field mapperInterfaceField;

    public DynamicDataSourceClassResolver() {
        Class<?> proxyClass = null;
        try {
            proxyClass = Class.forName("com.baomidou.mybatisplus.core.override.MybatisMapperProxy");
        } catch (ClassNotFoundException e1) {
            try {
                proxyClass = Class.forName("com.baomidou.mybatisplus.core.override.PageMapperProxy");
            } catch (ClassNotFoundException e2) {
                try {
                    proxyClass = Class.forName("org.apache.ibatis.binding.MapperProxy");
                } catch (ClassNotFoundException e3) {
                }
            }
        }
        if (proxyClass != null) {
            try {
                mapperInterfaceField = proxyClass.getDeclaredField("mapperInterface");
                mapperInterfaceField.setAccessible(true);
                mpEnabled = true;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public Class<?> targetClass(MethodInvocation invocation) throws IllegalAccessException {
        if (mpEnabled) {
            Object target = invocation.getThis();
            Class<?> targetClass = target.getClass();
            return Proxy.isProxyClass(targetClass) ? (Class) mapperInterfaceField.get(Proxy.getInvocationHandler(target)) : targetClass;
        }
        return invocation.getMethod().getDeclaringClass();
    }
}
