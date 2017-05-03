package com.wzq.duorou.widget;

/**
 * Created by weizhenqing on 2017/4/2.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wzq.duorou.R;


public class PullUpLoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private boolean isLoading  = false;

    private View mFooterView;

    private int mFooterHeight;

    private OnLoadMoreListener mListener;
    private LayoutInflater inflater;

    public PullUpLoadMoreListView(Context context) {
        this(context, null);
    }

    public PullUpLoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        setLoadState(loading);
    }

    public PullUpLoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initFooterView();
        setOnScrollListener(this);
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        mFooterView = inflater.inflate(R.layout.refresh_load_more, null);
        mFooterView.measure(0, 0);
        mFooterHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterHeight, 0, 0);
        this.addFooterView(mFooterView);
    }

    @Override public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
        if(this.getLastVisiblePosition() == this.getAdapter().getCount() - 1
                && !isLoading && (scrollstate == SCROLL_STATE_FLING || scrollstate == SCROLL_STATE_IDLE)){

            if(this.mListener != null){
                this.mListener.loadMore();
                setLoadState(true);
            }
        }
    }

    /**
     * 设置状态
     * @param b
     */
    public void setLoadState(boolean b) {
        this.isLoading = b;
        if(isLoading){
            mFooterView.setPadding(0,0,0,0);
            this.setSelection(this.getAdapter().getCount() + 1);
        }else {
            mFooterView.setPadding(0,-mFooterHeight,0,0);
        }
    }

    @Override public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mListener = listener;
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }
}