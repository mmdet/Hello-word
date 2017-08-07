package com.hhzb.fntalm.card.bean;

/**
 * Created by Administrator on 2017/7/27.
 */

public class JoinRes {
    boolean success;
    int cardNo;
    float amount;
    float blance;
    String consumerPhone;
    String consumerName;
    String shopCode;
    String shopName;
    String shopPhone;
    String shopAddress;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getBlance() {
        return blance;
    }

    public void setBlance(float blance) {
        this.blance = blance;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    @Override
    public String toString() {
        return "JoinRes{" +
                "\nsuccess=" + success +
                ",\n cardNo=" + cardNo +
                ",\n amount=" + amount +
                ",\n blance=" + blance +
                ",\n consumerPhone='" + consumerPhone + '\'' +
                ",\n consumerName='" + consumerName + '\'' +
                ", \nshopCode='" + shopCode + '\'' +
                ",\n shopName='" + shopName + '\'' +
                ",\n shopPhone='" + shopPhone + '\'' +
                ", \nshopAddress='" + shopAddress + '\'' +
                '}';
    }
}
