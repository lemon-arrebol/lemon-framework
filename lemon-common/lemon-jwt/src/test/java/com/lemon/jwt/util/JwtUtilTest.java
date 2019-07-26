package com.lemon.jwt.util;

import com.google.common.collect.Maps;
import com.lemon.jwt.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

public class JwtUtilTest {
    String signingKey = UUID.randomUUID().toString();

    SecureRandom secureRandom = new SecureRandom();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseJWT() {
        String token = JwtUtil.createJWTUseHmac(this.generateClaims(), this.signingKey);
        Claims cliams = JwtUtil.parseJWT(token, this.signingKey);

        Assert.notNull(cliams);
    }

    @Test
    public void isExpiration() {
        String token = JwtUtil.createJWTUseHmac(this.generateClaims(), this.signingKey);
        JwtUtil.isExpiration(token);
    }

    @Test
    public void createJWT() {
        String result = JwtUtil.createJWTUseHmac(this.generateClaims(), this.signingKey);

        Assert.hasText(result, "Token is empty");
    }

    @Test
    public void isVerify() {
        String token = JwtUtil.createJWTUseHmac(this.generateClaims(), this.signingKey);
        boolean result = JwtUtil.isVerify(token, this.signingKey);

        Assert.isTrue(result, "Invalid token");
    }

    private Map<String, Object> generateClaims() {
        Map<String, Object> claimMap = Maps.newHashMap();
        claimMap.put("userId", secureRandom.nextLong());
        claimMap.put(JwtConstants.EXPIRATION, System.currentTimeMillis() + secureRandom.nextInt());

        return claimMap;
    }
}