package com.hhzb.fntalm.fargment.home.output;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.admin.sendwash.SendWashFragment;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.SpacesItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class OutPutFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private CommonAdapter<BackPut> mAdapter;
    private List<BackPut> mDatas = new ArrayList<BackPut>();
    //WHAT
    public final static int HANDLER_WHAT_GetDataList = 0;
    String mMobile;
    public static OutPutFragment newInstance(String mobile) {
        OutPutFragment fg = new OutPutFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CommonData.ARG_Mobile, mobile);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMobile = bundle.getString(CommonData.ARG_Mobile);
        }
    }
    TopBar topBar;
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_output, container, false);
        initView(view);
        getData();
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
                pop();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);

    }

    private void getData() {
        mDatas = BackputMoudle.getInstance().get(mMobile,false);
        createList(mDatas);
    }
    private void createList(List<BackPut> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        if(mAdapter == null){
            mAdapter = new CommonAdapter<BackPut>(getActivity(), R.layout.fg_out_put_list_item, mDatas) {
                @Override
                protected void convert(ViewHolder holder, final BackPut backPut, int position) {
                    holder.setText(R.id.cabinet_no_item,String.valueOf(backPut.getCabinetNo()));
                    holder.setText(R.id.bill_no_item, backPut.getBillNo());
                    holder.setText(R.id.mobile_item, backPut.getClothNames());
                    holder.setText(R.id.count_no_item, String.valueOf(backPut.getClothCount()+"件"));
                    holder.setText(R.id.weekday, TimeUtils.getWeek(backPut.getBackDate()));
                    holder.setText(R.id.time_item, SendWashFragment.getDate(backPut.getBackDate()));
                    holder.setText(R.id.day_item, SendWashFragment.getDay(backPut.getBackDate()));
                    holder.setText(R.id.hhmmss_item, backPut.getOldPrice()+"元");
                }
            };
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BackPut backPut = mAdapter.getDatas().get(holder.getAdapterPosition());
                start(PayListFragment.newInstance(backPut), SupportFragment.SINGLETASK);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
