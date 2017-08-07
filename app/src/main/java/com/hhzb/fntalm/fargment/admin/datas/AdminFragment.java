package com.hhzb.fntalm.fargment.admin.datas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Admin;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.module.AdminModule;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TimeUtils;
import com.mmdet.lib.utils.ScreenUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class AdminFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private CommonAdapter<Admin> mAdapter;
    TextView addAdmin;

    private List<Admin> mDatas = new ArrayList<Admin>();
    public static AdminFragment newInstance() {
        return new AdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_data_admin, container, false);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        addAdmin = (TextView) view.findViewById(R.id.addAdmin);
        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(null,0);
            }
        });
    }

    private void getData() {
        mDatas = AdminModule.getInstance().getAll();
        createList(mDatas);
    }
    private void createList(List<Admin> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<Admin>(getActivity(), R.layout.admin_list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final Admin admin, final int position) {
                final Admin admins = mAdapter.getDatas().get(holder.getAdapterPosition());
                if(admins.getName().equals("1357924680"))
                    return ;
                holder.setText(R.id.name, admins.getName());
                holder.setText(R.id.pass, admins.getPassword());
                holder.setText(R.id.type, admins.getType() == 0?"口令账号":"手机账号");
                holder.setText(R.id.used, admins.getUsed() == true?"可用":"禁用");
                holder.setText(R.id.date, admins.getCreateDate());
                holder.setText(R.id.del, "删除");
                holder.setOnClickListener(R.id.del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AdminModule.getInstance().delete(admins);
                        mDatas.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Admin admin = mAdapter.getDatas().get(holder.getAdapterPosition());
                showAlert(admin,1);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    View dialogView;
    private void showAlert(final Admin admin,final int tytype) {
        adminTypeS = 0;
        final Admin admins;
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_edit_admin, null);
        final EditText adminName = (EditText)dialogView.findViewById(R.id.admin_name);
        final EditText adminPass = (EditText)dialogView.findViewById(R.id.admin_pass);
        final TextView text_tip_pass = (TextView)dialogView.findViewById(R.id.text_tip_pass);
        final RadioGroup adminType = (RadioGroup)dialogView.findViewById(R.id.admin_type);
        final RadioButton kouling = (RadioButton)dialogView.findViewById(R.id.kouling);
        final RadioButton shouji = (RadioButton)dialogView.findViewById(R.id.shouji);
        final CheckBox adminused = (CheckBox)dialogView.findViewById(R.id.canused);

        if(tytype == 0){
            admins = new Admin();
            adminused.setChecked(false);
            kouling.setChecked(true);
        }else {
            admins = admin;
            adminName.setText(admins.getName());
            adminPass.setText(admins.getPassword());
            if(admins.getType() == 0){
                kouling.setChecked(true);
            }else{
                shouji.setChecked(true);
            }
            adminused.setChecked(!admins.getUsed());
        }
        adminName.setHint("输入口令");
        adminPass.setVisibility(View.GONE);
        text_tip_pass.setVisibility(View.GONE);
        setRadioGroupListener(adminType,text_tip_pass,adminName,adminPass);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = adminName.getText().toString().trim();
                String pass = adminPass.getText().toString().trim();
                admins.setType(adminTypeS);
                admins.setName(name);
                admins.setPassword(pass);
                admins.setUsed(!adminused.isChecked());
                admins.setCreateDate(TimeUtils.date2String(new Date()));
                AdminModule.getInstance().insertOrReplace(admins);
                if(tytype == 0){
                    mDatas.add(admins);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        //给对话框设置视图
        builder.setView(dialogView);
        builder.setCancelable(true);//设置不可取消
        AlertDialog mAlertDialog = builder.create();
        Window win = mAlertDialog.getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.y = ScreenUtils.getScreenHeight(getActivity()) /4;//设置y坐标
        win.setAttributes(params);
        mAlertDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        mAlertDialog.show();
    }

    int adminTypeS = 0;
    private void setRadioGroupListener(RadioGroup adminType,final TextView tip_pass,final EditText adminName,final EditText adminPass) {
        //为RadioGroup设置监听事件
        adminType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.kouling){
                    adminTypeS = 0;
                    adminName.setHint("输入口令");
                    adminPass.setVisibility(View.GONE);
                    tip_pass.setVisibility(View.GONE);
                }else if(checkedId == R.id.shouji){
                    adminTypeS = 1;
                    adminName.setHint("输入手机号码");
                    adminPass.setVisibility(View.VISIBLE);
                    tip_pass.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
