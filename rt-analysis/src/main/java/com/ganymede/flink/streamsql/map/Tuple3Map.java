package com.ganymede.flink.streamsql.map;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 *
 */
public class Tuple3Map implements MapFunction<String, Tuple3<String, String, String>> {
    @Override
    public Tuple3<String, String, String> map(String value) throws Exception {
        String[] split = value.split(",");

        return new Tuple3(split[0], split[1], split[2]);
    }
}


