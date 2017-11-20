package com.nicro.latte.util.timer;

import java.util.TimerTask;

/**
 * Time启动TtimerTask，每执行一次，都会执行它的run方法
 * Created by rongwenzhao on 2017/11/21.
 */

public class BaseTimeTask extends TimerTask {

    private ITimerListener iTimerListener = null;

    public BaseTimeTask(ITimerListener iTimerListener) {
        this.iTimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if (iTimerListener != null) {
            iTimerListener.onTimer();
        }
    }
}
