package com.ganymede.flink.kafka;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;

import javax.annotation.Nullable;
import java.util.Date;

public class Kafka2KafkaExample {
    public static void main(String[] args) throws Exception {
        args = new String[]{"--input-topic", "test", "--output-topic", "test2", "--bootstrap.servers", "spark1:9092,spark2:9092,spark3:9092",
                "--zookeeper.connect", "spark1:2181,spark2:2181,spark3:2181",
                "--group.id", "myConsumer1"};

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        if (parameterTool.getNumberOfParameters() < 5) {
            System.out.println("Missing parameters! \n" +
                    "Usage : Kafka --input-topic <topic> --output-topic <topic> " +
                    "--zookeeper.connect <zk quorum> --group.id <some id>");
            return;
        }

        //flink环境变量
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(5000);
        env.getConfig().setGlobalJobParameters(parameterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<KafkaEvent> input = env.addSource(new FlinkKafkaConsumer010<KafkaEvent>(parameterTool.getRequired("input-topic"),
                new KafkaEventSchema(),
                parameterTool.getProperties())).assignTimestampsAndWatermarks(new CustomWatermarkExtractor())
                .keyBy("word")
                .map(new RollingAdditionMapper());

        //写入结果topic
        input.addSink(new FlinkKafkaProducer010<KafkaEvent>(parameterTool.getRequired("output-topic"),
                new KafkaEventSchema(), parameterTool.getProperties()));

        //启动流
        env.execute("kafka 2 t kafka");
    }

    private static class RollingAdditionMapper extends RichMapFunction<KafkaEvent, KafkaEvent> {
        private transient ValueState<Integer> currentTotalCount;

        @Override
        public KafkaEvent map(KafkaEvent kafkaEvent) throws Exception {
            Integer totalCount = currentTotalCount.value();

            if (totalCount == null) {
                totalCount = 0;
            }

            totalCount += kafkaEvent.getFequency();
            currentTotalCount.update(totalCount);

            return new KafkaEvent(kafkaEvent.getWord(), totalCount, new Date().getTime());
        }

        @Override
        public void open(Configuration parameters) throws Exception {
            currentTotalCount = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("currentTotalCount", Integer.class));
        }
    }

    private static class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<KafkaEvent> {
        private long currentTimestamp = Long.MIN_VALUE;

        @Nullable
        @Override
        public Watermark getCurrentWatermark() {
            return new Watermark(currentTimestamp == Long.MAX_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
        }

        @Override
        public long extractTimestamp(KafkaEvent element, long previousElementTimestamp) {
            this.currentTimestamp = element.getTimestamp();
            return element.getTimestamp();
        }
    }
}
