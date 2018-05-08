package ${basePackage}.aspect;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.Result;
import ${baseCommonPackage}.utils.AnnotationUtils;
import ${baseCommonPackage}.utils.LogUtils;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

<#include "/include/java_copyright.ftl">
@Component
@Aspect
public class CloudServiceAspect {

    private final LogUtils logger = LogUtils.build(CloudServiceAspect.class);

    @Autowired
    HttpServletRequest request;

    /**
     * 设置标识
     */
    @Pointcut("execution(public * ${basePackage}.controller.*.*(..))")
    public void invoke() {
    }

    /**
     * 环绕方法执行，proceedingJoinPoint.proceed()是执行环绕的方法
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("invoke()")
    public Object PlayAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Signature signature = proceedingJoinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return proceedingJoinPoint.proceed();
        }

        String methodName = "";
        String sessionId = "";
        try {

            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();
            methodName = request.getRemoteHost() + " - " + method.getDeclaringClass().getName() + "." + method.getName();
            sessionId = getSessionId(method, proceedingJoinPoint.getArgs(), methodName);

            logger.info(methodName, sessionId, "Cloud 服务开始执行", proceedingJoinPoint.getArgs());

            // StopWatch 计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object result = proceedingJoinPoint.proceed();
            stopWatch.stop();

            if (logger.isInfoEnabled()) {
                Map<String, Object> args = new HashMap<>(8);
                args.put("开始执行时间", stopWatch.getStartTime());
                args.put("开始执行时间（用来展示）", new Date(stopWatch.getStartTime()).toString());
                args.put("入参", proceedingJoinPoint.getArgs());
                args.put("出参", result);
                args.put("方法执行时长(ms)", stopWatch.getTime());
                logger.info(methodName, sessionId, "Cloud 服务执行结束", args);
            }
            return result;
        } catch (Exception e) {
            logger.error(methodName, sessionId, "Cloud 服务出现异常", e);
        }

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }


    private String getSessionId(Method method, Object[] args, String methodFullName) {

        Object sessionId = null;
        try {
            sessionId = AnnotationUtils.getParam(method, args, "sessionId", RequestParam.class);
        } catch (NoSuchMethodException e) {
            logger.error(methodFullName, "Cloud 服务出现异常", e);
        }
        if (null == sessionId) {
            sessionId = UUID.randomUUID();
        }
        return sessionId.toString();
    }
}
