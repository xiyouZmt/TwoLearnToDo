package com.example.xiyou.twolearntodo.app.pic;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.xiyou.twolearntodo.R;
import com.example.xiyou.twolearntodo.adapter.ImageAdapter;

import java.util.ArrayList;

public class PicActivity extends Activity {

	//图片轮播ViewPager
	public android.support.v4.view.ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic);
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		init();
	}

	//初始化数据
	public void init() {

		ImageView v1 = new ImageView(this);
		v1.setScaleType(ImageView.ScaleType.FIT_START);
		v1.setImageResource(R.mipmap.tujie01);

		ImageView v2 = new ImageView(this);
		v2.setScaleType(ImageView.ScaleType.FIT_START);
		v2.setImageResource(R.mipmap.tujie02);
		ImageView v3 = new ImageView(this);
		v3.setScaleType(ImageView.ScaleType.FIT_START);
		v3.setImageResource(R.mipmap.tujie03);
		ImageView v4 = new ImageView(this);
		v4.setScaleType(ImageView.ScaleType.FIT_START);
		v4.setImageResource(R.mipmap.tujie04);

		ImageView v5= new ImageView(this);
		v5.setScaleType(ImageView.ScaleType.FIT_START);
		v5.setImageResource(R.mipmap.tujie05);
		ImageView v6 = new ImageView(this);
		v6.setScaleType(ImageView.ScaleType.FIT_START);
		v6.setImageResource(R.mipmap.tujie06);
		ImageView v7 = new ImageView(this);
		v7.setScaleType(ImageView.ScaleType.FIT_START);
		v7.setImageResource(R.mipmap.tujie07);
		ImageView v8 = new ImageView(this);
		v8.setScaleType(ImageView.ScaleType.FIT_START);
		v8.setImageResource(R.mipmap.tujie08);

		ImageView v9 = new ImageView(this);
		v9.setScaleType(ImageView.ScaleType.FIT_START);
		v9.setImageResource(R.mipmap.tujie09);
		ImageView v10 = new ImageView(this);
		v10.setScaleType(ImageView.ScaleType.FIT_START);
		v10.setImageResource(R.mipmap.tujie10);
		ImageView v11 = new ImageView(this);
		v11.setScaleType(ImageView.ScaleType.FIT_START);
		v11.setImageResource(R.mipmap.tujie11);
		ImageView v12 = new ImageView(this);
		v12.setScaleType(ImageView.ScaleType.FIT_START);
		v12.setImageResource(R.mipmap.tujie12);

		ImageView v13 = new ImageView(this);
		v13.setScaleType(ImageView.ScaleType.FIT_START);
		v13.setImageResource(R.mipmap.tujie13);
		ImageView v14 = new ImageView(this);
		v14.setScaleType(ImageView.ScaleType.FIT_START);
		v14.setImageResource(R.mipmap.tujie14);
		ImageView v15 = new ImageView(this);
		v15.setScaleType(ImageView.ScaleType.FIT_START);
		v15.setImageResource(R.mipmap.tujie15);
		ImageView v16 = new ImageView(this);
		v16.setScaleType(ImageView.ScaleType.FIT_START);
		v16.setImageResource(R.mipmap.tujie16);
		ArrayList<ImageView> views = new ArrayList<>();
		views.add(v1);
		views.add(v2);
		views.add(v3);
		views.add(v4);
		views.add(v5);
		views.add(v6);
		views.add(v7);
		views.add(v8);
		views.add(v9);
		views.add(v10);
		views.add(v11);
		views.add(v12);
		views.add(v13);
		views.add(v14);
		views.add(v15);
		views.add(v16);
		ImageAdapter imageAdapter = new ImageAdapter(views);
		viewPager.setAdapter(imageAdapter);


	}
}
