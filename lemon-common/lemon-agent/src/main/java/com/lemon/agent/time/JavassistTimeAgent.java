package com.lemon.agent.time;

import java.lang.instrument.Instrumentation;

/**
 * @author houjuntao
 * @version 1.0
 * @description: -javaagent:/Users/houjuntao/IdeaProjects/framework/lemons/lemon-common/lemon-agent/target/lemon-agent-1.0-SNAPSHOT.jar=hellowrold
 * @date Create by houjuntao on 2019-07-23 12:34
 */
public class JavassistTimeAgent {
    /**
     * @param
     * @return
     * @description 以vm参数的方式载入，在Java程序的main方法执行之前执行
     * JavaAgent 是运行在 main方法之前的拦截器，它内定的方法名叫 premain ，也就是说先执行 premain 方法然后再执行 main 方法。
     * 那么如何实现一个 JavaAgent 呢？很简单，只需要增加 premain 方法即可。
     * @author houjuntao
     * @date 2019-07-23 12:49
     */
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("传入参数：" + args);
        instrumentation.addTransformer(new JavassistPerformMonitorTransformer());
    }

    /**
     * @param
     * @return
     * @description 以Attach的方式载入，在Java程序启动后执行
     * @author houjuntao
     * @date 2019-07-23 14:03
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {

    }
}
