package com.hhzb.fntalm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by c on 2017-02-27.
 */
@Entity
public class Input extends BaseModel{
    @Id
    private Long ID;
    @Unique
    private String BillNo;
    private String SiteID;
    @NotNull
    private String Mobile;
    @NotNull
    private int ClothCount;
    @NotNull
    private int CabinetNo;
    @NotNull
    private String InputDate;
    private Boolean IsOut;
    private String OutDate;
    private String PackCode;
    private Boolean IsUpload;
    private String UploadDate;
    private Boolean IsCancel;
    private String CancelDate;
    public String getCancelDate() {
        return this.CancelDate;
    }
    public void setCancelDate(String CancelDate) {
        this.CancelDate = CancelDate;
    }
    public Boolean getIsCancel() {
        return this.IsCancel;
    }
    public void setIsCancel(Boolean IsCancel) {
        this.IsCancel = IsCancel;
    }
    public String getUploadDate() {
        return this.UploadDate;
    }
    public void setUploadDate(String UploadDate) {
        this.UploadDate = UploadDate;
    }
    public Boolean getIsUpload() {
        return this.IsUpload;
    }
    public void setIsUpload(Boolean IsUpload) {
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
    public Boolean getIsOut() {
        return this.IsOut;
    }
    public void setIsOut(Boolean IsOut) {
        this.IsOut = IsOut;
    }
    public String getInputDate() {
        return this.InputDate;
    }
    public void setInputDate(String InputDate) {
        this.InputDate = InputDate;
    }
    public int getCabinetNo() {
        return this.CabinetNo;
    }
    public void setCabinetNo(int CabinetNo) {
        this.CabinetNo = CabinetNo;
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
    public String getSiteID() {
        return this.SiteID;
    }
    public void setSiteID(String SiteID) {
        this.SiteID = SiteID;
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
    @Generated(hash = 1693484273)
    public Input(Long ID, String BillNo, String SiteID, @NotNull String Mobile,
            int ClothCount, int CabinetNo, @NotNull String InputDate,
            Boolean IsOut, String OutDate, String PackCode, Boolean IsUpload,
            String UploadDate, Boolean IsCancel, String CancelDate) {
        this.ID = ID;
        this.BillNo = BillNo;
        this.SiteID = SiteID;
        this.Mobile = Mobile;
        this.ClothCount = ClothCount;
        this.CabinetNo = CabinetNo;
        this.InputDate = InputDate;
        this.IsOut = IsOut;
        this.OutDate = OutDate;
        this.PackCode = PackCode;
        this.IsUpload = IsUpload;
        this.UploadDate = UploadDate;
        this.IsCancel = IsCancel;
        this.CancelDate = CancelDate;
    }
    @Generated(hash = 289903166)
    public Input() {
    }

}
