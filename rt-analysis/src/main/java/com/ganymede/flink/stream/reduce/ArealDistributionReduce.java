package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ArealDistribution;
import com.ganymede.analy.ChannelFresh;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * 频道地区分布 reduce
 */
public class ArealDistributionReduce implements ReduceFunction<ArealDistribution> {
	@Override
	public ArealDistribution reduce(ArealDistribution value1, ArealDistribution value2) {
		long channelId = value1.getChannelId();
		String area = value1.getArea();
		long timeStamp1 = value1.getTimeStamp();
		String timeString = value1.getTimeString();

		long newCount1 = value1.getNewCount();
		long oldCount1 = value1.getOldCount();
		long pv1 = value1.getPv();
		long uv1 = value1.getUv();

		long newCount2 = value2.getNewCount();
		long oldCount2 = value2.getOldCount();
		long pv2 = value2.getPv();
		long uv2 = value2.getUv();

		ArealDistribution arealDistribution = new ArealDistribution();
		arealDistribution.setChannelId(channelId);
		arealDistribution.setTimeStamp(timeStamp1);
		arealDistribution.setTimeString(timeString);
		arealDistribution.setArea(area);
		arealDistribution.setPv(pv1 + pv2);
		arealDistribution.setUv(uv1 + uv2);
		arealDistribution.setNewCount(newCount1 + newCount2);
		arealDistribution.setOldCount(oldCount1 + oldCount2);
		arealDistribution.setGroupByField(value1.getGroupByField());

		System.out.println("ArealDistributionReduce -> " + arealDistribution);

		return arealDistribution;
	}
}
