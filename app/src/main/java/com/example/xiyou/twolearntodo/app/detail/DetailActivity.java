package com.example.xiyou.twolearntodo.app.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiyou.twolearntodo.R;
import com.example.xiyou.twolearntodo.app.list.ListActivity;
import com.example.xiyou.twolearntodo.model.MenuNewsModel;

public class DetailActivity extends Activity implements IDetailView {


    private WebView webView;
    TextView mTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DetailPresenter mDetailPresenter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);
        findView();
    }

    private void findView() {
        id = getIntent().getIntExtra("id",0);
        mTextView = (TextView) findViewById(R.id.title);
        mDetailPresenter = new DetailPresenter(this);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        // WebView打开权限
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);


        // webView.loadUrl(DataInfo.getInstance().mMenuNewsModel.getMContent());
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDetailPresenter.refresh(id);
            }
        });

        mDetailPresenter.refresh(id);
        setRefresh(true);

    }

    public static void startAction(Context context, int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void setRefresh(boolean status) {
        swipeRefreshLayout.setRefreshing(status);
    }

    @Override
    public void update(MenuNewsModel menuNewsModel) {
        mTextView.setText(menuNewsModel.getMTitle());
        webView.loadDataWithBaseURL("http://party.northk.cn/", menuNewsModel.getMContent(), "text/html", "utf-8", null);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
