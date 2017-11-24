package com.nicro.latte.wechat;

import android.app.Activity;

import com.nicro.latte.app.ConfigKeys;
import com.nicro.latte.app.Latte;
import com.nicro.latte.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 可参考：https://www.cnblogs.com/panxuejun/p/6094711.html
 * Created by rongwenzhao on 2017/11/24.
 */

public class LatteWeChat {
    static final String APP_ID = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    static final String APP_SECRET = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mIWeChatSignInCallback = null;

    private static final class Holder {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static final LatteWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private LatteWeChat() {
        final Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public LatteWeChat onSignInCallback(IWeChatSignInCallback iWeChatSignInCallback) {
        this.mIWeChatSignInCallback = iWeChatSignInCallback;
        return this;
    }

    public IWeChatSignInCallback getmIWeChatSignInCallback() {
        return mIWeChatSignInCallback;
    }

    //注：当必须把方法声明为public，同时又不希望外部重写时，将方法声明为final，JVM会做一些优化
    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    /**
     * 用这段代码向微信开放平台请求授权码code,可拉起微信并打开授权登录页
     * （前提是你安装了微信应用并已登录，未登录的会引导你先登录
     */
    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
