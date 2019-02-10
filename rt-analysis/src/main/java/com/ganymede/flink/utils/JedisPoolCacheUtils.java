package com.ganymede.flink.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class JedisPoolCacheUtils {

	private final static Logger log = LoggerFactory.getLogger(JedisPoolCacheUtils.class);

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		Properties prop = new Properties();
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(50);
			config.setMaxIdle(10);
			config.setMinIdle(5);
			config.setMaxWaitMillis(35);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setTestWhileIdle(true);
			jedisPool = new JedisPool(config, "spark1", 6379, 3500);
		} catch (Exception e) {
			log.error("First create JedisPool error : " + e);
		}
		//Jedis jedis = jedisPool.getResource();
		//log.info("=====初始化redis池成功!  状态:"+ jedis.ping());
		log.info("=====初始化redis池成功!");
	}


	public static String getByKey(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
		return null;
	}

	public static void setKeyValue(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
	}


	public static void lpush(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.lpush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJedis(jedis);
		}
	}


	/**
	 * closeJedis(释放redis资源)
	 *
	 * @param @param jedis
	 * @return void
	 * @throws
	 * @Title: closeJedis
	 */
	public static void closeJedis(Jedis jedis) {
		try {
			if (jedis != null) {
                /*jedisPool.returnBrokenResource(jedis);
                jedisPool.returnResource(jedis);
                jedisPool.returnResourceObject(jedis);*/
				//高版本jedis close 取代池回收
				jedis.close();
			}
		} catch (Exception e) {
			log.error("释放资源异常：" + e);
		}
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
