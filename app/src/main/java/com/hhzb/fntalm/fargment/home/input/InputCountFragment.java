package com.hhzb.fntalm.fargment.home.input;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Admin;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.fargment.QcordeFragment;
import com.hhzb.fntalm.fargment.admin.AdminFragment;
import com.hhzb.fntalm.fargment.home.cancel.CancelFragment;
import com.hhzb.fntalm.fargment.home.chaxun.SearchFargment;
import com.hhzb.fntalm.fargment.home.output.OutPutFragment;
import com.hhzb.fntalm.fargment.home.recharge.RechargeFargment;
import com.hhzb.fntalm.module.AdminModule;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.NumKeyboard;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.RegexUtils;
import com.mmdet.lib.utils.SPUtils;
import com.mmdet.lib.utils.ToastUtils;

import org.json.JSONObject;

import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 用户身份验证业务
 * 所有业务的入口处理分发类
 * 主要功能：手机号码验证or微信扫码验证
 * Created by c on 2016-12-09.
 */
public class InputCountFragment extends BaseFragment implements NumKeyboard.IKeyBoardListener {

    private TextView tvInput,codeLogin;
    //数据收集
    String mMobile,mClothCount;
    private NumKeyboard numkeyboard;
    public static InputCountFragment newInstance(String mMobile) {
        InputCountFragment fg = new InputCountFragment();
        Bundle bundle = new Bundle();
       bundle.putSerializable(CommonData.ARG_ARGS, mMobile);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMobile = (String)bundle.getSerializable(CommonData.ARG_ARGS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_input_count, container, false);
        TTS.speck_input_colthcount(getActivity());
        initView(view);
        return view;
    }
    TopBar topBar;
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
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

        numkeyboard = (NumKeyboard) view.findViewById(R.id.numkeyboard);
        numkeyboard.setiKeyBoardListener(this);
        tvInput = (TextView) view.findViewById(R.id.textview_input);
    }
    //确定操作
    private void doSure() {
        //存衣  -- 输入件数步骤
        mClothCount = CommonFun.getInput(tvInput);
        Log.i("",mClothCount+"");
        if(TextUtils.isEmpty(mClothCount)){
            TTS.speck_input(getActivity());
            //Toast.makeText(getActivity(), getResources().getString(R.string.Input_Tip_InputPlease), Toast.LENGTH_LONG).show();
            return;
        }
        //打开柜门 存放衣服
        openTheDoorAndInputColths();
    }
    private void openTheDoorAndInputColths() {
        //查询柜子情况，分配空柜子
        //存衣统一用叠式
        Cabin cabin = CabinModule.getInstance().getNoUsedCabin(CommonData.CABIN_TYPE_DIESHI);
        if(cabin == null){
            TTS.speck_no_cabin(getActivity());//"没有可以用的柜门"
            return;
        }
        if(SerialPortUtil.getInstance().open(cabin.getCmdNo())){
            /**
             * 开锁成功！
             * 1生成衣物订单号码，并且生成一条衣物数据写入数据库
             * 2更改柜门的使用状态
             * 3跳转成功界面，提醒顾客放好衣物
             */
            String orderTradeNo = CommonFun.getOutTradeNo();
            String SiteCode = SPUtils.getInstance(getActivity()).getString("site_id","01");
            Input input = new Input(null,orderTradeNo,SiteCode,mMobile,Integer.parseInt(mClothCount),cabin.getCabinNo(),
                    TimeUtils.date2String(new Date()),false,null,null,false,null,false,null);
            InputMoudle.getInstance().insertOrReplace(input);
            CabinModule.getInstance().setCabinUsed(cabin,true);
            start(InPutSuccessFargment.newInstance(orderTradeNo,mClothCount,String.valueOf(cabin.getCabinNo())));
        }else{
            /**
             * 开锁失败的情况！
             */
        }
    }

    @Override
    public void insert(String text) {
        CommonFun.insertInput(tvInput,text,false);
    }

    @Override
    public void delete() {
        CommonFun.delInput(tvInput);
    }

    @Override
    public void clear() {
        CommonFun.clearInput(tvInput);
    }

    @Override
    public void comfirm() {
        doSure();
    }

}
