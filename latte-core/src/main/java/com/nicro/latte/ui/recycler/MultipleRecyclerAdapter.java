package com.nicro.latte.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nicro.latte.R;
import com.nicro.latte.ui.banner.BannerCreator;
import com.nicro.latte.util.logger.LatteLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * 继承自BaseMultiItemQuickAdapter，BaseMultiItemQuickAdapter这个类是为了实现多布局而诞生的。
 * Created by rongwenzhao on 2017/11/26.
 */

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    //是否初始化一次Banner，防止重复item加载(RecyclerView上拉，下拉时，都会进行item的重新加载，而banner是不需要重新加载的)。
    private boolean mIsInitBanner = false;

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    /**
     * 简单工厂方式产生adapter
     *
     * @param data
     * @return
     */
    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter dataConverter) {
        return new MultipleRecyclerAdapter(dataConverter.convert());
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);

        //设置宽度的监听
        setSpanSizeLookup(this);
        //打开loading动画
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);

    }

    /**
     * 创建返回自定义的ViewHolder，并可以在自定义的viewHolder中做一些事情。
     *
     * @param view
     * @return
     */
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    /**
     * 根据item类型，生成不同类型的布局
     * holder.getItemViewType() 返回的就是上面init()方法中 addItemType()的第一个参数
     *
     * @param holder
     * @param entity
     */
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        LatteLogger.d("holder = " + holder + "and type = " + holder.getItemViewType());
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAGE:
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.img_single));
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.tv_multiple, text);
                Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.img_multiple));
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }

                break;
            default:
                break;

        }

    }

    /**
     * SpanSize为多少，表示占用几个item
     * gridLayoutManager.getSpanCount() 返回的是我们开始设置的一行几个item数量
     *
     * @param gridLayoutManager
     * @param position
     * @return
     */
    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    /**
     * banner项的点击事件（预留的，待扩展）
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }
}
