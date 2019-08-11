package com.lemon.common.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isBeforeOfToday() {
        System.out.println(TimeUtil.isBeforeOfCurrent("2019-06-22 10:17:33", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(TimeUtil.isBeforeOfCurrentByMilli(6000));
        System.out.println(666);
    }
}