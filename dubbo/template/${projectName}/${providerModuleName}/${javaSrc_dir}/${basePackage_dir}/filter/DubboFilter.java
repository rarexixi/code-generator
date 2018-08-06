package ${basePackage}.filter;

import ${baseCommonPackage}.annotation.ParamName;
import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.utils.AnnotationUtils;
import ${baseCommonPackage}.utils.LogUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author 郗世豪（rarexixi@outlook.com） All Rights Reserved.
 * @version 1.0
 * @date 08/03/2018
 */
@Activate(group = Constants.PROVIDER)
public class DubboFilter implements Filter {

    private static final LogUtils logger = LogUtils.build(DubboFilter.class);
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) {

        // 如果是dubbo自己的服务，直接返回
        if (GenericService.class == invoker.getInterface()) {
            return invoker.invoke(invocation);
        }

        String methodName = RpcContext.getContext().getRemoteHost() + "-" + invoker.getInterface().getName() + "." + invocation.getMethodName();
        String sessionId = getSessionId(invoker, invocation);

        try {
            logger.info(methodName, sessionId, "Dubbo 服务开始执行", invocation.getArguments());

            List<String> messages = getValidateResult(invoker, invocation);
            if (!messages.isEmpty()) {
                logger.info(methodName, sessionId, "Dubbo 服务参数验证失败", messages);
                return new RpcResult(new ResultVo<>(OperationConstants.PARAMETER_VALIDATION_FAILED, messages));
            }

            // StopWatch 计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Result result = invoker.invoke(invocation);
            stopWatch.stop();

            if (!result.hasException()) {
                if (logger.isInfoEnabled()) {
                    Map<String, Object> logArgs = new HashMap<>(4);
                    logArgs.put("开始执行时间", stopWatch.getStartTime());
                    logArgs.put("开始执行时间（用来展示）", new Date(stopWatch.getStartTime()).toString());
                    logArgs.put("出参", result);
                    logArgs.put("方法执行时长(ms)", stopWatch.getTime());
                    logger.info(methodName, sessionId, "Dubbo 服务执行结束", logArgs);
                }
                return result;
            }
            logger.error(methodName, sessionId, "服务出现异常", result.getException());
        } catch (ConstraintDeclarationException e) {
            logger.error(methodName, "参数验证失败", e);
            return new RpcResult(new ResultVo<>(OperationConstants.PARAMETER_VALIDATION_FAILED));
        } catch (RuntimeException | NoSuchMethodException e) {
            logger.error(methodName, sessionId, e);
        }
        return new RpcResult(new ResultVo<>(OperationConstants.SYSTEM_ERROR));
    }

    /**
     * 验证参数，有一个验证不通过，就直接返回
     *
     * @param invoker
     * @param invocation
     * @return
     * @throws NoSuchMethodException
     */
    private List<String> getValidateResult(Invoker<?> invoker, Invocation invocation) throws NoSuchMethodException {

        Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        Parameter[] parameters = method.getParameters();
        Object[] args = invocation.getArguments();

        List<String> messages = new LinkedList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Validated validated = parameter.getAnnotation(Validated.class);
            if (validated == null) continue;

            Object arg = args[i];
            // 参数是数组或者列表，并且验证不通过，直接返回
            if ((arg instanceof Collection || arg instanceof Object[]) && !validate(arg, validated)) break;

            Set<ConstraintViolation<Object>> validateResults = validator.validate(arg, validated.value());

            // 参数验证通过，继续下一个参数
            if (validateResults == null || validateResults.isEmpty()) continue;

            for (ConstraintViolation<Object> constraintViolation : validateResults) {
                String message = constraintViolation.getMessage();
                messages.add(message);
            }
            break;
        }
        return messages;
    }

    /**
     * 验证参数是否符合规范
     *
     * @param arg
     * @param validated
     * @return
     */
    private boolean validate(Object arg, Validated validated) {
        if (arg == null) return false;
        if (arg instanceof Collection) {
            for (Object obj : (Collection) arg) {
                if (validate(obj, validated)) continue;
                return false;
            }
        } else if (arg instanceof Object[]) {
            for (Object obj : (Object[]) arg) {
                if (validate(obj, validated)) continue;
                return false;
            }
        } else if (arg instanceof Map) {
            Set<Map.Entry> entrySet = ((Map) arg).entrySet();
            for (Map.Entry entry : entrySet) {
                if (validate(entry.getValue(), validated)) continue;
                return false;
            }
        } else {
            Set<ConstraintViolation<Object>> validateResults = validator.validate(arg, validated.value());
            return validateResults == null || validateResults.isEmpty();
        }
        return true;
    }

    private String getSessionId(Invoker<?> invoker, Invocation invocation) {

        Object sessionId = null;
        try {
            sessionId = AnnotationUtils.getParam(ParamName.class, invoker.getInterface(), invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments(), "sessionId");
        } catch (Exception e) {
            logger.error("getSessionId", "获取 Session ID 异常", e);
        }

        if (null == sessionId) {
            sessionId = UUID.randomUUID();
        }
        return sessionId.toString();
    }
}
