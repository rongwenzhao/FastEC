package com.nicro.latte.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by rongwenzhao on 2017/11/18.
 * 工具类
 */

public class Latte {
    public static Configurator init(Context context) {
        getConfigurator().getLatteConfigs().put(ConfigKeys.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    /*public static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }*/

    public static <T> T getConfiguration(Enum<ConfigKeys> key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static Context getApplicationContext() {
        return (Context) getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Application getApplication() {
        return (Application) getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

}
