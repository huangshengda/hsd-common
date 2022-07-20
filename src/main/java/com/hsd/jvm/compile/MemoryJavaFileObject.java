package com.hsd.jvm.compile;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @date 2020/9/21
 */
public class MemoryJavaFileObject extends SimpleJavaFileObject {

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    protected MemoryJavaFileObject(String className, Kind kind) {
        super(URI.create("string://" + className.replace('.', '/') + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() {
        return baos;
    }

    public byte[] getClassBytes() {
        return baos.toByteArray();
    }

}
