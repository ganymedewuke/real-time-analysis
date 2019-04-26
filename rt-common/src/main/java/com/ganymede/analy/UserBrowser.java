package com.ganymede.analy;

/**
 * 用户使用浏览器分析
 */
public class UserBrowser {
	private String browser;
	private long count;
	private long newCount;
	private long oldCount;
	private long timeStamp;
	private String timeString;

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
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

	@Override
	public String toString() {
		return "UserBrowser{" +
				"browser='" + browser + '\'' +
				", count=" + count +
				", newCount=" + newCount +
				", oldCount=" + oldCount +
				", timeStamp=" + timeStamp +
				", timeString='" + timeString + '\'' +
				'}';
	}
}
