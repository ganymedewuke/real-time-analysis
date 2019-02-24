package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.ChannelFresh;
import com.ganymede.analy.UserState;
import com.ganymede.flink.dao.ChannelVisterDao;
import com.ganymede.flink.utils.DateUtil;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class ChannelFreshMap implements FlatMapFunction<KafkaMessage, ChannelFresh> {
	@Override
	public void flatMap(KafkaMessage value, Collector<ChannelFresh> out) throws Exception {
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


		ChannelFresh channelFresh = new ChannelFresh();
		channelFresh.setChannelId(channelId);
		channelFresh.setTimeStamp(timeStamp);

		/**
		 * 新增用户
		 */
		long newUser = 0l;
		if (userState.isNew()) {
			newUser = 1l;
		}
		channelFresh.setNewCount(newUser);


		/**
		 * 老用户 小时
		 */
		long oldUser = 0l;
		if (!userState.isNew() && isFirstHour) {
			oldUser = 1l;
		}
		channelFresh.setOldCount(oldUser);
		channelFresh.setTimeString(hourTimeStamp);
		channelFresh.setGroupByField(hourTimeStamp + channelId);
		out.collect(channelFresh);
		System.out.println("频道新鲜度小时 " + channelFresh);

		/**
		 * 老用户 天
		 */
		oldUser = 0l;
		if (!userState.isNew() && isFirstDay) {
			oldUser = 1l;
		}
		channelFresh.setOldCount(oldUser);
		channelFresh.setTimeString(dayTimeStamp);
		channelFresh.setGroupByField(dayTimeStamp + channelId);
		out.collect(channelFresh);
		System.out.println("频道新鲜度天 " + channelFresh);

		/**
		 * 老用户 月
		 */
		oldUser = 0l;
		if (!userState.isNew() && isFirstMonth) {
			oldUser = 1l;
		}
		channelFresh.setOldCount(oldUser);
		channelFresh.setTimeString(monthTimeStamp);
		channelFresh.setGroupByField(monthTimeStamp + channelId);
		out.collect(channelFresh);
		System.out.println("频道新鲜度月 " + channelFresh);

	}
}
