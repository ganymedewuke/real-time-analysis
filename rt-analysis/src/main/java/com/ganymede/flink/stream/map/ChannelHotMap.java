package com.ganymede.flink.stream.map;

import com.ganymede.analy.HotChannel;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;

public class ChannelHotMap extends RichMapFunction<HotChannel, HotChannel> {

    private transient ValueState<Long> currentTotalCount;

    @Override
    public HotChannel map(HotChannel value) throws Exception {
        long totalcount = currentTotalCount.value();

        Long count = value.getCount();

        if(count == null){
            count = 0l;
        }
        totalcount += count;
        currentTotalCount.update(totalcount);

        HotChannel hotChannel = new HotChannel();
        hotChannel.setChannelId(value.getChannelId());
        hotChannel.setCount(Long.valueOf(value.getCount()+""));

        return null;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        currentTotalCount = getRuntimeContext().getState(new ValueStateDescriptor<Long>("currentTotalCount", Long.class));
    }
}
