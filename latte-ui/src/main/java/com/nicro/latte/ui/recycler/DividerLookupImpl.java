package com.nicro.latte.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * RecyclerView 间隔符需要的类，结合BaseDecoration使用
 * Created by rongwenzhao on 2017/11/27.
 */

public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {
    private final int COLOR;
    private final int SIZE;

    public DividerLookupImpl(int color, int size) {
        COLOR = color;
        SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }
}
