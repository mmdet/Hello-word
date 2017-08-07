package com.mmdet.lib.input;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class Tests {
    String BillNo;
    String CreateDate;

    public Tests(String billNo, String createDate) {
        BillNo = billNo;
        CreateDate = createDate;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    @Override
    public String toString() {
        return "Tests{" +
                "BillNo='" + BillNo + '\'' +
                ", CreateDate=" + CreateDate +
                '}';
    }
}
