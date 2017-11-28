package com.nicro.latte.delegates.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 浏览器内部的处理。
 * Created by rongwenzhao on 2017/11/28.
 */

public class WebChromeClientImpl extends WebChromeClient {
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
