package org.lemon.unit.generator;

import com.alibaba.fastjson.JSON;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.lemon.unit.generator.meta.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-12-06 13:58
 */
@Slf4j
public class UintGenerator {
    private static long count = 0;
    private static String javaPath;
    private static String classPath;
    private static String unitTestJavaRootPath;

    public static void main(String[] args) throws IOException {
        javaPath = System.getProperty("user.dir") + File.separator + "lemon-unit-test-generator/src/main/java";
        classPath = System.getProperty("user.dir") + File.separator + "lemon-unit-test-generator/target/classes";
        unitTestJavaRootPath = System.getProperty("user.dir") + File.separator + "lemon-unit-test-generator/src/unitTest";

        if (!Paths.get(unitTestJavaRootPath).toFile().exists()) {
            Paths.get(unitTestJavaRootPath).toFile().mkdirs();
        }

        log.debug(classPath);
        Files.list(Paths.get(classPath)).forEach((filePath) -> UintGenerator.listFile(filePath.toFile()));
        log.debug("Java文件数量" + count);
    }

    /**
     * @param classFile
     * @return void
     * @description 遍历指定目录下所有的java文件，包含子目录
     * @author lemon
     * @date 2019-12-06 14:36
     */
    private static void listFile(File classFile) {
        if (classFile.isDirectory()) {
            Stream.of(classFile.listFiles()).forEach(UintGenerator::listFile);
        } else if (classFile.getName().endsWith(".class")) {
            try {
                UintGenerator.generateUnitTest(classFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            count++;
        }
    }

    /**
     * @param file
     * @return java.lang.Class
     * @description 获取class
     * @author lemon
     * @date 2019-12-06 14:53
     */
    private static Class getClass(File file) {
        String fullClassName = file.getPath().replace(classPath, "").replaceAll("/", ".");
        fullClassName = fullClassName.substring(1, fullClassName.length() - 6);
        return UintGenerator.loadClass(fullClassName);
    }

    /**
     * @param fullClassName
     * @return java.lang.Class<?>
     * @description 加载类
     * @author lemon
     * @date 2019-12-06 15:10
     */
    private static Class<?> loadClass(String fullClassName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param file
     * @return void
     * @description 生成单元测试
     * @author lemon
     * @date 2019-12-06 15:12
     */
    private static void generateUnitTest(File file) throws Exception {
        String javaSourcePath = javaPath + File.separator + file.getPath().replace(classPath, "").replace(".class", ".java");
        log.debug("javaSourcePath " + javaSourcePath);
        // 将一个 java 代码的文本解析为一棵 CompilationUnit 类型的树
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(Paths.get(javaSourcePath).toFile());
        CompilationUnit compilationUnit = parseResult.getResult().get();
        Set<String> methodNameSet = Sets.newLinkedHashSet();
        PackageDescriptionCollector packageCollector = new PackageDescriptionCollector();
        packageCollector.visit(compilationUnit, methodNameSet);

        ImportDescriptionCollector importCollector = new ImportDescriptionCollector();
        importCollector.visit(compilationUnit, methodNameSet);

        FieldDescriptionCollector fieldCollector = new FieldDescriptionCollector();
        fieldCollector.visit(compilationUnit, methodNameSet);

        MethodDescriptionCollector methodCollector = new MethodDescriptionCollector();
        methodCollector.visit(compilationUnit, methodNameSet);

        MethodCallCollector methodCallCollector = new MethodCallCollector();
        methodCallCollector.visit(compilationUnit, methodNameSet);

        VariableDescriptionCollector variableCollector = new VariableDescriptionCollector();
        variableCollector.visit(compilationUnit, methodNameSet);
        log.debug(JSON.toJSONString(methodNameSet));

        Class clazz = UintGenerator.getClass(file);
        Set<String> importSet = Sets.newHashSet();
        List<Field> declaredFieldList = Lists.newArrayList();
        List<Method> methodList = Lists.newArrayList();

        if (ArrayUtils.isNotEmpty(clazz.getDeclaredFields())) {
            Stream.of(clazz.getDeclaredFields()).forEach((field) -> {
                declaredFieldList.add(field);

                if (!field.getType().isPrimitive() && !field.getType().getName().equals("java.lang.String")) {
                    importSet.add(field.getType().getName());
                }
            });
        }

        if (ArrayUtils.isNotEmpty(clazz.getMethods())) {
            Stream.of(clazz.getMethods()).forEach((method) -> {
                if (method.getName().equals("wait") || method.getName().equals("equals") || method.getName().equals("hashCode")
                        || method.getName().equals("toString") || method.getName().equals("notify") || method.getName().equals("notifyAll")
                        || method.getName().equals("getClass") || method.getName().equals("clone")) {
                    return;
                }

                methodList.add(method);
            });
        }

        // 创建freemarker.template.Configuration配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setTemplateLoader(new ClassTemplateLoader(UintGenerator.class, "/"));

        // 创建一个root hash对象，用来装载数据对象
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("package", clazz.getPackage().getName());
        dataMap.put("className", clazz.getSimpleName());
        dataMap.put("date", new SimpleDateFormat("yyyy-MM-dd HH:MM:ss,SSS").format(new Date()));
        dataMap.put("author", System.getProperties().getProperty("user.name"));
        dataMap.put("declaredFields", declaredFieldList);
        dataMap.put("methods", methodList);
        dataMap.put("imports", importSet);

        // 将数据对象装载入hash中
        // 获取模板
        Template temp = configuration.getTemplate("templates/unitTest.java.ftl");

        // 创建输出流，定义输出的文件
        String javaPath = unitTestJavaRootPath + File.separator + clazz.getPackage().getName().replaceAll("\\.", "/") + File.separator + clazz.getSimpleName() + ".java";

        File javaFile = Paths.get(javaPath).toFile();
        UintGenerator.createJavaFile(javaFile.getParentFile());
        javaFile.createNewFile();

        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Paths.get(javaPath).toFile())));
        // process方法将数据与模板进行绑定，输出到out输出流
        temp.process(dataMap, out);
    }

    /**
     * @param javaFile
     * @return void
     * @description 创建目录
     * @author lemon
     * @date 2019-12-06 19:44
     */
    private static void createJavaFile(File javaFile) throws IOException {
        if (!javaFile.getParentFile().exists()) {
            UintGenerator.createJavaFile(javaFile.getParentFile());
            return;
        }

        javaFile.mkdirs();
    }
}
