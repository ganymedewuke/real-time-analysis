package com.ganymede.flink.streamsql;


import com.alibaba.fastjson.JSON;
import com.ganymede.flink.transfer.KafkaMessageSchema;
import com.ganymede.flink.transfer.KafkaMessageWatermarks;
import com.ganymede.input.KafkaMessage;
import com.ganymede.log.UserScanLog;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 *
 */
public class KafkaStreamSQLJson2 {
	public static void main(String[] args) throws Exception {
		args = new String[]{"--input-topic", "realtime", "--bootstrap.servers", "bigdata1:9092,bigdata2:9092,bigdata3:9092",
				"--zookeeper.connect", "bigdata1:2181,bigdata2:2181,bigdata3:2181",
				"--group.id", "ProcessData_20190311_1",
				"--windows.size", "1", "--windows.slide", "1"};

		final ParameterTool parameterTool = ParameterTool.fromArgs(args);

		if (parameterTool.getNumberOfParameters() < 6) {
			System.out.println("Missing parameters! \n" +
					"Usage : Kafka --input-topic <topic>  " +
					"--zookeeper.connect <zk quorum> --group.id <some id>");
			return;
		}


		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.getConfig().disableSysoutLogging();
		env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
		env.enableCheckpointing(5000);
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


		StreamTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);

		FlinkKafkaConsumer flinkKafkaConsumer = new FlinkKafkaConsumer<>(parameterTool.getRequired("input-topic"), new KafkaMessageSchema(), parameterTool.getProperties());

		// 获取数据流，注意：实时为 DataStream, 批处理为 DataSet
		DataStreamSource<KafkaMessage> topic = env.addSource(flinkKafkaConsumer.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()));


		env.execute("KafkaStreamSQLJson");
	}

	public static class Part{
		private String part_id;
		private String part_name;

		public Part() {

		}
		public Part(String part_id, String part_name) {
			this.part_id = part_id;
			this.part_name = part_name;
		}

		public String getPart_id() {
			return part_id;
		}

		public void setPart_id(String part_id) {
			this.part_id = part_id;
		}

		public String getPart_name() {
			return part_name;
		}

		public void setPart_name(String part_name) {
			this.part_name = part_name;
		}
	}

}
