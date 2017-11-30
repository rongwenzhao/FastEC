package com.nicro.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.ec.R;

/**
 * Created by rongwenzhao on 2017/11/30.
 */

public class PersonalDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
