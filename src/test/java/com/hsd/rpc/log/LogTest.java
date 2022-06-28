package com.hsd.rpc.log;

import com.hsd.ApplicationMain;
import com.hsd.log.mock.LogMockTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-28 16:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationMain.class})
public class LogTest {

    @Resource
    private LogMockTest logMockTest;

    @Test
    public void f(){
        logMockTest.f();
        logMockTest.f("11");
        logMockTest.f1();
        System.out.println("finish");
    }

}
