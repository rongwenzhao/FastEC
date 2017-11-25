package com.nicro.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

import com.nicro.latte.app.Latte;

/**
 * 刷新小助手
 * Created by rongwenzhao on 2017/11/25.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    //传入需要被此小助手处理的SwipeRefreshLayout
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout refresh_layout) {
        REFRESH_LAYOUT = refresh_layout;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    private void refresh() {
        //告诉SwipeRefreshLayout，我们开始加载了。加载进度显示
        REFRESH_LAYOUT.setRefreshing(true);

        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //设置SwipeRefreshLayout不能再刷新滚动，亦即消失。
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);

    }

    //SwipeRefreshLayout的内部接口，用来监听SwipeRefreshLayout的refresh操作
    @Override
    public void onRefresh() {
        refresh();
    }
}
