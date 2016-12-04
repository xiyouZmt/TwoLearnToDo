/**
 * Copyright (C) 2016 xiyoumobile.com. All Rights Reserved.
 */
package com.example.xiyou.twolearntodo.model;

import com.google.gson.annotations.SerializedName;

/**
 * RequestModel
 *
 * @author ModelGenerator
 */
public class RequestModel<T> {
    
    @SerializedName("status")
   private int mStatus;
    
    @SerializedName("data")
    private T mData;
    
    @SerializedName("msg")
    private String mMsg;
    
    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

}