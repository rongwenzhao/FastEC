package com.nicro.latte.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by rongwenzhao on 2017/11/27.
 */

public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    //是否显示更多
    private boolean mIsMore = false;
    //本SectionBean的ID
    private int mId = -1;


    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public boolean isMore() {
        return mIsMore;
    }

    public void setIsMore(boolean isMore) {
        this.mIsMore = isMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
