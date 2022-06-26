package com.hsd.rpc.invoke;

import com.alibaba.fastjson.JSON;
import com.hsd.rpc.invoke.function.SFunction;
import com.hsd.rpc.invoke.support.FunctionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.function.Supplier;

public final class RpcInvokeUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(RpcInvokeUtil.class);

    private static final String MSG_1 = "call {} error";
    private static final String MSG_2 = "call {} request={}, response={}";

    public static <Req, Resp> Resp safeRetryInvoke(SFunction<Req, Resp> method, Req req, int retryTimes, Resp defaultResp) {
        return safeRetryInvoke(method, req, retryTimes, null, defaultResp);
    }

    public static <Req, Resp> Resp safeRetryInvoke(SFunction<Req, Resp> method, Req req, int retryTimes, Function<Exception, Boolean> retryJudge) {
        return safeRetryInvoke(method, req, retryTimes, retryJudge, null);
    }

    public static <Req, Resp> Resp safeRetryInvoke(SFunction<Req, Resp> method, Req req, int retryTimes) {
        return safeRetryInvoke(method, req, retryTimes, null, null);
    }

    /**
     * @param method      method
     * @param req         req
     * @param retryTimes  total invoke times = retryTimes
     * @param retryJudge  retryJudge
     * @param defaultResp Return default value in case of exception
     * @return resp
     */
    public static <Req, Resp> Resp safeRetryInvoke(SFunction<Req, Resp> method, Req req, int retryTimes, Function<Exception, Boolean> retryJudge, Resp defaultResp) {
        while (retryTimes-- >= 0) {
            try {
                return invoke(method, req);
            } catch (Exception e) {
                if (retryJudge == null) {
                    continue;
                }
                try {
                    if (!retryJudge.apply(e)) {
                        break;
                    }
                } catch (Exception e1) {
                    LOGGER.error("retryJudge exception", e);
                    break;
                }

            }
        }
        return defaultResp;
    }

    public static <Req, Resp> Resp safeInvoke(SFunction<Req, Resp> method, Req req) {
        return safeInvoke(method, req, null);
    }

    /**
     * @param method      method
     * @param req         req
     * @param defaultResp Return default value in case of exception
     * @return resp
     */
    public static <Req, Resp> Resp safeInvoke(SFunction<Req, Resp> method, Req req, Resp defaultResp) {
        String defaultLName = getDefaultLName(method);
        try {
            return invoke(method, req);
        } catch (Exception e) {
            LOGGER.error(defaultLName + "safeInvoke exception", e);
        }
        return defaultResp;
    }

    public static <Req, Resp> Resp invoke(SFunction<Req, Resp> method, Req req) {
        return invokeInternal(req, getDefaultLName(method), () -> method.apply(req));
    }

    private static <Resp, Req> Resp invokeInternal(Req req, String name, Supplier<Resp> supplier) {
        return doInvokeInternal(req, name, supplier);
    }

    private static <Resp, Req> Resp doInvokeInternal(Req req, String name, Supplier<Resp> supplier) {
        Resp resp = null;
        try {
            resp = supplier.get();
        } catch (Exception e) {
            LOGGER.error(MSG_1, name, e);
            throw e;
        } finally {
            LOGGER.info(MSG_2, name, toJSONString(req), toJSONString(resp));
        }
        return resp;
    }

    private static String getDefaultLName(SFunction<?, ?> method) {
        try {
            return FunctionSupport.getDefaultLName(method);
        } catch (Exception e) {
            LOGGER.warn("getDefaultLName exception", e);
        }
        return "defaultName";
    }

    private static String toJSONString(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            LOGGER.warn("toJSONString exception", e);
        }
        return "";
    }

}
