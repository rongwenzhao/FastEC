package com.nicro.latte.delegates.bottom;

/**
 * 底层按钮tab按钮(上面一个图片，下面字体)的封装bean。
 * 此处上面的图片用fontawesome代替了。
 * Created by rongwenzhao on 2017/11/25.
 */

public final class BottomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    /**
     * 将类变量声明为final，在构造方法中赋值，这样写是线程安全的（详见java并发编程）。
     * @param icon
     * @param title
     */
    public BottomTabBean(CharSequence icon, CharSequence title) {
        ICON = icon;
        TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
