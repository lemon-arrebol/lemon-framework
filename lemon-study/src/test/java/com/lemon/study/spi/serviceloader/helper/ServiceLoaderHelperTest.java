package com.lemon.study.spi.serviceloader.helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.lemon.study.spi.serviceloader.service.LemonServiceLoader;

import java.util.Iterator;

public class ServiceLoaderHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void iterator() {
        Iterator<LemonServiceLoader> animalIterator = ServiceLoaderHelper.iterator(LemonServiceLoader.class);

        while (animalIterator.hasNext()) {
            LemonServiceLoader lemonServiceLoader = animalIterator.next();
            System.out.println("iterator======" + lemonServiceLoader);
        }
    }

    @Test
    public void loadAll() {
        ServiceLoaderHelper.loadAll(LemonServiceLoader.class).forEach((lemonServiceLoader) -> {
            System.out.println("loadAll======" + lemonServiceLoader);
        });
    }

    @Test
    public void loadAllOrdered() {
    }

    @Test
    public void loadFirst() {
        System.out.println("loadFirst======" + ServiceLoaderHelper.loadFirst(LemonServiceLoader.class));
    }

    @Test
    public void loadLast() {
        System.out.println("loadLast======" + ServiceLoaderHelper.loadLast(LemonServiceLoader.class));
    }
}