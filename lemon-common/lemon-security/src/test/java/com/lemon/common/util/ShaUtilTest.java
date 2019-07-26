package com.lemon.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShaUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generateSalt() {
        byte[] salt = SaltUtil.generateSalt();
        System.out.println(CodecUtil.encodeHex(salt));
        System.out.println(CodecUtil.encodeUtf8(salt));
        System.out.println(CodecUtil.encodeBase64(salt));
    }

    @Test
    public void generateSalt1() {
    }

    @Test
    public void generateSalt2() {
    }
}