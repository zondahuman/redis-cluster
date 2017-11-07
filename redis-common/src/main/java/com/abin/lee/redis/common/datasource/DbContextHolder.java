package com.abin.lee.redis.common.datasource;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 15-5-4
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
public class DbContextHolder {
    //线程安全的ThreadLocal
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return ((String)contextHolder.get());
    }
    public static void clearDbType() {
        contextHolder.remove();
    }


}
