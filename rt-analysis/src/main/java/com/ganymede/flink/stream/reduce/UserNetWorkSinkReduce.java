package com.ganymede.flink.stream.reduce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganymede.analy.ArealDistribution;
import com.ganymede.analy.Usernetwork;
import com.ganymede.flink.utils.HBaseUtil;
import com.ganymede.flink.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

public class UserNetWorkSinkReduce implements SinkFunction<Usernetwork> {

	@Override
	public void invoke(Usernetwork value, Context context) throws Exception {
		String timeString = value.getTimeString();
		String network = value.getNetwork();
		long count = value.getCount();
		long newCount = value.getNewCount();
		long oldCount = value.getOldCount();


		String networkcount = HBaseUtil.getData("userinfo", timeString, "info", "networkcount");
		String networknewcount = HBaseUtil.getData("userinfo", timeString, "info", "networknewcount");
		String networkoldcount = HBaseUtil.getData("userinfo", timeString, "info", "networkoldcount");


		Map<String, String> datamap = new HashMap<>();
		Map<String, Long> map = null;

		map = JsonUtils.dataJson2Map(networkcount, network, count);
		datamap.put("areapv", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(networknewcount, network, newCount);
		datamap.put("areauv", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(networkoldcount, network, oldCount);
		datamap.put("arealNewCnt", JSON.toJSONString(map));

		HBaseUtil.put("userinfo", timeString + "->" + network, "info", datamap);
	}

}
