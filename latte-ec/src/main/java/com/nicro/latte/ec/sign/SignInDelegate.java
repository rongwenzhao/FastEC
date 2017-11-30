package com.nicro.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.nicro.latte.app.ConfigKeys;
import com.nicro.latte.app.Latte;
import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.R2;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.IError;
import com.nicro.latte.net.callback.IFailure;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.util.logger.LatteLogger;
import com.nicro.latte.wechat.LatteWeChat;
import com.nicro.latte.wechat.callbacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录的界面
 * Created by rongwenzhao on 2017/11/21.
 */

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener iSignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            iSignListener = (ISignListener) activity;
        }

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChatLogin() {
        Toast.makeText(getContext(), "微信登录", Toast.LENGTH_SHORT).show();
        LatteWeChat.getInstance().onSignInCallback(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {

            }
        }).signIn();
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder()
                    .url(Latte.getConfiguration(ConfigKeys.API_HOST) + "user_profile.php")
                    .params("password", mPassword.getText().toString())
                    .params("email", mEmail.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            //Toast.makeText(getContext(), "onSuccess " + response, Toast.LENGTH_SHORT).show();
                            //保存入库
                            SignHandler.onSignIn(response, iSignListener);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            LatteLogger.d("code = " + code + "msg = " + msg);
                            Toast.makeText(getContext(), "onError " + "code = " + code + "msg = " + msg, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .post();
        }
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
