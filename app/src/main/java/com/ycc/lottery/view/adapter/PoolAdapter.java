package com.ycc.lottery.view.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ycc.lottery.R;

import java.text.DecimalFormat;
import java.util.List;


/**
 * 选号容器用Adapter（双色球的红球）
 * 
 * @author Administrator
 * 
 */
public class PoolAdapter extends BaseAdapter {

	private Context context;

	private int endNum;

	private List<Integer> slectedNums;
	
	private int slectedBgResId;// 选中的背景图片的资源id


	
	

	public PoolAdapter(Context context, int endNum, List<Integer> slectedNums, int slectedBgResId) {
		super();
		this.context = context;
		this.endNum = endNum;
		this.slectedNums = slectedNums;
		this.slectedBgResId = slectedBgResId;
	}

	@Override
	public int getCount() {
		return endNum;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView ball = new TextView(context);
		DecimalFormat decimalFormat = new DecimalFormat("00");
		ball.setText(decimalFormat.format(position + 1));
		ball.setTextSize(16);
		// 居中
		ball.setGravity(Gravity.CENTER);

		// 获取到用户已选号码的集合，判读集合中有，背景图片修改为红色
		if (slectedNums.contains(position + 1)) {
			ball.setBackgroundResource(slectedBgResId);

		} else {
			ball.setBackgroundResource(R.drawable.id_defalut_ball);
		}

		return ball;
	}

}
