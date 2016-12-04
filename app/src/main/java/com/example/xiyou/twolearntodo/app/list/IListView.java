package com.example.xiyou.twolearntodo.app.list;

import com.example.xiyou.twolearntodo.model.MenuNewsModel;

import java.util.List;

/**
 * Created by maple on 2016/10/29.
 */

public interface IListView {
    void showToast(String msg);
    void updateNews(boolean isLoad,List<MenuNewsModel> list);
    void setLoadStatus(boolean status);
}
