package com.nicro.latte.app;

import com.nicro.latte.util.storage.LatterPreference;

/**
 * 管理用户信息的类
 * 和业务无关的，代码会放在core包中，和业务有关的，会放在ec中
 * Created by rongwenzhao on 2017/11/22.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    /**
     * 设置用户登录状态
     *
     * @param state
     */
    public static void setSignState(boolean state) {
        LatterPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 获取用户是否登录的本地变量的方法
     *
     * @return
     */
    private static boolean isSignIn() {
        return LatterPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }


}
