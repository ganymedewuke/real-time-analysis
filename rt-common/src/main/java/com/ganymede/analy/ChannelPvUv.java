package com.ganymede.analy;

public class ChannelPvUv {
	private long channelId;
	private long userId;
	private long pvCount;
	private long uvCount;
	private long timeStamp;
	private String timeString;
	private String groupByField;

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

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getGroupByField() {
		return groupByField;
	}

	public void setGroupByField(String groupByField) {
		this.groupByField = groupByField;
	}

	@Override
	public String toString() {
		return "ChannelPvUv{" +
				"channelId=" + channelId +
				", userId=" + userId +
				", pvCount=" + pvCount +
				", uvCount=" + uvCount +
				", timeStamp=" + timeStamp +
				", timeString='" + timeString + '\'' +
				", groupByField='" + groupByField + '\'' +
				'}';
	}
}
