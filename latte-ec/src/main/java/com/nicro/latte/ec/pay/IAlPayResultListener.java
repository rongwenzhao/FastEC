package com.nicro.latte.ec.pay;

/**
 * Created by rongwenzhao on 2017/11/30.
 */

public interface IAlPayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
