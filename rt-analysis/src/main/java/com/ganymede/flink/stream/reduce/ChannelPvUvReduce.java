package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ChannelPvUv;
import org.apache.flink.api.common.functions.ReduceFunction;

public class ChannelPvUvReduce implements ReduceFunction<ChannelPvUv> {
	@Override
	public ChannelPvUv reduce(ChannelPvUv value1, ChannelPvUv value2) {
		long channelId = value1.getChannelId();
		long timeStamp1 = value1.getTimeStamp();
		String timeString = value1.getTimeString();
		long pvCount1 = value1.getPvCount();
		long uvCount1 = value1.getUvCount();

		long pvCount2 = value2.getPvCount();
		long uvCount2 = value2.getUvCount();

		ChannelPvUv channelPvUv = new ChannelPvUv();
		channelPvUv.setChannelId(channelId);
		channelPvUv.setTimeStamp(timeStamp1);
		channelPvUv.setTimeString(timeString);
		channelPvUv.setPvCount(pvCount1+pvCount2);
		channelPvUv.setUvCount(uvCount1+uvCount2);
		channelPvUv.setGroupByField(value1.getGroupByField());
//		System.out.println(channelPvUv);

		return channelPvUv;
	}
}
