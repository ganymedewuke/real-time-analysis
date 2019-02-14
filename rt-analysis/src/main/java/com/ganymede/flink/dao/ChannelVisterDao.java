package com.ganymede.flink.dao;

import com.ganymede.analy.UserState;
import com.ganymede.flink.utils.DateUtil;
import com.ganymede.flink.utils.HBaseUtil;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ChannelVisterDao {

	/**
	 * 查询本次用户的访问状态
	 *
	 * @param userId
	 * @param timeStamp
	 * @return
	 * @throws ParseException
	 */
	public UserState getUserStateByVisiTime(String userId, long timeStamp) throws ParseException {
		UserState userState = new UserState();
		try {
			String result = HBaseUtil.getData("baseuserscaninfo", "userId", "time", "firstVistTime");

			if (result == null) { //第一次访问
				Map<String, String> dataMap = new HashMap<>();
				dataMap.put("firstVistTime", timeStamp + "");
				dataMap.put("lastVistTime", timeStamp + "");

				HBaseUtil.put("baseuserscaninfo", "userId", "time", dataMap);
				userState.setNew(true);
				userState.setFirstDay(true);
				userState.setFirstHour(true);
				userState.setFirstMonth(true);
			} else {

				String lastVistTimeStr = HBaseUtil.getData("baseuserscaninfo", "userId", "time", "lastVistTime");

				if (StringUtils.isNotBlank(lastVistTimeStr)) {
					//正点小时时间戳
					long timePort = DateUtil.getDateByCondition(timeStamp, "yyyyMMddHH");
					long lastVistTime = Long.valueOf(lastVistTimeStr);

					if (lastVistTime < timePort) {
						userState.setFirstHour(true);
					}

					//天
					timePort = DateUtil.getDateByCondition(timeStamp, "yyyyMMdd");
					if (lastVistTime < timePort) {
						userState.setFirstDay(true);
					}

					//月
					timePort = DateUtil.getDateByCondition(timeStamp, "yyyyMM");
					if (lastVistTime < timePort) {
						userState.setFirstMonth(true);
					}
				}

				//更新最后一次访问时间
				HBaseUtil.putData("baseuserscaninfo", "userId", "time", "lastVistTime", timeStamp + "");
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
		return userState;
	}
}
