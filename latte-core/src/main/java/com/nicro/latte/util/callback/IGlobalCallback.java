package com.nicro.latte.util.callback;


import android.support.annotation.Nullable;

/**
 * 全局的回调,T代表需要传入的参数。
 * Created by rongwenzhao on 2017/12/1.
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
