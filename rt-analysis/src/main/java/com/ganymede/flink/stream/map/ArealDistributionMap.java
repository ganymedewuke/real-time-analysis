package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.ArealDistribution;
import com.ganymede.analy.UserState;
import com.ganymede.flink.dao.ChannelVisterDao;
import com.ganymede.flink.utils.DateUtil;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 *  频道地区分布 map
 */
public class ArealDistributionMap implements FlatMapFunction<KafkaMessage, ArealDistribution> {
	@Override
	public void flatMap(KafkaMessage value, Collector<ArealDistribution> out) throws Exception {
		String jsonString = value.getJsonMessage();
		long timeStamp = value.getTimeStamp();

		String hourTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMddhh"); // 小时
		String dayTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMdd"); // 天
		String monthTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMM"); // 月

		UserScanLog userScanLog = JSON.parseObject(jsonString, UserScanLog.class);
		long channelId = userScanLog.getChannelId();
		long userId = userScanLog.getUserId();
		String city = userScanLog.getCity(); //城市


		UserState userState = ChannelVisterDao.getUserStateByVisiTime(userId + "", timeStamp);
		boolean isFirstHour = userState.isFirstHour();
		boolean isFirstDay = userState.isFirstDay();
		boolean isFirstMonth = userState.isFirstMonth();


		ArealDistribution arealDistribution = new ArealDistribution();
		arealDistribution.setChannelId(channelId);
		arealDistribution.setTimeStamp(timeStamp);
		arealDistribution.setArea(city);

		arealDistribution.setPv(1l);

		/**
		 * 新增用户
		 */
		long newUser = 0l;
		if (userState.isNew()) {
			newUser = 1l;
		}
		arealDistribution.setNewCount(newUser);


		/**
		 * 小时
		 */
		long uvCount = 0l;
		if(isFirstHour){
			uvCount = 1l;
		}
		arealDistribution.setUv(uvCount);

		long oldUser = 0l;
		if (!userState.isNew() && isFirstHour) {
			oldUser = 1l;
		}
		arealDistribution.setOldCount(oldUser);
		arealDistribution.setTimeString(hourTimeStamp);
		arealDistribution.setGroupByField(hourTimeStamp + channelId);
		out.collect(arealDistribution);
		System.out.println("频道地区分布小时 " + arealDistribution);

		/**
		 *  天
		 */
		uvCount = 0l;
		oldUser = 0l;
		if(isFirstDay){
			uvCount = 1l;
		}
		arealDistribution.setUv(uvCount);

		if (!userState.isNew() && isFirstDay) {
			oldUser = 1l;
		}
		arealDistribution.setOldCount(oldUser);
		arealDistribution.setTimeString(dayTimeStamp);
		arealDistribution.setGroupByField(dayTimeStamp + channelId);
		out.collect(arealDistribution);
		System.out.println("频道地区分布天 " + arealDistribution);

		/**
		 *  月
		 */
		uvCount = 0l;
		if(isFirstMonth){
			uvCount = 1l;
		}
		arealDistribution.setUv(uvCount);

		oldUser = 0l;
		if (!userState.isNew() && isFirstMonth) {
			oldUser = 1l;
		}
		arealDistribution.setOldCount(oldUser);
		arealDistribution.setTimeString(monthTimeStamp);
		arealDistribution.setGroupByField(monthTimeStamp + channelId);
		out.collect(arealDistribution);
		System.out.println("频道地区分布月 " + arealDistribution);

	}
}
