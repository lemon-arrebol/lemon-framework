package org.lemon.unit.generator.meta;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Set;

/**
 * @author lemon
 * @return
 * @description
 * @date 2019-12-23 15:26
 */
@Slf4j
public class VariableDescriptionCollector extends VoidVisitorAdapter<Set<String>> {
    /**
     * @param variableDeclarator
     * @param variableNameSet
     * @return void
     * @description 成员变量 + 方法局部变量
     * @author lemon
     * @date 2019-12-07 16:48
     */
    @Override
    public void visit(VariableDeclarator variableDeclarator, Set<String> variableNameSet) {
        if (variableDeclarator.getParentNode().isPresent() && !(variableDeclarator.getParentNode().get() instanceof FieldDeclaration)) {
            this.findeMethod(variableDeclarator.getParentNode());
        }

        log.debug("成员变量类型 " + variableDeclarator.getType() + ", 成员变量类型字符串 " + variableDeclarator.getTypeAsString() + ", 成员变量名 " + variableDeclarator.getNameAsString());

        super.visit(variableDeclarator, variableNameSet);
    }

    /**
     * @param parentNode
     * @return void
     * @description
     * @author lemon
     * @date 2019-12-07 16:56
     */
    private void findeMethod(Optional<Node> parentNode) {
        if (!parentNode.isPresent()) {
            return;
        }

        if (parentNode.get() instanceof MethodDeclaration) {
            MethodDeclaration methodDeclaration = (com.github.javaparser.ast.body.MethodDeclaration) parentNode.get();
            log.debug("成员变量所在的方法 " + methodDeclaration.getNameAsString());
            return;
        }

        this.findeMethod(parentNode.get().getParentNode());
    }
}