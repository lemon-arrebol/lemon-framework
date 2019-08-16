package com.lemon.cloud.author.oauth2.service.impl;

import com.lemon.cloud.author.oauth2.dao.UserEntityDao;
import com.lemon.cloud.author.oauth2.entity.UserEntity;
import com.lemon.cloud.author.oauth2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserEntityDao userDao;

    @Override
    public UserEntity findByname(String username) {
        return userDao.findByUsername(username) ;
    }
}
