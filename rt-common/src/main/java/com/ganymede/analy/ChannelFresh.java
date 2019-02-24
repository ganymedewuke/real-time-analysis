package com.ganymede.analy;

public class ChannelFresh {
	private long channelId;
	private long newCount;
	private long oldCount;
	private long timeStamp;
	private String timeString;
	private String groupByField;

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public long getNewCount() {
		return newCount;
	}

	public void setNewCount(long newCount) {
		this.newCount = newCount;
	}

	public long getOldCount() {
		return oldCount;
	}

	public void setOldCount(long oldCount) {
		this.oldCount = oldCount;
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
		return "ChannelFresh{" +
				"channelId=" + channelId +
				", newCount=" + newCount +
				", oldCount=" + oldCount +
				", timeStamp=" + timeStamp +
				", timeString='" + timeString + '\'' +
				", groupByField='" + groupByField + '\'' +
				'}';
	}
}
