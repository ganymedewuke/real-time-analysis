package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ChannelFresh;
import com.ganymede.analy.ChannelPvUv;
import com.ganymede.flink.utils.HBaseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

public class ChannelsFreshSinkReduce implements SinkFunction<ChannelFresh> {
	@Override
	public void invoke(ChannelFresh value,Context context) throws Exception {
		long channelId = value.getChannelId();
		long newCount = value.getNewCount();
		long oldCount = value.getOldCount();
		String timeString = value.getTimeString();

		String newCnt = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "freshNewCnt");
		String oldCnt = HBaseUtil.getData("channelinfo", channelId + "->" + timeString + "", "info", "freshOldCnt");

		if (StringUtils.isNotBlank(newCnt)) {
			newCount += newCount + Long.valueOf(newCnt);
		}

		if (StringUtils.isNotBlank(oldCnt)) {
			oldCount += oldCount + Long.valueOf(oldCnt);
		}


		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("freshNewCnt", newCount + "");
		dataMap.put("freshOldCnt", oldCount + "");

		HBaseUtil.put("channelinfo", channelId + "->" + timeString + "", "info", dataMap);
	}
}
