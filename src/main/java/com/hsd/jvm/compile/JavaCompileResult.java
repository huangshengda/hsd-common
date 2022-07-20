package com.hsd.jvm.compile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2020/9/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JavaCompileResult {

    private ClassLoader classLoader;

    private Class<?> loadedClass;

    private String className;

    private byte[] classBytes;

}
