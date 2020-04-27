package org.lemon.unit.generator.meta;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * @author lemon
 * @return
 * @description
 * @date 2019-12-23 15:20
 */
@Slf4j
public class MethodCallCollector extends VoidVisitorAdapter<Set<String>> {
    @Override
    public void visit(MethodCallExpr methodCallExpr, Set<String> methodNameSet) {
        log.debug("MethodCallCollector " + methodCallExpr.getTokenRange().get().toString());
        log.debug("调用方法名称 " + methodCallExpr.getNameAsString());

        NodeList<Expression> parameters = methodCallExpr.getArguments();
        this.printParameter(parameters);

        if (methodCallExpr.getScope().isPresent()) {
            this.recursive(methodCallExpr.getScope().get());
        }

        super.visit(methodCallExpr, methodNameSet);
    }

    /**
     * @param expression
     * @return void
     * @description 递归查找前方法调用者
     * @author lemon
     * @date 2019-12-07 17:23
     */
    private void recursive(Expression expression) {
        if (expression == null) {
            return;
        }

        if (expression instanceof NameExpr) {
            NameExpr nameExpr = (NameExpr) expression;
            log.debug("实例 " + nameExpr.getNameAsString());
            return;
        }

        if (expression instanceof MethodCallExpr) {
            MethodCallExpr methodCallExpr = (MethodCallExpr) expression;
            log.debug("调用者方法名称 " + methodCallExpr.getNameAsString());
            NodeList<Expression> parameters = methodCallExpr.getArguments();
            this.printParameter(parameters);

            if (methodCallExpr.getScope().isPresent()) {
                this.recursive(((MethodCallExpr) expression).getScope().get());
            }
        }
    }

    /**
     * @param parameters
     * @return void
     * @description 打印调用方法参数
     * @author lemon
     * @date 2019-12-07 17:23
     */
    private void printParameter(NodeList<Expression> parameters) {
        if (CollectionUtils.isEmpty(parameters)) {
            log.debug(", 无方法参数");
            return;
        }

        for (Expression expression : parameters) {
            log.debug(", 调用方法参数类型 ");

            if (expression.isStringLiteralExpr()) {
                log.debug(" String");
            } else if (expression instanceof BooleanLiteralExpr) {
                log.debug("  Boolean");
            } else if (expression instanceof NameExpr) {
                log.debug(" 方法局部变量 " + ((NameExpr) expression).getNameAsString());
            } else if (expression instanceof FieldAccessExpr) {
                log.debug(" FieldAccessExpr " + ((FieldAccessExpr) expression).getName());
            } else if (expression instanceof LambdaExpr) {
                log.debug(" LambdaExpr ");
            }

            log.debug(", 调用方法参数值 " + expression.toString());
            log.debug(", Arguments class " + expression.getClass());
        }
    }
}