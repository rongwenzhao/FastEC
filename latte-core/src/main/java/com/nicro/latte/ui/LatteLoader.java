package com.nicro.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.nicro.latte.R;
import com.nicro.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by rongwenzhao on 2017/11/19.
 */

public class LatteLoader {

    /**
     * dialog的宽高缩放比例
     */
    private static final int LATTE_SIZE_SCALE = 6;

    /**
     * height客制化的偏移量
     */
    private static final int LATTE_OFFSET_SCALE = 10;

    /**
     * 用来对添加的dialog进行存储，方便管理
     */
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    /**
     * 默认显示样式
     */
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    /**
     * 重载的showLoading方法，传入LoaderSstyle枚举值
     *
     * @param context
     * @param loaderStyle
     */
    public static void showLoading(Context context, Enum<LoaderStyle> loaderStyle) {
        showLoading(context, loaderStyle.name());
    }

    /**
     * 注意，loading对应dialog的context要传当前context，不是全局的application context
     *
     * @param context
     * @param type
     */
    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        //avLoadingIndicatorView 作为根视图，添加到dialog中
        dialog.setContentView(avLoadingIndicatorView);

        //下面设置dialog的宽和高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LATTE_SIZE_SCALE;
            lp.height = deviceHeight / LATTE_SIZE_SCALE;
            // 可客制化的偏移量需求lp.height = LATTE_OFFSET_SCALE + deviceHeight / LATTE_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();//cancel()方法使对话框不再显示，同时触发其相应取消回调
                    //而dismiss（）方法只是不再显示，没回调
                }
            }
        }
    }
}
