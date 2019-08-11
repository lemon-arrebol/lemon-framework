package com.lemon.dynamic.dasource.aspect;

import java.lang.annotation.*;

/**
 * @param
 * @author lemon
 * @description 切换指定数据源的注解
 * @return
 * @date 2019-08-10 22:14
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AssignDataSource {
    String value();
}