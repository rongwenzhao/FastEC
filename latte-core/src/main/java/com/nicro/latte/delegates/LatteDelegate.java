package com.nicro.latte.delegates;

/**
 * Created by rongwenzhao on 2017/11/18.
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate {

    /**
     * 获取父Delegate，若无，返回null
     *
     * @param <T>
     * @return
     */
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
