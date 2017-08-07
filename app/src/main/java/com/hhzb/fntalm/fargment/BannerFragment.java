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

public class BannerFragment extends BaseFragment {

    private TextView mTime,dateData,nongliData;
    private RollPagerView mRollViewPager;

    public static BannerFragment newInstance() {
        BannerFragment fg = new BannerFragment();
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_banner, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mRollViewPager  = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        mRollViewPager.setHintView(null);//隐藏指示器
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));
        mRollViewPager.getViewPager().setPageTransformer(true, new ZoomOutPageTransformer());
        mRollViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                start(MenuFragment.newInstance());
            }
        });
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(2000);
        scroller.initViewPagerScroll(mRollViewPager.getViewPager());

        mTime = (TextView) view.findViewById(R.id.mTime);
        dateData = (TextView) view.findViewById(R.id.dateData);
        nongliData = (TextView) view.findViewById(R.id.nomgliData);
        new TimeThread().start();
        //获取农历月和日
        String day = new CalendarUtil(Calendar.getInstance()).getDay();
        dateData.setText(StringData());
        nongliData.setText("农历"+day);
    }
    //获取公历年月日星期
    public static String StringData(){
        String mYear;
        String mMonth;
        String mDay;
        String mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取年
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取月
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取日
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));//获取星期
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mYear + "年" + mMonth + "月" + mDay+"日"+" 星期"+mWay;
    }

    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    mHandler.obtainMessage(0).sendToTarget();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = TimeUtils.milliseconds2String(sysTime,new SimpleDateFormat("HH:mm:ss"));
                    mTime.setText(sysTimeStr);
                    if(sysTimeStr.equals("00:00:00")){
                        //获取农历月和日
                        String day = new CalendarUtil(Calendar.getInstance()).getDay();
                        dateData.setText(StringData());
                        nongliData.setText("农历"+day);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.mipmap.timg,
                R.mipmap.timgs
        };

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

}
