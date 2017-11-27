package com.nicro.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;

/**
 * Created by rongwenzhao on 2017/11/27.
 */

public class ContentDelegate extends LatteDelegate {
    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    /**
     * 经典的fragment之间传递数据的写法：数据传入Bundle，
     * 之后调Fragment的setArguments传入，在OnCreate方法中取出。
     *
     * @param contentId
     * @return
     */
    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
