package com.hhzb.fntalm.fargment.admin.sendwash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.fargment.Fg;
import com.hhzb.fntalm.fargment.admin.backput.TypeFragment;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.view.NumKeyboard;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;

import java.util.Date;

/**
 * Created by c on 2016-12-08.
 */
public class PackageFargment extends BaseFragment implements NumKeyboard.IKeyBoardListener {
    private EditText tvInput;
    private int mStep =  CommonData.STEP_ONE;
    private Input mInput;
    private static final String ARG_Input = "arg_Input";
    NumKeyboard numkeyboard;
    public static PackageFargment newInstance(Input input) {
        PackageFargment fg = new PackageFargment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ARG_Input",input);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mInput = (Input)bundle.getSerializable("ARG_Input");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_package, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        numkeyboard = (NumKeyboard) view.findViewById(R.id.numkeyboard);
        numkeyboard.setiKeyBoardListener(this);
        tvInput = (EditText) view.findViewById(R.id.textview_input);
        TopBar topBar = (TopBar)view.findViewById(R.id.titletop);
        topBar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void rightclick(){
            }
            @Override
            public void leftclick(){
                pop();
            }
        });
    }
    //确定操作
    private void doSure(int step) {
        switch (step) {
            case  CommonData.STEP_ONE:
                String pageCode =  CommonFun.getInput(tvInput);
                if(TextUtils.isEmpty(pageCode)){
                    Toast.makeText(getActivity(), getResources().getString(R.string.Input_Tip_InputPlease), Toast.LENGTH_LONG).show();
                    return;
                }
                if(mInput == null){
                    //回柜
                    startWithPop(TypeFragment.newInstance(pageCode));
                }else{
                    //开锁，上传
                    //开柜
                    Cabin cabin = CabinModule.getInstance().getOneCabin(mInput.getCabinetNo());
                    if(SerialPortUtil.getInstance().open(cabin.getCmdNo())){
                        //开柜成功的 情况
                        //更改本件衣物的取走状态,取走日期
                        mInput.setPackCode(pageCode);
                        mInput.setIsOut(true);
                        mInput.setOutDate(TimeUtils.date2String(new Date()));
                        InputMoudle.getInstance().update(mInput);
                        //更改柜门状态
                        CabinModule.getInstance().setCabinUsed(mInput.getCabinetNo(),false);
                        Fg.startSingleTask(this,SendWashFragment.newInstance());
                    };

                }
                break;
        }
        CommonFun.clearInput(tvInput);
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
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
