package com.hsd.rpc.invoke.support;

import com.hsd.rpc.invoke.function.SFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * FunctionSupport
 *
 * @author huangshengda
 * @date 2022-06-25 14:53
 */
public class FunctionSupport {

    private final static Logger LOGGER = LoggerFactory.getLogger(FunctionSupport.class);

    public static String getDefaultLName(SFunction<?, ?> function) {
        SerializedLambda serializedLambda = getSerializedLambda(function);
        return serializedLambda.getImplClass().replaceAll("/", ".") + "." + serializedLambda.getImplMethodName();
    }

    private static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }

}
