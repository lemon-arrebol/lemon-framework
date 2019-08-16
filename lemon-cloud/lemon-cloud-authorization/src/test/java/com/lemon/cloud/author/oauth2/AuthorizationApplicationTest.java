package com.lemon.cloud.author.oauth2;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthorizationApplicationTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String aaa = passwordEncoder.encode("123456");
        System.out.println(aaa);

        String bbb = passwordEncoder.encode("123456");
        System.out.println(bbb);

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bcryptPasswordEncoder.matches("123456", bbb.substring(8, bbb.length())));
    }
}