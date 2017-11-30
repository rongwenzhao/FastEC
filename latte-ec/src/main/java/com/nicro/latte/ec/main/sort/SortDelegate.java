package com.nicro.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.main.sort.content.ContentDelegate;
import com.nicro.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * 分类delegate
 * Created by rongwenzhao on 2017/11/25.
 */

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    /**
     * 第一次点击分类按钮时，才去加载相应数据。
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
        //设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
    }
}
