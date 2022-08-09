package com.hsd.cache;

import com.alibaba.fastjson.JSON;
import com.hsd.ApplicationMain;
import com.hsd.cache.demo.CacheDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-07-26 15:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationMain.class})
public class CacheDemoTest {

    @Resource
    private CacheDemo cacheDemo;

    @Test
    public void test(){
        String ret = cacheDemo.get("hsd");
        System.out.println(ret);
    }

    @Test
    public void testList(){
        //List<String> listString = cacheDemo.getListString(null,"list");
        List<CacheDemo.Person> cacheDemo = this.cacheDemo.getListPerson("getListPerson");
        CacheDemo.Person person = cacheDemo.get(0);
        System.out.println(person.getAge());
        System.out.println(JSON.toJSONString(cacheDemo));
    }

    public static void main(String[] args) {
        String s = "[{name:'jj',age:3,addresses:[{add:'sh',phone:123}]},{name:'cc',age:4}]";
        List<CacheDemo.Person> personList = JSON.parseArray(s, CacheDemo.Person.class);
        System.out.println(personList.get(0).getAddresses().get(0).getAdd());
    }

}
