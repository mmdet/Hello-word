package com.hhzb.fntalm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by c on 2017-02-28.
 */

@Entity
public class Num extends BaseModel {
    @Id
    private Long ID;
    @Unique
    private int Num;
    public int getNum() {
        return this.Num;
    }
    public void setNum(int Num) {
        this.Num = Num;
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    @Generated(hash = 1734224096)
    public Num(Long ID, int Num) {
        this.ID = ID;
        this.Num = Num;
    }
    @Generated(hash = 537701133)
    public Num() {
    }

}
