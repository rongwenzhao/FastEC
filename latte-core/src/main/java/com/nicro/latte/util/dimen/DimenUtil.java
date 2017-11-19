package com.nicro.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.nicro.latte.app.Latte;

/**
 * 用于测量的工具类
 * Created by rongwenzhao on 2017/11/19.
 */

public class DimenUtil {

    public static int getScreenWidth() {
        Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
