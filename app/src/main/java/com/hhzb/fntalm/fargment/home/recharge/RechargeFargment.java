package com.hhzb.fntalm.fargment.home.recharge;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Card;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.SpacesItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.utils.JSONUtils;
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
public class RechargeFargment extends BaseFragment {
    private String mMobile;
    private RecyclerView mRecyclerView;
    private CommonAdapter<Card> mAdapter;
    List<Card> mDatas = new ArrayList<Card>();
    public final static int HANDLER_WHAT_GetDataList = 0;
    public static RechargeFargment newInstance(String mobile) {
        RechargeFargment fg = new RechargeFargment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.ARG_Mobile, mobile);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMobile = (String)bundle.getSerializable(CommonData.ARG_Mobile);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_recharge, container, false);
        TTS.speck_choose_card(getActivity());
        initView(view);
        getData();
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
        mRecyclerView = (RecyclerView)view.findViewById(R.id.id_recyclerviews);
    }

    private void getData() {

        RequestCenter.cardGet(getActivity(), mMobile, new DisposeDataListener() {
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
    private void createList(List<Card> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mAdapter = new CommonAdapter<Card>(getActivity(), R.layout.card_item, mDatas) {
            @Override
            protected void convert(final ViewHolder holder, final Card card, final int position) {
                if(card.getIsServer().equals("true")){
                    holder.setText(R.id.card_name,card.getCardType());
                    holder.setText(R.id.card_number,card.getCardNo());
                    holder.setText(R.id.card_remain,card.getRemain());
                    holder.setText(R.id.tome,card.getExpiryDate());

                }else{
                    holder.setText(R.id.card_name,"钱包");
                    holder.setText(R.id.card_number,"永久");
                    holder.setText(R.id.tome,card.getExpiryDate());
                    holder.setText(R.id.card_remain,card.getRemain());
                }

            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Card card = mAdapter.getDatas().get(holder.getAdapterPosition());
                start(CardTypeFargment.newInstance(mMobile,card));
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

}
