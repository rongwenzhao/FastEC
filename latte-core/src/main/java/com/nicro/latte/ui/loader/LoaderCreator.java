package com.nicro.latte.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by rongwenzhao on 2017/11/19.
 */

public final class LoaderCreator {
    private static final WeakHashMap<String, Object> LOADING_MAP = new WeakHashMap<>();

    /**
     * 本方法，通过缓存的方式获取对应Loader()主要是Indicator，相比原始包中，性能有所提高。
     *
     * @param type
     * @param context
     * @return
     */
    static AVLoadingIndicatorView create(String type, Context context) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator((Indicator) LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    /**
     * 根据传入的Indicator的实现类的名字，通过反射获取到 com.wang.avi.indicators包名下的实现类。
     * （所有实现类都在com.wang.avi.indicators包中）
     *
     * @param name
     * @return
     */
    private static Indicator getIndicator(String name) {

        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();//com.wang.avi
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
