package com.hsd.rpc.invoke;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-25 16:22
 */
public class PersonService {

    public String getName(String id) {
        return "jack-" + id;
    }

    public String getNameWithException(String id) throws Exception {
        throw new Exception("my Exception");
    }

    public String getNameWithRuntimeException(String id) {
        throw new RuntimeException("my RuntimeException");
    }

    public String getNameWithMyRuntimeException(String id){
        throw new MyRuntimeException("my MyRuntimeException");
    }

    public boolean retryJudge(Exception e){
        if(e.getMessage().equals("my MyRuntimeException")){
            return false;
        }
        return true;
    }

}
