package com.hhzb.fntalm.fargment.home.recharge;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Card;
import com.hhzb.fntalm.bean.CardType;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.GridItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.JSONUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-13.
 */
public class CardTypeFargment extends BaseFragment {
    private String mMobile;
    private RecyclerView mRecyclerView;
    private CommonAdapter<CardType> mAdapter;
    List<CardType> mDatas = new ArrayList<CardType>();
    public final static int HANDLER_WHAT_GetDataList = 0;
    TextView kefu_mobile,buyer_mobile,buycard;
    Card card;
    public static CardTypeFargment newInstance(String mobile ,Card card) {
        CardTypeFargment fg = new CardTypeFargment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.ARG_Mobile, mobile);
        bundle.putSerializable(CommonData.ARG_ARGS, card);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMobile = (String)bundle.getSerializable(CommonData.ARG_Mobile);
            card = (Card)bundle.getSerializable(CommonData.ARG_ARGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_card_type, container, false);
        TTS.speck_choose_card_type(getActivity());
        initView(view);
        getCardType();
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

        mRecyclerView = (RecyclerView)view.findViewById(R.id.id_recyclerview);
        kefu_mobile = (TextView)view.findViewById(R.id.kefu_mobile);
        buyer_mobile = (TextView)view.findViewById(R.id.buyer_mobile);
        buycard = (TextView)view.findViewById(R.id.buycard);
        buyer_mobile.setText("您的手机号码:"+mMobile);
        kefu_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // AlertDialogUtils.showMyDialog(getActivity());
            }
        });
        buycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardType == null || cardType.isChecked() == false){
                    ToastUtils.showShortToast(getActivity(),"没有选择卡");
                    return;
                }
                if(card.getIsServer().equals("false")){
                    Fg.startSingleTask(CardTypeFargment.this,ChoosePayTypeFragment.newInstance(mMobile,null,cardType));
                }else{
                    Fg.startSingleTask(CardTypeFargment.this,ChoosePayTypeFragment.newInstance(mMobile,card.getCardNo(),cardType));
                }

            }
        });
    }

    private void getCardType() {
        RequestCenter.cardTypeGet(getActivity(), CommonFun.getShopId(getActivity()), new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    String json = CommonJsonCallback.parseXml(responseObj.toString());
                    JSONObject jsonObj = new JSONObject(json);
                    String isSuccess = jsonObj.getString("IsSuccess");
                    String errorMsg = jsonObj.getString("ErrorMsg");
                    if (isSuccess.equals("true")) {
                        mDatas = JSONUtils.stringToList(jsonObj.getJSONArray("Rst").toString(),CardType[].class);
                        createList(mDatas);
                    }else {
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

                }
            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }
    private void createList(List<CardType> mDatas) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRecyclerView.addItemDecoration(new GridItemDecoration(3, 10,true));
        mAdapter = new CommonAdapter<CardType>(getActivity(), R.layout.charge_item_list, mDatas) {
            @Override
            protected void convert(final ViewHolder holder, final CardType cardType, final int position) {
                if(cardType.isChecked() == true){
                    holder.setTextColorRes(R.id.card_value,R.color.ColorMain);
                    holder.setTextColorRes(R.id.card_name,R.color.ColorMain);
                    holder.setBackgroundRes(R.id.main_bg,R.drawable.charge_btn_check);
                }else{
                    holder.setTextColorRes(R.id.card_value,R.color.ColorBlack);
                    holder.setTextColorRes(R.id.card_name,R.color.ColorGray);
                    holder.setBackgroundRes(R.id.main_bg,R.drawable.charge_btn_noaml);
                }
                holder.setText(R.id.card_value, cardType.getName());
                holder.setText(R.id.card_name, cardType.getFaceValue()+"元");
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                checkCard(position);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    CardType cardType;
    private void checkCard(int position) {
        List<CardType> cardTypes = mAdapter.getDatas();
        cardType = cardTypes.get(position);
        if(cardType.isChecked() == true){
            return;
        }
        for(CardType cardType :cardTypes){
            cardType.setChecked(false);
        }
        cardType.setChecked(true);
        mAdapter.notifyDataSetChanged();
    }
}
