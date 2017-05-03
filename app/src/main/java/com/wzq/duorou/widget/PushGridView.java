package com.wzq.duorou.widget;



import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by weizhenqing on 2017/1/22.
 */
public class PushGridView extends GridView {

    public PushGridView(Context context) {
        this(context,null);
    }

    public PushGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PushGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
