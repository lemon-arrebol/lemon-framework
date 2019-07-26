package com.lemon.jwt.util;

import com.google.common.collect.Maps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class AuthJwtUtilTest {
    private String token;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createJWTUseHMAC() {
        Map<String, Object> headerClaimMap = Maps.newHashMap();
        Map<String, Object> claimMap = Maps.newHashMap();
        claimMap.put("11", "22");
        claimMap.put("22", 33);
        int[] aa = {1, 2, 3};
        claimMap.put("33", aa);
        long dd =66L;
        claimMap.put("44", dd);
        claimMap.put("55", null);
        claimMap.put("66", "");

        token = AuthJwtUtil.createJWTUseHmac(headerClaimMap, claimMap, "HMAC256", "666666666");
        System.out.println(token);
    }

    @Test
    public void createJWTUseHMAC1() {
    }

    @Test
    public void createJWT() {
    }

    @Test
    public void parseJWT() {
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyIxMSI6IjIyIiwiMjIiOjMzLCIzMyI6WzEsMiwzXSwiNDQiOjY2LCI2NiI6IiJ9.lk7Ay6G8uVPqXywM7GCnwq2UpiDweacHsuo5Bz7HO-w";
        AuthJwtUtil.parseJWTUseHmac(token, "HMAC256", "666666666").forEach((key, claim) -> {
            System.out.println(key + "===" + claim.as(String.class));
        });
    }
}