package com.ganymede.flink.stream.reduce;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.UserBrowser;
import com.ganymede.flink.utils.HBaseUtil;
import com.ganymede.flink.utils.JsonUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

public class UserBrowserSinkReduce implements SinkFunction<UserBrowser> {

	@Override
	public void invoke(UserBrowser value, Context context) throws Exception {
		String timeString = value.getTimeString();
		long count = value.getCount();
		long newCount = value.getNewCount();
		long oldCount = value.getOldCount();
		String browser = value.getBrowser();

		String browsercount = HBaseUtil.getData("userinfo", timeString, "info", "browsercount");
		String browsernewcount = HBaseUtil.getData("userinfo", timeString, "info", "browsernewcount");
		String browseroldcount = HBaseUtil.getData("userinfo", timeString, "info", "browseroldcount");


		Map<String, String> datamap = new HashMap<>();
		Map<String, Long> map = null;

		map = JsonUtils.dataJson2Map(browsercount, browser, count);
		datamap.put("areapv", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(browsernewcount, browser, newCount);
		datamap.put("areauv", JSON.toJSONString(map));

		map = JsonUtils.dataJson2Map(browseroldcount, browser, oldCount);
		datamap.put("arealNewCnt", JSON.toJSONString(map));

		HBaseUtil.put("userinfo", timeString + "->" + browser, "info", datamap);
	}

}
