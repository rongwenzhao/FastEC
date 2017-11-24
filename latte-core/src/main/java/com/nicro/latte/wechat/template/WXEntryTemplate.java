package com.nicro.latte.wechat.template;

import com.nicro.latte.wechat.BaseWXEntryActivity;
import com.nicro.latte.wechat.LatteWeChat;

/**
 * 微信登录返回的类。
 * 处理办法：将本activity设置成透明的，生成之后就finish掉
 * Created by rongwenzhao on 2017/11/24.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {
    @Override
    protected void onResume() {
        super.onResume();
        //生成之后就finish
        finish();
        //不希望有动画
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getmIWeChatSignInCallback().onSignInSuccess(userInfo);

    }
}
