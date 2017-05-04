package com.wzq.duorou.ease.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.wzq.duorou.R;

public abstract class EaseBaseFragment extends Fragment{

    protected EaseTitleBar titleBar;
    protected InputMethodManager inputMethodManager;
    protected ImageView title_left;
    protected ImageView title_right;
    protected TextView title_name;
    protected ImageView disturb;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        titleBar = (EaseTitleBar) getView().findViewById(R.id.title_bar);
        title_left = (ImageView)getView().findViewById(R.id.title_left);
        title_right = (ImageView)getView().findViewById(R.id.title_right);
        disturb = (ImageView)getView().findViewById(R.id.disturb);
        title_name = (TextView)getView().findViewById(R.id.title_name);
        initView();
        setUpView();
    }
    
    public void showTitleBar(){
        if(titleBar != null){
            titleBar.setVisibility(View.VISIBLE);
        }
    }
    
    public void hideTitleBar(){
        if(titleBar != null){
            titleBar.setVisibility(View.GONE);
        }
    }
    
    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    
    protected abstract void initView();
    
    protected abstract void setUpView();


}
