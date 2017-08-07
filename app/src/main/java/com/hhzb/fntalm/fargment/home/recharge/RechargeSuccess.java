package com.hhzb.fntalm.fargment.home.recharge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.CardType;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.TopBar;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-13.
 */
public class RechargeSuccess extends BaseFragment {
    private CardType mCardType;
    private static final String ARG_OUTPUT = "arg_output";

    TextView card_no,card_value,card_name,done;
    String cardno;
    public static RechargeSuccess newInstance(String cardno,CardType cardType) {
        RechargeSuccess fg = new RechargeSuccess();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_OUTPUT, cardType);
        bundle.putSerializable(CommonData.ARG_ARGS, cardno);
        fg.setArguments(bundle);
        return fg;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        TTS.speck_recharge_succcess(getActivity());
        if (bundle != null) {
            mCardType = (CardType)bundle.getSerializable(ARG_OUTPUT);
            cardno = (String)bundle.getSerializable(CommonData.ARG_ARGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_charge_success, container, false);
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
                Fg.startSingleTask(RechargeSuccess.this, MenuFragment.newInstance());
            }
        });
        card_no = (TextView)view.findViewById(R.id.card_no);
        card_name = (TextView)view.findViewById(R.id.card_name);
        card_value = (TextView)view.findViewById(R.id.card_value);
        done = (TextView)view.findViewById(R.id.done);
        card_no.setText(cardno);
        card_name.setText(mCardType.getName());
        card_value.setText(mCardType.getFaceValue()+"");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fg.startSingleTask(RechargeSuccess.this, MenuFragment.newInstance());
            }
        });
    }


}
