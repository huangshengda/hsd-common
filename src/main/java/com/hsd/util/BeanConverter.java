package com.hsd.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public final class BeanConverter {

    public static <T> T convert(Object o, Class<T> tClass) {
        if (o == null) {
            return null;
        }
        try {
            T t = tClass.newInstance();
            BeanUtils.copyProperties(o, t);
            return t;
        } catch (Exception e) {
            String errorMsg = String.format("Converter convert Exception, o=%s, tClass=%s, %s", JSON.toJSONString(o), tClass.getName(), e.getMessage());
            log.error(errorMsg, e);
        }
        return null;
    }

    public static <T> T convert(Object o, Class<T> tClass, String... ignoreProperties) {
        if (o == null) {
            return null;
        }
        try {
            T t = tClass.newInstance();
            BeanUtils.copyProperties(o, t, ignoreProperties);
            return t;
        } catch (Exception e) {
            String errorMsg = String.format("Converter convert Exception, o=%s, tClass=%s, ignoreProperties=%s, %s", JSON.toJSONString(o), tClass.getName(), JSON.toJSONString(ignoreProperties), e.getMessage());
            log.error(errorMsg, e);
        }
        return null;
    }

}
