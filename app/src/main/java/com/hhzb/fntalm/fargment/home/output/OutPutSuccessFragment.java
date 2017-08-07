package com.hhzb.fntalm.fargment.home.output;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.view.TopBar;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class OutPutSuccessFragment extends BaseFragment {

    private BackPut mBackPut;
    private TextView cabinetNo,billno,countNo,hasDone,youhui;
    public static OutPutSuccessFragment newInstance(BackPut backPut) {
        OutPutSuccessFragment fg = new OutPutSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.ARG_ARGS, backPut);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBackPut = (BackPut)bundle.getSerializable(CommonData.ARG_ARGS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_out_success, container, false);
        initView(view);
        return view;
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
                start(MenuFragment.newInstance(), SupportFragment.SINGLETASK);
            }
        });
        cabinetNo = (TextView)view.findViewById(R.id.cabinet_no);
        billno = (TextView)view.findViewById(R.id.bill_no);
        countNo = (TextView)view.findViewById(R.id.count_no);
        youhui = (TextView)view.findViewById(R.id.youhui);
        hasDone = (TextView)view.findViewById(R.id.hasDone);
        cabinetNo.setText(String.valueOf(mBackPut.getCabinetNo()).length() == 1?"0"+String.valueOf(mBackPut.getCabinetNo()):String.valueOf(mBackPut.getCabinetNo()));
        cabinetNo.append("号柜");

        billno.setText(mBackPut.getBillNo());
        countNo.setText(mBackPut.getClothCount()+"件");

        youhui.setText(mBackPut.getRebate()+"");

        hasDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MenuFragment.newInstance(), SupportFragment.SINGLETASK);
            }
        });
    }

    TopBar topBar;
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
    }

}
