package com.example.xiyou.twolearntodo.utils;

import com.example.xiyou.twolearntodo.model.MenuNewsModel;

/**
 * Created by maple on 2016/11/4.
 */

//用于新闻传递数据的单列
public class DataInfo {

    public MenuNewsModel mMenuNewsModel;

    public static DataInfo getInstance() {
        return DataInfoHolder.sDataInfo;
    }


    private static class DataInfoHolder {
        public static DataInfo sDataInfo = new DataInfo();
    }
}
