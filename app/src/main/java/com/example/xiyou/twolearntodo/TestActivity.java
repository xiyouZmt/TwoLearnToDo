package com.example.xiyou.twolearntodo;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;

public class TestActivity extends Activity {

    private SimpleDraweeView mSimpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.image);
        mSimpleDraweeView.setImageURI("asset:///menu/speech.png");
    }
}
