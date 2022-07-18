package com.hsd.rpc.dubbo.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
// import com.alibaba.fastjson.JSON;
// import com.yupaopao.platform.common.dto.Response;
// import com.yupaopao.platform.order.log.api.enums.EnvEnum;
// import com.yupaopao.platform.order.log.api.enums.rpc.RpcProtocolEnum;
// import com.yupaopao.platform.order.log.api.enums.rpc.RpcTypeEnum;
// import com.yupaopao.platform.order.log.api.request.SaveOrderRpcLogRequest;
// import com.yupaopao.platform.order.log.api.service.OrderRpcLogService;
// import com.yupaopao.tracing.TracerContext;
// import com.yupaopao.tracing.constant.TracerConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huangshengda
 * @date 2021-05-14 14:27
 */
@Slf4j
@Activate(group = {"provider"})
public class ResponseProviderFilter implements Filter {

    @SuppressWarnings({"rawtypes", "DuplicatedCode"})
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (invocation.getMethodName().startsWith("$")) {
            return invoker.invoke(invocation);
        } else {
            Result result = invoker.invoke(invocation);
            Object value = result.getValue();
            // if (value instanceof Response) {
            //     try {
            //         Response response = (Response) value;
            //         saveRpcLog(invoker, invocation, response);
            //     } catch (Throwable t) {
            //         log.error("saveRpcLog error", t);
            //     }
            // }
            return result;
        }
    }

  //  @SuppressWarnings({"rawtypes", "DuplicatedCode"})
    //private void saveRpcLog(Invoker<?> invoker, Invocation invocation, Response response) {
        //防止循环调用
        // if (OrderRpcLogService.class.getName().equals(invoker.getInterface().getName())) {
        //     return;
        // }
        // //过滤正确的请求
        // if (response.isSuccess()) {
        //     return;
        // }
        // SaveOrderRpcLogRequest request = new SaveOrderRpcLogRequest();
        // request.setApplication(invoker.getUrl().getParameter("application"));
        // request.setProtocol(RpcProtocolEnum.DUBBO.getCode());
        // request.setType(RpcTypeEnum.PROVIDER.getCode());
        // request.setEnv(EnvEnum.getByDesc(invocation.getAttachments().get("version")) == null ? EnvEnum.PROD.getCode() : EnvEnum.getByDesc(invocation.getAttachments().get("version")).getCode());
        // request.setService(invoker.getInterface().getName());
        // request.setMethod(invocation.getMethodName());
        // request.setCode(response.getCode());
        // request.setMsg(response.getMsg());
        // request.setRequest(JSON.toJSONString(invocation.getArguments()));
        // request.setResponse(JSON.toJSONString(response));
        // request.setTid(TracerContext.getContext().get(TracerConstant.TRACER_TRACE_ID));
        // OrderRpcLogFactory.rpcSave(request);
   // }

}