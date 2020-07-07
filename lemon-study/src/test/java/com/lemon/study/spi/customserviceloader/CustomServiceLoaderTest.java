package com.lemon.study.spi.customserviceloader;

import com.lemon.study.spi.serviceloader.service.LemonServiceLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomServiceLoaderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void load() {
        List<LemonServiceLoader> lemonServiceLoaders = CustomServiceLoader.load(LemonServiceLoader.class);

        lemonServiceLoaders.forEach((lemonServiceLoader) -> System.out.println("====== " + lemonServiceLoader));
    }
}