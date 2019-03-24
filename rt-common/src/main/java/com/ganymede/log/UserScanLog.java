package com.ganymede.log;

import com.ganymede.data.TableData;

public class UserScanLog implements TableData {

    private Long channelId; //频道
    private Long categoryId; //产品类别id
    private Long productId; //产品id
    private Long userId;  //用户id
    private String country; //国家
    private String province; //省份
    private String city;  //城市

    private String netword; //网络方式
    private String source; //来源方式
    private String browser; //浏览器类型

    private Long startTime; //打开时间
    private Long endTime; //离开时间

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetword() {
        return netword;
    }

    public void setNetword(String netword) {
        this.netword = netword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "UserScanLog{" +
                "channelId=" + channelId +
                ", categoryId=" + categoryId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", netword='" + netword + '\'' +
                ", source='" + source + '\'' +
                ", browser='" + browser + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
