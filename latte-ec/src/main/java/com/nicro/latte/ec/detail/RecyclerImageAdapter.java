package com.nicro.latte.ec.detail;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nicro.latte.ec.R;
import com.nicro.latte.ui.recycler.ItemType;
import com.nicro.latte.ui.recycler.MultipleFields;
import com.nicro.latte.ui.recycler.MultipleItemEntity;
import com.nicro.latte.ui.recycler.MultipleRecyclerAdapter;
import com.nicro.latte.ui.recycler.MultipleViewHolder;

import java.util.List;


/**
 * Created by rongwenzhao on 2017/12/4.
 */

public class RecyclerImageAdapter extends MultipleRecyclerAdapter {

    protected RecyclerImageAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        final int type = holder.getItemViewType();
        switch (type) {
            case ItemType.SINGLE_BIG_IMAGE:
                final AppCompatImageView imageView = holder.getView(R.id.image_rv_item);
                final String url = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
