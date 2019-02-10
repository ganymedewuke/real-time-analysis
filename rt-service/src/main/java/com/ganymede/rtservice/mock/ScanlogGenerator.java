package com.ganymede.rtservice.mock;

import com.alibaba.fastjson.JSONObject;
import com.ganymede.log.UserScanLog;
import com.ganymede.rtservice.utils.UrlsendUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ScanlogGenerator {
    private static Long[] channels = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l}; //频道id集合
    private static Long[] categories = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l}; //产品类别id集合
    private static Long[] productids = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l}; //产品id集合
    private static Long[] userIds = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l}; //用户id集合

    /**
     * 地区
     */
    private static String[] countrys = new String[]{"America", "China"}; // 地区-国家集合
    private static String[] provinces = new String[]{"America", "China"}; // 地区-省集合
    private static String[] citys = new String[]{"America", "China"}; // 地区-市集合


    /**
     * 网络方式
     */
    private static String[] networks = new String[]{"电信", "移动", "联通"};

    /**
     * 来源方式
     */
    private static String[] sources = new String[]{"直接输入", "百度跳转", "360搜索跳转", "必应跳转"};

    /**
     * 浏览器
     */
    private static String[] browsers = new String[]{"火狐", "QQ浏览器", "360极速", "谷歌浏览器"};


    private static List<Long[]> useTimelog = new ScanlogGenerator().producetimes();

    private List<Long[]> producetimes() {
        List<Long[]> useTimelog = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Long[] timesArray = getTimes("2018-08-08 15:11:45:015");
            useTimelog.add(timesArray);
        }
        return useTimelog;
    }

    /**
     * 打开时间，离开时间
     */
    private Long[] getTimes(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        try {
            Date date = dateFormat.parse(time);
            long dateTime = date.getTime();
            Random random = new Random();
            int randomInt = random.nextInt(10);

            long starttime = dateTime - randomInt * 3600 * 1000;
            long endttime = starttime + randomInt * 3600 * 1000;
            return new Long[]{starttime, endttime};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 35; i++) {
            //频道id 类别id 产品id 用户id 打开时间 离开时间 地区 网络方式 来源方式 浏览器
            UserScanLog userScanLog = new UserScanLog();
            userScanLog.setChannelId(channels[random.nextInt(channels.length)]);
            userScanLog.setCategoryId(categories[random.nextInt(categories.length)]);
            userScanLog.setProductId(productids[random.nextInt(productids.length)]);
            userScanLog.setUserId(userIds[random.nextInt(userIds.length)]);

            userScanLog.setCountry(countrys[random.nextInt(countrys.length)]);
            userScanLog.setProvince(provinces[random.nextInt(provinces.length)]);
            userScanLog.setCity(citys[random.nextInt(citys.length)]);


            userScanLog.setNetword(networks[random.nextInt(networks.length)]);
            userScanLog.setSource(sources[random.nextInt(sources.length)]);
            userScanLog.setBrowser(browsers[random.nextInt(browsers.length)]);

            Long[] times = useTimelog.get(random.nextInt(useTimelog.size()));
            userScanLog.setStartTime(times[0]);
            userScanLog.setEndTime(times[1]);

            String jsonStr = JSONObject.toJSONString(userScanLog);
            System.out.println(jsonStr);

            UrlsendUtil.sendMessage("http://localhost:8088/RtInfoCollectService/webInfoColService", jsonStr);
        }

    }
}
