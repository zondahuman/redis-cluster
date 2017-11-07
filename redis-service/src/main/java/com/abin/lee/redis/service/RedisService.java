package com.abin.lee.redis.service;

import com.abin.lee.redis.model.Order;

/**
 * Description:
 * Author: abin
 * Update: (2015-10-10 20:32)
 */
public interface RedisService {

    public Order findById(Long id);

}
