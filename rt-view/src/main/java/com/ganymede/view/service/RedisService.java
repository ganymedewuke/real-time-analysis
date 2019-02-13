package com.ganymede.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("ALL")
@Service
public class RedisService {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	ValueOperations<String, String> valOpsString;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	ValueOperations<Object, Object> valOpsObj;

	/**
	 * 根据指定key获取String
	 *
	 * @param key
	 * @return
	 */
	public String getStr(String key) {
		return valOpsString.get(key);
	}

	/**
	 * 设置String缓存
	 *
	 * @param key
	 * @param val
	 */
	public void setStr(String key, String val) {
		valOpsString.set(key, val);
	}

	/**
	 * 删除指定key
	 *
	 * @param key
	 */
	public void del(String key) {
		stringRedisTemplate.delete(key);
	}


	/**
	 * 根据指定o获取Object
	 *
	 * @param o
	 * @return
	 */
	public Object getObj(Object o) {
		return valOpsObj.get(o);
	}

	/**
	 * 设置obj缓存
	 *
	 * @param o1
	 * @param o2
	 */
	public void setObj(Object o1, Object o2) {
		valOpsObj.set(o1, o2);
	}

	/**
	 * 删除Obj缓存
	 *
	 * @param o
	 */
	public void delObj(Object o) {
		redisTemplate.delete(o);
	}

	/**
	 * 获取缓存集合
	 *
	 * @param parentKey
	 * @return
	 */
	public Map<String, List<String>> getAllData(String parentKey) {
		Map<String, List<String>> resultMap = new HashMap<>();
		Set<String> setData = stringRedisTemplate.keys(parentKey + "*");

		for (String key : setData) {
			List<String> list = stringRedisTemplate.opsForList().range(key, 0l, 200l);
			resultMap.put(key, list);
		}
		return resultMap;
	}

}
