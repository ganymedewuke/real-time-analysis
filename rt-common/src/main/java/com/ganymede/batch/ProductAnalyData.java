package com.ganymede.batch;

public class ProductAnalyData {
	private long productId;
	private String dateString;
	private long chengjiao;
	private long dealCnt;
	private long notDeal;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public long getChengjiao() {
		return chengjiao;
	}

	public void setChengjiao(long chengjiao) {
		this.chengjiao = chengjiao;
	}

	public long getDealCnt() {
		return dealCnt;
	}

	public void setDealCnt(long dealCnt) {
		this.dealCnt = dealCnt;
	}

	public long getNotDeal() {
		return notDeal;
	}

	public void setNotDeal(long notDeal) {
		this.notDeal = notDeal;
	}
}
