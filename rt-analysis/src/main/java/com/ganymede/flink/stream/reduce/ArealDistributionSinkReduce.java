package com.ganymede.flink.stream.reduce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganymede.analy.ArealDistribution;
import com.ganymede.flink.utils.HBaseUtil;
import com.ganymede.flink.utils.JsonUtils;
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

		System.out.println(pvCount + " " + uvCount + " " + newCount + " " + oldCount);

		String timeString = value.getTimeString();

		String pv = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "areaPv");
		String uv = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "areaUv");

		String newCnt = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "arealNewCnt");
		String oldCnt = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "arealOldCnt");

		Map<String, String> datamap = new HashMap<>();
		Map<String, Long> map = null;

		map = JsonUtils.dataJson2Map(pv, area, pvCount);
		datamap.put("areapv", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(uv, area, uvCount);
		datamap.put("areauv", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(newCnt, area, newCount);
		datamap.put("arealNewCnt", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(oldCnt, area, oldCount);
		datamap.put("arealOldCnt", JSON.toJSONString(map));

		HBaseUtil.put("channelinfo", channelId + "->" + timeString + "", "info", datamap);
	}
}
