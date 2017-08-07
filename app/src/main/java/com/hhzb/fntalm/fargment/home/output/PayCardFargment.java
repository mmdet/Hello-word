package com.hhzb.fntalm.fargment.home.output;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Card;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.fargment.home.recharge.CardTypeFargment;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.SpacesItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.JSONUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class PayCardFargment extends BaseFragment {
    public static final String ARG_PAYTYPE = "arg_payType";
    private BackPut mBackPut;
    private TextView Order_price;
    private int mPayType;
    private RecyclerView mRecyclerView;
    private CommonAdapter<Card> mAdapter;
    List<Card> mDatas = new ArrayList<Card>();
    public static PayCardFargment newInstance(BackPut backPut, int payType) {
        PayCardFargment fg = new PayCardFargment();
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
        View view = inflater.inflate(R.layout.fg_pay_card, container, false);
        initView(view);
        getCardList();
        return view;
    }

    private void getCardList() {
        RequestCenter.cardGet(getActivity(), mBackPut.getMobile(), new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        mDatas = JSONUtils.stringToList(jsonObj.getJSONArray("Rst").toString(),Card[].class);
                        if(mDatas.size() != 0)
                            createList(mDatas);
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
    TopBar topBar;
    @Override
    public void onTick(long millisUntilFinished) {
        super.onTick(millisUntilFinished);
        topBar.setRightText(millisUntilFinished/1000 +"s");
    }
    private void initView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.id_recyclerviews);

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
    }

    private void createList(List<Card> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mAdapter = new CommonAdapter<Card>(getActivity(), R.layout.pay_card_list_item, mDatas) {
            @Override
            protected void convert(final ViewHolder holder, final Card card, final int position) {
                if(card.getIsServer().equals("false")){
                    holder.getConvertView().setVisibility(View.GONE);
                    holder.setText(R.id.Bill_No, mBackPut.getMobile());
                    holder.setText(R.id.card_type, "钱包");
                }else{
                    holder.setText(R.id.Bill_No, card.getCardType());
                    holder.setText(R.id.card_type, card.getCardType());
                }

                holder.setText(R.id.crad_remain, card.getRemain());
                holder.setText(R.id.Order_price,mBackPut.getOldPrice()+"" );
                holder.setText(R.id.Rebate,card.getRebate());
                float money = (float)mBackPut.getOldPrice()*(Float.parseFloat(card.getRebate()));
                holder.setText(R.id.Order_own, money+"");
                mBackPut.setNewPrice(money);
                mBackPut.setRebate(Float.parseFloat(card.getRebate()));

                holder.setOnClickListener(R.id.Order_out, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(card.getIsServer().equals("false")){
                            virtualCardPay(mBackPut.getMobile());
                        }else{
                            serverCard_Pay(card.getCardNo());
                        }

                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    private void serverCard_Pay(String cardNo) {

        RequestCenter.cardPay(getActivity(), CommonFun.getShopId(getActivity()), cardNo, mBackPut.getNewPrice()+"", new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                       //支付成功
                        openCabinDoor();
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


    private void virtualCardPay(String mobile) {

        RequestCenter.virtualCardPay(getActivity(), mobile, mBackPut.getNewPrice()+"", new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                       //支付成功
                        openCabinDoor();
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





    private void openCabinDoor() {
        mBackPut.setNewPrice(mBackPut.getOldPrice());
        mBackPut.setRebate(mBackPut.getRebate());
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
