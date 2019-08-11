package com.lemon.agent.time;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author lemon
 * @version 1.0
 * @description: -javaagent:/Users/lemon/IdeaProjects/framework/lemon-framework/lemon-common/lemon-agent/target/lemon-agent-1.0-SNAPSHOT.jar=hellowrold
 * @date Create by lemon on 2019-07-23 12:34
 */
public class BytebuddyTimeAgent {
    /**
     * @param
     * @return
     * @description 以vm参数的方式载入，在Java程序的main方法执行之前执行
     * JavaAgent 是运行在 main方法之前的拦截器，它内定的方法名叫 premain ，也就是说先执行 premain 方法然后再执行 main 方法。
     * 那么如何实现一个 JavaAgent 呢？很简单，只需要增加 premain 方法即可。
     * @author lemon
     * @date 2019-07-23 12:49
     */
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("传入参数：" + args);
        System.out.println("this is an perform monitor agent.");

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                return builder
                        // 拦截任意方法
                        .method(ElementMatchers.any())
                        // 委托
                        .intercept(MethodDelegation.to(BytebuddyTimeInterceptor.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }
        };

        new AgentBuilder
                .Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("com.lemon.agent.service"))
                // 指定需要拦截的方法
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
    }

    /**
     * @param
     * @return
     * @description 以Attach的方式载入，在Java程序启动后执行
     * @author lemon
     * @date 2019-07-23 14:03
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {

    }
}
