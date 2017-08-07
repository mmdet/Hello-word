package com.hhzb.fntalm.fargment.admin.set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.view.TopBar;
import com.mmdet.lib.utils.SPUtils;
import com.mmdet.lib.utils.ToastUtils;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class SetFragment extends BaseFragment {

    EditText shop_id,addres_api,addres_los,addres_lss,site_id,site_name;//
    Button bt_shop_id,bt_address_los,bt_address_api,bt_address_lss,bt_site_id,bt_site_name;//
    public static SetFragment newInstance() {
        return new SetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_set, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        shop_id = (EditText)view.findViewById(R.id.shop_id);
        site_id = (EditText)view.findViewById(R.id.site_id);
        site_name = (EditText)view.findViewById(R.id.site_name);
        addres_api = (EditText)view.findViewById(R.id.addres_api);
        addres_los = (EditText)view.findViewById(R.id.addres_los);
        addres_lss = (EditText)view.findViewById(R.id.addres_lss);

        bt_shop_id = (Button)view.findViewById(R.id.edit_shop_id);
        bt_address_api = (Button)view.findViewById(R.id.edit_address_api);
        bt_address_los = (Button)view.findViewById(R.id.edit_address_los);
        bt_address_lss = (Button)view.findViewById(R.id.edit_address_lss);
        bt_site_id = (Button)view.findViewById(R.id.edit_site_id);
        bt_site_name = (Button)view.findViewById(R.id.edit_site_name);

        shop_id.setText(SPUtils.getInstance(getActivity()).getString("shop_id","0000"));
        site_name.setText(SPUtils.getInstance(getActivity()).getString("site_name","未设置站点名称"));
        site_id.setText(SPUtils.getInstance(getActivity()).getString("site_id","01"));
        addres_api.setText(SPUtils.getInstance(getActivity()).getString("addres_api","http://192.168.4.174:8080/LaundryPS/api"));
        addres_los.setText(SPUtils.getInstance(getActivity()).getString("addres_los","http://106.14.29.22:6789"));
        addres_lss.setText(SPUtils.getInstance(getActivity()).getString("addres_lss","http://106.14.29.22:4567"));

        bt_shop_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopId = shop_id.getText().toString().trim();
                if(shopId.isEmpty()) {
                    ToastUtils.showShortToast(getActivity(),"请输入门店ID");
                }else{
                    SPUtils.getInstance(getActivity()).putString("shop_id",shopId);
                    ToastUtils.showShortToast(getActivity(),"设置成功");
                }
            }
        });
        bt_site_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopId = site_id.getText().toString().trim();
                if(shopId.isEmpty()) {
                    ToastUtils.showShortToast(getActivity(),"请输站点ID");
                }else{
                    SPUtils.getInstance(getActivity()).putString("site_id",shopId);
                    ToastUtils.showShortToast(getActivity(),"设置成功");
                }
            }
        });
        bt_site_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String siteName = site_name.getText().toString().trim();
                if(siteName.isEmpty()) {
                    ToastUtils.showShortToast(getActivity(),"请输站点名");
                }else{
                    SPUtils.getInstance(getActivity()).putString("site_name",siteName);
                    ToastUtils.showShortToast(getActivity(),"设置成功");
                }
            }
        });
        bt_address_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addresLos = addres_api.getText().toString().trim();
                if(addresLos.isEmpty()) {
                    ToastUtils.showShortToast(getActivity(),"请输入内容");
                }else{
                    SPUtils.getInstance(getActivity()).putString("addres_api",addresLos);
                    ToastUtils.showShortToast(getActivity(),"设置成功");
                }
            }
        });
        bt_address_los.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addresLos = addres_los.getText().toString().trim();
                if(addresLos.isEmpty()) {
                    ToastUtils.showShortToast(getActivity(),"请输入内容");
                }else{
                    SPUtils.getInstance(getActivity()).putString("addres_los",addresLos);
                    ToastUtils.showShortToast(getActivity(),"设置成功");
                }
            }
        });
        bt_address_lss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addresLss = addres_lss.getText().toString().trim();
                if(addresLss.isEmpty()) {
                    ToastUtils.showShortToast(getActivity(),"请输入内容");
                }else{
                    SPUtils.getInstance(getActivity()).putString("addres_lss",addresLss);
                    ToastUtils.showShortToast(getActivity(),"设置成功");
                }

            }
        });

        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
            }
            @Override
            public void leftclick(){
                pop();
            }
        });

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }

}
