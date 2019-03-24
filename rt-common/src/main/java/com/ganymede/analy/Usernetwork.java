package com.ganymede.analy;

public class Usernetwork {
	private String network;
	private long count;
	private long newCount;
	private long oldCount;
	private long timeStamp;
	private String timeString;

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
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

	@Override
	public String toString() {
		return "Usernetwork{" +
				"network='" + network + '\'' +
				", count=" + count +
				", newCount=" + newCount +
				", oldCount=" + oldCount +
				", timeStamp=" + timeStamp +
				", timeString='" + timeString + '\'' +
				'}';
	}
}
