package com.example.xiyou.twolearntodo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Admin on 2016/1/25.
 */
public class ImageAdapter extends PagerAdapter {

    private List<ImageView> viewlist;

    public ImageAdapter(List<ImageView> viewlist) {
        this.viewlist = viewlist;
    }
    @Override
    public int getCount() {
        return viewlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
     //   container.removeView(viewlist.get(position));
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      //  position = (position + 1) % viewlist.size();
        ImageView imageView = viewlist.get(position);
        ViewParent vp =imageView.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }
}
