package com.hhzb.fntalm.fargment.home.recharge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.CardType;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.TopBar;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.EncodeUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by c on 2016-12-13.
 */
public class ChoosePayTypeFragment extends BaseFragment {
    private int mPayType = CommonData.PAY_TYPE_WEIXIN;
    private CardType mCard;
    private String mMobile,mCardNo;
    private ImageView qr;
    private LinearLayout rbt_weixin;
    private LinearLayout rbt_zhifubao,payCode;
    private String paySign,payUrl;
    private String orderTradeNo;


    public static ChoosePayTypeFragment newInstance(String mobile,String cardNo,CardType card) {
        ChoosePayTypeFragment fg = new ChoosePayTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.ARG_Mobile, mobile);
        bundle.putSerializable(CommonData.ARG_ARGS, card);
        bundle.putSerializable(CommonData.ARG_MENUID, cardNo);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMobile = (String)bundle.getSerializable(CommonData.ARG_Mobile);
            mCardNo = (String)bundle.getSerializable(CommonData.ARG_MENUID);
            mCard = (CardType)bundle.getSerializable(CommonData.ARG_ARGS);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_card_paytype, container, false);

        TTS.speck_choose_pay_type(getActivity());
        initView(view);
        return view;
    }
    TextView paytype_tip,Order_price,dakaizhifubao;
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

        qr = (ImageView)view.findViewById(R.id.qr);
        Order_price = (TextView)view.findViewById(R.id.Order_price);
        dakaizhifubao = (TextView)view.findViewById(R.id.dakaizhifubao);
        rbt_weixin=(LinearLayout)view.findViewById(R.id.RadioButton_WeiXin);
        rbt_zhifubao=(LinearLayout)view.findViewById(R.id.RadioButton_ZhuFuBao);
        payCode=(LinearLayout)view.findViewById(R.id.payCode);

        Order_price.setText(mCard.getFreeMoney()+"");
        //支付方式的选择
        rbt_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = CommonData.PAY_TYPE_WEIXIN;
                setTipText(dakaizhifubao);
                CreateQRcode();
            }
        });
        rbt_zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayType = CommonData.PAY_TYPE_ZHIFUBAO;
                setTipText(dakaizhifubao);
                CreateQRcode();
            }
        });
    }
    void setTipText(TextView dakaizhifubao){
        switch (mPayType){
            case CommonData.PAY_TYPE_WEIXIN:
                dakaizhifubao.setText("打开微信客户端");
            break;
            case CommonData.PAY_TYPE_ZHIFUBAO:
            break;
        }
    }

    private void CreateQRcode() {
        orderTradeNo = CommonFun.getOutTradeNo();
        RequestCenter.payCode(getActivity(),mPayType,CommonFun.getShopId(getActivity()),orderTradeNo,String.valueOf(0.01),new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                //生成二维码
                createCode(responseObj.toString());
            }
            @Override
            public void onFailure(Object reasonObj) {

                ToastUtils.showShortToast(getActivity(),"生成二维码失败，请重试");
            }
        });
    }

    private void createCode(String res) {
        try{
            String json = CommonJsonCallback.parseXml(res);
            JSONObject jsonObj = new JSONObject(json);
            String isSuccess = jsonObj.getString("IsSuccess");
            String errorMsg = jsonObj.getString("ErrorMsg");
            if (isSuccess.equals("true")) {
                String weiQRCode = jsonObj.getString("Rst");
                payCode.setVisibility(View.VISIBLE);
                //生成二维码
                if(mPayType == CommonData.PAY_TYPE_WEIXIN){
                    Bitmap qrCode = EncodingUtils.createQRCode(weiQRCode, 300, 300, BitmapFactory.decodeResource(getResources(),R.mipmap.weiixn));
                    qr.setImageBitmap(qrCode);
                }else{
                    Bitmap qrCode = EncodingUtils.createQRCode(weiQRCode, 300, 300, BitmapFactory.decodeResource(getResources(),R.mipmap.zhifubao));
                    qr.setImageBitmap(qrCode);
                }
                //开始
                handler.postDelayed(runnable, 5000);
            }
        }catch (Exception e){

        }
    }
    private Handler handler = new Handler();
    /**
     * 每隔xx秒执行一次
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getPayState();
            handler.postDelayed(this, 4000);
        }
    };
    private void getPayState() {
        RequestCenter.payResult(getActivity(),mPayType,CommonFun.getShopId(getActivity()),orderTradeNo, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                payResultSuccess(responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }


    /**
     * 处理支付结果
     * @param res
     */
    private void payResultSuccess(String res) {
        try{
            String json = CommonJsonCallback.parseXml(res);
            JSONObject jsonObj = new JSONObject(json);
            String isSuccess = jsonObj.getString("IsSuccess");
            String errorMsg = jsonObj.getString("ErrorMsg");
            if (isSuccess.equals("true")) {
                String payState = jsonObj.getString("Rst");
                if(payState.equals("true")){
                    //支付成功  ,充值
                    handler.removeCallbacks(runnable);
                    if(mCardNo == null){
                        VirtualCardRecharge();
                    }else{
                        SeverCardRecharge();
                    }
                }
            }else {
                handler.removeCallbacks(runnable);
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            handler.removeCallbacks(runnable);
        }
    }

    private void SeverCardRecharge() {
        RequestCenter.cardRecharge(
                getActivity(), CommonFun.getShopId(getActivity()), mCard.getValue()+"", mCardNo,
                mCard.getName(), mCard.getFaceValue()+"", mCard.getRebate()+"",
                mCard.getFreeMoney()+"", mCard.getValidMonths()+"", new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try{
                            String json = CommonJsonCallback.parseXml(responseObj.toString());
                            JSONObject jsonObj = new JSONObject(json);
                            String isSuccess = jsonObj.getString("IsSuccess");
                            String errorMsg = jsonObj.getString("ErrorMsg");
                            if (isSuccess.equals("true")) {
                                Fg.startSingleTask(ChoosePayTypeFragment.this,RechargeSuccess.newInstance(mCardNo,mCard));
                            }else {
                                //充值数据提交失败

                                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
    }

    /**
     * 钱包充值
     */
    private void VirtualCardRecharge() {
        RequestCenter.virtuaCardRecharge(
                getActivity(), CommonFun.getShopId(getActivity()), mCard.getValue()+"", mMobile,
                mCard.getName(), mCard.getFaceValue()+"", mCard.getRebate()+"",
                mCard.getFreeMoney()+"", mCard.getValidMonths()+"", new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        try{
                            String json = CommonJsonCallback.parseXml(responseObj.toString());
                            JSONObject jsonObj = new JSONObject(json);
                            String isSuccess = jsonObj.getString("IsSuccess");
                            String errorMsg = jsonObj.getString("ErrorMsg");
                            if (isSuccess.equals("true")) {
                                Fg.startSingleTask(ChoosePayTypeFragment.this,RechargeSuccess.newInstance(mCardNo,mCard));
                            }else {
                                //充值数据提交失败

                                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
    }



}
