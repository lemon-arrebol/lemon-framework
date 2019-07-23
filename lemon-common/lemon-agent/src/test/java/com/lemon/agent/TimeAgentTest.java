package com.lemon.agent;

import com.lemon.agent.service.TimeService;
import com.lemon.agent.service.impl.TimeServiceImpl;

public class TimeAgentTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("执行main方法");
        TimeService timeService = new TimeServiceImpl();
        timeService.hello("");
    }
}