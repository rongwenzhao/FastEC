package com.nicro.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.ec.R;

/**
 * 首页delegate
 * Created by rongwenzhao on 2017/11/25.
 */

public class IndexDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
