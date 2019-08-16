package com.lemon.cloud.author.oauth2.dao;

import com.lemon.cloud.author.oauth2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param
 * @author lemon
 * @description
 * @return
 * @date 2019-08-15 14:31
 */
public interface UserEntityDao extends JpaRepository<UserEntity, Integer> {
    /**
     * 匹配姓名得到用户
     *
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);
}
