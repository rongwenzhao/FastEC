package com.nicro.fastec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.nicro.latte.app.Latte;
import com.nicro.latte.ec.icon.FontEcModule;

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
                .configure();
    }
}
