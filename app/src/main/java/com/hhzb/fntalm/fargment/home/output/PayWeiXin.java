package com.hhzb.fntalm.fargment.home.output;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.ToastUtils;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import org.json.JSONObject;

import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class PayWeiXin extends BaseFragment {
    public static final String ARG_PAYTYPE = "arg_payType";
    private BackPut mBackPut;
    private TextView Order_price,dakaizhifubao;
    private ImageView qr;
    private Handler handler = new Handler();
    private String orderTradeNo;
    private int mPayType = CommonData.PAY_TYPE_WEIXIN;
    public static PayWeiXin newInstance(BackPut backPut,int payType) {
        PayWeiXin fg = new PayWeiXin();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.ARG_ARGS, backPut);
        bundle.putInt(ARG_PAYTYPE, payType);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBackPut = (BackPut)bundle.getSerializable(CommonData.ARG_ARGS);
            mPayType = (int)bundle.getInt(ARG_PAYTYPE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_pay_weixin, container, false);
        initView(view);
        getPayCode();
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
        Order_price = (TextView)view.findViewById(R.id.Order_price);
        dakaizhifubao = (TextView)view.findViewById(R.id.dakaizhifubao);
        Order_price.setText(mBackPut.getOldPrice()+"");
        qr = (ImageView)view.findViewById(R.id.qr);
        dakaizhifubao.setText("打开手机微信");
        if(mPayType == CommonData.PAY_TYPE_ZHIFUBAO){
            dakaizhifubao.setText("打开手机支付宝");
        }
    }

    private void getPayCode() {
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

    /**
     * 根据获取到的生成二维码图片显示
     * @param res
     */
    private void createCode(String res) {
        try{
            String json = CommonJsonCallback.parseXml(res);
            JSONObject jsonObj = new JSONObject(json);
            String isSuccess = jsonObj.getString("IsSuccess");
            String errorMsg = jsonObj.getString("ErrorMsg");
            if (isSuccess.equals("true")) {
                String weiQRCode = jsonObj.getString("Rst");
                //生成二维码
                if(mPayType == CommonData.PAY_TYPE_WEIXIN){
                    Bitmap qrCode = EncodingUtils.createQRCode(weiQRCode, 350, 350, BitmapFactory.decodeResource(getResources(),R.mipmap.weiixn));
                    qr.setImageBitmap(qrCode);
                }else{
                    Bitmap qrCode = EncodingUtils.createQRCode(weiQRCode, 350, 350, BitmapFactory.decodeResource(getResources(),R.mipmap.zhifubao));
                    qr.setImageBitmap(qrCode);
                }
                //开始
                handler.postDelayed(runnable, 5000);
            }
        }catch (Exception e){

        }
    }

    /**
     * 每隔xx秒执行一次
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getPayState();
            handler.postDelayed(this, 5000);
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
                    //支付成功 开锁
                    handler.removeCallbacks(runnable);
                    openCabinDoor();
                }
            }else {
                handler.removeCallbacks(runnable);
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            handler.removeCallbacks(runnable);
        }
    }

    private void openCabinDoor() {
        mBackPut.setNewPrice(mBackPut.getOldPrice());
        mBackPut.setRebate(0);
        mBackPut.setPayType(mPayType);
        Cabin cabin = CabinModule.getInstance().getOneCabin(mBackPut.getCabinetNo());
        if(SerialPortUtil.getInstance().open(cabin.getCmdNo())){
            mBackPut.setIsOut(true);
            mBackPut.setOutDate(TimeUtils.date2String(new Date()));
            BackputMoudle.getInstance().insertOrReplace(mBackPut);
            CabinModule.getInstance().setCabinUsed(mBackPut.getCabinetNo(),false);
            startWithPop(OutPutSuccessFragment.newInstance(mBackPut));
        }else{
            //打开失败，重开柜门
            startWithPop(OutPutFailFragment.newInstance(mBackPut));
        }
    }

}
