package com.abin.lee.redis.test;

import com.abin.lee.redis.common.cache.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {
        "classpath*:applicationContext-common.xml",
        "classpath*:applicationContext-properties.xml",
        })
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
    
    @Test
    public void testRedisUtil(){
    	RedisUtil.getInstance().set("11", "bb");
    	String s = RedisUtil.getInstance().get("11");
    	s = RedisUtil.getInstance().set("aa".getBytes(), "bb".getBytes(), 2);
    	byte[] t = RedisUtil.getInstance().get("aa".getBytes());

    	//ru.set("META_BS_DELTA_INCR", String.valueOf(23));
    	byte[] input = "META_BS_DELTA_INCR".getBytes();
    	byte[] ret = RedisUtil.getInstance().get(input);
    	System.out.println(String.valueOf(ret));
    }
    

}
