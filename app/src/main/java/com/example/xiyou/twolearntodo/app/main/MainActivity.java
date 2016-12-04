package com.example.xiyou.twolearntodo.app.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xiyou.twolearntodo.R;
import com.example.xiyou.twolearntodo.adapter.CommonAdapter;
import com.example.xiyou.twolearntodo.adapter.ImageAdapter;
import com.example.xiyou.twolearntodo.app.detail.DetailActivity;
import com.example.xiyou.twolearntodo.app.list.ListActivity;
import com.example.xiyou.twolearntodo.common.ImageHandler;
import com.example.xiyou.twolearntodo.model.MenuNewsModel;
import com.example.xiyou.twolearntodo.utils.DisplayUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements IMainView ,SwipeRefreshLayout.OnRefreshListener{

    ImageAdapter imageAdapter;

    private RecyclerView recyclerview;
    private ViewPager viewPager;

    private ImageHandler<MainActivity> handler;

    private ArrayList<MenuNewsModel> mList;

    private RecyclerView news;

    private ArrayList<ImageView> mImageViewList;
    private MainPresenter mMainPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdapter mCommonAdapter;
    private int colums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        //轮播图
        mImageViewList = new ArrayList<>();
        imageAdapter = new ImageAdapter(mImageViewList);
        viewPager.setAdapter(imageAdapter);
        //控制器更新首页数据
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.initData();

        //获取数据
        setRefreshing(true);
        mMainPresenter.refreshData();
    }

    private void initView() {

        colums = DisplayUtils.colums(this);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        //设置四列
        recyclerview.setLayoutManager(new GridLayoutManager(this, colums, GridLayoutManager.VERTICAL, false));

        mList = new ArrayList<>();
        mCommonAdapter = new CommonAdapter(this,mList,colums,false);
        recyclerview.setAdapter(mCommonAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        mCommonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, boolean isMenu) {
                if(isMenu){
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("title",mList.get(pos).getName());
                    intent.putExtra("mId",mList.get(pos).getId());
                    startActivity(intent);
                }
                else{
                    DetailActivity.startAction(MainActivity.this,mList.get(pos).getMId());
                }
            }
        });



    }

    @Override
    public void updateViewPager(List<ImageView> imgList) {

        if (mImageViewList.size() != 0) {
            mImageViewList.clear();
        }
        mImageViewList.addAll(imgList);
        imageAdapter.notifyDataSetChanged();
        if (null != handler) {
            handler = null;
        }
        handler = new ImageHandler<>(new WeakReference<>(this), mImageViewList.size(), viewPager);
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFirstNews(List<MenuNewsModel> newses) {
        mList.clear();
        mList.addAll(newses);
        mCommonAdapter.notifyDataSetChanged();
    }


    public void setRefreshing(boolean status) {

        swipeRefreshLayout.setRefreshing(status);
    }

    @Override
    public void onRefresh() {
        mMainPresenter.refreshData();
    }
}
