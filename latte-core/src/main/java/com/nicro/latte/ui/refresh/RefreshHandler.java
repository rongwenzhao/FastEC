package com.nicro.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicro.latte.app.Latte;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.ui.recycler.DataConverter;
import com.nicro.latte.ui.recycler.MultipleRecyclerAdapter;
import com.nicro.latte.util.logger.LatteLogger;

/**
 * 刷新小助手
 * Created by rongwenzhao on 2017/11/25.
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    //传入需要被此小助手处理的SwipeRefreshLayout
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    /**
     * 注意：变量，一定要保证构造时都能初始化。不能保证的也要赋个初始值。
     *
     * @param refresh_layout
     * @param recyclerView
     * @param converter
     * @param bean
     */
    private RefreshHandler(SwipeRefreshLayout refresh_layout,
                           RecyclerView recyclerView,
                           DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = refresh_layout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    /**
     * 用简单工厂方式进行生产的包装。
     *
     * @param refresh_layout
     * @param recyclerView
     * @param converter
     * @return
     */
    public static RefreshHandler create(SwipeRefreshLayout refresh_layout,
                                        RecyclerView recyclerView,
                                        DataConverter converter) {
        return new RefreshHandler(refresh_layout, recyclerView, converter, new PagingBean());
    }

    private void refresh() {
        //告诉SwipeRefreshLayout，我们开始加载了。加载进度显示
        REFRESH_LAYOUT.setRefreshing(true);

        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //设置SwipeRefreshLayout不能再刷新滚动，亦即消失。
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);

    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));

                        //设置adpter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);//设置上拉加载更多监听。(上拉加载更多入口)
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();//分页部分逻辑。（加一页)

                    }
                })
                .build()
                .get();
    }

    //SwipeRefreshLayout的内部接口，用来监听SwipeRefreshLayout的refresh操作
    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        LatteLogger.d("下拉刷新");

    }
}
