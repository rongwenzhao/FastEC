package com.nicro.latte.ui.recycler;

import java.util.LinkedHashMap;

/**
 * MultipleItemEntity实体的构建类
 * Created by rongwenzhao on 2017/11/26.
 */

public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //构造时进行数据清理。
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }

}
