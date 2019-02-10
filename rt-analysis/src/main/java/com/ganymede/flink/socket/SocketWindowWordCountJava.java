package com.ganymede.flink.socket;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * nc -lk 9999
 */
public class SocketWindowWordCountJava {
    public static void main(String[] args) throws Exception {
        //定义连接端口
        final int port = 9999;

        //得到执行环境对象
        final StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        //连接套socket之后，读取输入data
        DataStream<String> text = executionEnvironment.socketTextStream("spark1", port, "\n");

        DataStream<WordWithCount> windowCounts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String values, Collector<WordWithCount> out) throws Exception {
                for (String word : values.split("\\s")) {
                    out.collect(new WordWithCount(word, 1L));
                }
            }
        }).keyBy("word")
                .timeWindow(Time.seconds(500), Time.seconds(1))
                .reduce(new ReduceFunction<WordWithCount>() {
                    @Override
                    public WordWithCount reduce(WordWithCount wordWithCount, WordWithCount t1) throws Exception {
                        return new WordWithCount(wordWithCount.word, wordWithCount.count + t1.count);
                    }
                });

        windowCounts.print().setParallelism(1);
        executionEnvironment.execute("Socket Window WordCount");
    }

    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        public WordWithCount() {
        }

        @Override
        public String toString() {
            return "WordWithCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
