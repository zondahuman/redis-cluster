package com.abin.lee.redis.model;

import java.io.Serializable;

/**
 * Description:
 * Author: abin
 * Update: (2015-10-10 20:41)
 */
public class Order implements Serializable{
    private Long id;
    private String orderName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
