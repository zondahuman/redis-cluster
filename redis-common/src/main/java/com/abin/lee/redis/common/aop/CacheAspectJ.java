package com.abin.lee.redis.common.aop;

import com.abin.lee.redis.common.util.DateUtil;
import com.abin.lee.redis.common.util.SerializeUtil;
import com.abin.lee.redis.common.cache.RedisUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;

/**
 * @ClassName:CacheAspectJ
 * @Description:
 * @author:lyc
 * @date:2015年6月15日 下午6:01:47
 */
@Aspect
@Component
public class CacheAspectJ implements Ordered {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CacheAspectJ.class);

    @Pointcut("execution(* com.abin.lee.redis.service..*.*(..))")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        LOGGER.debug("after aspect executed");
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws InvocationTargetException,
            IllegalAccessException {
        LOGGER.debug("before aspect executing");
    }

    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
        LOGGER.debug("afterReturning executed, return result is :{}", returnVal);
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        Cache cache = (Cache) method.getAnnotation(Cache.class);
        Object result = null;
        if (null != cache) {
            String cacheKey = cache.cacheKey();
            int expiry = cache.expiry();
            if (cacheKey == null || cacheKey.equals("")) {
                String className = pjp.getTarget().getClass().getSimpleName();
                String methodName = method.getName();
                cacheKey = StringUtils.join(className, "." + methodName);
            }
            Object[] arguments = pjp.getArgs();
            cacheKey = getCacheKey(cacheKey, arguments);
            byte[] value = RedisUtil.getInstance().get(cacheKey.getBytes());
            if (value == null) {
                result = pjp.proceed();
                RedisUtil.getInstance().set(cacheKey.getBytes(), SerializeUtil.serializeTransNull(result), expiry);
                LOGGER.info("nocached,key:" + cacheKey);
                /*
				 * null不缓存，改为缓存
				 * if(result!=null){
					RedisUtil.getInstance().set(cacheKey.getBytes(), SerializeUtil.serialize(result), expiry);
					LOGGER.info("nocached,result isnot null,cache this:"+cacheKey);
				}else{
					LOGGER.info("nocached,result is null,no cache this:"+cacheKey);
				}*/
            } else {
                result = SerializeUtil.unserializeTransNull(value);
                LOGGER.info("cached,key:" + cacheKey);
            }

        } else {
            result = pjp.proceed();
        }

        return result;
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "error")
    public void afterThrowing(JoinPoint jp, Throwable error) {
        LOGGER.debug("error:{}", error);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private String getCacheKey(String cacheKey, Object[] arguments) {
        StringBuffer sb = new StringBuffer(cacheKey);
        if (arguments != null && arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] == null) {
                    sb.append("/");
                } else if (arguments[i] instanceof Date) {
                    sb.append("/").append(DateUtil.getYMDHMS((Date) (arguments[i])));
                } else {
                    sb.append("/").append(arguments[i]);
                }
            }
        }
        return sb.toString();
    }
}
