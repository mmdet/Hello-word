package com.hhzb.fntalm.fargment.admin.datas;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class DataOutputFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private CommonAdapter<BackPut> mAdapter;
    private List<BackPut> mDatas = new ArrayList<BackPut>();
    public static DataOutputFragment newInstance() {
        return new DataOutputFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_data_output, container, false);
        initView(view);
        getData();
        return view;
    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
    }
    private void getData() {
        mDatas = BackputMoudle.getInstance().get();
        createList(mDatas);
    }
    private void createList(List<BackPut> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<BackPut>(getActivity(), R.layout.output_list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final BackPut input, int position) {
                holder.setText(R.id.BillNo, input.getBillNo());
                holder.setText(R.id.mobile, input.getMobile());
                holder.setText(R.id.ClothCount, input.getClothCount()+"");
                holder.setText(R.id.ClothInfo, input.getClothCount()+"");
                holder.setText(R.id.CabinetNo, input.getCabinetNo()+"");
                holder.setText(R.id.BatchNo, input.getBatchNo()+"");
                holder.setText(R.id.OldPrice, input.getOldPrice()+"");
                holder.setText(R.id.PayType, input.getPayType()+"");
                holder.setText(R.id.BackDate, input.getBackDate());
                holder.setText(R.id.IsBackShop, input.getIsBackShop()+"");
                holder.setText(R.id.BackShopDate, input.getBackShopDate());
                holder.setText(R.id.NewPrice, input.getNewPrice()+"");
                holder.setText(R.id.Rebate, input.getRebate()+"");
                holder.setText(R.id.IsOut, input.getIsOut()+"");
                holder.setText(R.id.OutDate, input.getOutDate());
                holder.setText(R.id.PackCode, input.getPackCode());
                holder.setText(R.id.IsUpload, input.getIsUpload()+"");
                holder.setText(R.id.UploadDate, input.getUploadDate());
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
