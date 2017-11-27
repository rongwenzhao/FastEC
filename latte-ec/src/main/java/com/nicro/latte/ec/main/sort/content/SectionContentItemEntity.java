package com.nicro.latte.ec.main.sort.content;

/**
 * 每一个good信息
 * Created by rongwenzhao on 2017/11/27.
 */

public class SectionContentItemEntity {
    private int mGoodsId = 0;
    private String mGoodsName = null;
    private String mGoodsThumb = null;//缩略图

    public SectionContentItemEntity(int mGoodsId, String mGoodsName, String mGoodsThumb) {
        this.mGoodsId = mGoodsId;
        this.mGoodsName = mGoodsName;
        this.mGoodsThumb = mGoodsThumb;
    }

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        this.mGoodsId = goodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        this.mGoodsName = goodsName;
    }

    public String getGoodsThumb() {
        return mGoodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.mGoodsThumb = goodsThumb;
    }
}
