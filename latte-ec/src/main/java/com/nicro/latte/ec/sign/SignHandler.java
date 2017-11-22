package com.nicro.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nicro.latte.app.AccountManager;
import com.nicro.latte.ec.database.DatabaseManager;
import com.nicro.latte.ec.database.UserProfile;

/**
 * Created by rongwenzhao on 2017/11/22.
 */

public class SignHandler {
    /**
     * {
     * "code": 0,
     * "message": "OK",
     * "data": {
     * "userId": 1,
     * "name": "猿猿",
     * "avatar": "http://wx.qlogo.cn/mmopen/guWqj0vybsIHxY2BIqqI3iaSHcbWZXiaSQysU0JKwmqjqMw8Uhia6AribBBynqnr9qxVOTkaUMnAnzqvXYjEDctsoXxzeQ2ibqWt0/0",
     * "gender": "男",
     * "address": "西安"
     * }
     * }
     * 注册成功调用的方法
     *
     * @param response
     */
    public static void onSignUp(String response, ISignListener iSignListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getmUserProfileDao().insert(userProfile);

        //用户注册成功，保存用户状态
        AccountManager.setSignState(true);
        //调用上层的activity的onSignUpSuccess方法
        if (iSignListener != null) {
            iSignListener.onSignUpSuccess();
        }

    }

    /**
     * 用户登录成功处理
     * @param response
     * @param iSignListener
     */
    public static void onSignIn(String response, ISignListener iSignListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile userProfile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getmUserProfileDao().insert(userProfile);

        //用户注册成功，保存用户状态
        AccountManager.setSignState(true);
        //调用上层的activity的onSignUpSuccess方法
        if (iSignListener != null) {
            iSignListener.onSignInSuccess();
        }

    }
}
