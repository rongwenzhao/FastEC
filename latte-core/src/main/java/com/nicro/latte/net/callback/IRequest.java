package com.nicro.latte.net.callback;

/**
 * Created by rongwenzhao on 2017/11/19.
 * 请求开始，结束的回调，可以控制加载框的显示与关闭
 */

public interface IRequest {
    //请求开始的回调
    void onRequestStart();

    //请求结束的回调
    void onRequestEnd();
}
