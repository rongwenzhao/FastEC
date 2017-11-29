package com.nicro.latte.delegates.bottom;

import android.widget.Toast;

import com.nicro.latte.R;
import com.nicro.latte.app.Latte;
import com.nicro.latte.delegates.LatteDelegate;

/**
 * 底层点击每个按钮要显示的界面所对应的delegate
 * Created by rongwenzhao on 2017/11/25.
 */

public abstract class BottomItemDelegate extends LatteDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
