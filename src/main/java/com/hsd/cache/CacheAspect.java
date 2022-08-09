package com.hsd.cache;

import com.alibaba.fastjson.JSON;
import com.hsd.cache.annotation.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 缓存切面
 *
 * @author huangshengda
 * @date 2022-06-27 10:52
 */
@Slf4j
@Aspect
@Component
public class CacheAspect {

    @Resource
    private CacheService cacheService;

    @Around("@annotation(com.hsd.cache.annotation.Cacheable)")
    public Object cacheableExecutor(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret = getFromCache(joinPoint);
        if (ret == null) {
            ret = joinPoint.proceed();
            //todo 加入缓存
        }
        return ret;
    }

    @Around("@annotation(com.hsd.cache.annotation.CacheEvict)")
    public Object cacheEvictExecutor(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret = null;
        ret = joinPoint.proceed();
        return ret;
    }


    private Object getFromCache(ProceedingJoinPoint joinPoint) {
        Cacheable cacheable = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Cacheable.class);
        int keyIndex = cacheable.keyIndex();
        String key = null;
        Object[] args = joinPoint.getArgs();
        if (keyIndex < 0) {
            //log.error("","");
            return null;
        }
        if (args == null || args.length <= keyIndex || args[keyIndex] == null || !args[keyIndex].getClass().equals(String.class)) {
            return null;
        }

        key = String.valueOf(args[keyIndex]);
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        Class<?> returnType = ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();
        if (returnType.equals(Void.class)) {
            return null;
        }

        Object obj = cacheService.get(key);
        if (obj == null) {
            return null;
        }
        if (returnType.equals(String.class)) {
            return String.valueOf(obj);
        }
        return JSON.parseObject(String.valueOf(obj), returnType);
    }

    public static void main(String[] args) {
        String s = "134";
        System.out.println(JSON.parseObject(String.valueOf("12347"), Integer.class));
    }
}
