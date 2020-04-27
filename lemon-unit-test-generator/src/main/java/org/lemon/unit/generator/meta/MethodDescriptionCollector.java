package org.lemon.unit.generator.meta;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author lemon
 * @return
 * @description
 * @date 2019-12-23 15:25
 */
@Slf4j
public class MethodDescriptionCollector extends VoidVisitorAdapter<Set<String>> {
    @Override
    public void visit(MethodDeclaration methodDeclaration, Set<String> methodNameSet) {
        methodDeclaration.getModifiers().forEach((modifier) -> {
            log.debug(modifier.getKeyword().asString() + " ");
        });

        log.debug("方法名称 " + methodDeclaration.getNameAsString());
        log.debug("返回值类型 " + methodDeclaration.getType() + ", 返回值类型字符串 " + methodDeclaration.getTypeAsString());

        NodeList<Parameter> parameters = methodDeclaration.getParameters();

        for (Parameter parameter : parameters) {
            methodNameSet.add(String.valueOf(parameter.getName()));
            log.debug("参数类型 " + parameter.getType() + ", 参数类型字符串 " + parameter.getTypeAsString() + ", 参数名 " + parameter.getNameAsString());
        }

        super.visit(methodDeclaration, methodNameSet);
    }
}