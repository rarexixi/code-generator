package org.xi.quick.codegenerator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.utils.StringUtil;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2017/11/29 16:08
 */
@Aspect
@Component
public class GeneratorAspect {

    static Logger logger = LoggerFactory.getLogger(GeneratorAspect.class);

    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.deleteAllOnce(..))")
    public Object deleteAllOnceAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("正在删除所有需要的基本类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("删除所有需要的基本类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.generateAllOnce(..))")
    public Object generateAllOnceAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("正在生成需要的基本类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("生成所有需要的基本类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.deleteAllAggregate(..))")
    public Object deleteAllAggregateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("正在删除所有聚合类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("删除所有聚合类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.generateAllAggregate(..))")
    public Object generateAllAggregateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("正在生成聚合类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("生成所有聚合类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.deleteAll(..))")
    public Object deleteAllAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("正在删除所有类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("删除所有类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.generateAll(..))")
    public Object generateAllAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("正在生成所有类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("生成所有类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.delete(..))")
    public Object deleteAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        String tables = StringUtil.join(",", (Object[])args[0]);

        logger.info("正在删除" + tables + "相关的类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("删除" + tables + "相关的类完成");

        return object;
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegenerator.service.GeneratorService.generate(..))")
    public Object generateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        String tables = StringUtil.join(",", (Object[])args[0]);

        logger.info("正在生成" + tables + "相关的类...");
        Object object = proceedingJoinPoint.proceed();
        logger.info("生成" + tables + "相关的类完成");

        return object;
    }
}
