package com.lemon.dynamic.dasource.aspect;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@AssignDataSource("AssignDataSourceContain")
public @interface AssignDataSourceContain {
    String value();
}