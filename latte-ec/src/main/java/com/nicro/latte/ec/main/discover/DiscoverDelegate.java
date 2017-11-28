package com.nicro.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.delegates.web.WebDelegateImpl;
import com.nicro.latte.ec.R;

/**
 * 发现界面，该界面是加载html的。
 * Created by rongwenzhao on 2017/11/28.
 */

public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebDelegateImpl delegate = WebDelegateImpl.create("index.html");
        /*设置页面加载前，加载后的回调，备用
        delegate.setPageLoadListener(new IPageLoadListener() {
            @Override
            public void onPageStart() {

            }

            @Override
            public void onPageEnd() {

            }
        });*/

        //此处传入的是this.getParentDelegate() 的delegate，也就最底层的ECBottomDelegate。
        // 用它来启动wevView界面，会是完整界面，底部不会有底边拦。
        delegate.setTopDelegate(this.getParentDelegate());
        loadRootFragment(R.id.web_discover_container, delegate);
    }
}
