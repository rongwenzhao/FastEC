package com.nicro.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;

/**
 * 分类界面，左侧的分类列表
 * Created by rongwenzhao on 2017/11/27.
 */

public class VerticalListDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
