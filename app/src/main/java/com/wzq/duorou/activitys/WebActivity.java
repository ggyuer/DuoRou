package com.wzq.duorou.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.wzq.duorou.R;
import com.wzq.duorou.base.MyBaseActivity;
import com.wzq.duorou.fragments.WebFragment;

public class WebActivity extends MyBaseActivity {

    private FragmentTransaction fragmentTransaction;
    private WebFragment fragment;

    public static Intent getInstance(Activity activity) {
        return new Intent(activity, WebActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        titleName.setText(getIntent().getStringExtra("title"));
        leftImg.setVisibility(View.VISIBLE);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = new WebFragment();
        fragment.setUrl(getUrl());
        fragmentTransaction.add(R.id.content, fragment).commit();
    }

    public String getUrl() {
        return getIntent().getStringExtra("url");
    }
}
