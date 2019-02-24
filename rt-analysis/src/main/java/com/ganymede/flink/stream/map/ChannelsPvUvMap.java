package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.ChannelPvUv;
import com.ganymede.analy.UserState;
import com.ganymede.flink.dao.ChannelVisterDao;
import com.ganymede.flink.utils.DateUtil;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * 计算频道pvuv的Map
 */
public class ChannelsPvUvMap implements FlatMapFunction<KafkaMessage, ChannelPvUv> {
	@Override
	public void flatMap(KafkaMessage value, Collector<ChannelPvUv> out) throws Exception {
		String jsonString = value.getJsonMessage();
		long timeStamp = value.getTimeStamp();

		String hourTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMddhh"); // 小时
		String dayTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMdd"); // 天
		String monthTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMM"); // 月

		UserScanLog userScanLog = JSON.parseObject(jsonString, UserScanLog.class);
		long channelId = userScanLog.getChannelId();
		long userId = userScanLog.getUserId();

		UserState userState = ChannelVisterDao.getUserStateByVisiTime(userId + "", timeStamp);
		boolean isFirstHour = userState.isFirstHour();
		boolean isFirstDay = userState.isFirstDay();
		boolean isFirstMonth = userState.isFirstMonth();

		ChannelPvUv channelPvUv = new ChannelPvUv();
		channelPvUv.setChannelId(channelId);
		channelPvUv.setUserId(userId);
		channelPvUv.setPvCount((Long.valueOf(value.getCount() + "")));
		channelPvUv.setUvCount(isFirstHour == true ? 1l : 0l);
		channelPvUv.setTimeStamp(timeStamp);
		channelPvUv.setTimeString(hourTimeStamp);
		channelPvUv.setGroupByField(hourTimeStamp + channelId);
		out.collect(channelPvUv);
		System.out.println("小时==" + channelPvUv);

		//天
		channelPvUv.setUvCount(isFirstDay == true ? 1l : 0l);
		channelPvUv.setGroupByField(dayTimeStamp + channelId);
		channelPvUv.setTimeString(dayTimeStamp);
		out.collect(channelPvUv);
		System.out.println("天==" + channelPvUv);

		//月
		channelPvUv.setUvCount(isFirstMonth == true ? 1l : 0l);
		channelPvUv.setGroupByField(monthTimeStamp + channelId);
		channelPvUv.setTimeString(monthTimeStamp);
		out.collect(channelPvUv);
		System.out.println("月==" + channelPvUv);

	}
}
