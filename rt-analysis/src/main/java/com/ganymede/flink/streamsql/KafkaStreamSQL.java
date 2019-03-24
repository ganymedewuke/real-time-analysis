package com.ganymede.flink.streamsql;

import com.ganymede.flink.streamsql.map.Tuple2Map;
import com.ganymede.flink.streamsql.udf.GetJsonV;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 *
 */
public class KafkaStreamSQL {
    private final static Logger logger = LoggerFactory.getLogger(KafkaStreamSQL.class);

    // 3,张三,5
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "bigdata1:9092,bigdata2:9092,bigdata3:9092");
        properties.setProperty("group.id", "flink_2019");


        DataStreamSource<String> topic = env.addSource(new FlinkKafkaConsumer<String>("realtime", new SimpleStringSchema(), properties));

        SingleOutputStreamOperator<Tuple2> kafkaSource = topic.map(new Tuple2Map());

        kafkaSource.timeWindowAll(Time.seconds(5l),Time.seconds(1l));

        tEnv.registerFunction("getJsonV", new GetJsonV());
        tEnv.registerDataStream("users", kafkaSource, "id,name");


        Table result = tEnv.sqlQuery("select name,count(*) cnt from users where id>1 group by name");



        //更新
        tEnv.toRetractStream(result, Row.class).print();

        env.execute("KafkaStreamSQL");
    }

}
