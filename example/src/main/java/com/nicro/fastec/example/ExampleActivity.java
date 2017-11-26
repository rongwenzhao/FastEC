package com.nicro.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.nicro.latte.activities.ProxyActivity;
import com.nicro.latte.app.Latte;
import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.launcher.LauncherDelegate;
import com.nicro.latte.ec.main.EcBottomDelegate;
import com.nicro.latte.ec.sign.ISignListener;
import com.nicro.latte.ec.sign.SignInDelegate;
import com.nicro.latte.ui.launcher.ILauncherListener;
import com.nicro.latte.ui.launcher.OnLauncherFinishTag;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //初始化activity变量
        Latte.getConfigurator().withActivity(this);

        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "用户登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        //此处可以做些操作，比如增加统计信息到服务器
        Toast.makeText(this, "用户注册成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 控制轮播图的显示情况
     *
     * @param tag
     */
    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
