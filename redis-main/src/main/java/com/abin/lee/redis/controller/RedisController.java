package com.abin.lee.redis.controller;

import com.abin.lee.redis.common.exception.OperateType;
import com.abin.lee.redis.common.util.json.JsonUtil;
import com.abin.lee.redis.model.Order;
import com.abin.lee.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description:
 * Author: abin
 * Update: (2015-10-10 20:47)
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping(value = "/redis")
public class RedisController {
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Resource
    RedisService redisService;

    @RequestMapping(value = "findById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String findById(@PathVariable Long id) {
        LOGGER.info(OperateType.getCmName()+"id={}",id);
        String result = "SUCCESS";
        try {
            Order order = this.redisService.findById(id);
            result = JsonUtil.serialize(order);
            LOGGER.info(OperateType.getCmName()+"result={}",result);
        } catch (Exception e) {
            LOGGER.error(OperateType.getCmName() + "e:{} e.message={}", e, e.getMessage());
            result = "FAILURE";
        }
        return result;
    }
}
