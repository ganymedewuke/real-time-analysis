package com.ganymede.analy;

public class ChannelPvUv {
	private long channelId;
	private long userId;
	private long pvCount;
	private long uvCount;
	private long timeStamp;

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPvCount() {
		return pvCount;
	}

	public void setPvCount(long pvCount) {
		this.pvCount = pvCount;
	}

	public long getUvCount() {
		return uvCount;
	}

	public void setUvCount(long uvCount) {
		this.uvCount = uvCount;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
