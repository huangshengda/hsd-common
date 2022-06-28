package com.hsd.log;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志切面
 *
 * @author huangshengda
 * @date 2022-06-28 15:27
 */
@Component
@Aspect
public class LogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Around("@annotation(log)")
    public Object commonExecutor(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        Class<?> clazz = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Object result = null;
        long start = System.currentTimeMillis();
        boolean exceptionFlag = Boolean.FALSE;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            exceptionFlag = Boolean.TRUE;
            throw e;
        } finally {
            LOGGER.info(getLogString(clazz.getSimpleName(), method.getName(), args, result, exceptionFlag, System.currentTimeMillis() - start));
        }
        return result;
    }

    private String getLogString(String className, String methodName, Object[] args, Object result, boolean ef, long costs) {
        try {
            return String.format("title: %s.%s, req: %s, resp: %s, e:%s, costs: %d", className, methodName, JSON.toJSONString(args), JSON.toJSONString(result), ef, costs);
        } catch (Exception e) {
            LOGGER.error("log json 转换异常", e);
            return null;
        }
    }

}
