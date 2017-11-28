package com.nicro.latte.delegates;

/**
 * 承载webView的界面，在webView加载时的回调
 * Created by rongwenzhao on 2017/11/28.
 */

public interface IPageLoadListener {

    void onPageStart();

    void onPageEnd();
}
