package com.hhzb.fntalm.fargment.admin.backput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.fargment.admin.AdminFragment;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-08.
 */
public class BackPutSuccessFargment extends BaseFragment {
    private static final String ARG_BILLNO = "arg_billno";
    private static final String ARG_COUNT = "arg_count";
    private static final String ARG_CABIN = "arg_cabinNo";
    private String mBillno,mCount,mCabinNo;
    TextView hasDone;
    public static BackPutSuccessFargment newInstance(String cabinNo) {
        BackPutSuccessFargment fg = new BackPutSuccessFargment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CABIN, cabinNo);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCabinNo = (String)bundle.getSerializable(ARG_CABIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_backput_success, container, false);
        initView(view);
        return view;
    }
    private TextView cabinetNo;

    private void initView(View view) {
        cabinetNo = (TextView)view.findViewById(R.id.cabinet_no);
        cabinetNo.setText(mCabinNo.length() == 1?"0"+mCabinNo:mCabinNo);
        cabinetNo.append("号柜");

        hasDone = (TextView)view.findViewById(R.id.hasDone);
        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
                Toast.makeText(getActivity(), "Right Clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void leftclick(){
                start(AdminFragment.newInstance(),SupportFragment.SINGLETASK);
            }
        });
        hasDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdminFragment.newInstance(), SupportFragment.SINGLETASK);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
