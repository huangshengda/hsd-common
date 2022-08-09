package com.hsd.cache.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hsd.cache.CacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-07-26 15:00
 */
@Component
public class LocalCacheServiceImpl implements CacheService {


    @Override
    @Cacheable
    public Object get(String key) {
        if ("list".equals(key)) {
            return JSON.toJSONString(Lists.newArrayList("567", "678"));
        } else if ("getListPerson".equals(key)) {
            return "[{name:'jj',age:3},{name:'cc',age:4}]";
        }
        return "from cache 123";
    }

    @Override
    public void put(String key, Object object, int expireTime, TimeUnit timeUnit, boolean sync) {
        System.out.printf("put cache key=%s, val=%s, expireTime=%d, timeUnit = %s%n", key, JSON.toJSONString(object), expireTime, timeUnit.toString());
    }

    @Override
    public void del(String key, boolean sync) {
        System.out.println("del local cache, key = " + key);
    }
}
