package com.hsd.jvm.compile;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @date 2020/9/21
 */
public class MemoryClassLoader extends URLClassLoader {

    private final MemoryFileManager classFileManager;

    public MemoryClassLoader(ClassLoader parent, MemoryFileManager classFileManager) {
        super(new URL[0], parent);
        this.classFileManager = classFileManager;
    }

    @Override
    protected Class<?> findClass(String name) {
        MemoryJavaFileObject memoryJavaFileObject = classFileManager.findJavaFileObject(name);
        if (memoryJavaFileObject == null) {
            throw new RuntimeException(String.format("MemoryClassLoader.findClass error class not found name=%s", name));
        }
        byte[] classBytes = memoryJavaFileObject.getClassBytes();
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

}
