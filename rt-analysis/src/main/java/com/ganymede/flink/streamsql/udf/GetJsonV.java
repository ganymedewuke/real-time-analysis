package com.ganymede.flink.streamsql.udf;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * created by wuke on 2019/3/24
 */
public class GetJsonV extends ScalarFunction {

    public GetJsonV() {
    }

    public String eval(String context ,String key) {
        JSONObject jsonObject = JSONObject.parseObject(key);
        return jsonObject.getString(key);
    }

    public static void main(String[] args) {
        String str = "{\"browser\":\"谷歌浏览器\",\"categoryId\":6,\"channelId\":7,\"city\":\"hangzhou\",\"country\":\"America\",\"endTime\":1533712305015,\"netword\":\"移动\",\"productId\":2,\"province\":\"China\",\"source\":\"直接输入\",\"startTime\":1533679905015,\"userId\":11}";
        String res = eval(stre,"")
}
}
