package com.lemon.common.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DigestUtilTest {
    String data = "hjt love lr";
    String algorithm = "SHA";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void digest() {
        String digest = DigestUtil.digest(this.algorithm, this.data);
        System.out.println(digest);
    }

    @Test
    public void digest1() {
    }

    @Test
    public void digest2() {
    }

    @Test
    public void verify() {
        String digest = DigestUtil.digest(this.algorithm, this.data);
        System.out.println(digest);
        boolean result = DigestUtil.verify(this.algorithm, digest, this.data, SaltUtil.DEFAULT_SALT_SIZE, DigestUtil.DEFAULT_ITERATIONS_SIZE);

        Assert.assertTrue(result);
    }
}