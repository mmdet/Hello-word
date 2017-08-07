package com.hhzb.fntalm.fargment;

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
import com.hhzb.fntalm.bean.WxOuath;
import com.hhzb.fntalm.fargment.admin.AdminFragment;
import com.hhzb.fntalm.fargment.home.buycard.BuyCardFargment;
import com.hhzb.fntalm.fargment.home.cancel.CancelFragment;
import com.hhzb.fntalm.fargment.home.chaxun.SearchFargment;
import com.hhzb.fntalm.fargment.home.input.InPutSuccessFargment;
import com.hhzb.fntalm.fargment.home.output.OutPutFragment;
import com.hhzb.fntalm.fargment.home.recharge.RechargeFargment;
import com.hhzb.fntalm.module.AdminModule;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.service.timer.TimerObserver;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.NumKeyboard;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.JSONUtils;
import com.mmdet.lib.utils.RegexUtils;
import com.mmdet.lib.utils.SPUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.tts.Speak;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 用户身份验证业务
 * 所有业务的入口处理分发类
 * 主要功能：手机号码验证or微信扫码验证
 * Created by c on 2016-12-09.
 */
public class CheckFragment extends BaseFragment implements NumKeyboard.IKeyBoardListener{

    private TextView tvInput,codeLogin;
    private int mStep = CommonData.STEP_ONE;
    //数据收集
    String mMobile,mMobileCode,mClothCount;
    private int mMenuId;
    private NumKeyboard numkeyboard;
    String openid;
    public static CheckFragment newInstance(int menuId,String openid) {
        CheckFragment fg = new CheckFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonData.ARG_MENUID, menuId);
        bundle.putSerializable(CommonData.ARG_ARGS, openid);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMenuId = bundle.getInt(CommonData.ARG_MENUID);
            openid = (String)bundle.getSerializable(CommonData.ARG_ARGS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_checked, container, false);
        initView(view);
        return view;
    }
    TopBar topBar;
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
        codeLogin = (TextView) view.findViewById(R.id.login_qcode);

        if(mMenuId == CommonData.MENU_ADMIN){
            TTS.speck_input_admin(getActivity());
            tvInput.setHint("输入管理员口令/手机号码");
        }else{
            TTS.speck_input_mobile(getActivity());
            tvInput.setHint(getResources().getString(R.string.Input_Tip_InputMobile));
        }

        codeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fg.startSingleTask(CheckFragment.this,QcordeFragment.newInstance(mMenuId));
            }
        });

    }
    Admin admin;
    //确定操作
    private void doSure(int step) {
        switch (step) {
            case CommonData.STEP_ONE:
                mMobile = CommonFun.getInput(tvInput);
                if(mMenuId == CommonData.MENU_ADMIN){
                    //管理员进入，无需验证手机号码
                    admin = AdminModule.getInstance().getCommand(mMobile);
                    if(admin == null){
                        TTS.speck_no_user(getActivity());
                       // Toast.makeText(getActivity(), "没有此用户，登陆失败", Toast.LENGTH_LONG).show();
                        return;
                    }else{
                        if(admin.getType() == 0){
                            //口令
                            if(admin.getUsed() == false){
                                TTS.speck_cant_used_kouling(getActivity());
                                //Toast.makeText(getActivity(), "口令禁用，请用手机号码登陆", Toast.LENGTH_LONG).show();
                                return;
                            }else{
                                start(AdminFragment.newInstance(), SupportFragment.SINGLETASK);
                            }

                        }else{
                            //手机号
                            mStep = CommonData.STEP_TWO;
                            TTS.speck_input_password(getActivity());
                            tvInput.setHint(getResources().getString(R.string.Input_Tip_InputPassword));
                        }
                    }

                }else{
                    //验证手机号码
                    if(!RegexUtils.isMobileExact(mMobile)){
                        TTS.speck_input_err_mobile(getActivity());
                        Toast.makeText(getActivity(), getResources().getString(R.string.Input_ErrorTip_MobileErr), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //发送手机验证码
                    sendMess(mMobile);
                }

                break;
            case CommonData.STEP_TWO:
                mMobileCode = CommonFun.getInput(tvInput);
                if(TextUtils.isEmpty(mMobileCode)){
                    TTS.speck_input_mobileCode(getActivity());
                    return;
                }
                if(mMenuId == CommonData.MENU_ADMIN){
                    if(!admin.getPassword().equals(mMobileCode)){
                        TTS.speck_err_password(getActivity());
                       //  "密码错误"
                        return;
                    }else if(admin.getUsed() == false){
                        TTS.speck_cant_used_connt(getActivity());
                        // "账号禁用",
                        return;
                    }else{
                        start(AdminFragment.newInstance(), SupportFragment.SINGLETASK);
                    }

                }else{
                    //进行验证码验证
                    chekMess(mMobile,mMobileCode);
                }

                break;
            case CommonData.STEP_THREE:
                //存衣  -- 输入件数步骤
                mClothCount = CommonFun.getInput(tvInput);
                if(TextUtils.isEmpty(mClothCount)){
                    TTS.speck_input(getActivity());
                    //Toast.makeText(getActivity(), getResources().getString(R.string.Input_Tip_InputPlease), Toast.LENGTH_LONG).show();
                    return;
                }
                //打开柜门 存放衣服
                openTheDoorAndInputColths();

                break;
        }
       // CommonFun.clearInput(tvInput);
    }
    private void openTheDoorAndInputColths() {
        //查询柜子情况，分配空柜子
        //存衣统一用叠式
        Cabin cabin = CabinModule.getInstance().getNoUsedCabin(CommonData.CABIN_TYPE_DIESHI);
        if(cabin == null){
            TTS.speck_no_cabin(getActivity());
            //Toast.makeText(getActivity(),"没有可以用的柜门",Toast.LENGTH_SHORT).show();
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


    /**
     * 获取手机验证码
     * @param mobile
     */
    private void sendMess(String mobile) {
        tvInput.setHint(mobile);
        RequestCenter.getCaptchas(getActivity(),mobile, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try{
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        ToastUtils.showShortToast(getActivity(),"手机验证码已经发送至您的手机");
                        //验证码发送成功，更换当前步骤，界面提示
                        mStep = CommonData.STEP_TWO;
                        CommonFun.clearInput(tvInput);
                        tvInput.setHint(getResources().getString(R.string.Input_Tip_InputMobileCode));
                        TTS.speck_input_mobileCode(getActivity());
                    }else{
                        ToastUtils.showShortToast(getActivity(),"获取验证码失败，请重试");
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Object reasonObj)
            {
                ToastUtils.showShortToast(getActivity(),"与服务器连接失败");
            }
        });
    }


    private void chekMess(String mobile,String captcha) {
        tvInput.setHint(captcha);
        RequestCenter.checkCaptchas(getActivity(),mobile, captcha,new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try{
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        //验证成功，更换当前步骤，界面提示
                        if(mMenuId == CommonData.MENU_INPUT){
                            //如果是存衣菜单进来的，那么要走第三步骤进行衣物件数录入
                            mStep = CommonData.STEP_THREE;
                            CommonFun.clearInput(tvInput);
                            TTS.speck_input_colthcount(getActivity());
                            tvInput.setHint(getResources().getString(R.string.Input_Tip_InputCount));
                        }else{
                            if(mMenuId==CommonData.MENU_OUTPUT){
                                start(OutPutFragment.newInstance(mMobile),SupportFragment.SINGLETASK);
                            }else if(mMenuId==CommonData.MENU_CANCEL){
                                start(CancelFragment.newInstance(mMobile),SupportFragment.SINGLETASK);
                            }else if(mMenuId==CommonData.MENU_BUYCARD){
                                start(BuyCardFargment.newInstance(mMobile),SupportFragment.SINGLETASK);
                            }else if(mMenuId==CommonData.MENU_RECHARGE){
                                start(RechargeFargment.newInstance(mMobile),SupportFragment.SINGLETASK);
                            }else if(mMenuId == CommonData.MENU_SEARCH){
                                start(SearchFargment.newInstance(mMobile),SupportFragment.SINGLETASK);
                            }
                        }
                    }else{
                        ToastUtils.showShortToast(getActivity(),"获取验证码失败，请重试");
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Object reasonObj)
            {
            }
        });

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
        doSure(mStep);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
    }
}
