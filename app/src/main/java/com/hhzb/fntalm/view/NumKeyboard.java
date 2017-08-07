package com.hhzb.fntalm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhzb.fntalm.R;

/**
 * 自定义的数字键盘
 * Created by c on 2016-12-14.
 */

public class NumKeyboard extends LinearLayout implements View.OnClickListener{
    View view;
    IKeyBoardListener iKeyBoardListener;

    private TextView numkeyboard_one,numkeyboard_two,numkeyboard_three,numkeyboard_four;// 数字1234
    private TextView numkeyboard_five,numkeyboard_six,numkeyboard_seven,numkeyboard_eight;// 数字4567
    private TextView numkeyboard_nine,numkeyboard_zero;// 数字9,0
    private TextView numkeyboard_clear,numkeyboard_comfirm;// 清空,确认

    public NumKeyboard(Context context){
        this(context, null);
    }
    public NumKeyboard(Context context, AttributeSet attrs){
        super(context, attrs);
        //在构造函数中将Xml中定义的布局解析出来。
        view = LayoutInflater.from(context).inflate(R.layout.num_key_board, this);
        intView();
    }

    private void intView() {
        numkeyboard_one = (TextView)view.findViewById(R.id.keyOne);
        numkeyboard_one.setTag(1);
        numkeyboard_two = (TextView)view.findViewById(R.id.keyTwo);
        numkeyboard_two.setTag(2);
        numkeyboard_three = (TextView)view.findViewById(R.id.keyThree);
        numkeyboard_three.setTag(3);
        numkeyboard_four = (TextView)view.findViewById(R.id.keyFour);
        numkeyboard_four.setTag(4);
        numkeyboard_five = (TextView)view.findViewById(R.id.keyFive);
        numkeyboard_five.setTag(5);
        numkeyboard_six = (TextView)view.findViewById(R.id.keySix);
        numkeyboard_six.setTag(6);
        numkeyboard_seven = (TextView)view.findViewById(R.id.keySeven);
        numkeyboard_seven.setTag(7);
        numkeyboard_eight = (TextView)view.findViewById(R.id.keyEight);
        numkeyboard_eight.setTag(8);
        numkeyboard_nine = (TextView)view.findViewById(R.id.keyNine);
        numkeyboard_nine.setTag(9);
        numkeyboard_zero = (TextView)view.findViewById(R.id.keyZero);
        numkeyboard_zero.setTag(0);
        numkeyboard_clear = (TextView)view.findViewById(R.id.keyDel);
        numkeyboard_comfirm = (TextView)view.findViewById(R.id.keySure);

        numkeyboard_one.setOnClickListener(this);
        numkeyboard_two.setOnClickListener(this);
        numkeyboard_three.setOnClickListener(this);
        numkeyboard_four.setOnClickListener(this);
        numkeyboard_five.setOnClickListener(this);
        numkeyboard_six.setOnClickListener(this);
        numkeyboard_seven.setOnClickListener(this);
        numkeyboard_eight.setOnClickListener(this);
        numkeyboard_nine.setOnClickListener(this);
        numkeyboard_zero.setOnClickListener(this);
        numkeyboard_clear.setOnClickListener(this);
        numkeyboard_comfirm.setOnClickListener(this);
        numkeyboard_clear.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (iKeyBoardListener != null) {
                    iKeyBoardListener.clear();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == numkeyboard_comfirm) {
            comfirm();
        } else if (v == numkeyboard_clear) {
            delete();
        } else {
            setvalue(v);
        }
    }

    public interface IKeyBoardListener {
        //插入
        public void insert(String text);
        //删除一位
        public void delete();
        //清空
        public void clear();
        //确定
        public void comfirm();
    }
    public void setiKeyBoardListener(IKeyBoardListener iKeyBoardListener) {
        if (iKeyBoardListener != this.iKeyBoardListener) {
            this.iKeyBoardListener = iKeyBoardListener;
        }
    }

    public void comfirm() {
        if (iKeyBoardListener != null) {
            iKeyBoardListener.comfirm();
        }
    }

    private void delete() {
        if (iKeyBoardListener != null) {
            iKeyBoardListener.delete();
        }
    }
    private void setvalue(View view) {
        String text = view.getTag().toString();
        if (iKeyBoardListener != null) {
            iKeyBoardListener.insert(text);
        }
    }
}
