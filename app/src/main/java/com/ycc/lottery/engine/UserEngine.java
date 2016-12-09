package com.ycc.lottery.engine;

import com.ycc.lottery.bean.User;
import com.ycc.lottery.net.protocal.Message;

/**
 * 用户相关的业务操作的接口
 * Created by Administrator on 2016/12/9.
 */
public interface UserEngine {
    /**
     * 用户登录
     * @param user
     * @return
     */
    Message login(User user);
}
