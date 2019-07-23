package com.lemon.agent.metric;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BytebuddyMetricAgentTest {

    @Test
    public void premain() {
        boolean is = true;

        while (is) {
            List<String> list = new ArrayList<String>();
            list.add("hello world");
        }
    }
}