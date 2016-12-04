package com.example.xiyou.twolearntodo.controller;

import com.example.xiyou.twolearntodo.model.MenuNewsModel;
import com.example.xiyou.twolearntodo.model.PageDataModel;
import com.example.xiyou.twolearntodo.model.RequestModel;
import com.example.xiyou.twolearntodo.net.HttpResponse;
import com.example.xiyou.twolearntodo.net.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by maple on 2016/10/31.
 */

public class NetController {


/*
    String menu = "http://192.168.1.108:8080/api/menu/menu";
    String topnews = "http://192.168.1.108:8080/api/news/top";

    String newsList ="http://192.168.1.108:8080/api/news/list/%s/%s?size=20";*/

    String menu = "https://tomcat.xiyoumobile.com/party-api-1.0/api/menu/menu";

    String topnews = "https://tomcat.xiyoumobile.com/party-api-1.0/api/news/top";

    String newsList ="https://tomcat.xiyoumobile.com/party-api-1.0/api/news/list/%s/%s?size=20";

    HttpUtils mHttpUtils;
    private String newsDetail = "https://tomcat.xiyoumobile.com/party-api-1.0/api/news/detail?id=%s";

    public NetController(){

        mHttpUtils = new HttpUtils();
    }




    /* 请求新闻列表
    * 是否为首页新闻数据
    * */
    public void getNewsList(int page,int type, final IJsonObject iJsonObject){

        mHttpUtils.getRequest(String.format(newsList, type, page), new HttpResponse() {
            @Override
            public void httpResponse(String info) {
                Gson mGson = new Gson();
                try {
                    RequestModel<PageDataModel> requestModel = mGson.fromJson(info,new TypeToken<RequestModel<PageDataModel>>(){}.getType());
                    for (MenuNewsModel item : requestModel.getData().getDataList()){
                        item.setMenu(false);
                    }
                    iJsonObject.handleData(requestModel);
                }
                catch (Exception ex){

                    RequestModel<String> requestModel = (RequestModel<String>) mGson.fromJson(info,new TypeToken<RequestModel<String>>(){}.getRawType());
                    requestModel.setStatus(-1);
                    iJsonObject.handleData(requestModel);
                }
            }
        });

    }
    /*
     * 请求首页菜单
     * */
    public void getMenu(final IJsonObject iJsonObject) {

        mHttpUtils.getRequest(menu, new HttpResponse() {
            @Override
            public void httpResponse(String info) {
                Gson mGson = new Gson();
                try {
                    RequestModel<List<MenuNewsModel>> requestModel = mGson.fromJson(info,new TypeToken<RequestModel<List<MenuNewsModel>>>(){}.getType());
                    for (MenuNewsModel item : requestModel.getData()){
                        item.setMenu(true);
                    }
                    iJsonObject.handleData(requestModel);
                }
                catch (Exception ex){

                    RequestModel<String> requestModel = mGson.fromJson(info,new TypeToken<RequestModel<String>>(){}.getType());
                    requestModel.setStatus(-1);
                    iJsonObject.handleData(requestModel);
                }
            }
        });
    }

    public void getTopNews(final IJsonObject iJsonObject){

        mHttpUtils.getRequest(topnews, new HttpResponse() {
            @Override
            public void httpResponse(String info) {
                Gson mGson = new Gson();
                try {
                    RequestModel<List<MenuNewsModel>> requestModel = mGson.fromJson(info,new TypeToken<RequestModel<List<MenuNewsModel>>>(){}.getType());
                    for (MenuNewsModel item : requestModel.getData()){
                        item.setMenu(false);
                    }
                    iJsonObject.handleData(requestModel);
                }
                catch (Exception ex){

                    RequestModel<String> requestModel = mGson.fromJson(info,new TypeToken<RequestModel<String>>(){}.getType());
                    requestModel.setStatus(-1);
                    iJsonObject.handleData(requestModel);
                }
            }
        });
    }

    public void getNewsDetail(int id, final IJsonObject iJsonObject){
        mHttpUtils.getRequest(String.format(newsDetail, id), new HttpResponse() {
            @Override
            public void httpResponse(String info) {
                Gson mGson = new Gson();
                try {
                    RequestModel<MenuNewsModel> requestModel = mGson.fromJson(info,new TypeToken<RequestModel<MenuNewsModel>>(){}.getType());

                    iJsonObject.handleData(requestModel);
                }
                catch (Exception ex){

                    RequestModel<String> requestModel = (RequestModel<String>) mGson.fromJson(info,new TypeToken<RequestModel<String>>(){}.getRawType());
                    requestModel.setStatus(-1);
                    iJsonObject.handleData(requestModel);
                }
            }
        });
    }

}
