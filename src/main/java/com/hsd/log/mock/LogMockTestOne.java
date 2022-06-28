package com.hsd.log.mock;

import com.hsd.log.Log;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-28 16:47
 */
@Log
@Component
public class LogMockTestOne {

    @Resource
    private InnerClass innerClass;

    public void tt(){
        innerClass.innerMethod();
    }

    @Log
    @Component
    public static class InnerClass{
        public void innerMethod(){

        }

    }

}
