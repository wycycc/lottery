package com.ycc.lottery.view;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.ycc.lottery.ConstantValue;
import com.ycc.lottery.view.manager.BaseUI;

/**
 * 第二个简答的界面
 * 
 * @author Administrator
 * 
 */
public class SecondUI extends BaseUI {
	private TextView textView;

	public SecondUI(Context context) {
		super(context);
	}

	/**
	 * 初始化：调用一次
	 */
	public void init() {
		// 简单界面：
		textView = new TextView(context);

		LayoutParams layoutParams = textView.getLayoutParams();
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		textView.setLayoutParams(layoutParams);

		textView.setBackgroundColor(Color.RED);
		textView.setText("这是第二个界面");
	}

	/**
	 * 获取需要在中间容器加载的控件
	 * 
	 * @return
	 */
	public View getChild() {
		return textView;
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_SECOND;
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}
}
