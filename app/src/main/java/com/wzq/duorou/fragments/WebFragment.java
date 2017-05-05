package com.wzq.duorou.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wzq.duorou.R;
import com.wzq.duorou.chat.view.activity.ForwardMessageActivity;
import com.wzq.duorou.jsbridge.BridgeHandler;
import com.wzq.duorou.jsbridge.BridgeWebView;
import com.wzq.duorou.jsbridge.CallBackFunction;
import com.wzq.duorou.jsbridge.DefaultHandler;
import com.wzq.duorou.share.ShareUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    private BridgeWebView webView;
    private String url;
    private ShareUtils shareUtils;

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
        shareUtils = new ShareUtils(getActivity());
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
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.loadUrl(getUrl());
        webView.registerHandler("share", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Gson gson = new Gson();
                Type type = gson.fromJson(data,Type.class);
                if (type.getType()==1){
//                    Intent intent = ShareMenuActivity.getInstance(getActivity());
//                    startActivity(intent);
                    shareUtils.shareOpen();
                }
            }
        });

        webView.registerHandler("like", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"+1",Toast.LENGTH_LONG).show();
            }
        });

        webView.registerHandler("forward", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(getActivity(),ForwardMessageActivity.class);
                intent.putExtra("type","forward");
                startActivity(intent);
            }
        });

        webView.registerHandler("center", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"待开发！",Toast.LENGTH_LONG).show();
            }
        });

        webView.registerHandler("first", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(getActivity(),"待开发！",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        //UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        shareUtils.result(requestCode,requestCode,data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        shareUtils.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //   UMShareAPI.get(this).release();
    }

    class Type{
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
