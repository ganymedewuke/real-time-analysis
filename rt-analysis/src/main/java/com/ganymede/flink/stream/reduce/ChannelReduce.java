package com.ganymede.flink.stream.reduce;

import com.ganymede.analy.HotChannel;
import org.apache.flink.api.common.functions.ReduceFunction;

public class ChannelReduce implements ReduceFunction<HotChannel> {
    @Override
    public HotChannel reduce(HotChannel value1, HotChannel value2) {
        return new HotChannel(value1.getChannelId(), value1.getCount() + value2.getCount());
    }
}
