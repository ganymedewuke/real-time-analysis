package com.ganymede.analy;

/**
 * 频道热点
 */
public class HotChannel {
    private Long channelId;
    private Long count;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public HotChannel(Long channelId, Long count) {
        this.channelId = channelId;
        this.count = count;
    }

    public HotChannel() {
    }
}
