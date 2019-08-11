package com.lemon.dynamic.dasource.aspect;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-11 07:18
 */
@AssignDataSource("777")
public interface IBaseDataSource {
    @AssignDataSource("888")
    void aaa();

    @AssignDataSourceContain("111111")
    default void bbb() {

    }
}
