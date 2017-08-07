package com.hhzb.fntalm.fargment.admin.backshop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.admin.sendwash.PackageFargment;
import com.hhzb.fntalm.fargment.home.output.OutPutFailFragment;
import com.hhzb.fntalm.fargment.home.output.OutPutSuccessFragment;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.SpacesItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
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
 * Created by c on 2016-12-09.
 */
public class BackShopFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private CommonAdapter<BackPut> mAdapter;
    private List<BackPut> mDatas = new ArrayList<BackPut>();
    public static BackShopFragment newInstance() {
        BackShopFragment fg = new BackShopFragment();
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_back_shop, container, false);
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
        List<BackPut> mDatas = BackputMoudle.getInstance().getBackShopCloths();
        createList(mDatas);
    }

    private void createList(List<BackPut> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mAdapter = new CommonAdapter<BackPut>(getActivity(), R.layout.fg_send_wash_list_item, mDatas) {
                @Override
                protected void convert(ViewHolder holder, final BackPut backPut, int position) {
                    holder.setText(R.id.cabinet_no_item,String.valueOf(backPut.getCabinetNo()));
                    holder.setText(R.id.bill_no_item, backPut.getBillNo());
                    holder.setText(R.id.mobile_item, backPut.getMobile());
                    holder.setText(R.id.count_no_item, String.valueOf(backPut.getClothCount()+"件"));
                    holder.setText(R.id.weekday, TimeUtils.getWeek(backPut.getBackDate()));
                    holder.setText(R.id.time_item, getDate(backPut.getBackDate()));
                    holder.setText(R.id.day_item, getDay(backPut.getBackDate()));
                    holder.setText(R.id.hhmmss_item, backPut.getBackDate().split(" ")[1]);
                }
            };
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BackPut input = mAdapter.getDatas().get(holder.getAdapterPosition());
                sendClothWash(input);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void sendClothWash(final BackPut backPut) {
        Log.i("res","开始返店");
        RequestCenter.clothBackShop(getActivity(), JsonUtils.BeanToJson(backPut), new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.i("res",responseObj.toString());
                if(responseObj.toString().equals("true")){
                    //返店数据上传成功
                    Cabin cabin = CabinModule.getInstance().getOneCabin(backPut.getCabinetNo());
                    if(SerialPortUtil.getInstance().open(cabin.getCmdNo())){
                        //开锁
                        backPut.setIsBackShop(true);
                        backPut.setBackShopDate(TimeUtils.date2String(new Date()));
                        BackputMoudle.getInstance().insertOrReplace(backPut);
                        CabinModule.getInstance().setCabinUsed(backPut.getCabinetNo(),false);
                        mDatas.remove(backPut);
                        mAdapter.notifyDataSetChanged();
                    }else{
                        ToastUtils.showShortToast(getActivity(),"开锁失败");
                    }

                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.i("res",reasonObj.toString());
            }
        });
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
