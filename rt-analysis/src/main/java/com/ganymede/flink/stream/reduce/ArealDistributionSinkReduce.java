package com.ganymede.flink.stream.reduce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganymede.analy.ArealDistribution;
import com.ganymede.flink.utils.HBaseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

public class ArealDistributionSinkReduce implements SinkFunction<ArealDistribution> {

	@Override
	public void invoke(ArealDistribution value, Context context) throws Exception {
		long channelId = value.getChannelId();
		String area = value.getArea();
		long pvCount = value.getPv();
		long uvCount = value.getUv();
		long newCount = value.getNewCount();
		long oldCount = value.getOldCount();

		String timeString = value.getTimeString();

		String pv = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "areaPv");
		String uv = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "areaUv");

		String newCnt = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "arealNewCnt");
		String oldCnt = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "arealOldCnt");

		Map<String, Long> map = new HashMap<>();
		Map<String, String> datamap = new HashMap<>();

		dataJson2Map(pv, map, area, pvCount);
		datamap.put("areapv", JSON.toJSONString(map));

		dataJson2Map(uv, map, area, uvCount);
		datamap.put("areauv", JSON.toJSONString(map));

		dataJson2Map(newCnt, map, area, newCount);
		datamap.put("arealNewCnt", JSON.toJSONString(map));

		dataJson2Map(oldCnt, map, area, oldCount);
		datamap.put("arealOldCnt", JSON.toJSONString(map));

		System.out.println("ArealDistributionSinkReduce -> " + datamap);
		HBaseUtil.put("channelinfo", channelId + "->" + timeString + "", "info", datamap);
	}

	private void dataJson2Map(String data, Map<String, Long> map, String area, Long value) {
		if (StringUtils.isNotBlank(data)) {
			map = JSONObject.parseObject(data, Map.class);
			Long count = Long.valueOf(map.get(area)+"");
			if (count != null) {
				value += value + count;
			}
		}
		map.put(area, value);
	}
}
