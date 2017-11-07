package com.abin.lee.redis.common.exception;

public class OperateType {

    public static void main(String[] args) {
        String result = OperateType.getCmName();
        System.out.println("result=" + result);
    }

    public static String getCmName() {
        String clazz = Thread.currentThread().getStackTrace()[2].getClassName();
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        return clazz + "--" + method + "--";
    }
    public static String getCmName(Class<?> classer) {
        String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        return clazz + "--" + method + "--";
    }

}
