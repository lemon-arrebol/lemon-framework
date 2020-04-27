package org.lemon.unit.generator.meta;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author lemon
 * @return
 * @description
 * @date 2019-12-23 15:20
 */
@Slf4j
public class ImportDescriptionCollector extends VoidVisitorAdapter<Set<String>> {
    @Override
    public void visit(ImportDeclaration importDeclaration, Set<String> importNameSet) {
        importDeclaration.getParentNode().get().getChildNodes().stream().filter((node) -> node instanceof ImportDeclaration).forEach((node) -> {
            ImportDeclaration importDeclarationNode = (ImportDeclaration) node;
            log.debug("import " + importDeclarationNode.getName());
        });

        super.visit(importDeclaration, importNameSet);
    }
}