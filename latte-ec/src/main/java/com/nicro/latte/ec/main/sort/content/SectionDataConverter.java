package com.nicro.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流的数据转换器
 * Created by rongwenzhao on 2017/11/27.
 */

public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
        int size = dataArray.size();
        //section循环
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getIntValue("id");
            final String title = data.getString("section");

            //增加title
            final SectionBean sectionTitleBean = new SectionBean(true, title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setIsMore(true);
            dataList.add(sectionTitleBean);

            final JSONArray goods = data.getJSONArray("goods");
            //商品内容循环
            final int goodSize = goods.size();
            for (int j = 0; j < goodSize; j++) {
                final JSONObject contentItem = goods.getJSONObject(j);
                final int goodsId = contentItem.getIntValue("goods_id");
                final String goodsName = contentItem.getString("goods_name");
                final String goodsThumb = contentItem.getString("goods_thumb");
                //封装成entity
                SectionContentItemEntity itemEntity = new SectionContentItemEntity(goodsId, goodsName, goodsThumb);

                //添加内容到数据集
                dataList.add(new SectionBean(itemEntity));
            }
        }
        return dataList;
    }
}
