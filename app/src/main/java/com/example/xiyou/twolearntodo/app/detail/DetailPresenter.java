package com.example.xiyou.twolearntodo.app.detail;

import com.example.xiyou.twolearntodo.controller.IJsonObject;
import com.example.xiyou.twolearntodo.controller.NetController;
import com.example.xiyou.twolearntodo.model.MenuNewsModel;
import com.example.xiyou.twolearntodo.model.RequestModel;

/**
 * Created by maple on 2016/11/21.
 */

public class DetailPresenter {
    DetailActivity mDetailActivity;
    NetController mNetController;
    private MenuNewsModel model;

    public DetailPresenter(DetailActivity activity) {
        mDetailActivity = activity;
        mNetController = new NetController();
    }


    public void refresh(int id){
        mNetController.getNewsDetail(id, new IJsonObject() {
            @Override
            public void handleData(Object data) {
                RequestModel<Object> requestModel = (RequestModel<Object>) data;
                if (requestModel.getStatus() == 0) {
                    model = (MenuNewsModel) requestModel.getData();
                    mDetailActivity.update(model);

                } else {
                    mDetailActivity.showToast("新闻请求失败："+requestModel.getMsg());

                }
                mDetailActivity.setRefresh(false);
            }
        });
    }
}
