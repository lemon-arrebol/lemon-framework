package com.lemon.dynamic.dasource.aspect;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-11 07:18
 */
public class BaseDataSource {
    @AssignDataSource("666")
    public void aaa() {
        System.out.println("Method use AssignDataSource");
    }

    @AssignDataSourceContain("999")
    public void bbb() {

    }
}
