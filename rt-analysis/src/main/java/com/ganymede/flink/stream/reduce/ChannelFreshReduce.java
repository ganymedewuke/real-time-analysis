package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ChannelFresh;
import com.ganymede.analy.ChannelPvUv;
import org.apache.flink.api.common.functions.ReduceFunction;

public class ChannelFreshReduce implements ReduceFunction<ChannelFresh> {
	@Override
	public ChannelFresh reduce(ChannelFresh value1, ChannelFresh value2) {
		long channelId = value1.getChannelId();
		long timeStamp1 = value1.getTimeStamp();
		String timeString = value1.getTimeString();

		long newCount1 = value1.getNewCount();
		long oldCount1 = value1.getOldCount();

		long newCount2 = value2.getNewCount();
		long oldCount2 = value2.getOldCount();

		ChannelFresh channelFresh = new ChannelFresh();
		channelFresh.setChannelId(channelId);
		channelFresh.setTimeStamp(timeStamp1);
		channelFresh.setTimeString(timeString);
		channelFresh.setNewCount(newCount1 + newCount2);
		channelFresh.setOldCount(oldCount1 + oldCount2);
		channelFresh.setGroupByField(value1.getGroupByField());

		System.out.println("ChannelFreshReduce -> " + channelFresh);

		return channelFresh;
	}
}
