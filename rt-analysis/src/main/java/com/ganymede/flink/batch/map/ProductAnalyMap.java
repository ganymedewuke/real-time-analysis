package com.ganymede.flink.batch.map;

import com.alibaba.fastjson.JSONObject;
import com.ganymede.batch.OrderInfo;
import com.ganymede.batch.ProductAnalyData;
import com.ganymede.flink.utils.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.Date;

public class ProductAnalyMap implements FlatMapFunction<String, ProductAnalyData> {
	@Override
	public void flatMap(String value, Collector<ProductAnalyData> out) throws Exception {
		OrderInfo orderInfo = JSONObject.parseObject(value, OrderInfo.class);
		long productId = orderInfo.getProductId();
		Date createTime = orderInfo.getCreateTime();
		String timeString = DateUtil.getDateBy(createTime.getTime(), "yyyyMM");
		Date payTime = orderInfo.getPayTime();

		long dealCnt = 0l; //成交
		long notDeal = 0l; //未成交

		if (payTime != null) {
			dealCnt = 1l;
		} else {
			notDeal = 1l;
		}

		ProductAnalyData productAnalyData = new ProductAnalyData();
		productAnalyData.setProductId(productId);
		productAnalyData.setDateString(timeString);
		productAnalyData.setDealCnt(dealCnt);
		productAnalyData.setNotDeal(notDeal);

		out.collect(productAnalyData);
	}
}
