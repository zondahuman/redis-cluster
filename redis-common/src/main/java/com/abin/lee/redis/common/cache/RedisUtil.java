package com.abin.lee.redis.common.cache;

import com.abin.lee.redis.common.util.context.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisUtil {

private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	private static Map<String, RedisUtil> instances = new HashMap<String, RedisUtil>();

	private ShardedJedisPool shardedJedisPool;
	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	private RedisUtil(String beanId) {
		this.shardedJedisPool = SpringContextUtils.getBean(beanId, ShardedJedisPool.class);
	}

	public static RedisUtil getInstance() {
		if(!instances.containsKey("shardedJedisPool")){
			synchronized (RedisUtil.class) {
				if(!instances.containsKey("shardedJedisPool")){
					instances.put("shardedJedisPool", new RedisUtil("shardedJedisPool"));
				}
			}

		}
		return instances.get("shardedJedisPool");
	}

	public static RedisUtil getSessionInstance() {
		if(!instances.containsKey("sessionShardedJedisPool")){
			synchronized (RedisUtil.class) {
				if(!instances.containsKey("sessionShardedJedisPool")){
					instances.put("sessionShardedJedisPool", new RedisUtil("sessionShardedJedisPool"));
				}
			}

		}
		return instances.get("sessionShardedJedisPool");

	}

	public ShardedJedis getJedis() {
		return this.getShardedJedisPool().getResource();
	}

	public static void returnJedis(ShardedJedis jedis) {
		if (jedis != null)
			jedis.close();
	}

	public static interface JedisAutoReturn extends JedisCommands, BinaryJedisCommands {
//		Map<String, String> hgetAll(String key);
	}

	private static Map<Method, Method> cache = new HashMap<Method, Method>();

	private static Method getMethod(Method m) throws SecurityException, NoSuchMethodException {
		Method ret = cache.get(m);
		if (ret != null)
			return ret;

		Class<ShardedJedis> c = ShardedJedis.class;
		ret = c.getMethod(m.getName(), m.getParameterTypes());
		cache.put(m, ret);
		return ret;
	}

	/**
	 * 返回的JedisAutoReturn对象，每次执行方法后都会自动释放连接
	 * @return
	 */
	public JedisAutoReturn getJedisAutoReturn() {
		Object proxy = Proxy.newProxyInstance(ShardedJedisPool.class.getClassLoader(), new Class[] { JedisAutoReturn.class },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						ShardedJedisPool pool = getShardedJedisPool();
						ShardedJedis jedis = null;
						try {
							jedis = pool.getResource();
							if (JedisAutoReturn.class != method.getDeclaringClass()) {
								return method.invoke(jedis, args);
							} else {
								return getMethod(method).invoke(jedis, args);
							}
						} finally {
							jedis.close();
						}
					}
				});
		return (JedisAutoReturn) proxy;
	}


	public void set(String key,String value){
		getJedisAutoReturn().set(key, value);
	}

	public String get(String key){
		return getJedisAutoReturn().get(key);
	}
	public String set(byte[] key,byte[] value,int seconds){
		return getJedisAutoReturn().setex(key, seconds, value);
	}

	public byte[] get(byte[] key){
		return getJedisAutoReturn().get(key);
	}

	public Long del(byte[] key){
		return getJedisAutoReturn().del(key);
	}

	/**
	 * 获取所有节点内的key
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {

		ShardedJedis jedis = getJedis();
		try {
			Set<String> rst = new HashSet<String>();
			for(Jedis j : jedis.getAllShards()){
				rst.addAll(j.keys(pattern));
			}
			return rst;
		} finally {
			returnJedis(jedis);
		}
	}

	public byte[] hget(byte[] key,byte[] field){
		return getJedisAutoReturn().hget(key, field);
	}

	public Long hset(byte[] key,byte[] field,byte[] value){
		return getJedisAutoReturn().hset(key, field, value);
	}

	public Long hdel(byte[] key, byte[]... fields){
		return getJedisAutoReturn().hdel(key, fields);
	}

	public Long hincrBy(byte[] key, byte[] field,long value){
		return getJedisAutoReturn().hincrBy(key, field, value);
	}

	public Set<byte[]> hkeys(byte[] key){
		return getJedisAutoReturn().hkeys(key);
	}

	public Long hlen(byte[] key){
		return getJedisAutoReturn().hlen(key);
	}

	public Map<byte[],byte[]> hgetAll(byte[] key){
		return getJedisAutoReturn().hgetAll(key);

	}

}
