package com.ganymede.analy;

public class UserState {
	private boolean isNew = false; //是否是新来的用户
	private boolean isFirstHour = false; // 是否是小时第一次来
	private boolean isFirstDay = false; // 是否是今天第一次来
	private boolean isFirstMonth = false; // 是否是这个月第一次来

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}

	public boolean isFirstHour() {
		return isFirstHour;
	}

	public void setFirstHour(boolean firstHour) {
		isFirstHour = firstHour;
	}

	public boolean isFirstDay() {
		return isFirstDay;
	}

	public void setFirstDay(boolean firstDay) {
		isFirstDay = firstDay;
	}

	public boolean isFirstMonth() {
		return isFirstMonth;
	}

	public void setFirstMonth(boolean firstMonth) {
		isFirstMonth = firstMonth;
	}
}
