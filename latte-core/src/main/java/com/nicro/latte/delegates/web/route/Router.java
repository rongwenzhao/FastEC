package com.nicro.latte.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.delegates.web.WebDelegate;
import com.nicro.latte.delegates.web.WebDelegateImpl;

/**
 * Created by rongwenzhao on 2017/11/28.
 */

public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 在WebViewClient的实现类中的shouldOverrideUrlLoading使用。
     * 返回true，则说明由我们来处理接下来事件，
     * 返回false，则我们不接管，接下来的事件由webView来处理。
     *
     * @param delegate
     * @param url
     * @return
     */
    public final boolean handleWebUrl(WebDelegate delegate, String url) {

        //若js中包含电话的协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        //没有电话协议，剩下的就是原生跳转了。
        final LatteDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);
        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }

    /**
     * 加载本地资源文件(html文件)
     *
     * @param webView
     * @param url
     */
    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        //是网络请求，或者已经包含了file:///android_asset/内容，就直接访问
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }


    /**
     * 跳到拨号界面，让用户确认是否拨打
     *
     * @param context
     * @param uri
     */
    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }
}
