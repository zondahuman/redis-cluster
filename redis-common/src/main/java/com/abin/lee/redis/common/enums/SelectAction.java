package com.abin.lee.redis.common.enums;

import java.lang.annotation.*;

/**
 * Description:
 * Author: abin
 * Update: (2015-09-05 15:43)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SelectAction {
    OperationAction action() default OperationAction.SEARCH_SERVICE ;
}
