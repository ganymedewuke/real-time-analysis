package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.ArealDistribution;
import com.ganymede.analy.UserState;
import com.ganymede.analy.Usernetwork;
import com.ganymede.flink.dao.ChannelVisterDao;
import com.ganymede.flink.utils.DateUtil;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * 用户网络分析Map
 */
public class UsernetworkMap implements FlatMapFunction<KafkaMessage, Usernetwork> {
	@Override
	public void flatMap(KafkaMessage value, Collector<Usernetwork> out) throws Exception {
		String jsonString = value.getJsonMessage();
		long timeStamp = value.getTimeStamp();

		String hourTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMddhh"); // 小时
		String dayTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMdd"); // 天
		String monthTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMM"); // 月

		UserScanLog userScanLog = JSON.parseObject(jsonString, UserScanLog.class);
		long userId = userScanLog.getUserId();
		String network = userScanLog.getNetword();

		UserState userState = ChannelVisterDao.getUserStateByVisiTime(userId + "", timeStamp);
		boolean isFirstHour = userState.isFirstHour();
		boolean isFirstDay = userState.isFirstDay();
		boolean isFirstMonth = userState.isFirstMonth();


		Usernetwork usernetwork = new Usernetwork();
		usernetwork.setNetwork(network);
		usernetwork.setTimeStamp(timeStamp);
		usernetwork.setCount(1);


		/**
		 * 新增用户
		 */
		long newUser = 0l;
		if (userState.isNew()) {
			newUser = 1l;
		}
		usernetwork.setNewCount(newUser);


		/**
		 * oldcount
		 * 小时
		 */
		long oldUser = 0l;
		if (!userState.isNew() && isFirstHour) {
			oldUser = 1l;
		}
		usernetwork.setOldCount(oldUser);
		usernetwork.setTimeString(hourTimeStamp);
		out.collect(usernetwork);
		System.out.println("用户网络分析 " + usernetwork);

		/**
		 *  天
		 */

		if (!userState.isNew() && isFirstDay) {
			oldUser = 1l;
		}
		usernetwork.setOldCount(oldUser);
		usernetwork.setTimeString(dayTimeStamp);
		out.collect(usernetwork);
		System.out.println("用户网络分析 " + usernetwork);

		/**
		 *  月
		 */
		oldUser = 0l;
		if (!userState.isNew() && isFirstMonth) {
			oldUser = 1l;
		}
		usernetwork.setOldCount(oldUser);
		usernetwork.setTimeString(monthTimeStamp);
		out.collect(usernetwork);
		System.out.println("频道地区分布月 " + usernetwork);

	}
}
