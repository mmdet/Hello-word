package com.hhzb.fntalm.fargment.admin.cabinet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.bean.Menu;
import com.hhzb.fntalm.fargment.BaseFragment;
import com.hhzb.fntalm.fargment.MenuFragment;
import com.hhzb.fntalm.module.CabinModule;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.utils.DialogUtil;
import com.hhzb.fntalm.utils.TTS;
import com.hhzb.fntalm.view.SpacesItemDecoration;
import com.hhzb.fntalm.view.TopBar;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.utils.SPUtils;
import com.mmdet.lib.utils.ScreenUtils;
import com.mmdet.lib.utils.StringUtils;
import com.mmdet.lib.utils.TimeUtils;
import com.mmdet.lib.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class CabinetFragment extends BaseFragment {

    private EditText etCabincount;
    private TextView sure,addone,address;
    private RecyclerView mRecyclerView;
    private CommonAdapter<Cabin> mAdapter;
    private long mCabinCount;

    private StringBuilder sb;
    public static CabinetFragment newInstance() {
        return new CabinetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cabinet, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
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


        etCabincount = (EditText)view.findViewById(R.id.et_cabincount);
        sure = (TextView)view.findViewById(R.id.sure);
        addone = (TextView)view.findViewById(R.id.addone);
        address = (TextView)view.findViewById(R.id.address);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mCabinCount = CabinModule.getInstance().getCount();
        if(mCabinCount != 0){
            etCabincount.setText(""+mCabinCount);
            sure.setText("一键重置");
            createList(CabinModule.getInstance().getAll());
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建柜子数据
                CreateCabin();
            }
        });
        addone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建柜子数据
                CreateOneCabin();
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置地址
                setAddress();
            }
        });

    }



    private void CreateOneCabin() {
            cabinTypes = 0;
            sb = new StringBuilder();
            dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_edit_cabin, null);
            final EditText cabinNo = (EditText)dialogView.findViewById(R.id.edit_cabin);
            final EditText cmdNo = (EditText)dialogView.findViewById(R.id.edit_cmd);
            final RadioGroup cabinType = (RadioGroup)dialogView.findViewById(R.id.cabin_type);

            cx_xiyi = (CheckBox) dialogView.findViewById(R.id.cx_xiyi);
            cx_juanzeng = (CheckBox) dialogView.findViewById(R.id.cx_juanzeng);
            cx_huishou = (CheckBox) dialogView.findViewById(R.id.cx_huishou);


            setRadioGroupListener(cabinType);

            cx_xiyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        sb.append("洗衣,");
                    }
                }
            });
            cx_juanzeng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        sb.append("捐赠,");
                    }
                }
            });
            cx_huishou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        sb.append("回收,");
                    }
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String cabinNos = cabinNo.getText().toString().trim();
                    String cmdNos = cmdNo.getText().toString().trim();
                    if(cabinNos.equals("")  || cmdNos.equals("")){
                        TTS.speck_input_cabincount(getActivity());
                        return;
                    }
                    Cabin cabin = new Cabin();
                    cabin.setCabinType(cabinTypes);
                    cabin.setCabinNo(Integer.parseInt(cabinNos));
                    cabin.setCmdNo(Integer.parseInt(cmdNos));
                    cabin.setUseRanges(sb.toString().equals("")?"洗衣":sb.toString().substring(0,sb.toString().length()-1));
                    CabinModule.getInstance().insertOrReplace(cabin);
                    mAdapter.getDatas().add(cabin);
                    mAdapter.notifyDataSetChanged();
                }
            });
