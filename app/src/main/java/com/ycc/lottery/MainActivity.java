package com.ycc.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.ycc.lottery.utils.FadeUtil;
import com.ycc.lottery.utils.PromptManager;
import com.ycc.lottery.view.FirstUI;
import com.ycc.lottery.view.Hall;
import com.ycc.lottery.view.SecondUI;
import com.ycc.lottery.view.manager.BaseUI;
import com.ycc.lottery.view.manager.BottomManager;
import com.ycc.lottery.view.manager.MiddleManager;
import com.ycc.lottery.view.manager.TitleManager;

public class MainActivity extends Activity {

    private RelativeLayout middle;// 中间占着位置的容器

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // changeUI();
            changeUI(new SecondUI(MainActivity.this));
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.il_main);

        // // commons-codec.jar:加密用——MD5
        // DigestUtils.md5Hex("");
        // // commons-lang3-3.0-beta.jar:字符串操作
        // // 字符串非空判断:null "" "     "
        // StringUtils.isBlank("");//true
        // StringUtils.isNotBlank("");//false
        // // 字符串替换
        // String info="ppppNUM1ppppNUM2ppppp";
        // info=StringUtils.replaceEach(info, new String[]{"NUM1","NUM2"}, new
        // String[]{"1","2"});
        // // 字符截取
        // info="<body>.......</body>";
        // StringUtils.substringBetween(info, "<body>", "</body>");

        // 获取屏幕的宽度

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GlobalParams.WIN_WIDTH = metrics.widthPixels;

        init();

    }

    private void init() {
        TitleManager manager = TitleManager.getInstance();
        manager.init(this);
        manager.showUnLoginTitle();

        BottomManager.getInstrance().init(this);
        BottomManager.getInstrance().showCommonBottom();

        middle = (RelativeLayout) findViewById(R.id.ii_middle);
        MiddleManager.getInstance().setMiddle(middle);

        // 建立观察者和被观察者之间的关系（标题和底部导航添加到观察者的容器里面）
        MiddleManager.getInstance().addObserver(TitleManager.getInstance());
        MiddleManager.getInstance().addObserver(BottomManager.getInstrance());

        // loadFirstUI();
        // MiddleManager.getInstance().changeUI(FirstUI.class);
        MiddleManager.getInstance().changeUI(Hall.class);

        // 当第一个界面加载完2秒钟后，第二个界面显示
        // handler.sendEmptyMessageDelayed(10, 2000);
    }

    private View child1;

    private void loadFirstUI() {
        FirstUI firstUI = new FirstUI(this);

        child1 = firstUI.getChild();
        middle.addView(child1);
    }

    protected void loadSecondUI() {
        SecondUI secondUI = new SecondUI(this);

        View child = secondUI.getChild();
        // 切换界面的核心方法二
        middle.addView(child);

        // 执行切换动画
        // child.startAnimation(AnimationUtils.loadAnimation(this,
        // R.anim.ia_view_change));
        FadeUtil.fadeIn(child, 2000, 1000);
    }

    /**
     * 切换界面
     *
     * @param ui
     */
    protected void changeUI(BaseUI ui) {
        // 切换界面的核心代码
        middle.removeAllViews();
        // FadeUtil.fadeOut(child1, 2000);
        View child = ui.getChild();
        middle.addView(child);
        child.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ia_view_change));
        // FadeUtil.fadeIn(child, 2000, 1000);
    }

    /**
     * 切换界面
     */
    protected void changeUI() {
        // 1、切换界面时清理上一个显示内容

        // 切换界面的核心方法一
        // middle.removeAllViews();
        FadeUtil.fadeOut(child1, 2000);
        // middle.removeView(child1);
        loadSecondUI();
    }

    // 1、切换界面时清理上一个显示内容
    // 2、处理切换动画：简单动画——复杂动画（淡入淡出）
    // 3、切换界面通用处理——增加一个参数（明确切换的目标界面,通用）
    // 4、不使用Handler，任意点击按钮切换

    // a、用户返回键操作捕捉
    // b、响应返回键——切换到历史界面

    //
    // LinkedList<String>——AndroidStack

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean result = MiddleManager.getInstance().goBack();
            // 返回键操作失败
            if (!result) {
                // Toast.makeText(MainActivity.this, "是否退出系统", 1).show();
                PromptManager.showExitSystem(this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
