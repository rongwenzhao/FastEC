package com.nicro.fastec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.nicro.latte.app.Latte;
import com.nicro.latte.ec.database.DatabaseManager;
import com.nicro.latte.ec.icon.FontEcModule;
import com.nicro.latte.net.interceptors.DebugInterceptor;

/**
 * Created by rongwenzhao on 2017/11/18.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcons(new FontAwesomeModule())//添加字体
                .withIcons(new FontEcModule())//自定义字体
                .withApiHost("http://127.0.0.1")
                .withWeChatAppId("")
                .withWeChatSecretId("")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
        //调试打开是白屏，网页被屏蔽
        //initStetho();
        DatabaseManager.getInstance().init(this);
    }

/*    *//**
     * 调试工具的初始化
     *//*
    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }*/
}
