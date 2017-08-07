package com.hhzb.fntalm.card.bean;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ICCItem<T> {
    /// <summary>
    /// 代号
    /// </summary>
    public String Name;
    /// <summary>
    /// 扇区ID
    /// </summary>
    public int SectorID ;
    /// <summary>
    /// 块ID
    /// </summary>
    public int BlockID ;
    /// <summary>
    /// 存储的数值
    /// </summary>
    public T Value ;

    public ICCItem<T> Copy()
    {
        ICCItem<T> ii = new ICCItem<T>();
        ii.Name = this.Name;
        ii.SectorID = this.SectorID;
        ii.BlockID = this.BlockID;
        ii.Value = this.Value;
        return ii;
    }
}
