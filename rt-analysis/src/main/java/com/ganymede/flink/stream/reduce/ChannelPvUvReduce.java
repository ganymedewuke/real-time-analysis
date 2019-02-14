package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ChannelPvUv;
import com.ganymede.analy.HotChannel;
import org.apache.flink.api.common.functions.ReduceFunction;

public class ChannelPvUvReduce implements ReduceFunction<ChannelPvUv> {
	@Override
	public ChannelPvUv reduce(ChannelPvUv value1, ChannelPvUv value2) {
		long channelId = value1.getChannelId();
		long userId = value1.getUserId();
		long timeStamp1 = value1.getTimeStamp();
		long pvCount1 = value1.getPvCount();
		long uvCount1 = value1.getUvCount();

		long timeStamp2 = value2.getTimeStamp();
		long pvCount2 = value2.getPvCount();
		long uvCount2 = value2.getUvCount();

		ChannelPvUv channelPvUv = new ChannelPvUv();
		channelPvUv.setChannelId(channelId);
		channelPvUv.setUserId(userId);

		channelPvUv.setTimeStamp(timeStamp1);
		channelPvUv.setPvCount(pvCount1 + pvCount2);

		return null;
	}
}
