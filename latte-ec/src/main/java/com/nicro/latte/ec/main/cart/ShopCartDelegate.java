package com.nicro.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.nicro.latte.app.Latte;
import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.R2;
import com.nicro.latte.ec.pay.FastPay;
import com.nicro.latte.ec.pay.IAlPayResultListener;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.ui.recycler.MultipleItemEntity;
import com.nicro.latte.util.logger.LatteLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 购物车主界面
 * Created by rongwenzhao on 2017/11/29.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ICartItemListener, IAlPayResultListener {

    ShopCartRecyclerAdapter mAdapter = null;
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView icon_select_all = null;

    //购物车为空时，填充view。用stubView开始是不需要渲染的，性能棒棒的
    @BindView(R2.id.stub_view_no_item)
    ViewStubCompat mStubView = null;

    @BindView(R2.id.tv_total_price)
    AppCompatTextView tvTotalPrice = null;

    /**
     * 点击结算按钮的处理逻辑
     */
    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {
        FastPay fastPay = FastPay.create(this)
                .setPayResultListener(this)
                .setOrderId(00);
        fastPay.beginPayDialog();

    }

    //创建订单，注意，和支付是没有关系的
    private void createOrder() {
        final String orderUrl = "";//自己的服务器地址
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        //自己服务器的一些约定参数
        orderParams.put("userid", "");
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "测试支付");
        orderParams.put("ordertype", "orderType");
        RestClient.builder()
                .url(orderUrl)
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //自己服务器返回信息。支付信息都在自己服务器请求第三方服务器。
                    }
                })
                .build()
                .post();
    }

    private boolean isInflated = false;

    /**
     * 购物车为空时显示的视图
     */
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0 && !isInflated) {
            final View stubView = mStubView.inflate();
            isInflated = true;
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你该购物了!", Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else if (count > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.refreshTotalPrice(true);
        }
    }

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onSelectAllClick() {
        int tag = (int) icon_select_all.getTag();
        if (tag == 0) {
            icon_select_all.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            icon_select_all.setTag(1);
            mAdapter.setSelectedAll(true);
            //更新RecyclerView的显示状态
            //mAdapter.notifyDataSetChanged();
            //更好的优化方式，是只更新左侧的选择框。下面的方法，相对于mAdapter.notifyDataSetChanged()只是简单优化
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            icon_select_all.setTextColor(Color.GRAY);
            icon_select_all.setTag(0);
            mAdapter.setSelectedAll(false);
            //更新RecyclerView的显示状态
            //mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }

        /**
         * 待adapter数据刷新完成才更新这边的主界面
         */
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.refreshTotalPrice(true);
            }
        }, 100);
    }

    /**
     * 点击右上角删除按钮，删除选中的商品项
     */
    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        LatteLogger.d(data);
        //要删除的数据
        List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }

        for (MultipleItemEntity entity : deleteEntities) {
            int dataSize = data.size();
            //注意：deleteEntities，data中的数据是同步的。当data中数据更改时，deleteEntities中与data中共有的还未删掉的数据也会更新。
            //所以，当有多个删除项时，currentPosition = entity.getField(ShopCartItemFields.POSIZION)也是更新后的position信息。
            int currentPosition = entity.getField(ShopCartItemFields.POSIZION);
            if (currentPosition < dataSize) {
                mAdapter.remove(currentPosition);
                //注意，mAdapter.remove(currentPosition)，该方法里面已经调用了
                //notifyItemRangeChanged(internalPosition, mData.size() - internalPosition);
                //方法，所以下面的操作基于更新之后的数据
                //还有，此处删除数据之后，上面的变量data列表的数据也是更新后的最新数据，所以，此处更新data中数据，
                // 也就直接更新了mAdapter中的数据了。
                //另，此处判断条件 currentPosition < dataSize - 1 是对的，因为data中已经减掉一个数据了
                for (; currentPosition < dataSize - 1; currentPosition++) {
                    int rawItemPos = data.get(currentPosition).getField(ShopCartItemFields.POSIZION);
                    data.get(currentPosition).setField(ShopCartItemFields.POSIZION, rawItemPos - 1);
                }
            }
        }

        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCount();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        icon_select_all.setTag(0);

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
                        mAdapter = new ShopCartRecyclerAdapter(dataConverter.setJsonData(response).convert());
                        mAdapter.setItemListener(ShopCartDelegate.this);
                        mRecyclerView.setAdapter(mAdapter);
                        checkItemCount();
                    }
                }).build()
                .get();
    }

    /**
     * adapter的商品被选择后，回调的方法
     *
     * @param itemTotalPrice
     */
    @Override
    public void onItemClick(double itemTotalPrice) {
        tvTotalPrice.setText(String.valueOf("￥" + itemTotalPrice));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
