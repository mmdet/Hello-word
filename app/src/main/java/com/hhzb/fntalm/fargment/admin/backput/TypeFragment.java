package com.hhzb.fntalm.fargment.admin.backput;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.JsonUtils;
import org.json.JSONObject;

import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-13.
 */
public class TypeFragment extends BaseFragment {
    private static final String ARG_PAGECODE = "arg_pageCode";
    private String mBillNo;
    private TextView dieshi,guashi, price,count,bill,clothInfo;
    private BackPut mBackput;
    public static TypeFragment newInstance(String billNo) {
        TypeFragment fg = new TypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PAGECODE, billNo);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBillNo = bundle.getString(ARG_PAGECODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_type, container,false);
        initView(view);
        getClothInfos(mBillNo);
        return view;
    }

    private void getClothInfos(String billNo) {
        RequestCenter.clothInfos(getActivity(),mBillNo,new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                showView(responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj)
            {
                //获取订单信息失败，请重试
            }
        });
    }

    /**
     * 显示衣物信息
     * @param s
     */
    private void showView(String s) {
        try{
            JSONObject ja = new JSONObject(s);
            mBackput = JsonUtils.JsonToBean(ja.toString(), BackPut.class);
            if(mBackput == null){
                //没有订单信息
            }else{
                bill.setText(mBackput.getBillNo());
                count.setText(String.valueOf(mBackput.getClothCount()));
                price.setText(String.valueOf(mBackput.getOldPrice()));
                clothInfo.setText(mBackput.getClothNames());
            }

        }catch (Exception e){
                e.printStackTrace();
        }

    }

    private void initView(View view) {
        price = (TextView)view.findViewById(R.id.Order_price);
        count = (TextView)view.findViewById(R.id.count_no);
        bill = (TextView)view.findViewById(R.id.bill_no);
        clothInfo = (TextView)view.findViewById(R.id.clothInfo);

        dieshi = (TextView)view.findViewById(R.id.dieshi);
        guashi = (TextView)view.findViewById(R.id.guashi);
        dieshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBackInput (CommonData.CABIN_TYPE_DIESHI);
            }
        });
        guashi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBackInput(CommonData.CABIN_TYPE_GUASHI);
            }
        });

        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
                Toast.makeText(getActivity(), "Right Clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void leftclick(){
                pop();
            }
        });

    }

    private void doBackInput(int type){
        Cabin cabin = CabinModule.getInstance().getNoUsedCabin(CommonData.CABIN_TYPE_DIESHI);
        if(cabin == null){
            Toast.makeText(getActivity(),"没有可以用的柜子",Toast.LENGTH_SHORT).show();
            return;
        }
        if(SerialPortUtil.getInstance().open(cabin.getCmdNo())){
            //柜门已经打开
            //本地存入衣物信息
            mBackput.setPackCode(mBillNo);
            mBackput.setCabinetNo(cabin.getCabinNo());
            mBackput.setIsOut(false);
            mBackput.setBackDate(TimeUtils.date2String(new Date()));
            BackputMoudle.getInstance().insertOrReplace(mBackput);
            CabinModule.getInstance().setCabinUsed(cabin,true);
            Fg.startSingleTask(this,BackPutSuccessFargment.newInstance(cabin.getCabinNo()+""));
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
