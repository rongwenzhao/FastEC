package com.nicro.latte.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * 使用轮播库所需的holder creater
 * Created by rongwenzhao on 2017/11/21.
 */

public class LauncherHolderCrreator implements CBViewHolderCreator<LauncherHolder> {
    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
