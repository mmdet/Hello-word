package com.hhzb.fntalm.bean;

/**
 * Created by c on 2016-12-07.
 */
public class Card extends BaseModel{
    String CardNo;
    String CardType;
    String Rebate;
    String Remain;
    String IsServer;
    String ExpiryDate;
    String CanUsed;

    public Card(String cardNo, String cardType, String rebate, String remain, String isServer, String expiryDate, String canUsed) {
        CardNo = cardNo;
        CardType = cardType;
        Rebate = rebate;
        Remain = remain;
        IsServer = isServer;
        ExpiryDate = expiryDate;
        CanUsed = canUsed;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getRebate() {
        return Rebate;
    }

    public void setRebate(String rebate) {
        Rebate = rebate;
    }

    public String getRemain() {
        return Remain;
    }

    public void setRemain(String remain) {
        Remain = remain;
    }

    public String getIsServer() {
        return IsServer;
    }

    public void setIsServer(String isServer) {
        IsServer = isServer;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getCanUsed() {
        return CanUsed;
    }

    public void setCanUsed(String canUsed) {
        CanUsed = canUsed;
    }
}
