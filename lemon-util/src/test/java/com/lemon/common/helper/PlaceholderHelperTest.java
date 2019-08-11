package com.lemon.common.helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class PlaceholderHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Extract keys from placeholder, e.g.
     * <ul>
     * <li>${some.key} => "some.key"</li>
     * <li>${some.key:${some.other.key:100}} => "some.key", "some.other.key"</li>
     * <li>${${some.key}} => "some.key"</li>
     * <li>${${some.key:other.key}} => "some.key"</li>
     * <li>${${some.key}:${another.key}} => "some.key", "another.key"</li>
     * <li>#{new java.text.SimpleDateFormat('${some.key}').parse('${another.key}')} => "some.key", "another.key"</li>
     *
     * <li>${some.key:${some.other.key:${some.key}}} => "some.key", "some.other.key"</li> 循环引用
     * </ul>
     */
    @Test
    public void extractPlaceholderKeys() {
        PlaceholderHelper placeholderHelper = new PlaceholderHelper();

        String placeHolder = "${some.key}";
        Set<String> result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();

        placeHolder = "${some.key:${some.other.key:100}}";
        result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();

        placeHolder = "${${some.key}}";
        result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();

        placeHolder = "${${some.key:other.key}}";
        result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();

        placeHolder = "${${some.key}:${another.key}}";
        result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();

        placeHolder = "#{new java.text.SimpleDateFormat('${some.key}').parse('${another.key}')}";
        result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();

        // 循环引用
        placeHolder = "${some.key:${some.other.key:${some.key}}}";
        result = placeholderHelper.extractPlaceholderKeys(placeHolder);
        System.out.println(placeHolder + " => " + result);
        System.out.println();
    }
}