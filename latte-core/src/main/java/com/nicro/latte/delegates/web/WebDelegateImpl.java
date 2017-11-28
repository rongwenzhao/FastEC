package com.nicro.latte.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nicro.latte.delegates.IPageLoadListener;
import com.nicro.latte.delegates.web.chromeclient.WebChromeClientImpl;
import com.nicro.latte.delegates.web.client.WebViewClientImpl;
import com.nicro.latte.delegates.web.route.RouteKeys;
import com.nicro.latte.delegates.web.route.Router;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 提供给用户的默认的WebDelegate的实现类。
 * Created by rongwenzhao on 2017/11/28.
 */

public class WebDelegateImpl extends WebDelegate {

    private IPageLoadListener mIPageLoadListener = null;

    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getUrl() != null) {
            //用原生的方式模拟Web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
