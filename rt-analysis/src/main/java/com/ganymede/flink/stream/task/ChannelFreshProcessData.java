package com.ganymede.flink.stream.task;

import com.ganymede.analy.ChannelFresh;
import com.ganymede.flink.stream.map.ChannelFreshMap;
import com.ganymede.flink.stream.reduce.ChannelFreshReduce;
import com.ganymede.flink.stream.reduce.ChannelsFreshSinkReduce;
import com.ganymede.flink.transfer.KafkaMessageSchema;
import com.ganymede.flink.transfer.KafkaMessageWatermarks;
import com.ganymede.input.KafkaMessage;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 频道新鲜度
 */
public class ChannelFreshProcessData {
	private final static Logger logger = LoggerFactory.getLogger(ChannelFreshProcessData.class);

	public static void main(String[] args) throws Exception {
		args = new String[]{"--input-topic", "test", "--bootstrap.servers", "spark1:9092,spark2:9092,spark3:9092",
				"--zookeeper.connect", "spark1:2181,spark2:2181,spark3:2181",
				"--group.id", "ProcessData_20190217",
				"--windows.size", "1", "--windows.slide", "1"};

		final ParameterTool parameterTool = ParameterTool.fromArgs(args);

		if (parameterTool.getNumberOfParameters() < 6) {
			System.out.println("Missing parameters! \n" +
					"Usage : Kafka --input-topic <topic>  " +
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


		FlinkKafkaConsumer010 flinkKafkaConsumer010 = new FlinkKafkaConsumer010<>(parameterTool.getRequired("input-topic"), new KafkaMessageSchema(), parameterTool.getProperties());

		// 获取数据流，注意：实时为 DataStream, 批处理为 DataSet
		DataStream<KafkaMessage> input = env.addSource(flinkKafkaConsumer010.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()));

		DataStream<ChannelFresh> map = input.flatMap(new ChannelFreshMap());


		DataStream<ChannelFresh> reduce = map.keyBy("groupByField").
				countWindow(Long.valueOf(parameterTool.getRequired("windows.size"))).
				reduce(new ChannelFreshReduce());

		//打印reduce的内容
//		reduce.print();
		reduce.addSink(new ChannelsFreshSinkReduce()).name("HotChannelReduce");

		env.execute("ChannelFresh");
	}
}
