package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.ChannelPvUv;
import com.ganymede.analy.Usernetwork;
import org.apache.flink.api.common.functions.ReduceFunction;

public class UserNetworkReduce implements ReduceFunction<Usernetwork> {
	@Override
	public Usernetwork reduce(Usernetwork value1, Usernetwork value2) {
		System.out.println(value1);
		System.out.println(value2);

		long timeStamp1 = value1.getTimeStamp();
		String timeString = value1.getTimeString();
		long countValue1 = value1.getCount();
		long networkValue1 = value1.getNewCount();
		long oldCountValue1 = value1.getOldCount();

		long countValue2 = value2.getCount();
		long networkValue2 = value2.getNewCount();
		long oldCountValue2 = value2.getOldCount();


		Usernetwork usernetwork = new Usernetwork();
		usernetwork.setTimeStamp(timeStamp1);
		usernetwork.setTimeString(timeString);
		usernetwork.setCount(countValue1 + countValue2);
		usernetwork.setNewCount(networkValue1 + networkValue2);
		usernetwork.setOldCount(oldCountValue1 + oldCountValue2);
		System.out.println(usernetwork);

		return usernetwork;
	}
}
