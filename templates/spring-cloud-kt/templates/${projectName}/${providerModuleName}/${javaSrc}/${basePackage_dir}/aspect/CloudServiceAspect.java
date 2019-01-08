package ${basePackage}.aspect;

import ${baseCommonPackage}.constant.OperationConstants;
import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.utils.AnnotationUtils;
import ${baseCommonPackage}.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
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

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = request.getRemoteHost() + method.getDeclaringClass().getName() + "." + method.getName();
        String sessionId = request.getParameter("sessionId");

        try {
            List<String> messages = getErrorMessage(proceedingJoinPoint.getArgs());
            if (!messages.isEmpty()) {
                logger.error(methodName, "参数验证失败", messages);
                return new ResponseVo<>(OperationConstants.PARAMETER_VALIDATION_FAILED, messages);
            }

            Object result = proceedingJoinPoint.proceed();
            return result;
        } catch (ConstraintViolationException e) {
            logger.error(methodName, "参数验证失败", e);
            List<String> messages = getErrorMessage(e);
            return new ResponseVo<>(OperationConstants.PARAMETER_VALIDATION_FAILED, messages);
        } catch (ValidationException e) {
            logger.error(methodName, "参数验证失败", e);
            return new ResponseVo<>(OperationConstants.PARAMETER_VALIDATION_FAILED, e.getMessage());
        } catch (Exception e) {
            logger.error(methodName, "服务出现异常", e);
        }

        return new ResponseVo<>(OperationConstants.SYSTEM_ERROR);
    }

    /**
     * 获取非Errors类型参数，否则转json报错
     *
     * @param args
     * @return
     */
    private List<Object> getParameters(Object[] args) {
        List<Object> parameters = new LinkedList<>();
        for (Object arg : args) {
            if (arg instanceof Errors) continue;
            parameters.add(arg);
        }
        return parameters;
    }

    /**
     * 获取拦截的验证失败信息
     *
     * @param exception
     * @return
     */
    private List<String> getErrorMessage(ConstraintViolationException exception) {
        List<String> messages = new LinkedList<>();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation violation : violations) {
            messages.add(violation.getMessage());
        }
        return messages;
    }

    /**
     * 获取拦截的Errors信息
     *
     * @param args
     * @return
     */
    private List<String> getErrorMessage(Object[] args) {
        List<String> messages = new LinkedList<>();
        for (Object arg : args) {
            if (arg instanceof Errors && ((Errors) arg).hasErrors()) {
                for (ObjectError objectError : ((Errors) arg).getAllErrors()) {
                    messages.add(objectError.getDefaultMessage());
                }
            }
        }
        return messages;
    }
}
