package com.nicro.latte.ec.main;

import android.graphics.Color;

import com.nicro.latte.delegates.bottom.BaseBottomDelegate;
import com.nicro.latte.delegates.bottom.BottomItemDelegate;
import com.nicro.latte.delegates.bottom.BottomTabBean;
import com.nicro.latte.delegates.bottom.ItemBuilder;
import com.nicro.latte.ec.main.discover.DiscoverDelegate;
import com.nicro.latte.ec.main.index.IndexDelegate;
import com.nicro.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * 电商项目的整个主界面
 * Created by rongwenzhao on 2017/11/25.
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        //设置tab点击之后icon与title的颜色
        return Color.parseColor("#ffff8800");
    }
}
