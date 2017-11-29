package com.nicro.latte.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nicro.latte.ui.recycler.DataConverter;
import com.nicro.latte.ui.recycler.MultipleFields;
import com.nicro.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by rongwenzhao on 2017/11/29.
 */

public class ShopCartDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData())
                .getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getIntValue("id");
            final String title = data.getString("title");
            final double price = data.getDouble("price");
            final String desc = data.getString("desc");
            final int count = data.getIntValue("count");
            final String thumb = data.getString("thumb");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(ShopCartItemFields.PRICE, price)
                    .setField(ShopCartItemFields.TITLE, title)
                    .setField(ShopCartItemFields.DESC, desc)
                    .setField(ShopCartItemFields.COUNT, count)
                    .setField(ShopCartItemFields.IS_SELECTED, false)//默认没被点击
                    .setField(ShopCartItemFields.POSIZION, i)
                    .build();
            dataList.add(entity);

        }

        return dataList;
    }
}
