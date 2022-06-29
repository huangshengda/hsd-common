package com.hsd.trace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 方法调用链路
 *
 * @author huangshengda
 * @date 2022-06-29 14:12
 */
@Aspect
@Component
public class MethodTraceAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<MethodTraceBody> threadLocal = ThreadLocal.withInitial(MethodTraceBody::new);

    @Around("execution(* com.hsd..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> clazz = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object result;
        enter(clazz.getName(), method.getName());
        try {
            result = joinPoint.proceed();
        } finally {
            exit();
        }
        return result;
    }

    private void enter(String className, String methodName) {
        try {
            MethodTraceBody methodTraceBody = threadLocal.get();
            methodTraceBody.setLevel(methodTraceBody.getLevel() + 1);
            methodTraceBody.setCount(methodTraceBody.getCount() + 1);
            threadLocal.get().getMethodBodyList().add(MethodBody.builder().sort(methodTraceBody.getCount()).level(methodTraceBody.getLevel()).className(className).methodName(methodName).build());
        } catch (Exception e) {
            LOGGER.error("enter method error", e);
        }
    }

    private void exit() {
        try {
            MethodTraceBody methodTraceBody = threadLocal.get();
            methodTraceBody.setCount(methodTraceBody.getCount() - 1);
            methodTraceBody.setLevel(methodTraceBody.getLevel() - 1);
            if (threadLocal.get().getCount() <= 0) {
                log(methodTraceBody, Thread.currentThread().getName());
                threadLocal.remove();
            }
        } catch (Exception e) {
            LOGGER.error("exit method error", e);
        }
    }

    private void log(MethodTraceBody methodTraceBody, String threadName) {
        StringBuilder s = new StringBuilder("\n" + threadName + "\n");
        for (MethodBody item : methodTraceBody.getMethodBodyList()) {
            StringBuilder indentSpace = new StringBuilder();
            for (int i = 1; i < item.getLevel(); i++) {
                indentSpace.append("++");
            }
            s.append(indentSpace.toString()).append(item.getClassName()).append(".").append(item.getMethodName()).append("\n");
        }
        LOGGER.info(s.toString());
    }

}
