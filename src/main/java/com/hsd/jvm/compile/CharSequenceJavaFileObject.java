package com.hsd.jvm.compile;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    private final CharSequence code;

    protected CharSequenceJavaFileObject(String className, CharSequence code) {
        super(URI.create("string://" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }

}
