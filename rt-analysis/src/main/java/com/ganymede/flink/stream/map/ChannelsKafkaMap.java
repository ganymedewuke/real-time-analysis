package com.ganymede.flink.stream.map;

import com.alibaba.fastjson.JSON;
import com.ganymede.analy.HotChannel;
import com.ganymede.flink.stream.ProcessData;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算频道排序
 */
public class ChannelsKafkaMap extends RichMapFunction<KafkaMessage, HotChannel> {
    private final static Logger logger = LoggerFactory.getLogger(ChannelsKafkaMap.class);

    @Override
    public HotChannel map(KafkaMessage value) throws Exception {
        logger.info("map进来的数据 value === : " + value);
        String jsonString = value.getJsonMessage();
        UserScanLog userScanLog = JSON.parseObject(jsonString, UserScanLog.class);

        long channelId = userScanLog.getChannelId();

        HotChannel hotChannel = new HotChannel();
        hotChannel.setChannelId(channelId);
        hotChannel.setCount(Long.parseLong(value.getCount() + ""));

        return hotChannel;
    }
}
