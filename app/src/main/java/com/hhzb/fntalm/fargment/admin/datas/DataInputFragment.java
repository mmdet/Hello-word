package com.hhzb.fntalm.fargment.admin.datas;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.module.manager.DbBackups;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.service.UploadService;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TimeUtils;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.JsonUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class DataInputFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private CommonAdapter<Input> mAdapter;
    private List<Input> mDatas = new ArrayList<Input>();
    public static DataInputFragment newInstance() {
        return new DataInputFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_data_input, container, false);
        initView(view);
        getData();

        return view;
    }

    private void updata() {
        final List<Input> Inputs = InputMoudle.getInstance().getSendCloth();
        ToastUtils.showShortToast(getActivity(),Inputs.size()+"件衣物信息");
        if(Inputs.size() !=0){
            RequestCenter.uploadInputList(getActivity(), JsonUtils.BeanToJson(Inputs), new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    ToastUtils.showShortToast(getActivity(),responseObj.toString());
                    if(responseObj.toString().equals("true")){
                        ToastUtils.showShortToast(getActivity(),"上传成功");
                        for(Input input:Inputs){
                            input.setIsUpload(true);
                            input.setOutDate(TimeUtils.date2String(new Date()));
                        }
                        InputMoudle.getInstance().insertOrReplace(Inputs);
                        Inputs.clear();
                    }
                }
                @Override
                public void onFailure(Object reasonObj) {

                }
            });
        }
    }
    TextView uploadData;
    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        uploadData = (TextView)view.findViewById(R.id.uploadData);
        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updata();
            }
        });
    }
    private void getData() {
        mDatas = InputMoudle.getInstance().get();
        createList(mDatas);
    }
    private void createList(List<Input> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<Input>(getActivity(), R.layout.input_list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final Input input, int position) {
                holder.setText(R.id.siteId, input.getSiteID());
                holder.setText(R.id.BillNo, input.getBillNo());
                holder.setText(R.id.mobile, input.getMobile());
                holder.setText(R.id.ClothCount, input.getClothCount()+"");
                holder.setText(R.id.CabinetNo, input.getCabinetNo()+"");
                holder.setText(R.id.InputDate, input.getInputDate());
                holder.setText(R.id.IsOut, input.getIsOut().toString());
                holder.setText(R.id.OutDate, input.getOutDate());
                holder.setText(R.id.PackCode, input.getPackCode());
                holder.setText(R.id.IsUpload, input.getIsUpload().toString());
                holder.setText(R.id.UploadDate, input.getUploadDate());
                holder.setText(R.id.IsCancel, input.getIsCancel().toString());
                holder.setText(R.id.CancelDate, input.getCancelDate());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
