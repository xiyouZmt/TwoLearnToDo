/**
 * Copyright (C) 2016 xiyoumobile.com. All Rights Reserved.
 */
package com.example.xiyou.twolearntodo.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * RequestModelDataModel
 *
 * @author ModelGenerator
 */
public class PageDataModel {
    
    @SerializedName("dataList")
    private List<MenuNewsModel> mDataList;
    
    @SerializedName("pageInfo")
    private PageInfoModel mPageInfo;
    
    public List<MenuNewsModel> getDataList() {
        return mDataList;
    }

    public void setDataList(List<MenuNewsModel> dataList) {
        mDataList = dataList;
    }

    public PageInfoModel getPageInfo() {
        return mPageInfo;
    }

    public void setPageInfo(PageInfoModel pageInfo) {
        mPageInfo = pageInfo;
    }

}