package com.nicro.latte.ec.main.cart;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.joanzapata.iconify.widget.IconTextView;
import com.nicro.latte.ec.R;
import com.nicro.latte.ui.recycler.MultipleFields;
import com.nicro.latte.ui.recycler.MultipleItemEntity;
import com.nicro.latte.ui.recycler.MultipleRecyclerAdapter;
import com.nicro.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by rongwenzhao on 2017/11/29.
 */

public class ShopCartRecyclerAdapter extends MultipleRecyclerAdapter {
    protected ShopCartRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
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

                break;
            default:
                break;
        }
    }
}
