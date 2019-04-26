package com.ganymede.flink.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

	public static Map dataJson2Map(String data, String col, Long value) {
		Map<String, Long> map = new HashMap<>();
		if (StringUtils.isNotBlank(data) && !data.equals("null")) {
			map = JSONObject.parseObject(data, Map.class);
			String cntStr = map.get(col) + "";
			System.out.println(map);
			if (!cntStr.equals("null")) {
				Long count = Long.valueOf(map.get(col) + "");
				if (count != null) {
					value += value + count;
				}
			}
			map.put(col, value);
		} else {
			map.put(col, value);
		}
		return map;
	}
}
