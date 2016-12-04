package com.example.xiyou.twolearntodo.app.main;


import android.widget.ImageView;

import com.example.xiyou.twolearntodo.model.MenuNewsModel;

import java.util.List;

/**
 * Created by maple on 2016/10/29.
 */

public interface IMainView {

    void updateViewPager(List<ImageView> imgList);
    void showToast(String msg);

    //首页新闻//显示菜单
    void updateFirstNews(List<MenuNewsModel> newses);
}
