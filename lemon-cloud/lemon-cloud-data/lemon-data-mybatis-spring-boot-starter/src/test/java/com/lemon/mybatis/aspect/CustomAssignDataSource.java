package com.lemon.mybatis.aspect;

import com.lemon.dynamic.dasource.aspect.AssignDataSource;

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
@AssignDataSource("custom01")
public @interface CustomAssignDataSource {
    String value();
}