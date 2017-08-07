package com.hhzb.fntalm.fargment.admin.datas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.admin.AdminFragment;
import com.hhzb.fntalm.module.manager.DbBackups;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.view.TopBar;
import com.mmdet.lib.utils.ToastUtils;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class DataBackPutFragment extends BaseFragment {


    public static DataBackPutFragment newInstance() {
        return new DataBackPutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_data_backput, container, false);
        initView(view);
        return view;
    }
    Button backputs;
    private void initView(View view) {
        backputs = (Button)view.findViewById(R.id.backputs);

        backputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbBackups dbp = new DbBackups(getActivity());
                if(dbp.doBackup()){
                    ToastUtils.showShortToast(getActivity(),"备份成功");
                };
            }
        });
    }

    /**
     * 替换加载 内容Fragment
     *
     * @param fragment
     */
    public void switchContentFragment(SupportFragment fragment) {
        //SupportFragment contentFragment = findChildFragment(ContentFragment.class);
        //if (contentFragment != null) {
            replaceFragment(DataInputFragment.newInstance(), false);
        //}
    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
