package com.nicro.latte.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * 元素构建器：将底部的元素容器与底部tab元素关联、构造。
 * Created by rongwenzhao on 2017/11/25.
 */

public class ItemBuilder {
    //底部tab的存储变量。用有序的LinkedHashMap来存储，保证每次底部的显示都是有序的
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    //用简单工厂模式生成ItemBuilder。其构造参数一目了然。
    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    //添加元素
    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }

}
