package com.nicro.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.R2;
import com.nicro.latte.ec.main.EcBottomDelegate;
import com.nicro.latte.ui.recycler.BaseDecoration;
import com.nicro.latte.ui.refresh.RefreshHandler;
import com.nicro.latte.util.callback.CallbackManager;
import com.nicro.latte.util.callback.CallbackType;
import com.nicro.latte.util.callback.IGlobalCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页delegate
 * Created by rongwenzhao on 2017/11/25.
 */

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_index)
    RecyclerView mRecylerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;

    @OnClick(R2.id.icon_index_scan)
    void onScanClick() {
        startScanWithCheck(this.getParentDelegate());
    }

    RefreshHandler mRefreshHandler = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecylerView, new IndexDataConverter());
        /**
         * 添加扫码二维码的回调监听。扫描成功后，可在此处做些需要的处理。
         */
        CallbackManager.getInstance().addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
            @Override
            public void executeCallback(@Nullable String args) {
                //args是二维码扫描返回的结果
                Toast.makeText(getContext(), "扫描结果 = " + args, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initRefreshLayout() {
        //初始化SwipeRefreshLayout的显示颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        //SwipeRefreshLayout 是否缩放，出发位置，显示位置的设置
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecylerView.setLayoutManager(manager);
        //添加分隔符。ContextCompat.getColor(getContext(), R.color.app_background) 获取颜色的值即 #1111
        mRecylerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));

        //添加点击事件
        //注意，这里传入IndexItemClickListener的delegate实例，是主页中最底部的delegate，
        // 也就是EcBottomDelegate的实例,
        //如果传入当前的delegate，那么跳转到新界面，还是在最底部的delegate里面，底部的四个tab还会显示。
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecylerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    //Fregmention提供的懒加载方法。Lazy initial，Called when fragment is first called.
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.php");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }
}
