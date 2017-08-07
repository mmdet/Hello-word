package com.hhzb.fntalm.fargment.home.output;


import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.MyApplication;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.card.DataUtils;
import com.hhzb.fntalm.card.MCSJoinReader;
import com.hhzb.fntalm.card.MCSNewReader;
import com.hhzb.fntalm.card.MCSReaderAPI;
import com.hhzb.fntalm.card.MCSUtils;
import com.hhzb.fntalm.card.bean.ICC;
import com.hhzb.fntalm.card.bean.JoinCard;
import com.hhzb.fntalm.card.bean.JoinRes;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialDataUtils;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;

import cn.wch.ch37xdriver.CH37xDriver;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class PayShitiCard extends BaseFragment {
    TextView data;
    Button read,pay;
    public static boolean isOpen;
    private boolean READ_ENABLE = false;
    JoinCard joinCard;
    EditText etmoney;
    public static PayShitiCard newInstance() {
        PayShitiCard fg = new PayShitiCard();
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        open();
        View view = inflater.inflate(R.layout.fg_pay_shiticard, container, false);
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
                pop();
            }
        });
        data = (TextView)view.findViewById(R.id.data);
        read = (Button)view.findViewById(R.id.read);
        pay = (Button)view.findViewById(R.id.pay);
        etmoney = (EditText)view.findViewById(R.id.etmoney);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setText("");
                if (isOpen) {

                    int type = MCSUtils.getCardType();
                    data.append("\n卡类型："+type);

                    //卡信息读取
                    readIcCard();
                } else {
                    Toast.makeText(getActivity(), "设备打开失败!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = etmoney.getText().toString().trim();
                payIcCard(money);
            }
        });
    }

    /**
     * 打开设备
     */
    private void open(){
        MyApplication.ch37xdrv = new CH37xDriver(
                (UsbManager) getActivity().getSystemService(Context.USB_SERVICE),
                getActivity(), MCSReaderAPI.ACTION_USB_PERMISSION);
        if (!isOpen) {
            if (MCSReaderAPI.openDevice()) {
                Toast.makeText(getActivity(), "设备打开成功!", Toast.LENGTH_SHORT).show();
                isOpen = true;
                READ_ENABLE = true;
            } else {
                Toast.makeText(getActivity(), "设备打开失败!", Toast.LENGTH_SHORT).show();
            }
        } else {
            isOpen = false;
            READ_ENABLE = false;
            MyApplication.ch37xdrv.CloseDevice();
        }

    }

    //联盟卡读卡
    private void redJoinCard(){
            //读取卡信息
            MCSJoinReader.ReadCardNo(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    try{
                        String result = responseObj.toString();
                        if(result.startsWith("<card>")){
                            joinCard = DataUtils.getJoinCard(result);
                            joinCard.setTerminalCode("FNT0120150720N1129");
                            data.append("\n卡信息："+joinCard.toString());
                        }else{
                            data.append("\n读取卡信息失败");
                        }
                    }catch (Exception e){
                    }
                }
                @Override
                public void onFailure(Object reasonObj) {
                    data.append("\n结果：失败");
                }
            });
    }
    //IC卡读卡
    private void readIcCard(){
        ICC icc = MCSNewReader.ReadCard();
        data.append("\n卡信息："+icc.toString());
    }

    //联盟卡扣费
    private void payJoinCard(String money){
        MCSJoinReader.FeeMoney(joinCard, money,new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                data.setText("");
                try{
                    JoinRes joinRes = DataUtils.getJoinRes(responseObj.toString());
                    data.append(joinRes.toString());
                }catch (Exception e){
                }
            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }


    private void payIcCard(String money){
        ICC icc = MCSNewReader.ReadCard();
        MCSNewReader.PayCard(icc,20);
    }



}
