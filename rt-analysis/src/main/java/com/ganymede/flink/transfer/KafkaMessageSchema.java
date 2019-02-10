package com.ganymede.flink.transfer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganymede.input.KafkaMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class KafkaMessageSchema implements DeserializationSchema<KafkaMessage>, SerializationSchema<KafkaMessage> {
    @Override
    public KafkaMessage deserialize(byte[] message) throws IOException {
        String jsonString = new String(message);
        KafkaMessage kafkaMessage = JSON.parseObject(jsonString,KafkaMessage.class);
        return kafkaMessage;
    }

    @Override
    public boolean isEndOfStream(KafkaMessage kafkaMessage) {
        return false;
    }

    @Override
    public byte[] serialize(KafkaMessage kafkaMessage) {
        String jsonString = JSONObject.toJSONString(kafkaMessage);
        return jsonString.getBytes();
    }

    @Override
    public TypeInformation<KafkaMessage> getProducedType() {
        return TypeInformation.of(KafkaMessage.class);
    }
}
