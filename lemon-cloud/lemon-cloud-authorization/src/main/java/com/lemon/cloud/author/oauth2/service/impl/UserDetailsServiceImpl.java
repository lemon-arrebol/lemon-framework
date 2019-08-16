package com.lemon.cloud.author.oauth2.service.impl;

import com.lemon.cloud.author.oauth2.domain.Authority;
import com.lemon.cloud.author.oauth2.entity.UserEntity;
import com.lemon.cloud.author.oauth2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService iUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = iUserService.findByname(username);
        String name = userEntity.getUsername();
        String password = userEntity.getPassword();
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new Authority(1L, "ROLE_ADMIN"));
        authorities.add(new Authority(2L, "ROLE_USER"));

        return new User(name, password, true, true, true, true, authorities);
    }

}