package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.ChannelPvUv;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.RichMapFunction;

/**
 * 计算频道pvuv的Map
 */
public class ChannelsPvUvMap extends RichMapFunction<KafkaMessage, ChannelPvUv> {
	@Override
	public ChannelPvUv map(KafkaMessage value) throws Exception {
		String jsonString = value.getJsonMessage();
		long timeStamp = value.getTimeStamp();

		UserScanLog userScanLog = JSON.parseObject(jsonString, UserScanLog.class);
		long channelId = userScanLog.getChannelId();
		long userId = userScanLog.getUserId();

		ChannelPvUv channelPvUv = new ChannelPvUv();
		channelPvUv.setChannelId(channelId);
		channelPvUv.setUserId(userId);
		channelPvUv.setPvCount((Long.valueOf(value.getCount() + "")));

		return channelPvUv;
	}
}
