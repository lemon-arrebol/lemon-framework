package com.lemon.agent.time;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-07-23 15:30
 */
public class BytebuddyTimeInterceptor {
    /**
     * @param
     * @return
     * @description
     * @This 调用对象
     * @Argument 方法参数(只能选择一个)
     * @Arguments 方法所有参数
     * @Origin 调用方法
     * @SuperCall 回调父类方法
     * @author lemon
     * @date 2019-07-23 16:01
     */
    @RuntimeType
    public static Object intercept(@This Object proxy, @Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();

        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
