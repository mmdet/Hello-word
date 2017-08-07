package com.hhzb.fntalm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by c on 2017-02-28.
 */

@Entity
public class Cabin extends BaseModel {
    @Id
    private Long ID;
    @Unique
    private int CabinNo;
    @NotNull
    private int CmdNo;
    @NotNull
    private int CabinType;
    @NotNull
    private boolean IsUsed;
    private String UseRanges;
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
    public String getUseRanges() {
        return this.UseRanges;
    }
    public void setUseRanges(String UseRanges) {
        this.UseRanges = UseRanges;
    }
    public boolean getIsUsed() {
        return this.IsUsed;
    }
    public void setIsUsed(boolean IsUsed) {
        this.IsUsed = IsUsed;
    }
    public int getCabinType() {
        return this.CabinType;
    }
    public void setCabinType(int CabinType) {
        this.CabinType = CabinType;
    }
    public int getCmdNo() {
        return this.CmdNo;
    }
    public void setCmdNo(int CmdNo) {
        this.CmdNo = CmdNo;
    }
    public int getCabinNo() {
        return this.CabinNo;
    }
    public void setCabinNo(int CabinNo) {
        this.CabinNo = CabinNo;
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    @Generated(hash = 1693973654)
    public Cabin(Long ID, int CabinNo, int CmdNo, int CabinType, boolean IsUsed,
            String UseRanges, boolean IsUpload, String UploadDate) {
        this.ID = ID;
        this.CabinNo = CabinNo;
        this.CmdNo = CmdNo;
        this.CabinType = CabinType;
        this.IsUsed = IsUsed;
        this.UseRanges = UseRanges;
        this.IsUpload = IsUpload;
        this.UploadDate = UploadDate;
    }
    @Generated(hash = 1956057308)
    public Cabin() {
    }

}
