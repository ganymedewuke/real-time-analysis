package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.UserBrowser;
import org.apache.flink.api.common.functions.ReduceFunction;

public class UserBrowserReduce implements ReduceFunction<UserBrowser> {
	@Override
	public UserBrowser reduce(UserBrowser value1, UserBrowser value2) {
		long timeStamp1 = value1.getTimeStamp();
		String timeString = value1.getTimeString();
		long countValue1 = value1.getCount();
		long networkValue1 = value1.getNewCount();
		long oldCountValue1 = value1.getOldCount();

		long countValue2 = value2.getCount();
		long networkValue2 = value2.getNewCount();
		long oldCountValue2 = value2.getOldCount();


		UserBrowser userBrowser = new UserBrowser();
		userBrowser.setTimeStamp(timeStamp1);
		userBrowser.setTimeString(timeString);
		userBrowser.setCount(countValue1 + countValue2);
		userBrowser.setNewCount(networkValue1 + networkValue2);
		userBrowser.setOldCount(oldCountValue1 + oldCountValue2);
		System.out.println("reduce --userBrowser " + userBrowser);

		return userBrowser;
	}
}
