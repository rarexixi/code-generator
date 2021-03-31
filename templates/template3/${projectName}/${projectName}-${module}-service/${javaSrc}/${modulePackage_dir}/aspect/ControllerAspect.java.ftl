package ${modulePackage}.aspect;

import ${commonPackage}.annotation.SetField;
import ${commonPackage}.annotation.SetFieldTypes;
import ${commonPackage}.models.DmpUser;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Component
@Aspect
public class ControllerAspect {

    private final Map<String, Field> userFieldMap;

    public ControllerAspect() {
        Class<DmpUser> userClass = DmpUser.class;
        Field[] fields = userClass.getDeclaredFields();
        userFieldMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            userFieldMap.put(field.getName(), field);
        }
    }

    private final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    HttpServletRequest request;

    /**
     * 设置标识
     */
    @Pointcut("execution(public org.springframework.http.ResponseEntity ${modulePackage}.controller.*.*(..))")
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

        String methodName = method.getDeclaringClass().getName() + "." + method.getName();

        String path = request.getServletPath();
        Object[] args = proceedingJoinPoint.getArgs();

    //        DmpUser user = (DmpUser) SecurityUtils.getSubject().getPrincipal();
    //        if (user != null) {
    //            logger.info("{}({});#path:{};#method:{};#args:{}", user.getName(), user.getUsername(), path, methodName, args);
    //            setUsers(method, args, user);
    //        }

        return proceedingJoinPoint.proceed();
    }

    /**
     * 设置用户相关字段
     *
     * @param method 方法
     * @param args   实际参数
     * @param user   DMP用户
     */
    private void setUsers(final Method method, final Object[] args, final DmpUser user) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> fieldValueMap = new HashMap<>(userFieldMap.size());
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            SetFieldTypes setFieldTypes = parameter.getAnnotation(SetFieldTypes.class);
            if (setFieldTypes == null) {
                continue;
            }

            Object arg = args[i];
            setUsers(setFieldTypes.types(), arg, user, fieldValueMap);
        }
    }

    /**
     * 设置用户相关字段
     *
     * @param types 更新的类型列表
     * @param obj   更新的对象
     * @param user  DMP用户
     */
    private void setUsers(final String[] types, final Object obj, final DmpUser user, Map<String, Object> fieldValueMap) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Collection) {
            for (Object o : (Collection) obj) {
                setUsers(types, o, user, fieldValueMap);
            }
            return;
        } else if (obj instanceof Map) {
            for (Object o : ((Map) obj).values()) {
                setUsers(types, o, user, fieldValueMap);
            }
            return;
        }
        Class<?> clazz = obj.getClass();
        while (!clazz.equals(Object.class)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                SetField setField;
                SetFieldTypes setFieldTypes;

                if ((setField = field.getAnnotation(SetField.class)) != null) {
                    Field userField = userFieldMap.getOrDefault(setField.field(), null);
                    if (userField == null) {
                        continue;
                    }
                    Object fieldValue = null;
                    if (fieldValueMap.containsKey(setField.field()) && (fieldValue = fieldValueMap.get(setField.field())) == null) {
                        continue;
                    }
                    setFieldFor:
                    for (String type : types) {
                        for (String fieldType : setField.types()) {
                            if (!type.equals(fieldType)) {
                                continue;
                            }
                            if (fieldValue == null) {
                                fieldValue = getFieldValue(userField, user);
                            }
                            setFieldValue(field, obj, fieldValue);
                            fieldValueMap.put(setField.field(), fieldValue);
                            break setFieldFor;
                        }
                    }
                } else if ((setFieldTypes = field.getAnnotation(SetFieldTypes.class)) != null) {
                    Object fieldValue = getFieldValue(field, obj);
                    if (fieldValue == null) {
                        continue;
                    }
                    setUsers(setFieldTypes.types(), fieldValue, user, fieldValueMap);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private void setFieldValue(Field field, Object obj, Object val) {
        try {
            boolean oldFieldAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(obj, val);
            field.setAccessible(oldFieldAccessible);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object getFieldValue(Field field, Object obj) {
        Object fieldValue = null;
        try {
            boolean oldFieldAccessible = field.isAccessible();
            field.setAccessible(true);
            fieldValue = field.get(obj);
            field.setAccessible(oldFieldAccessible);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValue;
    }
}