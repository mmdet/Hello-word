package com.hhzb.fntalm.fargment.home.output;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.view.TopBar;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class PayListFragment extends BaseFragment{

   private  BackPut mBackPut;
    LinearLayout weixin,alipay,vipCard;
    public static PayListFragment newInstance(BackPut backPut) {
        PayListFragment fg = new PayListFragment();
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
        View view = inflater.inflate(R.layout.fg_paylidt, container, false);
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
        weixin = (LinearLayout)view.findViewById(R.id.weixin);
        alipay = (LinearLayout)view.findViewById(R.id.alipay);
        vipCard = (LinearLayout)view.findViewById(R.id.vipCard);
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PayWeiXin.newInstance(mBackPut,CommonData.PAY_TYPE_WEIXIN), SupportFragment.SINGLETASK);
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PayWeiXin.newInstance(mBackPut,CommonData.PAY_TYPE_ZHIFUBAO), SupportFragment.SINGLETASK);
            }
        });
        vipCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PayCardFargment.newInstance(mBackPut,CommonData.PAY_TYPE_ZHIFUBAO), SupportFragment.SINGLETASK);
            }
        });
    }



}
