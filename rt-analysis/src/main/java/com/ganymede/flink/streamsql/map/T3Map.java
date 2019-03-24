package com.ganymede.flink.streamsql.map;


import org.apache.flink.api.common.functions.MapFunction;

/**
 *
 */
public class T3Map<T, R> implements MapFunction<T, R> {
    private R r;

    @Override
    public R map(T value) {
        return null;
    }

    public R getR() {
        return r;
    }

    public void setR(R r) {
        this.r = r;
    }
}


