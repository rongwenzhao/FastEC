package com.nicro.latte.app;

/**
 * Created by rongwenzhao on 2017/11/18.
 * 枚举类型，全应用单例
 */

public enum ConfigKeys {
    API_HOST,//服务器访问地址
    APPLICATION_CONTEXT,
    CONFIG_READY,//配置是否完成
    ICON,
    INTERCEPTOR,
    WE_CHAT_APP_ID,
    WE_CHAT_APP_SECRET,
    ACTIVITY, //微信拉取它的回调的时候需要一个activity上下文，非全局的applicationContext上下文
    HANDLER
}
