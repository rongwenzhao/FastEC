package com.nicro.latte.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.nicro.latte.delegates.web.event.Event;
import com.nicro.latte.delegates.web.event.EventManager;
import com.nicro.latte.util.logger.LatteLogger;

/**
 * 用来处理WebView和原生交互的。
 * Created by rongwenzhao on 2017/11/28.
 */

public class LatteWebInterface {
    private final WebDelegate DELEGATE;

    private LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate) {
        return new LatteWebInterface(delegate);
    }

    /**
     * js与原生交互的方式：js中调用Latte.event(param)即可调用此方法，
     * 这边根据param中action的值，
     * 选择对应已经添加的Event实例进行处理。比较便于扩展。
     *
     * @param params
     * @return
     */
    @JavascriptInterface
    public String event(String params) {
        LatteLogger.d("JavascriptInterface params = " + params);
        //这边是跟html界面约定的东西。获取json字符串中的action属性对应的值。
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (action != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
