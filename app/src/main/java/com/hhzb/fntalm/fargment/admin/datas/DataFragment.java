package com.hhzb.fntalm.fargment.admin.datas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.admin.AdminFragment;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.view.TopBar;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class DataFragment extends BaseFragment {


    public static DataFragment newInstance() {
        return new DataFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_datas, container, false);
        initView(view);
        return view;
    }
    TextView data_input,data_backput,data_backputs,data_admin;
    private void initView(View view) {
        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
            }
            @Override
            public void leftclick(){
                start(AdminFragment.newInstance(), SupportFragment.SINGLETASK);
            }
        });
        data_backputs = (TextView)view.findViewById(R.id.data_backputs);
        data_input = (TextView)view.findViewById(R.id.data_input);
        data_backput = (TextView)view.findViewById(R.id.data_backput);
        data_admin = (TextView)view.findViewById(R.id.data_admin);

        data_backputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceLoadRootFragment(R.id.fl_child_content, DataBackPutFragment.newInstance(),false);
            }
        });

        data_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceLoadRootFragment(R.id.fl_child_content, DataInputFragment.newInstance(),false);
            }
        });

        data_backput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceLoadRootFragment(R.id.fl_child_content, DataOutputFragment.newInstance(),false);
            }
        });
        data_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceLoadRootFragment(R.id.fl_child_content, com.hhzb.fntalm.fargment.admin.datas.AdminFragment.newInstance(),false);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            replaceLoadRootFragment(R.id.fl_child_content, DataBackPutFragment.newInstance(),false);
        } else { // 这里可能会出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(DataBackPutFragment.class) == null) {
                replaceLoadRootFragment(R.id.fl_child_content, DataBackPutFragment.newInstance(),false);
            }
        }
    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
