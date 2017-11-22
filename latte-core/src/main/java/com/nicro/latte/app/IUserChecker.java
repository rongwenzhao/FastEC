package com.nicro.latte.app;

/**
 * 用户是否登录的判断接口
 * Created by rongwenzhao on 2017/11/22.
 */

public interface IUserChecker {
    void onSignIn();

    void onNotSignIn();
}
