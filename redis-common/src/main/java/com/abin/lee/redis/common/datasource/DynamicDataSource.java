package com.abin.lee.redis.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: abin
 * Date: 15-5-4
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }

}
