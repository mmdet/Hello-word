package com.hhzb.fntalm.fargment.home.cancel;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.fargment.home.buycard.BuyCardSuccess;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by c on 2016-12-09.
 */
public class CancelFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private CommonAdapter<Input> mAdapter;
    private List<Input> mDatas = new ArrayList<Input>();
    private String mMobile;
    //WHAT
    public final static int HANDLER_WHAT_GetDataList = 0;
    public final static int HANDLER_WHAT_delDataList = 1;
    private static final String ARG_MOBILE = "arg_mobile";
    private int delPosition;
    public static CancelFragment newInstance(String mobile) {
        CancelFragment fg = new CancelFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_MOBILE, mobile);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMobile = bundle.getString(ARG_MOBILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cancel_list, container, false);
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);

        topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
                Toast.makeText(getActivity(), "Right Clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void leftclick(){
                Fg.startSingleTask(CancelFragment.this, MenuFragment.newInstance());
            }
        });
    }

    private void getData() {
        mDatas = InputMoudle.getInstance().get(mMobile,false);
        if(!mDatas.isEmpty())
            createList(mDatas);
    }

    private void createList(List<Input> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<Input>(getActivity(), R.layout.cancel_list_item, mDatas) {
            @Override
            protected void convert(final ViewHolder holder, final Input input, final int position) {
                holder.setText(R.id.Bill_No, "订单号:"+input.getBillNo());
                holder.setText(R.id.thecabin, "柜门号码:"+input.getCabinetNo()+"");
                holder.setText(R.id.theCount, "存放数量:"+input.getClothCount()+"");
                //holder.setText(R.id.Order_state, input.getOrderStateName());
                holder.setText(R.id.Order_Type,"洗衣");
                holder.setText(R.id.date, "存入日期:"+input.getInputDate());
                holder.setOnClickListener(R.id.Order_out, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delPosition = holder.getAdapterPosition();
                        Input input = mAdapter.getDatas().get(delPosition);
                        cancelOrder(input);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 撤单
     * @param input
     */
    private void cancelOrder(Input input) {
        Cabin c = CabinModule.getInstance().getOneCabin(input.getCabinetNo());
        if(SerialPortUtil.getInstance().open(c.getCmdNo())){
            input.setIsCancel(true);
            input.setCancelDate(TimeUtils.date2String(new Date()));
            InputMoudle.getInstance().insertOrReplace(input);
            CabinModule.getInstance().setCabinUsed(input.getCabinetNo(),false);
            removeData(delPosition);
        }
    }
    private void removeData(int position) {
        mDatas.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

}
