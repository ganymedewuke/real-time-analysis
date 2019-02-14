package com.ganymede.flink.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


	/**
	 * 时间戳转换成格式化日期格式 eg yyyy-MM-dd HH:mm:ss
	 * @param timestamp
	 * @param dateFormat
	 * @return
	 */
	public static String getDateBy(long timestamp, String dateFormat) {
		Date date = new Date(timestamp);
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatDate = sdf.format(date);
		return formatDate;
	}

	/**
	 * 获取整点时间戳
	 * @param timeStamp
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static long getDateByCondition(long timeStamp, String dateFormat) throws ParseException {
		Date dateTemp = new Date(timeStamp);

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatDate = sdf.format(dateTemp);
		Date date = sdf.parse(formatDate);

		return date.getTime();
	}
}
