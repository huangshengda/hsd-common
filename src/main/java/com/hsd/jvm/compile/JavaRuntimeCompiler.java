package com.hsd.jvm.compile;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.List;

/**
 * Java运行时编译器
 *
 * @date 2020/9/21
 */
@Slf4j
public class JavaRuntimeCompiler {

    private static final JavaRuntimeCompiler INSTANCE = new JavaRuntimeCompiler();

    public static JavaRuntimeCompiler getInstance() {
        return INSTANCE;
    }

    public JavaCompileResult compileAndLoadClass(String code) throws Exception {
        return compileAndLoadClass(code, null);
    }

    public JavaCompileResult compileAndLoadClass(String code, String classpath) throws Exception {
        JavaParseResult parseResult = JavaCodeParser.parse(code);
        return compileAndLoadClass(parseResult.getClassName(), code, classpath);
    }

    private JavaCompileResult compileAndLoadClass(String className, String code, String classpath) throws Exception {
        MemoryFileManager fileManager = compile(className, code, classpath);
        MemoryClassLoader memoryClassLoader = new MemoryClassLoader(Thread.currentThread().getContextClassLoader(), fileManager);
        Class<?> loadedClass = memoryClassLoader.loadClass(className);
        MemoryJavaFileObject memoryJavaFileObject = fileManager.findJavaFileObject(className);
        return new JavaCompileResult(memoryClassLoader, loadedClass, className, memoryJavaFileObject.getClassBytes());
    }

    public byte[] compile(String code) {
        JavaParseResult parseResult = JavaCodeParser.parse(code);
        MemoryFileManager fileManager = compile(parseResult.getClassName(), code, null);
        MemoryJavaFileObject memoryJavaFileObject = fileManager.findJavaFileObject(parseResult.getClassName());
        return memoryJavaFileObject.getClassBytes();
    }

    private MemoryFileManager compile(String className, String code, String classpath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostic = new DiagnosticCollector<>();
        MemoryFileManager fileManager = new MemoryFileManager(compiler.getStandardFileManager(diagnostic, null, null));
        List<String> options = Lists.newArrayList(
                "-encoding", "UTF-8"
        );
        if (StringUtils.isNotBlank(classpath)) {
            options.add("-classpath");
            options.add(classpath);
        }

        List<JavaFileObject> compileJavaFileObjectList = Lists.newArrayList(new CharSequenceJavaFileObject(className, code));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostic, options, null, compileJavaFileObjectList);
        boolean compileResult = task.call();
        if (!compileResult) {
            throw new RuntimeException(String.format("%s compile error code=%s", getClass().getSimpleName(), code));
        }
        return fileManager;
    }

}
