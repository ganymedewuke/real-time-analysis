package com.ganymede.flink.transfer;

import com.ganymede.input.KafkaMessage;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

public class KafkaMessageWatermarks implements AssignerWithPeriodicWatermarks<KafkaMessage> {

    private long currentTimestamp = Long.MIN_VALUE;

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
    }

    @Override
    public long extractTimestamp(KafkaMessage element, long previousElementTimestamp) {
        this.currentTimestamp = element.getTimeStamp();
        return element.getTimeStamp();
    }
}
