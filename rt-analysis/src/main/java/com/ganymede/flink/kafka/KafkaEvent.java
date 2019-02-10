package com.ganymede.flink.kafka;

public class KafkaEvent {
    private String word;
    private int fequency;
    private long timestamp;

    public KafkaEvent() {
    }

    public KafkaEvent(String word, int fequency, long timestamp) {
        this.word = word;
        this.fequency = fequency;
        this.timestamp = timestamp;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFequency() {
        return fequency;
    }

    public void setFequency(int fequency) {
        this.fequency = fequency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static KafkaEvent fromString(String eventStr) {
        String[] split = eventStr.split(",");
        return new KafkaEvent(split[0], Integer.parseInt(split[1]), Long.valueOf(split[2]));
    }

    @Override
    public String toString() {
        return "KafkaEvent{" +
                "word='" + word + '\'' +
                ", fequency=" + fequency +
                ", timestamp=" + timestamp +
                '}';
    }
}
