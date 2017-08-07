package com.hhzb.fntalm.card.bean;

/**
 * 联盟卡信息
 * Created by Administrator on 2017/7/24.
 */

public class JoinCard {

    /// 读卡的终端号
    public String terminalCode;
    public int id ;
    public String cardNo ;
    public int cardRfic ;
    public int branchId ;
    public int status ;
    public String assignDate ;
    public String sellDate ;
    public String lastConsumeDate ;
    public String validateDate;
    /// 面额
    public float faceAmount ;
    /// 余额
    public float balance ;
    /// 折扣率
    public float discountConsume ;
    /// 计算折扣率
    public float discountSettlement ;

    public int consumerId ;
    public int shopGroupId ;
    public String consumerName ;
    public String consumerPhone ;
    public String gmtCreate ;

    public JoinCard() {

    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCardRfic() {
        return cardRfic;
    }

    public void setCardRfic(int cardRfic) {
        this.cardRfic = cardRfic;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getLastConsumeDate() {
        return lastConsumeDate;
    }

    public void setLastConsumeDate(String lastConsumeDate) {
        this.lastConsumeDate = lastConsumeDate;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public float getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(float faceAmount) {
        this.faceAmount = faceAmount;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getDiscountConsume() {
        return discountConsume;
    }

    public void setDiscountConsume(float discountConsume) {
        this.discountConsume = discountConsume;
    }

    public float getDiscountSettlement() {
        return discountSettlement;
    }

    public void setDiscountSettlement(float discountSettlement) {
        this.discountSettlement = discountSettlement;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public int getShopGroupId() {
        return shopGroupId;
    }

    public void setShopGroupId(int shopGroupId) {
        this.shopGroupId = shopGroupId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "JoinCard{" +
                "\n终端号='" + terminalCode + '\'' +
                ", \nid=" + id +
                ", \n卡号='" + cardNo + '\'' +
                ", \n验证码=" + cardRfic +
                ", \n最近消费时间='" + lastConsumeDate + '\'' +
                ", \n面额=" + faceAmount +
                ", \n余额=" + balance +
                ", \n姓名='" + consumerName + '\'' +
                ", \n手机号='" + consumerPhone + '\'' +
                '}';
    }
}
