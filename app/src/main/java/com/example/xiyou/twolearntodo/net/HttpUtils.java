package com.example.xiyou.twolearntodo.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xiyou.twolearntodo.application.TwoLearnToDoApp;
import com.example.xiyou.twolearntodo.model.RequestModel;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by maple on 2016/8/30.
 */
public class HttpUtils {

    private final Gson mGson;
    Context mContext;
    RequestQueue mRequestQueue;

    public HttpUtils() {
        mContext = TwoLearnToDoApp.getInstance().getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(mContext);
        mGson = new Gson();
    }

    public void getRequest(String url, final HttpResponse res) {
        StringRequest stringRequest = new StringRequest(url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                res.httpResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                RequestModel<String> requestModel = VolleyErrorHelper.getMessage(error,mContext);
                res.httpResponse(mGson.toJson(requestModel));
            }
        });

        mRequestQueue.add(stringRequest);
    }

    public void postRequest(String url,HttpResponse response) {
        StringRequest req = new StringRequest(Request.Method.POST, url,
                //回调响应
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                    //出错回调
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


            }
        }) {
            //输入参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                return null;
            }
        };
        mRequestQueue.add(req);
    }

    public void postRequest(String url, final Map<String, String> params, final Map<String, String> headers, final HttpResponse httpResponse) {
        StringRequest req = new StringRequest(Request.Method.POST, url,
                //回调响应
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        httpResponse.httpResponse(s);
                    }
                    //出错回调
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Object o = new Object();
            }
        }) {
            //输入参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (null != headers) {
                    return headers;
                }
                return null;
            }
        };


        mRequestQueue.add(req);
    }

    public void postRequest(String url, final Map<String, String> params, final HttpResponse httpResponse) {
        StringRequest req = new StringRequest(Request.Method.POST, url,
                //回调响应
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        httpResponse.httpResponse(s);
                    }
                    //出错回调
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Object o = new Object();
            }
        }) {
            //输入参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }


        };


        mRequestQueue.add(req);
    }
}
