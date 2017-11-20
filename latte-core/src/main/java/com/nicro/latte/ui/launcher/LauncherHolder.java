package com.nicro.latte.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by rongwenzhao on 2017/11/21.
 */

public class LauncherHolder implements Holder<Integer> {
    private AppCompatImageView mImageView = null;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    /**
     * 返回的imageView的资源更新
     *
     * @param context
     * @param position
     * @param data
     */
    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setImageResource(data);
    }
}
