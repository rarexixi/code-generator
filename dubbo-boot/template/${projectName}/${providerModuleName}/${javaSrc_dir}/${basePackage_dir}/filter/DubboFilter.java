package ${basePackage}.filter;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.utils.AnnotationUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.apache.commons.lang3.time.StopWatch;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

<#include "/include/java_copyright.ftl">
@Activate(group = Constants.PROVIDER)
public class DubboFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DubboFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {

        // 如果是dubbo自己的服务，直接返回
        if (GenericService.class == invoker.getInterface()) {
            return invoker.invoke(invocation);
        }

        String method = "";
        String sessionId = "";

        try {
            method = RpcContext.getContext().getRemoteHost() + "-" + invoker.getInterface().getName() + "." + invocation.getMethodName();
            sessionId = getSessionId(invoker, invocation);

            logger.info(method, sessionId, "Dubbo 服务开始执行", invocation.getArguments());

            // StopWatch 计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Result result = invoker.invoke(invocation);
            stopWatch.stop();

            if (!result.hasException()) {
                if (logger.isInfoEnabled()) {
                    Map<String, Object> args = ImmutableMap.<String, Object>builder()
                            .put("开始执行时间", stopWatch.getStartTime())
                            .put("开始执行时间（用来展示）", new Date(stopWatch.getStartTime()).toString())
                            .put("入参", invocation.getArguments())
                            .put("出参", result.getValue())
                            .put("方法执行时长(ms)", stopWatch.getTime())
                            .build();
                    logger.info(method, sessionId, "Dubbo 服务执行结束", args);
                }
                return result;
            }
            logger.error(method, sessionId, "服务出现异常", result.getException());
        } catch (RuntimeException e) {
            logger.error(method, sessionId, e);
        }
        return new RpcResult(new org.xi.common.model.Result<>(OperationConstants.SYSTEM_ERROR));
    }

    private String getSessionId(Invoker<?> invoker, Invocation invocation) {

        Object sessionId = null;
        try {
            sessionId = AnnotationUtils.getParam(invoker.getInterface(), invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments(), "sessionId");
        } catch (NoSuchMethodException e) {
            logger.error("getSessionId", "获取 Session ID 异常", e);
        }

        if (null == sessionId) {
            sessionId = UUID.randomUUID();
        }
        return sessionId.toString();
    }
}
