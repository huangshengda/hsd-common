package com.hsd.cache.demo;

import com.google.common.collect.Lists;
import com.hsd.cache.annotation.CacheEvict;
import com.hsd.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-07-26 14:30
 */
@Component
public class CacheDemo {

    @Cacheable(expireTimeSec = 10, keyIndex = 1)
    public List<String> getListString(String s, String key) {
        return Lists.newArrayList("444");
    }

    @Cacheable(expireTimeSec = 5)
    public String get(String nameKey) {
        doGet();
        return "123";
    }

    private String doGet() {
        return "456";
    }

    @CacheEvict(key = "nameKey")
    public void add(String nameKey) {
        System.out.println("添加");
    }

    @Cacheable
    public List<Person> getListPerson(String key) {
        return Lists.newArrayList(new Person("jack", 1, Lists.newArrayList(new Address("上海", 177L))));
    }

    @Data
    @AllArgsConstructor
    public static class Person {

        private String name;
        private int age;
        private List<Address> addresses;

    }

    @Data
    @AllArgsConstructor
    public static class Address {
        private String add;
        private long phone;
    }

}
