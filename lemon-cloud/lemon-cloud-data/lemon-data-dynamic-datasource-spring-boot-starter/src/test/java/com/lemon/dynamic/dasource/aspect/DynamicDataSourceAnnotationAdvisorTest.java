package com.lemon.dynamic.dasource.aspect;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class DynamicDataSourceAnnotationAdvisorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPointcut() throws Exception {
        Class<?> declaringClass = BaseDataSourceImplSub.class;
        Method method = declaringClass.getMethod("bbb");

        System.out.println(AnnotatedElementUtils.hasAnnotation(method, AssignDataSource.class) );

        System.out.println(AnnotatedElementUtils.hasAnnotation(declaringClass, AssignDataSource.class) );

        System.out.println("method " + JSON.toJSONString(AnnotationUtils.findAnnotation(method, AssignDataSource.class)));

        System.out.println("class " + JSON.toJSONString(AnnotationUtils.findAnnotation(declaringClass, AssignDataSource.class)));
    }
}