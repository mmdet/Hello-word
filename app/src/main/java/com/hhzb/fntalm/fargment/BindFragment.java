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
import com.hhzb.fntalm.fargment.admin.AdminFragment;
import com.hhzb.fntalm.fargment.home.buycard.BuyCardFargment;
import com.hhzb.fntalm.fargment.home.cancel.CancelFragment;
import com.hhzb.fntalm.fargment.home.chaxun.SearchFargment;
import com.hhzb.fntalm.fargment.home.input.InPutSuccessFargment;
import com.hhzb.fntalm.fargment.home.input.InputCountFragment;
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
import com.mmdet.lib.utils.ToastUtils;

import org.json.JSONObject;

import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;

/**
 *手机号码与微信openID
 */
public class BindFragment extends BaseFragment implements NumKeyboard.IKeyBoardListener {

    private TextView tvInput;
    private int mStep = CommonData.STEP_ONE;
    //数据收集
    String mMobile,mMobileCode;
    private int mMenuId;
    private NumKeyboard numkeyboard;
    private String openid;
    public static BindFragment newInstance(int menuId, String openid) {
        BindFragment fg = new BindFragment();
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
        View view = inflater.inflate(R.layout.fg_bind, container, false);
        TTS.speck_input_mobile(getActivity());
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
            }
            @Override
            public void leftclick(){
                pop();
            }
        });

        numkeyboard = (NumKeyboard) view.findViewById(R.id.numkeyboard);
        numkeyboard.setiKeyBoardListener(this);
        tvInput = (TextView) view.findViewById(R.id.textview_input);

        tvInput.setHint(getResources().getString(R.string.Input_Tip_InputMobile));
    }
    //确定操作
    private void doSure(int step) {
        switch (step) {
            case CommonData.STEP_ONE:
                mMobile = CommonFun.getInput(tvInput);
                //验证手机号码
                if(!RegexUtils.isMobileExact(mMobile)){
                    TTS.speck_input_err_mobile(getActivity());
                    Toast.makeText(getActivity(), getResources().getString(R.string.Input_ErrorTip_MobileErr), Toast.LENGTH_LONG).show();
                    return;
                }
                //发送手机验证码
                sendMess(mMobile);
                break;
            case CommonData.STEP_TWO:
                mMobileCode = CommonFun.getInput(tvInput);
                if(TextUtils.isEmpty(mMobileCode)){
                    TTS.speck_input_mobileCode(getActivity());
                   // Toast.makeText(getActivity(), getResources().getString(R.string.Input_Tip_InputPlease), Toast.LENGTH_LONG).show();
                    return;
                }
                //绑定手机与微信
                Customer_BindOpenID();
                break;
        }
        CommonFun.clearInput(tvInput);
    }

    private void Customer_BindOpenID() {
        RequestCenter.bindOpenID(getActivity(), mMobile, mMobileCode, openid, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                       //绑定成功
                        TTS.speck_bind_success(getActivity());
                        ToastUtils.showShortToast(getActivity(),"绑定成功");
                        //验证成功，更换当前步骤，界面提示
                        if(mMenuId == CommonData.MENU_INPUT){
                            Fg.startSingleTask(BindFragment.this, InputCountFragment.newInstance(mMobile));
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
                    } else {
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
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
                        TTS.speck_input_mobileCode(getActivity());
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

}
