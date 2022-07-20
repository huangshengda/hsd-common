package com.hsd.jvm.compile;

import java.net.URL;
import java.net.URLClassLoader;

public class ByteArrayClassLoader extends URLClassLoader {

    private final byte[] classBytes;

    public ByteArrayClassLoader(byte[] classBytes) {
        super(new URL[0], Thread.currentThread().getContextClassLoader());
        this.classBytes = classBytes;
    }

    @Override
    protected Class<?> findClass(String name) {
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

}
