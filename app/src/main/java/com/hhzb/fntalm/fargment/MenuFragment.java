package com.hhzb.fntalm.fargment;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.hhzb.fntalm.MyApplication;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Menu;
import com.hhzb.fntalm.card.MCSJoinReader;
import com.hhzb.fntalm.service.timer.TimerObserver;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.GridItemDecoration;
import com.mmdet.lib.utils.SPUtils;
import com.tts.Speak;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

import cn.wch.ch37xdriver.CH37xDriver;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 首页菜单业务
 * 主要功能：创建首页菜单、验证入口跳转、管理员页面跳转
 * Created by c on 2017-02-21.
 */

public class MenuFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private SwipeLayout mSwipeLayout;
    private CommonAdapter<Menu> mAdapter;
    private GridLayoutManager mLayoutManager;
    List<Menu> mDatas = new ArrayList<Menu>();
    private LinearLayout adminIn;
    TextView timers,siteName;
    public static MenuFragment newInstance() {
        MenuFragment fg = new MenuFragment();
        return fg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_index, container, false);
        TTS.speck_welcome(getActivity());
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.id_recyclerview);
        mSwipeLayout =  (SwipeLayout)view.findViewById(R.id.sample1);
        timers =  (TextView)view.findViewById(R.id.timers);
        siteName =  (TextView)view.findViewById(R.id.siteNmae);
        siteName.setText(SPUtils.getInstance(getActivity()).getString("site_name","未设置站点名称"));
        createIndexMenu();
        adminIn = (LinearLayout)view.findViewById(R.id.adminIn);
        adminIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CheckFragment.newInstance(CommonData.MENU_ADMIN,null));
            }
        });
    }

    private void createIndexMenu() {
        Menu m1 = new Menu(CommonData.MENU_INPUT,"洗衣",R.drawable.menu_danlan,R.mipmap.cunyi);
        mDatas.add(m1);
        Menu m2 = new Menu(CommonData.MENU_OUTPUT,"取衣",R.drawable.menu_danhuang,R.mipmap.menu_quyi);
        mDatas.add(m2);
        Menu m3 = new Menu(CommonData.MENU_CANCEL,"撤单",R.drawable.menu_dancheng,R.mipmap.menu_chedan);
        mDatas.add(m3);
        Menu m4 = new Menu(CommonData.MENU_BUYCARD,"购卡",R.drawable.menu_danhong,R.mipmap.menu_shouka);
        mDatas.add(m4);
        Menu m5 = new Menu(CommonData.MENU_RECHARGE,"充值",R.drawable.menu_danlanse,R.mipmap.menu_chongzhi);
        mDatas.add(m5);
        Menu m6 = new Menu(CommonData.MENU_SEARCH,"余额查询",R.drawable.menu_danlv,R.mipmap.meni_chaxun);
        mDatas.add(m6);
        // use a linear layout manager
        //创建默认的线性LayoutManager
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridItemDecoration(3, 10,false));
        mAdapter =  new CommonAdapter<Menu>(getActivity(), R.layout.menu_item_list, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, Menu m, int position) {
                holder.setBackgroundRes(R.id.item_list_img,m.getImg());
                holder.setBackgroundRes(R.id.main_bg,m.getBg());
                holder.setText(R.id.menu_name,m.getName());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Menu m = mAdapter.getDatas().get(holder.getAdapterPosition());
                start(CheckFragment.newInstance(m.getID(),null), SupportFragment.SINGLETASK);
                //Speak.speck(getActivity(),"请输入手机号码");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        timers.setText(millisUntilFinished/1000+"s");
    }
}
