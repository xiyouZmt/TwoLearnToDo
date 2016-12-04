package com.example.xiyou.twolearntodo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by maple on 2016/11/1.
 */

public class MenuNewsModel {


    private  boolean mIsMenu;

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("mMenuImg")
    private String mMMenuImg;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getMMenuImg() {
        return mMMenuImg;
    }

    public void setMMenuImg(String mMenuImg) {
        mMMenuImg = mMenuImg;
    }

    @SerializedName("mId")
    private int mMId;

    @SerializedName("mMenuId")
    private int mMMenuId;

    @SerializedName("mContent")
    private String mMContent;

    @SerializedName("mTime")
    private String mMTime;

    @SerializedName("mAuthor")
    private String mMAuthor;

    @SerializedName("mTitle")
    private String mMTitle;

    public int getMId() {
        return mMId;
    }

    public void setMId(int mId) {
        mMId = mId;
    }

    public int getMMenuId() {
        return mMMenuId;
    }

    public void setMMenuId(int mMenuId) {
        mMMenuId = mMenuId;
    }

    public String getMContent() {
        return mMContent;
    }

    public void setMContent(String mContent) {
        mMContent = mContent;
    }

    public String getMTime() {
        return mMTime;
    }

    public void setMTime(String mTime) {
        mMTime = mTime;
    }

    public String getMAuthor() {
        return mMAuthor;
    }

    public void setMAuthor(String mAuthor) {
        mMAuthor = mAuthor;
    }

    public String getMTitle() {
        return mMTitle;
    }

    public void setMTitle(String mTitle) {
        mMTitle = mTitle;
    }


    public boolean isMenu() {
        return mIsMenu;
    }

    public void setMenu(boolean menu) {
        mIsMenu = menu;
    }


}
