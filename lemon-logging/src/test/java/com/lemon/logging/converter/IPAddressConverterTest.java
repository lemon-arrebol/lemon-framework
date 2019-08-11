package com.lemon.logging.converter;

import org.junit.Test;

import static org.junit.Assert.*;

public class IPAddressConverterTest {

    @Test
    public void convert() {
        IPAddressConverter ipAddressConverter = new IPAddressConverter();
        System.out.println(ipAddressConverter.convert(null));
    }
}