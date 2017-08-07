package com.hhzb.fntalm.bean;

import java.io.Serializable;

/**
 * Created by c on 2017-01-03.
 */

public class CardType implements Serializable{
    private String Name;
    private int FaceValue;
    private int Value;
    private float Rebate;
    private int ValidMonths;
    private int FreeMoney;
    private boolean AllowBuy;
    private boolean AllowRecharge;
    private boolean IsChecked = false;

    public CardType(String name, int faceValue, int value, float rebate, int validMonths, int freeMoney, boolean allowBuy, boolean allowRecharge, boolean isChecked) {
        Name = name;
        FaceValue = faceValue;
        Value = value;
        Rebate = rebate;
        ValidMonths = validMonths;
        FreeMoney = freeMoney;
        AllowBuy = allowBuy;
        AllowRecharge = allowRecharge;
        IsChecked = isChecked;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getFaceValue() {
        return FaceValue;
    }

    public void setFaceValue(int faceValue) {
        FaceValue = faceValue;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public float getRebate() {
        return Rebate;
    }

    public void setRebate(float rebate) {
        Rebate = rebate;
    }

    public int getValidMonths() {
        return ValidMonths;
    }

    public void setValidMonths(int validMonths) {
        ValidMonths = validMonths;
    }

    public int getFreeMoney() {
        return FreeMoney;
    }

    public void setFreeMoney(int freeMoney) {
        FreeMoney = freeMoney;
    }

    public boolean isAllowBuy() {
        return AllowBuy;
    }

    public void setAllowBuy(boolean allowBuy) {
        AllowBuy = allowBuy;
    }

    public boolean isAllowRecharge() {
        return AllowRecharge;
    }

    public void setAllowRecharge(boolean allowRecharge) {
        AllowRecharge = allowRecharge;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }
}
