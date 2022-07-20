package com.hsd.jvm.compile;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.google.common.collect.Sets;
import com.hsd.util.LambdaUtil;

import java.util.Set;

/**
 * @author huangshengda
 */
public class JavaCodeParser {

    public static JavaParseResult parse(String code) {
        ParseResult<CompilationUnit> parseResult = new JavaParser().parse(code);
        if (!parseResult.isSuccessful()) {
            // java代码解析失败
            String errorMsg = parseResult.getProblem(0).toString();
            throw new RuntimeException(String.format("Java脚本代码语法错误,errorMsg=%s", errorMsg));
        }

        if (!parseResult.getResult().isPresent()) {
            throw new RuntimeException("Java脚本代码语法错误");
        }
        CompilationUnit compilationUnit = parseResult.getResult().get();

        // 包名
        String packageName = "";
        if (compilationUnit.getPackageDeclaration().isPresent()) {
            packageName = compilationUnit.getPackageDeclaration().get().getNameAsString();
        }

        // 查找public类
        TypeDeclaration<?> publicClassDeclaration = findPublicClassDeclaration(compilationUnit.getTypes());
        if (publicClassDeclaration == null) {
            throw new RuntimeException(String.format("Java脚本代码错误 errorMsg=%s", "无public的类"));
        }
        return new JavaParseResult(packageName, publicClassDeclaration.getNameAsString());
    }

    public static TypeDeclaration<?> findPublicClassDeclaration(NodeList<TypeDeclaration<?>> typeDeclarations) {
        for (TypeDeclaration<?> typeDeclaration : typeDeclarations) {
            if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;
                if (classOrInterfaceDeclaration.isInterface()) {
                    continue;
                }

                // public类 非abstract
                Set<String> modifierSet = Sets.newHashSet(LambdaUtil.map(typeDeclaration.getModifiers(), m -> m.getKeyword().asString()));
                if (!(modifierSet.contains(Modifier.Keyword.PUBLIC.asString())
                        && !modifierSet.contains(Modifier.Keyword.ABSTRACT.asString()))) {
                    continue;
                }

                return typeDeclaration;
            }
        }
        return null;
    }

}
