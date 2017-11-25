package com.nicro.latte.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.nicro.latte.R;
import com.nicro.latte.delegates.LatteDelegate;

/**
 * 底层点击每个按钮要显示的界面所对应的delegate
 * Created by rongwenzhao on 2017/11/25.
 */

public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener {

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    /**
     * Fregment每次重新显示的时候，需要对其TouchMode需要重新处理，让其获得焦点。
     * 这样，它的onKeyListener才会起作用，双击back键退出应用才会有效。
     */
    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //双重保险验证key
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //时间间隔大于两秒
            if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
                Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {//否则退出应用。下面退出的操作，根据项目具体重写。
                _mActivity.finish();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
                //返回true，说明本事件已经被消费，不需要系统额外处理。
                return true;
            }
        }
        return false;
    }
}
