package com.hsd.jvm.compile;

import com.google.common.collect.Maps;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;
import java.util.Map;

/**
 * @date 2020/9/21
 */
public class MemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final Map<String, MemoryJavaFileObject> javaFileObjectMap = Maps.newHashMap();

    protected MemoryFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        MemoryJavaFileObject javaFileObject = new MemoryJavaFileObject(className, kind);
        javaFileObjectMap.put(className, javaFileObject);
        return javaFileObject;
    }

    public MemoryJavaFileObject findJavaFileObject(String className) {
        return javaFileObjectMap.get(className);
    }

}
