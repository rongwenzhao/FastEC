package com.nicro.fastec.example.event;

import android.webkit.WebView;
import android.widget.Toast;

import com.nicro.latte.delegates.web.event.Event;

/**
 * Created by rongwenzhao on 2017/11/28.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), "执行参数: " + params + getAction(), Toast.LENGTH_SHORT).show();
        if (getAction().equals("test")) {
            //通过View.post(Runnable) 这种方式运行，能保证运行在主线程
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    //webView调用js的方法
                    webView.evaluateJavascript("nativeCall();", null);
                }
            });
        }
        return null;
    }
}
