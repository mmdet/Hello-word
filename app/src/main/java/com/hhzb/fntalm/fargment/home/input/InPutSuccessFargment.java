package com.hhzb.fntalm.fargment.home.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.fargment.home.cancel.CancelFragment;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.TopBar;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-08.
 */
public class InPutSuccessFargment extends BaseFragment {
    private static final String ARG_BILLNO = "arg_billno";
    private static final String ARG_COUNT = "arg_count";
    private static final String ARG_CABIN = "arg_cabinNo";
    private String mBillno,mCount,mCabinNo;
    private TextView cabinetNo,billno,countNo,hasDone;

    public static InPutSuccessFargment newInstance(String billno, String count, String cabinNo) {
        InPutSuccessFargment InPutSuccessFg = new InPutSuccessFargment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BILLNO, billno);
        bundle.putString(ARG_COUNT, count);
        bundle.putString(ARG_CABIN, cabinNo);
        InPutSuccessFg.setArguments(bundle);
        return InPutSuccessFg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBillno = bundle.getString(ARG_BILLNO);
            mCount = bundle.getString(ARG_COUNT);
            mCabinNo = bundle.getString(ARG_CABIN);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_input_success, container, false);
        TTS.speck_input_cloth_success(getActivity());
        initView(view);
        return view;
    }
    TopBar topBar;
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
    }
    private void initView(View view) {
        topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
                Toast.makeText(getActivity(), "Right Clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void leftclick(){
                Fg.startSingleTask(InPutSuccessFargment.this, MenuFragment.newInstance());
            }
        });

        cabinetNo = (TextView)view.findViewById(R.id.cabinet_no);
        billno = (TextView)view.findViewById(R.id.bill_no);
        countNo = (TextView)view.findViewById(R.id.count_no);

        hasDone = (TextView)view.findViewById(R.id.hasDone);

        cabinetNo.setText(mCabinNo.length() == 1?"0"+mCabinNo:mCabinNo);
        cabinetNo.append("号柜");

        billno.setText(mBillno);
        countNo.setText(mCount+"件");

        hasDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MenuFragment.newInstance(), SupportFragment.SINGLETASK);
            }
        });

    }

    /**
     *
     public String getBillNo(){
     String billNo = null;
     Snum snum = SnumModel.getLastData();
     if(snum == null){
     Snum s = new Snum(null,1, TimeUtils.date2String(new Date(),new SimpleDateFormat("yyyyMMdd")));
     billNo = s.getDate()+Const.ShopID+getSN(s.getSN());
     SnumModel.insertData(s);
     }else{
     if(snum.getSN()<1000){
     int sn = snum.getSN()+1;
     String date = TimeUtils.date2String(new Date(),new SimpleDateFormat("yyyyMMdd"));
     billNo = date+Const.ShopID+getSN(sn);
     SnumModel.insertData(new Snum(null,sn,date));
     }else{
     //当天如果大于1000.从1继续开始
     Snum s = new Snum(null,1, TimeUtils.date2String(new Date(),new SimpleDateFormat("yyyyMMdd")));
     billNo = s.getDate()+Const.ShopID+getSN(s.getSN());
     SnumModel.insertData(s);
     }
     }
     return billNo;
     }

     public String getSN(int sunm){
     String sn = null;
     if(10<=sunm && sunm<100){
     sn = "0"+sunm;
     }else if(sunm>0 && sunm<10){
     sn = "00"+sunm;
     }else{
     sn = ""+sunm;
     }
     return sn;
     }
     */
}
