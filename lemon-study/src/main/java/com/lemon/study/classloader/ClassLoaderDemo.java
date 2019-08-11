package com.lemon.study.classloader;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ServiceLoader;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-03 21:29
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
        DocumentBuilderFactory aa;
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println();
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println();
        System.out.println(System.getProperty("java.class.path"));
        System.out.println();

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();

        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }

        // 主类加载器
        System.out.println("主类加载器: " + ClassLoaderDemo.class.getClassLoader());
        // 主类加载器的父类加载器
        System.out.println("主类加载器的父类加载器: " + ClassLoaderDemo.class.getClassLoader().getParent());
        // 主类加载器的父类加载器的父加载器
        System.out.println("主类加载器的父类加载器的父加载器: " + ClassLoaderDemo.class.getClassLoader().getParent().getParent());

        System.out.println();
        System.out.println("current thread contextloader:" + Thread.currentThread().getContextClassLoader());
        System.out.println("current loader:" + ClassLoaderDemo.class.getClassLoader());
        System.out.println("ServiceLoader loader:" + ServiceLoader.class.getClassLoader());
    }
}
