package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ChannelPvUv;
import com.ganymede.flink.utils.HBaseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

public class ChannelsPvUvSinkReduce implements SinkFunction<ChannelPvUv> {
	@Override
	public void invoke(ChannelPvUv value) throws Exception {
		long channelId = value.getChannelId();
		long pvCount = value.getPvCount();
		long uvCount = value.getUvCount();
		String timeString = value.getTimeString();

		String pv = HBaseUtil.getData("channelinfo", channelId + timeString + "", "info", "pv");
		String uv = HBaseUtil.getData("channelinfo", channelId + timeString + "", "info", "uv");

		if (StringUtils.isNotBlank(pv)) {
			pvCount += pvCount + Long.valueOf(pv);
		}

		if (StringUtils.isNotBlank(uv)) {
			uvCount += uvCount + Long.valueOf(uv);
		}


		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("pv", pvCount + "");
		dataMap.put("uv", uvCount + "");

		HBaseUtil.put("channelinfo", channelId + "->" + timeString + "", "info", dataMap);
	}
}
