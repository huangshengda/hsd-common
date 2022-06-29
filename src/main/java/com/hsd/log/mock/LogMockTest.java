package com.hsd.log.mock;

import com.hsd.log.Log;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-28 16:14
 */
//@Log
@Component
public class LogMockTest {

    @Resource
    private LogMockTestOne logMockTestOne;
    @Resource
    private LogMockTest logMockTest;

    // @Log
    public String f() {
        //f();
        logMockTestOne.tt();
        logMockTest.f("123");
        return "no arg, has result";
    }

    @Log
    public String f(String name) {
        inner();
        return "has arg, has result";
    }

    public void f1(){
        //throw new RuntimeException("1");
    }

    private String inner(){
        return "inner no arg, no result";
    }

}
