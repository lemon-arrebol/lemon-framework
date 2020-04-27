package org.lemon.unit.generator.meta;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author lemon
 * @return
 * @description
 * @date 2019-12-23 15:26
 */
@Slf4j
public class PackageDescriptionCollector extends VoidVisitorAdapter<Set<String>> {
    @Override
    public void visit(PackageDeclaration packageDeclaration, Set<String> packageSet) {
        log.debug("package " + packageDeclaration.getName());

        super.visit(packageDeclaration, packageSet);
    }
}