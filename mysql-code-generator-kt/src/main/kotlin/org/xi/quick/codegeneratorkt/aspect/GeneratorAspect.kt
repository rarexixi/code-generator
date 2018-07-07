package org.xi.quick.codegeneratorkt.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class GeneratorAspect {

    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.deleteAllOnce(..))")
    @Throws(Throwable::class)
    fun deleteAllOnceAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        logger.info("正在删除所有需要的基本类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("删除所有需要的基本类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.generateAllOnce(..))")
    @Throws(Throwable::class)
    fun generateAllOnceAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        logger.info("正在生成需要的基本类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("生成所有需要的基本类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.deleteAllAggregate(..))")
    @Throws(Throwable::class)
    fun deleteAllAggregateAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        logger.info("正在删除所有聚合类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("删除所有聚合类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.generateAllAggregate(..))")
    @Throws(Throwable::class)
    fun generateAllAggregateAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        logger.info("正在生成聚合类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("生成所有聚合类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.deleteAll(..))")
    @Throws(Throwable::class)
    fun deleteAllAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        logger.info("正在删除所有类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("删除所有类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.generateAll(..))")
    @Throws(Throwable::class)
    fun generateAllAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        logger.info("正在生成所有类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("生成所有类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.delete(..))")
    @Throws(Throwable::class)
    fun deleteAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        val args = proceedingJoinPoint.args
        val tables = (args[0] as Array<Any>).joinToString(",")

        logger.info("正在删除" + tables + "相关的类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("删除" + tables + "相关的类完成")

        return result
    }


    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.xi.quick.codegeneratorkt.service.GeneratorService.generate(..))")
    @Throws(Throwable::class)
    fun generateAround(proceedingJoinPoint: ProceedingJoinPoint): Any? {

        val args = proceedingJoinPoint.args
        val tables = (args[0] as Array<Any>).joinToString(",")

        logger.info("正在生成" + tables + "相关的类...")
        val result = proceedingJoinPoint.proceed()
        logger.info("生成" + tables + "相关的类完成")

        return result
    }

    companion object {
        internal var logger = LoggerFactory.getLogger(GeneratorAspect::class.java)
    }
}
