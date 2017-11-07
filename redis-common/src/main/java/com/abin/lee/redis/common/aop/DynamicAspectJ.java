package com.abin.lee.redis.common.aop;

import com.abin.lee.redis.common.datasource.DbContextHolder;
import com.abin.lee.redis.common.datasource.SelectIdentity;
import com.abin.lee.redis.common.exception.OperateType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 15-5-4
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
//@Aspect
//@Component
public class DynamicAspectJ implements Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicAspectJ.class);

    @Pointcut("execution(* com.abin.lee.integrate.service..*.*(..))")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
//        LOGGER.info(OperateType.getCmName() + "joinPoint={}", joinPoint);
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
//        LOGGER.info(OperateType.getCmName() + "joinPoint={}", joinPoint);
        //如果需要这里可以取出参数进行处理
        transfer(joinPoint);
    }

    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
//        LOGGER.info(OperateType.getCmName() + "joinPoint={} returnVal={}", joinPoint, returnVal);
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        LOGGER.info(OperateType.getCmName() + "pjp={} isStart={}", pjp, "start");
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable ex) {
            LOGGER.info(OperateType.getCmName() + "pjp={} ex.message={} ex={}", pjp, ex.getMessage(), ex);
            throw ex;
        }
//        LOGGER.info(OperateType.getCmName() + "pjp={} isEnd={}", pjp, "end");
        return obj;
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "error")
    public void afterThrowing(JoinPoint jp, Throwable error) {
//        LOGGER.info(OperateType.getCmName() + "jp={} error={} error.message={}", jp, error, error.getMessage());
    }

    public void transfer(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName = method.getName();
        String identity = "";

        if (null != method) {
            SelectIdentity selectIdentity = (SelectIdentity) method.getAnnotation(SelectIdentity.class);
            if (null != selectIdentity){
                identity = selectIdentity.source().identity();
                DbContextHolder.setDbType(identity);
            }
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }

}
