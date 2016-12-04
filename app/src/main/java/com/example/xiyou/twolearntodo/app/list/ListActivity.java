package com.example.xiyou.twolearntodo.app.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiyou.twolearntodo.R;
import com.example.xiyou.twolearntodo.adapter.CommonAdapter;
import com.example.xiyou.twolearntodo.app.detail.DetailActivity;
import com.example.xiyou.twolearntodo.model.MenuNewsModel;
import com.example.xiyou.twolearntodo.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity implements IListView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerview;


    private String title;
    private int mId;
    private int colums;
    private ArrayList<MenuNewsModel> mList;
    private CommonAdapter mCommonAdapter;

    ListPresenter mListPresenter;


    boolean isLoading = false;
    private RecyclerView.OnScrollListener mRecyclerViewOnScrollerChanged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        title = getIntent().getStringExtra("title");
        mId = getIntent().getIntExtra("mId", 0);

        initView();
    }


    private void initView() {


        mListPresenter = new ListPresenter(this);

        ((TextView) findViewById(R.id.title)).setText(title);

        colums = DisplayUtils.colums(this);

        recyclerview = (RecyclerView) findViewById(R.id.list);
        //设置四列
        recyclerview.setLayoutManager(new GridLayoutManager(this, colums, GridLayoutManager.VERTICAL, false));

        mList = new ArrayList<>();
        mCommonAdapter = new CommonAdapter(this, mList, colums, true);
        recyclerview.setAdapter(mCommonAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mListPresenter.refreshData(false, mId);
            }
        });

        setRefreshing(true);
        mListPresenter.refreshData(false, mId);

        mCommonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, boolean isMenu) {
                if (isMenu) {
                    Intent intent = new Intent(ListActivity.this, ListActivity.class);
                    intent.putExtra("title", mList.get(pos).getName());
                    intent.putExtra("mId", mList.get(pos).getId());
                    startActivity(intent);
                } else {
                    DetailActivity.startAction(ListActivity.this,mList.get(pos).getMId());
                }
            }
        });


        recyclerview.addOnScrollListener((mRecyclerViewOnScrollerChanged=new RecyclerViewOnScrollerChanged()));


    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateNews(boolean isLoad, List<MenuNewsModel> list) {
        if(list.size() == 0){
            showToast("没有数据了");
            mCommonAdapter.setLoadItemVisible(View.GONE);
        }
        if (!isLoad) {
            mList.clear();
        }
        mList.addAll(list);
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoadStatus(boolean status) {
        isLoading = status;
    }


    public void setRefreshing(boolean status) {

        swipeRefreshLayout.setRefreshing(status);
    }


    public void setLoadMore(int visuable){
        mCommonAdapter.setLoadItemVisible(visuable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerview.removeOnScrollListener(mRecyclerViewOnScrollerChanged);
    }

    public class RecyclerViewOnScrollerChanged extends RecyclerView.OnScrollListener{
        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            if(20 > mCommonAdapter.getItemCount()){
                mCommonAdapter.setLoadItemVisible(View.GONE);

            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if(isLoading){
                return;
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mCommonAdapter.getItemCount()) {
                mCommonAdapter.setLoadItemVisible(View.VISIBLE);
               mListPresenter.refreshData(true,mId);
            }
        }


    }
}

