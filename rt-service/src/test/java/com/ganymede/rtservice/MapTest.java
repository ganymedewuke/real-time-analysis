package com.ganymede.rtservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTest {
	public static void main(String[] args) {
		Map dataMap = new HashMap();
		dataMap.put("a", 1);
		dataMap.put("b", 2);
		dataMap.put("c", 3);

		if (dataMap != null) {
			Set<Map.Entry<String, String>> set = dataMap.entrySet();
			for (Map.Entry<String, String> entry : set) {
				String key = entry.getKey();
				Object value = entry.getValue();
				System.out.println(key + "," + value);
			}
		}
	}
}
