package com.nicro.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.ec.R;
import com.nicro.latte.ec.main.sort.SortDelegate;
import com.nicro.latte.ec.main.sort.content.ContentDelegate;
import com.nicro.latte.ui.recycler.ItemType;
import com.nicro.latte.ui.recycler.MultipleFields;
import com.nicro.latte.ui.recycler.MultipleItemEntity;
import com.nicro.latte.ui.recycler.MultipleRecyclerAdapter;
import com.nicro.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by rongwenzhao on 2017/11/27.
 */

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {
    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;//上一次被点击的位置

    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        //添加菜单布局
        addItemType(ItemType.VERTICAL_SORT_LIST_MENU, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_SORT_LIST_MENU:
                final String text = entity.getField(MultipleFields.NAME);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition = holder.getAdapterPosition();
                        if (mPrePosition != currentPosition) {
                            //还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);//单元素更新

                            //更新选中的item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            //根据ContentID产生对应的ContentDelegate，之后再显示出来。
                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_vertical_item_name, text);

                break;
            default:
                break;
        }

    }

    /**
     * 根据contentId产生ContentDelegate，并显示处理
     *
     * @param contentId
     */
    private void showContent(int contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    /**
     * 先检查SortDelegate中有没有ContentDelegate类型的子Fragment，肯定会有，因为初始化为contentID为 1 。
     * 所以，此处，就是在点击了非当前分类后的内容切换。
     * 找到之后直接replaceFreagment，并且不加入返回栈。
     *
     * @param delegate
     */
    private void switchContent(ContentDelegate delegate) {
        final LatteDelegate contentDelegate =
                SupportHelper.findFragment(DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        //DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
            //contentDelgate.replaceFragment(delegate, false);
        }
    }
}
