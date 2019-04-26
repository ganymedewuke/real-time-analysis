package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.UserBrowser;
import com.ganymede.analy.UserState;
import com.ganymede.flink.dao.ChannelVisterDao;
import com.ganymede.flink.utils.DateUtil;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * 用户使用浏览器分析Map
 */
public class UserBrowserMap implements FlatMapFunction<KafkaMessage, UserBrowser> {
	@Override
	public void flatMap(KafkaMessage value, Collector<UserBrowser> out) throws Exception {
		String jsonString = value.getJsonMessage();
		long timeStamp = value.getTimeStamp();

		String hourTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMddhh"); // 小时
		String dayTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMMdd"); // 天
		String monthTimeStamp = DateUtil.getDateBy(timeStamp, "yyyyMM"); // 月

		UserScanLog userScanLog = JSON.parseObject(jsonString, UserScanLog.class);
		long userId = userScanLog.getUserId();
		String browser = userScanLog.getBrowser();

		UserState userState = ChannelVisterDao.getUserStateByVisiTime(userId + "", timeStamp);
		boolean isFirstHour = userState.isFirstHour();
		boolean isFirstDay = userState.isFirstDay();
		boolean isFirstMonth = userState.isFirstMonth();


		UserBrowser userBrowser = new UserBrowser();
		userBrowser.setBrowser(browser);
		userBrowser.setTimeStamp(timeStamp);
		userBrowser.setCount(1);


		/**
		 * 新增用户
		 */
		long newUser = 0l;
		if (userState.isNew()) {
			newUser = 1l;
		}
		userBrowser.setNewCount(newUser);


		/**
		 * oldcount
		 * 小时
		 */
		long oldUser = 0l;
		if (!userState.isNew() && isFirstHour) {
			oldUser = 1l;
		}
		userBrowser.setOldCount(oldUser);
		userBrowser.setTimeString(hourTimeStamp);
		out.collect(userBrowser);
		System.out.println("用户使用浏览器分析 " + userBrowser);

		/**
		 *  天
		 */

		if (!userState.isNew() && isFirstDay) {
			oldUser = 1l;
		}
		userBrowser.setOldCount(oldUser);
		userBrowser.setTimeString(dayTimeStamp);
		out.collect(userBrowser);
		System.out.println("用户使用浏览器分析 " + userBrowser);

		/**
		 *  月
		 */
		oldUser = 0l;
		if (!userState.isNew() && isFirstMonth) {
			oldUser = 1l;
		}
		userBrowser.setOldCount(oldUser);
		userBrowser.setTimeString(monthTimeStamp);
		out.collect(userBrowser);
		System.out.println("用户使用浏览器分析 " + userBrowser);

	}
}
