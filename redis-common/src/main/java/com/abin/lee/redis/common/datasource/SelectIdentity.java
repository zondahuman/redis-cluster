package com.abin.lee.redis.common.datasource;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 15-5-5
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelectIdentity {
    DataSource source() default DataSource.ABIN_MASTER;
}
