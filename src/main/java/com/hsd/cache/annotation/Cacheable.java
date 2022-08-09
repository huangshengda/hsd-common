package com.hsd.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法缓存
 *
 * @author huangshengda
 * @date 2022-06-26 14:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    /**
     * 缓存中key
     */
    int keyIndex() default 0;

    /**
     * 过期时间 单位秒
     */
    int expireTimeSec() default 5;

    /**
     * 同步加入缓存
     */
    boolean syncAddCache() default false;

}
