package com.ganymede.rtservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
    private final static Logger logger = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("kafka的key : {}", record.key());
        logger.info("kafka的value ： {}", record.value());
    }
}
