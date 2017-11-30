package com.nicro.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joanzapata.iconify.widget.IconTextView;
import com.nicro.latte.ec.R;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.ui.recycler.MultipleFields;
import com.nicro.latte.ui.recycler.MultipleItemEntity;
import com.nicro.latte.ui.recycler.MultipleRecyclerAdapter;
import com.nicro.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by rongwenzhao on 2017/11/29.
 */

public class ShopCartRecyclerAdapter extends MultipleRecyclerAdapter {

    //全选是否被点击
    private boolean mIsSelectedAll = false;
    //总价格有变化时的回调
    ICartItemListener mCartItemListener = null;

    public void setItemListener(ICartItemListener itemListener) {
        this.mCartItemListener = itemListener;
    }

    public void refreshTotalPrice(boolean refresh) {
        if (refresh) {
            //通知delegate，总价格有变化
            if (mCartItemListener != null) {
                mCartItemListener.onItemClick(getTotalPrice());
            }
        }
    }

    private double getTotalPrice() {
        double totalPrice = 0.00;
        for (MultipleItemEntity entity : getData()) {
            if (entity.getField(ShopCartItemFields.IS_SELECTED)) {
                totalPrice += ((int) entity.getField(ShopCartItemFields.COUNT)) * ((double) entity.getField(ShopCartItemFields.PRICE));
            }
        }
        return totalPrice;
    }

    protected ShopCartRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setSelectedAll(boolean selectedAll) {
        mIsSelectedAll = selectedAll;
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final double price = entity.getField(ShopCartItemFields.PRICE);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);

                //取出所有控件
                final AppCompatImageView thumbImageView = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tv_title = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tv_desc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tv_price = holder.getView(R.id.tv_item_shop_cart_price);
                final AppCompatTextView tv_count = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView icon_item_plus = holder.getView(R.id.icon_item_plus);
                final IconTextView icon_item_minus = holder.getView(R.id.icon_item_minus);
                final IconTextView icon_isSelected = holder.getView(R.id.icon_item_shop_cart);
                //赋值
                tv_title.setText(title);
                tv_desc.setText(desc);
                tv_count.setText(String.valueOf(count));
                tv_price.setText(String.valueOf(price));
                Glide.with(mContext)
                        .load(thumb)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .into(thumbImageView);

                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);

                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                if (isSelected) {
                    icon_isSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                } else {
                    icon_isSelected.setTextColor(Color.GRAY);
                }

                //左侧勾勾的点击事件
                icon_isSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelecteInfo = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelecteInfo) {
                            icon_isSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            icon_isSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);
                        }
                        if (mCartItemListener != null) {
                            mCartItemListener.onItemClick(getTotalPrice());
                        }
                    }
                });

                //加，减点击事件
                icon_item_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //可根据具体需求按需处理。
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        RestClient.builder()
                                .url("shop_cart_count.php")
                                .params("count", currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tv_count.getText().toString());
                                        countNum++;
                                        tv_count.setText(String.valueOf(countNum));
                                        entity.setField(ShopCartItemFields.COUNT, countNum);
                                        if (((boolean) entity.getField(ShopCartItemFields.IS_SELECTED)) && mCartItemListener != null) {
                                            mCartItemListener.onItemClick(getTotalPrice());
                                        }
                                    }
                                })
                                .build()
                                .post();

                    }
                });

                icon_item_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //可根据具体需求按需处理。
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        if (Integer.parseInt(tv_count.getText().toString()) > 1) {
                            RestClient.builder()
                                    .url("shop_cart_count.php")
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tv_count.getText().toString());
                                            countNum--;
                                            tv_count.setText(String.valueOf(countNum));
                                            entity.setField(ShopCartItemFields.COUNT, countNum);
                                            if (((boolean) entity.getField(ShopCartItemFields.IS_SELECTED)) && mCartItemListener != null) {
                                                mCartItemListener.onItemClick(getTotalPrice());
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });


                break;
            default:
                break;
        }
    }
}
