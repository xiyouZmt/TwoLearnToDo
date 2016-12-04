package com.example.xiyou.twolearntodo.app.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.xiyou.twolearntodo.R;
import com.example.xiyou.twolearntodo.app.pic.PicActivity;
import com.example.xiyou.twolearntodo.controller.IJsonObject;
import com.example.xiyou.twolearntodo.controller.NetController;
import com.example.xiyou.twolearntodo.model.MenuNewsModel;
import com.example.xiyou.twolearntodo.model.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maple on 2016/10/29.
 */

public class MainPresenter {
    MainActivity mMainActivity;
    NetController mNetController;

    private List<MenuNewsModel> menus;
    private List<MenuNewsModel> newss;


    public MainPresenter(MainActivity mainActivity) {
        mMainActivity = mainActivity;

        mNetController = new NetController();
    }

    public void initData() {
        ImageView v1 = new ImageView(mMainActivity);
        v1.setScaleType(ImageView.ScaleType.FIT_XY);
        v1.setImageResource(R.mipmap.first);


        ImageView v2 = new ImageView(mMainActivity);
        v2.setScaleType(ImageView.ScaleType.FIT_XY);
        v2.setImageResource(R.mipmap.two);
        ImageView v3 = new ImageView(mMainActivity);
        v3.setScaleType(ImageView.ScaleType.FIT_XY);
        v3.setImageResource(R.mipmap.three);
        ImageView v4 = new ImageView(mMainActivity);
        v4.setScaleType(ImageView.ScaleType.FIT_XY);
        v4.setImageResource(R.mipmap.four);


        ArrayList<ImageView> views = new ArrayList<>();

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, PicActivity.class);
                mMainActivity.startActivity(intent);
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, PicActivity.class);
                mMainActivity.startActivity(intent);
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, PicActivity.class);
                mMainActivity.startActivity(intent);
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainActivity, PicActivity.class);
                mMainActivity.startActivity(intent);
            }
        });
        views.add(v1);
        views.add(v2);
        views.add(v3);
        views.add(v4);
        mMainActivity.updateViewPager(views);
    }


    public void refreshData() {

        mNetController.getMenu(new IJsonObject() {
            @Override
            public void handleData(Object data) {
                RequestModel<Object> requestModel = (RequestModel<Object>) data;
                if (requestModel.getStatus() == 0) {

                    menus = (List<MenuNewsModel>) requestModel.getData();
                    fun();
                } else {
                    mMainActivity.showToast("首页新闻请求失败："+requestModel.getMsg());
                    mMainActivity.setRefreshing(false);


                }
            }
        });


        mNetController.getTopNews( new IJsonObject() {
            @Override
            public void handleData(Object data) {
                RequestModel<Object> requestModel = (RequestModel<Object>) data;
                if (requestModel.getStatus() == 0) {
                    newss = (List<MenuNewsModel>) requestModel.getData();
                    fun();
                } else {
                    mMainActivity.showToast("首页新闻请求失败："+requestModel.getMsg());
                    mMainActivity.setRefreshing(false);
                }
            }
        });

    }

    int count = 0;

    public void fun() {
        synchronized (this) {
            count++;
            if (count == 2) {
                //停止刷新
                mMainActivity.setRefreshing(false);
                if (null != menus) {
                    menus.addAll(newss);
                    mMainActivity.updateFirstNews(menus);
                } else {
                    mMainActivity.updateFirstNews(newss);
                }
                count = 0;
            }
        }

    }
}
