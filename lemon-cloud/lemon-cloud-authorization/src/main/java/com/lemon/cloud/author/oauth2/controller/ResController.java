package com.lemon.cloud.author.oauth2.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @param
 * @author lemon
 * @description 作为资源服务时，不能带上url，@RestController("/res")是错的，无法识别。只能在方法上注解全路径
 * @return
 * @date 2019-08-12 12:53
 */
@RestController()
public class ResController {

    /**
     * @param
     * @return
     * @description principal中封装了客户端（用户，也就是clientDetails，区别于Security的UserDetails，
     * 其实clientDetails中也封装了UserDetails），不是必须的参数，除非你想得到用户信息，才加上principal
     * @author lemon
     * @date 2019-08-12 12:54
     */
    @RequestMapping("/res/getMsg")
    public String getMsg(String msg, Principal principal) {
        System.out.println(JSON.toJSONString(principal));
        return "Get the msg: " + msg;
    }
}