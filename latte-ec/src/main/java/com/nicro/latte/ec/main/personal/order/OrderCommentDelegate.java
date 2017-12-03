package com.nicro.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.R2;
import com.nicro.latte.ui.widget.AutoPhotoLayout;
import com.nicro.latte.ui.widget.StarLayout;
import com.nicro.latte.util.callback.CallbackManager;
import com.nicro.latte.util.callback.CallbackType;
import com.nicro.latte.util.callback.IGlobalCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by rongwenzhao on 2017/12/2.
 */
public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;

    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhote = null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onClickSubmit() {
        Toast.makeText(getContext(), "评分： " + mStarLayout.getStarCount(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAutoPhote.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        //添加新图片
                        mAutoPhote.onCropTarget(args);
                    }
                });
    }
}
