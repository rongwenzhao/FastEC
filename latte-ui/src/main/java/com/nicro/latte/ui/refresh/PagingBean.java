package com.nicro.latte.ui.refresh;

/**
 * 存放分页相关的信息的bean
 * Created by rongwenzhao on 2017/11/26.
 */

public final class PagingBean {
    //当前是第几页
    private int mPageIndex = 0;
    //总数据条数
    private int mTotal = 0;
    //一页显示几条数据
    private int mPageSize = 0;
    //当前已经显示了几条数据
    private int mCurrentCount = 0;
    //加载延迟
    private int mDelayed = 0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this;
    }

    public int getTotal() {
        return mTotal;
    }

    public PagingBean setTotal(int mTotal) {
        this.mTotal = mTotal;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PagingBean setDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this;
    }

    /**
     * 注意：第一次进入界面，当前页面就应该加一，也就是第一页。
     * @return
     */
    PagingBean addIndex(){
        mPageIndex ++;
        return this;
    }
}
