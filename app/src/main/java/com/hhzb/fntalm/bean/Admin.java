package com.hhzb.fntalm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
@Entity
public class Admin extends BaseModel {
    @Id
    private Long ID;

    @Unique
    private String Name;

    private String Password;

    /**
     * 账号类型
     * 0口令
     * 1手机号
     */
    private int Type;

    /**
     * 禁用还是使用
     */
    private boolean Used;

    private String CreateDate;

    public String getCreateDate() {
        return this.CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public boolean getUsed() {
        return this.Used;
    }

    public void setUsed(boolean Used) {
        this.Used = Used;
    }

    public int getType() {
        return this.Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Generated(hash = 811622245)
    public Admin(Long ID, String Name, String Password, int Type, boolean Used,
            String CreateDate) {
        this.ID = ID;
        this.Name = Name;
        this.Password = Password;
        this.Type = Type;
        this.Used = Used;
        this.CreateDate = CreateDate;
    }

    @Generated(hash = 1708792177)
    public Admin() {
    }
    
}
