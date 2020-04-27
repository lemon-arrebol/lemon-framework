package org.lemon.unit.generator.meta;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author lemon
 * @return
 * @description
 * @date 2019-12-23 15:17
 */
@Slf4j
public class FieldDescriptionCollector extends VoidVisitorAdapter<Set<String>> {
    /**
     * @param fieldAccessExpr
     * @param fieldNameSet
     * @return void
     * @description FieldAccessExpr cacheRedis
     * <p>
     * FieldAccessExpr UTF_8
     * <p>
     * []
     * @author lemon
     * @date 2019-12-07 16:40
     */
    @Override
    public void visit(FieldAccessExpr fieldAccessExpr, Set<String> fieldNameSet) {
        log.debug("FieldAccessExpr " + fieldAccessExpr.getName());
        super.visit(fieldAccessExpr, fieldNameSet);
    }

    /**
     * @param fieldDeclaration
     * @param fieldNameSet
     * @return void
     * @description VariableDeclarator 类型 DynamicRedisProvider, 类型字符串 DynamicRedisProvider, 变量名 dynamicRedisProvider
     * <p>
     * VariableDeclarator 类型 StringRedisTemplate, 类型字符串 StringRedisTemplate, 变量名 cacheRedis
     * <p>
     * VariableDeclarator 类型 int, 类型字符串 int, 变量名 NO_PARAM_KEY
     * <p>
     * VariableDeclarator 类型 int, 类型字符串 int, 变量名 NULL_PARAM_KEY
     * @author lemon
     * @date 2019-12-07 16:40
     */
    @Override
    public void visit(FieldDeclaration fieldDeclaration, Set<String> fieldNameSet) {
        fieldDeclaration.getVariables().forEach((variableDeclarator) -> {
            log.debug("VariableDeclarator 类型 " + variableDeclarator.getType() + ", 类型字符串 " + variableDeclarator.getTypeAsString() + ", 变量名 " + variableDeclarator.getNameAsString());
        });

        super.visit(fieldDeclaration, fieldNameSet);
    }
}