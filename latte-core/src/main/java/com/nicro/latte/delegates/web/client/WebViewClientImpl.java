package com.nicro.latte.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nicro.latte.app.Latte;
import com.nicro.latte.delegates.IPageLoadListener;
import com.nicro.latte.delegates.web.WebDelegate;
import com.nicro.latte.delegates.web.route.Router;
import com.nicro.latte.ui.loader.LatteLoader;
import com.nicro.latte.util.logger.LatteLogger;

/**
 * WebViewClient的默认实现类
 * Created by rongwenzhao on 2017/11/28.
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private Handler handler = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    /**
     * 重写此方法即可，上面的shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
     * 是新版本提供的，对低版本不一定调的到。
     * <p>
     * shouldOverrideUrlLoading(WebView view, String url)方法中可以做路由的截断和自己的处理。
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading " + url);

        //返回true，则所有页面<a>链接的跳转或者JavaScript中的href重定向，都由原生来处理。
        //详见handleWebUrl内部。
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onPageStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onPageEnd();
        }
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {*/
        LatteLoader.stopLoading();
         /*   }
        },1000);*/
    }
}
