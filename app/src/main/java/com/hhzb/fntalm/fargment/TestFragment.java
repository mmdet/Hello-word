package com.hhzb.fntalm.fargment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhzb.fntalm.R;
import com.hhzb.fntalm.utils.CalendarUtil;
import com.hhzb.fntalm.utils.TimeUtils;
import com.hhzb.fntalm.utils.ZoomOutPageTransformer;
import com.hhzb.fntalm.view.ViewPagerScroller;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 主界面轮播广告，日期时间显示业务
 * Created by c on 2017-02-21.
 */

public class TestFragment extends BaseFragment {

    public static TestFragment newInstance() {
        TestFragment fg = new TestFragment();
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_pay_weixin_or_zhifubao, container, false);
        return view;
    }

}
