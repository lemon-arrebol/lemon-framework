package com.lemon.study.spi.customserviceloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.lemon.study.spi.serviceloader.service.LemonServiceLoader;

public class CustomServiceLoaderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void load() {
        CustomServiceLoader.load(LemonServiceLoader.class);
    }
}