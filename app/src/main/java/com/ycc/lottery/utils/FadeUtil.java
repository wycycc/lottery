package com.ycc.lottery.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 淡入淡出的切换
 * 
 * @author Administrator
 * 
 */
public class FadeUtil {
	// 当前正在展示的淡出，动画的执行时间
	// 在这个执行过程中，第二界面处于等待状态
	// 第二个界面淡入，动画的执行时间

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			View view = (View) msg.obj;
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
	};

	/**
	 * 淡出
	 * 
	 * @param view
	 *            ：执行动画的界面
	 * @param duration
	 *            ：执行的时间
	 */
	public static void fadeOut(final View view, long duration) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(duration);

		// 动画执行完成之后，做删除view的操作
		// 增加动画执行完成之后的监听
		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 做删除view的操作
				// 获取到view的父容器——RelativeLayout，removeView
				// ViewGroup parent = (ViewGroup) view.getParent();
				// parent.removeView(view);

				// 2.3模拟器，抛异常，但是在4.0的模拟器

				Message msg = Message.obtain();
				msg.obj = view;
				handler.sendMessage(msg);

			}
		});

		view.startAnimation(alphaAnimation);
	}

	/**
	 * 淡入
	 * 
	 * @param view
	 *            ：执行动画的界面
	 * @param delay
	 *            ：等待时间（淡出的界面执行动画的时间相同）
	 * @param duration
	 *            ：执行的时间
	 */
	public static void fadeIn(View view, long delay, long duration) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

		// 设置延时的时间
		alphaAnimation.setStartOffset(delay);

		alphaAnimation.setDuration(duration);
		view.startAnimation(alphaAnimation);
	}
}
