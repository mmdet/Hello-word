package com.hhzb.fntalm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by c on 2017-02-27.
 */
@Entity
public class BackPut extends BaseModel{
    @Id
    private Long ID;
    private String BillNo;
    private String Mobile;
    private int ClothCount;
    private String ClothNames;
    private int CabinetNo;
    private int BatchNo;
    private double OldPrice;
    private int PayType;
    private String BackDate;
    private boolean IsBackShop;
    private String BackShopDate;
    private double NewPrice;
    private double Rebate;
    private boolean IsOut;
    private String OutDate;
    private String PackCode;
    private boolean IsUpload;
    private String UploadDate;
    public String getUploadDate() {
        return this.UploadDate;
    }
    public void setUploadDate(String UploadDate) {
        this.UploadDate = UploadDate;
    }
    public boolean getIsUpload() {
        return this.IsUpload;
    }
    public void setIsUpload(boolean IsUpload) {
        this.IsUpload = IsUpload;
    }
    public String getPackCode() {
        return this.PackCode;
    }
    public void setPackCode(String PackCode) {
        this.PackCode = PackCode;
    }
    public String getOutDate() {
        return this.OutDate;
    }
    public void setOutDate(String OutDate) {
        this.OutDate = OutDate;
    }
    public boolean getIsOut() {
        return this.IsOut;
    }
    public void setIsOut(boolean IsOut) {
        this.IsOut = IsOut;
    }
    public double getRebate() {
        return this.Rebate;
    }
    public void setRebate(double Rebate) {
        this.Rebate = Rebate;
    }
    public double getNewPrice() {
        return this.NewPrice;
    }
    public void setNewPrice(double NewPrice) {
        this.NewPrice = NewPrice;
    }
    public String getBackShopDate() {
        return this.BackShopDate;
    }
    public void setBackShopDate(String BackShopDate) {
        this.BackShopDate = BackShopDate;
    }
    public boolean getIsBackShop() {
        return this.IsBackShop;
    }
    public void setIsBackShop(boolean IsBackShop) {
        this.IsBackShop = IsBackShop;
    }
    public String getBackDate() {
        return this.BackDate;
    }
    public void setBackDate(String BackDate) {
        this.BackDate = BackDate;
    }
    public int getPayType() {
        return this.PayType;
    }
    public void setPayType(int PayType) {
        this.PayType = PayType;
    }
    public double getOldPrice() {
        return this.OldPrice;
    }
    public void setOldPrice(double OldPrice) {
        this.OldPrice = OldPrice;
    }
    public int getBatchNo() {
        return this.BatchNo;
    }
    public void setBatchNo(int BatchNo) {
        this.BatchNo = BatchNo;
    }
    public int getCabinetNo() {
        return this.CabinetNo;
    }
    public void setCabinetNo(int CabinetNo) {
        this.CabinetNo = CabinetNo;
    }
    public String getClothNames() {
        return this.ClothNames;
    }
    public void setClothNames(String ClothNames) {
        this.ClothNames = ClothNames;
    }
    public int getClothCount() {
        return this.ClothCount;
    }
    public void setClothCount(int ClothCount) {
        this.ClothCount = ClothCount;
    }
    public String getMobile() {
        return this.Mobile;
    }
    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }
    public String getBillNo() {
        return this.BillNo;
    }
    public void setBillNo(String BillNo) {
        this.BillNo = BillNo;
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    @Generated(hash = 533150287)
    public BackPut(Long ID, String BillNo, String Mobile, int ClothCount,
            String ClothNames, int CabinetNo, int BatchNo, double OldPrice,
            int PayType, String BackDate, boolean IsBackShop, String BackShopDate,
            double NewPrice, double Rebate, boolean IsOut, String OutDate,
            String PackCode, boolean IsUpload, String UploadDate) {
        this.ID = ID;
        this.BillNo = BillNo;
        this.Mobile = Mobile;
        this.ClothCount = ClothCount;
        this.ClothNames = ClothNames;
        this.CabinetNo = CabinetNo;
        this.BatchNo = BatchNo;
        this.OldPrice = OldPrice;
        this.PayType = PayType;
        this.BackDate = BackDate;
        this.IsBackShop = IsBackShop;
        this.BackShopDate = BackShopDate;
        this.NewPrice = NewPrice;
        this.Rebate = Rebate;
        this.IsOut = IsOut;
        this.OutDate = OutDate;
        this.PackCode = PackCode;
        this.IsUpload = IsUpload;
        this.UploadDate = UploadDate;
    }
    @Generated(hash = 536813815)
    public BackPut() {
    }

    
}
