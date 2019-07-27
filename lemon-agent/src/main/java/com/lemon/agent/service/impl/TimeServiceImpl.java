package com.lemon.agent.service.impl;

import com.lemon.agent.service.TimeService;

public class TimeServiceImpl implements TimeService {
    /**
     * @param
     * @return
     * @description 有异常必须向上一层抛出，如果用try{}catch(){}捕获异常，则抛出异常
     * @author lemon
     * @date 2019-07-23 18:55
     */
    @Override
    public void hello(String hello) throws InterruptedException {
        Thread.sleep(1000);
    }
}