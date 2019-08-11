package com.lemon.uuid.generator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnowflakeIdWorkerGeneratorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void nextId() {
        SnowflakeIdWorkerGenerator idWorker = SnowflakeIdWorkerGenerator.getInstance();
        System.out.println(System.currentTimeMillis());

        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}