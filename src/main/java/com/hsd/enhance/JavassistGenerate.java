package com.hsd.enhance;

import javassist.*;

import java.io.IOException;

public class JavassistGenerate {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("com.hsd.enhance.asm.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
        cc.writeFile("/Users/shengda.huang1/Downloads/temp/");
        Base h = (Base)c.newInstance();
        h.process();
    }
}
