package com.nicro.latte.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.R2;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.util.logger.LatteLogger;

import butterknife.BindView;

/**
 * 购物车主界面
 * Created by rongwenzhao on 2017/11/29.
 */

public class ShopCartDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LatteLogger.d("shop_cart data " + response);
                        final ShopCartDataConverter dataConverter = new ShopCartDataConverter();
                        ShopCartRecyclerAdapter adapter =
                                new ShopCartRecyclerAdapter(dataConverter.setJsonData(response).convert());
                        mRecyclerView.setAdapter(adapter);
                    }
                }).build()
                .get();
    }
}
