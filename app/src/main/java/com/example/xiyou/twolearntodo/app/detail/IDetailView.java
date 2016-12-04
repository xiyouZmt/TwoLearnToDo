package com.example.xiyou.twolearntodo.app.detail;

import com.example.xiyou.twolearntodo.model.MenuNewsModel;

/**
 * Created by maple on 2016/11/21.
 */

public interface IDetailView {
    void setRefresh(boolean status);

    void update(MenuNewsModel menuNewsModel);

    void showToast(String msg);
}
