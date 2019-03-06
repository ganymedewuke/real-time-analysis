package com.ganymede.analy;

public class ArealDistribution {
	private long channelId;
	private String area; // 地区
	private long pv;
	private long uv;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getPv() {
		return pv;
	}

	public void setPv(long pv) {
		this.pv = pv;
	}

	public long getUv() {
		return uv;
	}

	public void setUv(long uv) {
		this.uv = uv;
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
		return "ChannelArealDistribution{" +
				"channelId=" + channelId +
				", area='" + area + '\'' +
				", pv=" + pv +
				", uv=" + uv +
				", newCount=" + newCount +
				", oldCount=" + oldCount +
				", timeStamp=" + timeStamp +
				", timeString='" + timeString + '\'' +
				", groupByField='" + groupByField + '\'' +
				'}';
	}
}
