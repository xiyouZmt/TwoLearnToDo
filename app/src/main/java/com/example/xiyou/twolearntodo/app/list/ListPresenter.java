package com.example.xiyou.twolearntodo.app.list;

import android.view.View;

import com.example.xiyou.twolearntodo.controller.IJsonObject;
import com.example.xiyou.twolearntodo.controller.NetController;
import com.example.xiyou.twolearntodo.model.PageDataModel;
import com.example.xiyou.twolearntodo.model.RequestModel;

/**
 * Created by maple on 2016/10/29.
 */

public class ListPresenter {

    private final ListActivity mListActivity;
    private final NetController mNetController;

    private PageDataModel mPageDataModel;

    public ListPresenter(ListActivity listActivity) {
        mListActivity = listActivity;

        mNetController = new NetController();
    }


    public void refreshData(final boolean isload, int mID) {



        int page;
        if (isload) {

            if(!mPageDataModel.getPageInfo().getHasNextPage()){
                mListActivity.showToast("没有更多了");
                mListActivity.setLoadMore(View.GONE);
                return;
            }

            //如果是加载更多
            if (mPageDataModel == null) {
                page = 1;
            } else {
                page = mPageDataModel.getPageInfo().getCurrentPage() + 1;
            }
            mListActivity.setLoadStatus(true);

        } else {
            //如果下拉刷新
            mPageDataModel = null;
            page = 1;
        }


        mNetController.getNewsList(page, mID, new IJsonObject() {
            @Override
            public void handleData(Object data) {
                RequestModel<Object> requestModel = (RequestModel<Object>) data;
                if (requestModel.getStatus() == 0) {
                    mPageDataModel = (PageDataModel) requestModel.getData();
                    mListActivity.updateNews(isload,mPageDataModel.getDataList());


                } else {
                    mListActivity.showToast("新闻请求失败："+requestModel.getMsg());

                }
                mListActivity.setRefreshing(false);
                mListActivity.setLoadStatus(false);
            }
        });

    }
}
