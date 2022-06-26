package com.hsd.rpc.invoke;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-25 13:53
 */
public class RpcRetryUtilTest {

    public static void main(String[] args) {
        PersonService personService = new PersonService();
        String s = RpcInvokeUtil.safeRetryInvoke(personService::getNameWithMyRuntimeException, "0", 3,personService::retryJudge,null);
        System.out.println(s);
        System.out.println(RpcInvokeUtil.safeInvoke(personService::getName,"1"));
        System.out.println(RpcInvokeUtil.safeInvoke(personService::getNameWithMyRuntimeException,"2","tom"));
        System.out.println(RpcInvokeUtil.invoke(personService::getName,"3"));
        String invoke = RpcInvokeUtil.invoke(personService::getNameWithMyRuntimeException, "2");


    }

}
