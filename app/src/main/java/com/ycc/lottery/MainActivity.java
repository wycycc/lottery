package com.ycc.lottery;

import android.app.Activity;
import android.os.Bundle;

import com.ycc.lottery.view.manager.BottomManager;
import com.ycc.lottery.view.manager.TitleManager;

public class MainActivity extends Activity {

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

        init();
    }

    private void init() {
        TitleManager manager = TitleManager.getInstance();
        manager.init(this);
        manager.showUnLoginTitle();
        BottomManager.getInstrance().init(this);
        BottomManager.getInstrance().showCommonBottom();




        // 当第一个界面加载完2秒钟后，第二个界面显示
        // handler.sendEmptyMessageDelayed(10, 2000);
    }

}
