package com.abin.lee.redis.service.impl;

import com.abin.lee.redis.common.aop.Cache;
import com.abin.lee.redis.model.Order;
import com.abin.lee.redis.service.RedisService;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Author: abin
 * Update: (2015-10-10 20:33)
 */
@Service
public class RedisServiceImpl implements RedisService{

    @Override
    @Cache(expiry = 60*60*2)
    public Order findById(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setOrderName("order-name"+id);
        return order;
    }
}
