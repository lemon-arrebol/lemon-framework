package com.lemon.cloud.author.oauth2.service;

import com.lemon.cloud.author.oauth2.entity.UserEntity;

/**
 * @param
 * @author lemon
 * @description
 * @return
 * @date 2019-08-15 14:31
 */
public interface IUserService {
    UserEntity findByname(String username);
}
