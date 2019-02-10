package com.ganymede.input;

public class KafkaMessage {
    private String jsonMessage; // json格式的消息内容

    private int count; // 消息的次数

    private Long timeStamp; //消息的时间

    public String getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "jsonMessage='" + jsonMessage + '\'' +
                ", count=" + count +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
