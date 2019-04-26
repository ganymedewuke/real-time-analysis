package com.ganymede.batch;

import java.util.Date;

public class OrderInfo {
	private long orderId;
	private long userId;
	private long merchantId;
	private double orderAmount;
	private long payType;
	private Date payTime;
	private double gitfAmout;
	private double chitAmout;
	private long productId;
	private long activityNumber;
	private Date createTime;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public long getPayType() {
		return payType;
	}

	public void setPayType(long payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public double getGitfAmout() {
		return gitfAmout;
	}

	public void setGitfAmout(double gitfAmout) {
		this.gitfAmout = gitfAmout;
	}

	public double getChitAmout() {
		return chitAmout;
	}

	public void setChitAmout(double chitAmout) {
		this.chitAmout = chitAmout;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(long activityNumber) {
		this.activityNumber = activityNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrderInfo{" +
				"orderId=" + orderId +
				", userId=" + userId +
				", merchantId=" + merchantId +
				", orderAmount=" + orderAmount +
				", payType=" + payType +
				", payTime=" + payTime +
				", gitfAmout=" + gitfAmout +
				", chitAmout=" + chitAmout +
				", productId=" + productId +
				", activityNumber=" + activityNumber +
				", createTime=" + createTime +
				'}';
	}
}
