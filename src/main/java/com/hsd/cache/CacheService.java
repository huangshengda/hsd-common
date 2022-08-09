package com.hsd.cache;


import java.util.concurrent.TimeUnit;

/**
 * 缓存操作
 *
 * @author huangshengda
 * @date 2022-07-26 14:56
 */
public interface CacheService {

    /**
     * 获取
     *
     * @param key key
     * @return obj
     */
    Object get(String key);

    /**
     * 缓存数据
     *
     * @param key        key
     * @param object     object
     * @param expireTime 过期时间
     * @param timeUnit   过期时间单位
     * @param sync       是否同步设置到缓存
     */
    void put(String key, Object object, int expireTime, TimeUnit timeUnit, boolean sync);

    /**
     * 删除缓存
     *
     * @param key  key
     * @param sync 是否同步删除缓存
     */
    void del(String key, boolean sync);

}
