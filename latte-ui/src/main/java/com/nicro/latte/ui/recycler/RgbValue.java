package com.nicro.latte.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * 存储颜色值的bean。由AutoValue库自动生成。
 * TranslucentBehavior类中使用。
 * Created by rongwenzhao on 2017/11/27.
 */
@AutoValue
public abstract class RgbValue {
    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
