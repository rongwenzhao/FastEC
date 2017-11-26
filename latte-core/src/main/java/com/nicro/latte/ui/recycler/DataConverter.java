package com.nicro.latte.ui.recycler;

import java.util.ArrayList;

/**
 * 数据转换的类的约束类，定义为抽象类。
 * 其实现类的功能，是将传入的json字符串转换为entity列表返回。
 * Created by rongwenzhao on 2017/11/26.
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

}
