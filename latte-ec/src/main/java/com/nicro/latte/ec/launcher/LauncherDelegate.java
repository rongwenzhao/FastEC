package com.nicro.latte.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.R2;
import com.nicro.latte.util.timer.BaseTimeTask;
import com.nicro.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页启动界面
 * Created by rongwenzhao on 2017/11/21.
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {

    private Timer mTimer = null;
    private int mCount = 5;//倒计时数
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer;

    /**
     * 点击可以结束掉倒计时的view
     */
    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {

    }

    private void initTimer() {
        mTimer = new Timer();
        mTimer.schedule(new BaseTimeTask(this), 0, 1000);//每隔一秒处理一次
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        //倒计时结束处理
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                }
            }
        });
    }
}
