package com.nicro.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nicro.latte.app.AccountManager;
import com.nicro.latte.app.IUserChecker;
import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ui.launcher.ILauncherListener;
import com.nicro.latte.ui.launcher.LauncherHolderCrreator;
import com.nicro.latte.ui.launcher.OnLauncherFinishTag;
import com.nicro.latte.ui.launcher.ScrollLauncherTag;
import com.nicro.latte.util.storage.LatterPreference;

import java.util.ArrayList;

/**
 * 实现轮播功能的delegate
 * Created by rongwenzhao on 2017/11/21.
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> convenientBanner = null;
    //存储图片资源id
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private ILauncherListener iLauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            iLauncherListener = (ILauncherListener) activity;
        }
    }

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        convenientBanner.setPages(new LauncherHolderCrreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//指示器的位置设置
                .setOnItemClickListener(this)//点击事件
                .setCanLoop(true);

    }

    @Override
    public Object setLayout() {
        convenientBanner = new ConvenientBanner<Integer>(getContext());
        return convenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //如果点击了最后一个
        if (position == INTEGERS.size() - 1) {
            LatterPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查用户是否登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (iLauncherListener != null) {
                        iLauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (iLauncherListener != null) {
                        iLauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }

    }
}
