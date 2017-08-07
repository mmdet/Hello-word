package com.hhzb.fntalm.fargment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allenliu.versionchecklib.AVersionService;
import com.allenliu.versionchecklib.HttpRequestMethod;
import com.allenliu.versionchecklib.VersionDialogActivity;
import com.allenliu.versionchecklib.VersionParams;
import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Menu;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.fargment.admin.backshop.BackShopFragment;
import com.hhzb.fntalm.fargment.admin.cabinet.CabinetFragment;
import com.hhzb.fntalm.fargment.admin.datas.DataFragment;
import com.hhzb.fntalm.fargment.admin.sendwash.PackageFargment;
import com.hhzb.fntalm.fargment.admin.sendwash.SendWashFragment;
import com.hhzb.fntalm.fargment.admin.set.SetFragment;
import com.hhzb.fntalm.network.http.HttpConstants;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.service.UpdateService;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.GridItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.lzy.okgo.model.HttpParams;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.AppUtils;
import com.mmdet.lib.utils.SPUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-07.
 */
public class AdminFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private CommonAdapter<Menu> mAdapter;
    private GridLayoutManager mLayoutManager;
    List<Menu> mDatas = new ArrayList<Menu>();

    public static AdminFragment newInstance() {
        return new AdminFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_admin, container, false);
        initView(view);
        //检查更新
        String urlLss = SPUtils.getInstance(getActivity()).getString("addres_lss","");
        return view;
    }
    TextView version,exit,newVersion;
    private void initView(View view) {
        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
            }
            @Override
            public void leftclick(){
                start(MenuFragment.newInstance(), SupportFragment.SINGLETASK);
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.id_recyclerview);
        version = (TextView)view.findViewById(R.id.version);
        newVersion = (TextView)view.findViewById(R.id.newVersion);
        exit = (TextView)view.findViewById(R.id.exit);
        version.setText("版本号:"+AppUtils.getAppVersionName(getActivity()));
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        newVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVersion();
            }
        });
        createIndexMenu();
    }

    private void createIndexMenu() {
        Menu m1 = new Menu(1,"送洗衣物",R.drawable.menu_danlan,R.mipmap.menu_songxi);
        mDatas.add(m1);
        Menu m2 = new Menu(2,"衣物回柜",R.drawable.menu_danhuang,R.mipmap.menu_huigui);
        mDatas.add(m2);
        Menu m3 = new Menu(3,"衣物返店",R.drawable.menu_dancheng,R.mipmap.menu_fandian);
        mDatas.add(m3);
        Menu m4 = new Menu(4,"系统设置",R.drawable.menu_danlv,R.mipmap.menu_set);
        mDatas.add(m4);
        Menu m5 = new Menu(5,"柜子管理",R.drawable.menu_danhong,R.mipmap.gzsz);
        mDatas.add(m5);
        Menu m6 = new Menu(6,"数据管理",R.drawable.menu_danlanse,R.mipmap.menu_data);
        mDatas.add(m6);
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
                if(m.getID() == 1){
                    start(SendWashFragment.newInstance(),SupportFragment.SINGLETASK);
                }else if(m.getID() == 2){
                    start(PackageFargment.newInstance(null),SupportFragment.SINGLETASK);
                }else if(m.getID() == 3){
                    start(BackShopFragment.newInstance(),SupportFragment.SINGLETASK);
                }else if(m.getID() == 4){
                    start(SetFragment.newInstance(),SupportFragment.SINGLETASK);
                }else if(m.getID() == 5){
                    start(CabinetFragment.newInstance(),SupportFragment.SINGLETASK);
                }else{
                    start(DataFragment.newInstance(),SupportFragment.SINGLETASK);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    private void checkVersions() {
        RequestCenter.versionCheck(getActivity(), "版本号", new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try{
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        JSONObject versonJSONObject = jsonObj.getJSONObject("Rst");
                        if(versonJSONObject == null || versonJSONObject.toString().equals("null") || versonJSONObject.toString().equals("")){
                            ToastUtils.showShortToast(getActivity(),"已经是最新版本！");
                            return;
                        }
                        final String versionNo = versonJSONObject.getString("VersionNo");
                        final String downUrl = versonJSONObject.getString("Url");
                        final String comment = versonJSONObject.getString("Comment");
                        newVersion.setVisibility(View.VISIBLE);
                        newVersion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                //showNoticeDialog(versionNo,downUrl,comment);
                            }
                        });
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });

    }

    private void checkVersion() {

        //弹框处理 更新逻辑
        //getActivity().stopService(new Intent(getActivity(),UpdateService.class));
        HttpParams params = new HttpParams();
        params.put("softType","2");
        params.put("versionNo", AppUtils.getAppVersionName(getActivity()));
        params.put("token", HttpConstants.Token);
        VersionParams versionParams = new VersionParams()
                .setRequestUrl(HttpConstants.Version_Check(getActivity()))
                .setRequestParams(params)
                .setRequestMethod(HttpRequestMethod.POST);
        Intent intent = new Intent(getActivity(), UpdateService.class);
        intent.putExtra(AVersionService.VERSION_PARAMS_KEY, versionParams);
        getActivity().startService(intent);
    }


    @Override
    public void onSupportVisible() {
        TimerManager.getmInstace().remove(this);
    }
}
