package com.wzq.duorou.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.wzq.duorou.R;
import com.wzq.duorou.jsbridge.BridgeWebView;
import com.wzq.duorou.jsbridge.DefaultHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    private BridgeWebView webView;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        webView = (BridgeWebView) view.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        webView.setDefaultHandler(new DefaultHandler());
        webView.loadUrl(getUrl());
    }

}
