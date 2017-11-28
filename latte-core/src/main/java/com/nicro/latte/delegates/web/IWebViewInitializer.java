package com.nicro.latte.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 初始化webView的接口
 * Created by rongwenzhao on 2017/11/28.
 */

public interface IWebViewInitializer {

    WebView initWebView(WebView webView);

    /**
     * WebViewClient是针对浏览器本身的控制
     *
     * @return
     */
    WebViewClient initWebViewClient();

    /**
     * WebChromeClient是针对浏览器内部页面的控制
     *
     * @return
     */
    WebChromeClient initWebChromeClient();
}
