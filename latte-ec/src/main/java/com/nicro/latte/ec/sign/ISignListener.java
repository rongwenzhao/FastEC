package com.nicro.latte.ec.sign;

/**
 * 用户登录成功，注册成功后，对应的外部需要做处理的回调方法
 * Created by rongwenzhao on 2017/11/22.
 */

public interface ISignListener {
    void onSignInSuccess();

    void onSignUpSuccess();
}
