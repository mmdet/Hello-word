package com.hhzb.fntalm.fargment.admin.sendwash;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.SpacesItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-09.
 */
public class SendWashFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private CommonAdapter<Input> mAdapter;
    private List<Input> mDatas = new ArrayList<Input>();
    //WHAT
    public final static int HANDLER_WHAT_GetDataList = 0;
    public static SendWashFragment newInstance() {
        SendWashFragment fg = new SendWashFragment();
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_send_wash, container, false);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {
        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
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
        List<Input> mDatas = InputMoudle.getInstance().get(false);
        createList(mDatas);
    }

    private void createList(List<Input> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mAdapter = new CommonAdapter<Input>(getActivity(), R.layout.fg_send_wash_list_item, mDatas) {
                @Override
                protected void convert(ViewHolder holder, final Input input, int position) {
                    holder.setText(R.id.cabinet_no_item,String.valueOf(input.getCabinetNo()));
                    holder.setText(R.id.bill_no_item, input.getBillNo());
                    holder.setText(R.id.mobile_item, input.getMobile());
                    holder.setText(R.id.count_no_item, String.valueOf(input.getClothCount()+"件"));
                    holder.setText(R.id.weekday, TimeUtils.getWeek(input.getInputDate()));
                    holder.setText(R.id.time_item, getDate(input.getInputDate()));
                    holder.setText(R.id.day_item, getDay(input.getInputDate()));
                    holder.setText(R.id.hhmmss_item, input.getInputDate().split(" ")[1]);
                }
            };
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
               Input input = mAdapter.getDatas().get(holder.getAdapterPosition());
                sendClothWash(input);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 员工从柜子取衣，进行送洗
     * @param input
     */
    private void sendClothWash(Input input) {
        start(PackageFargment.newInstance(input), SupportFragment.SINGLETASK);
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
        getData();
    }

    /**
     *获取年 月
     * @param date
     * <p>date格式为yyyy-MM-dd HH:mm:ss</p>
     * @return
     * 返回格式：xx月 xxxx年
     */
    public static String getDate(String date){
        String[] times = date.split("-");
        return times[1]+"月 "+times[0];
    }
    /**
     *获取日
     * @param date
     * <p>date格式为yyyy-MM-dd HH:mm:ss</p>
     * @return
     * 返回格式：xx日
     */
    public static String getDay(String date){
        String[] times = date.split("-");
        return times[2].split(" ")[0];
    }


}
