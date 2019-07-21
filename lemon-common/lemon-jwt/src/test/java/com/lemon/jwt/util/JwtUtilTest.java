package com.lemon.jwt.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lemon.jwt.constants.JwtPayloadConstants;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class JwtUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseJWT() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwOTAxMDQ2MzI3MzQxMDU2Iiwic3QiOiIzIiwibG4iOiJ6aG91bGlkb25nIiwicm4iOiLlkajliKnmoIsiLCJleHAiOjE1MjgzMzgyODR9.JFflYIdE40Yw091XYoWziaLbFeqKKfMkwzRQldpUiVE";
        System.out.println((token.split("[.]")[1]));
        System.out.println(Base64.decodeBase64(token.split("[.]")[1]));
        System.out.println(new String(Base64.decodeBase64(token.split("[.]")[1])));
        JSONObject jsonObject = JwtUtil.parseJWTPayload(token);
        System.out.println(jsonObject.getLong("exp"));
        System.out.println(jsonObject);
    }

    @Test
    public void isExpiration() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm1hZSI6ImhvdWp1bnRhbyIsImV4cCI6MzIzMjMzMSwianRpIjoiNjY2NjY2Nzc3Nzc3Nzg4ODg4OCIsImlhdCI6MTU2MzcyMTAzMX0.InVor5wup56zv85vqUMTQe4iX0sN-RHIvM3PS6aqcSU";
        System.out.println(JwtUtil.isExpiration(token));
    }

    @Test
    public void createJWT() {
        Map<String, Object> claimMap = Maps.newHashMap();
        claimMap.put("userNmae", "houjuntao");
        claimMap.put(JwtPayloadConstants.EXPIRATION, 3232331);
        String signingKey = "6666667777777888888";
        System.out.println(JwtUtil.createJWT(claimMap, signingKey));
    }

    @Test
    public void isVerify() {
    }
}