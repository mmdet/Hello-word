package com.hhzb.fntalm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hhzb.fntalm.R;

public class TopBar extends RelativeLayout {

	private Button leftButton, rightButton;
	private TextView title;

	// 左侧button属性
	private int leftTextColor;
	private Drawable leftBackground;
	private String leftText;

	// 右侧button属性
	private int rightTextColor;
	private Drawable rightBackground;
	private String rightText;

	// title属性
	private int titleTextColor;
	private float titleTextSize;
	private String titleText;

	// 布局属性
	private LayoutParams leftParams, rirhtParams, titleParams;

	private TopBarClickListener listener;

	public TopBar(Context context, AttributeSet attrs){

		super(context, attrs);
		//获取自定义属性和值的映射集合
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

		//取出自定义属性 - 左侧
		leftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, Color.WHITE);
		leftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
		leftText = ta.getString(R.styleable.TopBar_leftText);

		//取出自定义属性 - 右侧
		rightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, Color.WHITE);
		rightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
		rightText = ta.getString(R.styleable.TopBar_rightText);

		//取出自定义属性 - 标题
		titleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor, Color.WHITE);
		titleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize, 30);
		titleText = ta.getString(R.styleable.TopBar_titleText);
		
		//回收TypedArray（避免浪费资源，避免因为缓存导致的错误）
		ta.recycle();

		leftButton = new Button(context);
		rightButton = new Button(context);
		title = new TextView(context);

		//设置属性 - 左侧
		leftButton.setText(leftText);
		leftButton.setTextColor(leftTextColor);
		leftButton.setBackground(leftBackground);

		//设置属性 - 左侧
		rightButton.setText(rightText);
		rightButton.setTextSize(titleTextSize);
		rightButton.setTextColor(rightTextColor);
		rightButton.setBackground(rightBackground);

		//设置属性 - 标题
		title.setText(titleText);
		title.setTextSize(titleTextSize);
		title.setTextColor(titleTextColor);
		title.setGravity(Gravity.CENTER);

		//设置整体背景颜色
		//setBackgroundColor(0xfff59563);

		//设置布局 - 左
		leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		leftParams.leftMargin = 10;
		addView(leftButton, leftParams);//将按钮添加进布局中

		//设置布局 - 右
		rirhtParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rirhtParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		rirhtParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		rirhtParams.rightMargin = 10;
		addView(rightButton, rirhtParams);//将按钮添加进布局中

		//设置布局 - 标题
		titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
		addView(title, titleParams);//将按钮添加进布局中

		//设置监听器
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){
				if(listener != null){
					listener.leftclick();
				}

			}
		});
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){
				if(listener != null){
					listener.rightclick();
				}

			}
		});
	}

	// 点击事件监听器接口
	public interface TopBarClickListener {

		public void leftclick();

		public void rightclick();
	}

	// 设置监听器
	public void setOnTopBarClickListener(TopBarClickListener listener) {
		this.listener = listener;
	}

	public void setLeftIsVisible(boolean visible) {
		if (visible) {
			leftButton.setVisibility(View.VISIBLE);
		} else {
			leftButton.setVisibility(View.GONE);
		}
	}

	public void setRightIsVisible(boolean visible) {
		if (visible) {
			rightButton.setVisibility(View.VISIBLE);
		} else {
			rightButton.setVisibility(View.GONE);
		}
	}
	public void setRightText(String text) {
		rightButton.setText(text);
	}

}
