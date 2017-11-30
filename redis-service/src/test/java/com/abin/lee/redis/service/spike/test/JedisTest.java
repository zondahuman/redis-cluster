package com.abin.lee.redis.service.spike.test;

import com.abin.lee.redis.service.spike.RedisUtil;
import org.junit.Test;

/**
 * Created by abin on 2017/11/30 2017/11/30.
 * redis-cluster
 * com.abin.lee.redis.service.spike.test
 */
public class JedisTest {

    @Test
    public void test1(){
        String key ="abin1";
        String value ="lee1";
        Long letter = RedisUtil.getJedis().lpush(key, value);
        System.out.println("letter="+letter);
        String result = RedisUtil.getJedis().lpop(key);
        System.out.println("result="+result);
    }

    @Test
    public void test2(){
        String key ="abin1";
        String value ="lee1";
        Long letter = RedisUtil.getJedis().lpush(key, value);
        System.out.println("letter="+letter);
        String result = RedisUtil.getJedis().rpop(key);
        System.out.println("result="+result);
    }

    @Test
    public void test3(){
        String key ="abin1";
        String key1 ="abin2";
        String value ="lee1";
        Long letter = RedisUtil.getJedis().lpush(key, value);
        System.out.println("letter="+letter);
        String result = RedisUtil.getJedis().rpoplpush(key, key1);
        System.out.println("result="+result);
    }


}
