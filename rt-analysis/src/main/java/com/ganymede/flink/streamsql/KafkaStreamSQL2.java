package com.ganymede.flink.streamsql;

import com.ganymede.flink.streamsql.map.T3Map;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple3;
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
public class KafkaStreamSQL2 {
    private final static Logger logger = LoggerFactory.getLogger(KafkaStreamSQL2.class);

    // 3,张三,5
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        properties.setProperty("group.id", "flink_2019");


        DataStreamSource<String> topic = env.addSource(new FlinkKafkaConsumer<String>("realtime", new SimpleStringSchema(), properties));

        T3Map<String,Tuple3<String,String,String>> t3Map = new T3Map<String,Tuple3<String,String,String>>();

        SingleOutputStreamOperator<?> kafkaSource = topic.map(t3Map);

        kafkaSource.timeWindowAll(Time.seconds(5l),Time.seconds(1l));

        tEnv.registerDataStream("users", kafkaSource, "id,name,part_id");


        Table result = tEnv.sqlQuery("select name,count(*) cnt from users where id>1 group by name");

        //更新
        tEnv.toRetractStream(result, Row.class).print();

        env.execute("KafkaStreamSQL");
    }

}
