package com.example.xiyou.twolearntodo.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by maple on 2016/10/28.
 */

public class TwoLearnToDoApp extends Application {

    static TwoLearnToDoApp sInstance;
    RequestQueue mRequestQueue ;
    public static final String TAG = "Volley";


    @Override
    public void onCreate() {
        super.onCreate();

        //配置Fresco
        Fresco.initialize(getApplicationContext());

        //配置Volley

        sInstance = this;

    }



    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static synchronized TwoLearnToDoApp getInstance() {
        return sInstance;
    }
}
