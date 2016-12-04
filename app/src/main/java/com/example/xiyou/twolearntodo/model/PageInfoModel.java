/**
 * Copyright (C) 2016 xiyoumobile.com. All Rights Reserved.
 */
package com.example.xiyou.twolearntodo.model;

import com.google.gson.annotations.SerializedName;

/**
 * RequestModelDataPageInfoModel
 *
 * @author ModelGenerator
 */
public class PageInfoModel {
    
    @SerializedName("everyPage")
    private int mEveryPage;
    
    @SerializedName("totalCount")
    private int mTotalCount;
    
    @SerializedName("totalPage")
    private int mTotalPage;
    
    @SerializedName("currentPage")
    private int mCurrentPage;
    
    @SerializedName("beginIndex")
    private int mBeginIndex;
    
    @SerializedName("hasPrePage")
    private boolean mHasPrePage;
    
    @SerializedName("hasNextPage")
    private boolean mHasNextPage;
    
    public int getEveryPage() {
        return mEveryPage;
    }

    public void setEveryPage(int everyPage) {
        mEveryPage = everyPage;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(int totalPage) {
        mTotalPage = totalPage;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public int getBeginIndex() {
        return mBeginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        mBeginIndex = beginIndex;
    }

    public boolean getHasPrePage() {
        return mHasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        mHasPrePage = hasPrePage;
    }

    public boolean getHasNextPage() {
        return mHasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        mHasNextPage = hasNextPage;
    }

}