//给对话框设置视图
            builder.setView(dialogView);
            builder.setCancelable(true);//设置不可取消
            AlertDialog mAlertDialog = builder.create();

            Window win = mAlertDialog.getWindow();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.y = ScreenUtils.getScreenHeight(getActivity()) /4;//设置y坐标
            win.setAttributes(params);
            mAlertDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
            mAlertDialog.show();

    }

    private void CreateCabin() {
        if(mCabinCount != 0)
            CabinModule.getInstance().delete();
        String inputCount = etCabincount.getText().toString().trim();
        if(StringUtils.isEmpty(inputCount))
            return;
        List<Cabin> cabins = new ArrayList<Cabin>();
        int count = Integer.parseInt(inputCount);
        for(int i=0;i<count;i++){
            Cabin cabin;
            if(i>5){
                cabin = new Cabin(
                        null, i + 1, i + 1, 1, false, "洗衣", false, TimeUtils.milliseconds2String(System.currentTimeMillis()));
            }else {
                cabin = new Cabin(
                        null, i + 1, i + 1, 0, false, "洗衣", false, TimeUtils.milliseconds2String(System.currentTimeMillis()));
            }
            cabins.add(cabin);
        }
        CabinModule.getInstance().insertOrReplace(cabins);
        createList(CabinModule.getInstance().getAll());
    }

    private ArrayAdapter<String> adapter;
    private void createList(List<Cabin> mDatas) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mAdapter = new CommonAdapter<Cabin>(getActivity(), R.layout.cabin_code_list_item, mDatas) {
            @Override
            protected void convert(final ViewHolder holder, final Cabin cabin, final int position) {
                holder.setText(R.id.Cabin_No, cabin.getCabinNo()+"");
                holder.setText(R.id.Cmd_No, cabin.getCmdNo()+"");
                holder.setText(R.id.CabinType, cabin.getCabinType() ==0?"叠式":"挂式");
                holder.setText(R.id.IsUsed,cabin.getIsUsed()?"使用中":"未使用");
                holder.setText(R.id.UseRanges, cabin.getUseRanges()+"");
                holder.setOnClickListener(R.id.open, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SerialPortUtil.getInstance().open(cabin.getCmdNo())){
                            //开柜成功
                            ToastUtils.showShortToast(getActivity(),"成功");
                        }

                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Cabin cabin = mAdapter.getDatas().get(holder.getAdapterPosition());
                edit(cabin);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    private View dialogView;
    private int cabinTypes = 0;
    CheckBox cx_xiyi,cx_juanzeng,cx_huishou;

    void edit(final Cabin cabin){
        cabinTypes = 0;
        sb = new StringBuilder();
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_edit_cabin, null);
        final EditText cabinNo = (EditText)dialogView.findViewById(R.id.edit_cabin);
        final EditText cmdNo = (EditText)dialogView.findViewById(R.id.edit_cmd);
        final RadioGroup cabinType = (RadioGroup)dialogView.findViewById(R.id.cabin_type);

        cx_xiyi = (CheckBox) dialogView.findViewById(R.id.cx_xiyi);
        cx_juanzeng = (CheckBox) dialogView.findViewById(R.id.cx_juanzeng);
        cx_huishou = (CheckBox) dialogView.findViewById(R.id.cx_huishou);

        cabinNo.setText(cabin.getCabinNo()+"");
        cmdNo.setText(cabin.getCmdNo()+"");
        setRadioGroupListener(cabinType);

        cx_xiyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sb.append("洗衣,");
                }
            }
        });
        cx_juanzeng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sb.append("捐赠,");
                }
            }
        });
        cx_huishou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sb.append("回收,");
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cabinNos = cabinNo.getText().toString().trim();
                String cmdNos = cmdNo.getText().toString().trim();
                cabin.setCabinType(cabinTypes);
                cabin.setCabinNo(Integer.parseInt(cabinNos));
                cabin.setCmdNo(Integer.parseInt(cmdNos));
                cabin.setUseRanges(sb.toString().equals("")?"洗衣":sb.toString().substring(0,sb.toString().length()-1));
                CabinModule.getInstance().insertOrReplace(cabin);
                mAdapter.notifyDataSetChanged();
            }
        });
        //给对话框设置视图
        builder.setView(dialogView);
        builder.setCancelable(true);//设置不可取消
        AlertDialog mAlertDialog = builder.create();

        Window win = mAlertDialog.getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.y = ScreenUtils.getScreenHeight(getActivity()) /4;//设置y坐标
        win.setAttributes(params);
        mAlertDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        mAlertDialog.show();
    }

    private void setRadioGroupListener(RadioGroup cabinType) {
        //为RadioGroup设置监听事件
        cabinType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.die){
                    cabinTypes = 0;
                }else if(checkedId == R.id.gua){
                    cabinTypes = 1;
                }
            }
        });
    }



    private void setAddress() {
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_address_cabin, null);
        final EditText edit_a = (EditText)dialogView.findViewById(R.id.edit_a);
        final EditText edit_b = (EditText)dialogView.findViewById(R.id.edit_b);
        final TextView edit_set = (TextView)dialogView.findViewById(R.id.edit_set);

        edit_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addrA = edit_a.getText().toString().trim();
                String addrB = edit_b.getText().toString().trim();
                if(!addrA.isEmpty() && !addrB.isEmpty()){
                    if( SerialPortUtil.getInstance().setAddress(addrA,addrB)){
                        //TTS.speck_set_success(getActivity());
                    }else {
                        //TTS.speck_set_fail(getActivity());
                    }
                    //etCabincount.setText(SerialPortUtil.getInstance().resultData);
                }else{
                    TTS.speck_input(getActivity());
                }

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("关闭窗口", null);
        //给对话框设置视图
        builder.setView(dialogView);
        builder.setCancelable(true);//设置不可取消
        AlertDialog mAlertDialog = builder.create();

        Window win = mAlertDialog.getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.y = ScreenUtils.getScreenHeight(getActivity()) /4;//设置y坐标
        win.setAttributes(params);
        mAlertDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        mAlertDialog.show();

    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().remove(this);
    }
}
