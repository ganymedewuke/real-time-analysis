package com.ganymede.flink.streamsql.map;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 *
 */
public class Tuple2Map implements MapFunction<String, Tuple2> {
    @Override
    public Tuple2 map(String value) throws Exception {
        String[] split = value.split(",");

        return new Tuple2(split[0], split[1]);
    }
}


