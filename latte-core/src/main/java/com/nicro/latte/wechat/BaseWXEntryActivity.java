package com.nicro.latte.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.IError;
import com.nicro.latte.net.callback.IFailure;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.util.logger.LatteLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by rongwenzhao on 2017/11/24.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    //用户登录登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //微信发送请求到第三方应用的回调
    //一般不会用到
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        int errorCode = baseResp.errCode;
        switch (errorCode) {
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                final String code = ((SendAuth.Resp) baseResp).code;
                final StringBuilder authUrl = new StringBuilder();
                authUrl
                        .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                        .append(LatteWeChat.APP_ID)
                        .append("&secret=")
                        .append(LatteWeChat.APP_SECRET)
                        .append("&code=")
                        .append(code)
                        .append("&grant_type=authorization_code");

                LatteLogger.d("authUrl", authUrl.toString());
                getAuth(authUrl.toString());
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                break;
            default:
                break;
        }
    }


    private void getAuth(String authUrl) {
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        LatteLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());

                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
